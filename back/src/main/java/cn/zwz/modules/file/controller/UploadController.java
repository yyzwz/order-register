package cn.zwz.modules.file.controller;

import cn.zwz.common.constant.CommonConstant;
import cn.zwz.common.constant.SettingConstant;
import cn.zwz.common.limit.RedisRaterLimiter;
import cn.zwz.common.utils.*;
import cn.zwz.modules.base.entity.Setting;
import cn.zwz.modules.base.service.SettingService;
import cn.zwz.modules.base.vo.OssSetting;
import cn.zwz.common.vo.Result;
import cn.zwz.modules.file.entity.File;
import cn.zwz.modules.file.manage.FileManageFactory;
import cn.zwz.modules.file.service.FileService;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @author 郑为中
 */
@RestController
@Api(tags = "文件上传")
@RequestMapping("/zwz/upload")
@Transactional
public class UploadController {

    @Autowired
    private FileManageFactory fileManageFactory;

    @Autowired
    private SettingService settingService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传")
    public Result<Object> upload(@RequestParam(required = false) MultipartFile file,@RequestParam(required = false) String base64,HttpServletRequest request) {
        Setting setting = settingService.get(SettingConstant.OSS_USED);
        if(setting==null|| StrUtil.isBlank(setting.getValue())){
            return ResultUtil.error(501, "未配置系统文件存储位置");
        }
        if(StrUtil.isNotBlank(base64)){
            file = Base64DecodeMultipartFile.base64Convert(base64);
        }
        String result = "";
        String fKey = CommonUtil.renamePic(file.getOriginalFilename());
        File f = new File();
        try {
            InputStream inputStream = file.getInputStream();
            result = fileManageFactory.getFileManage(null).inputStreamUpload(inputStream, fKey, file);
            f.setLocation(getType(setting.getValue()));
            f.setName(file.getOriginalFilename());
            f.setSize(file.getSize());
            f.setType(file.getContentType());
            f.setFKey(fKey);
            f.setUrl(result);
            fileService.save(f);
        } catch (Exception e) {
            return ResultUtil.error(e.toString());
        }
        if(setting.getValue().equals(SettingConstant.LOCAL_OSS)){
            OssSetting os = new Gson().fromJson(settingService.get(SettingConstant.LOCAL_OSS).getValue(), OssSetting.class);
            result = os.getHttp() + os.getEndpoint() + "/" + f.getId();
        }
        return ResultUtil.data(result);
    }

    public Integer getType(String type){
        switch (type){
            case SettingConstant.QINIU_OSS:
                return CommonConstant.OSS_QINIU;
            case SettingConstant.ALI_OSS:
                return CommonConstant.OSS_ALI;
            case SettingConstant.TENCENT_OSS:
                return CommonConstant.OSS_TENCENT;
            case SettingConstant.MINIO_OSS:
                return CommonConstant.OSS_MINIO;
            case SettingConstant.LOCAL_OSS:
                return CommonConstant.OSS_LOCAL;
            default:
                return -1;
        }
    }
}
