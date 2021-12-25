package cn.zwz.modules.file.manage.impl;

import cn.zwz.common.constant.SettingConstant;
import cn.zwz.common.exception.ZwzException;
import cn.zwz.modules.base.entity.Setting;
import cn.zwz.modules.base.service.SettingService;
import cn.zwz.modules.base.vo.OssSetting;
import cn.zwz.modules.file.manage.FileManage;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author 郑为中
 */
@Slf4j
@Component
public class LocalFileManage implements FileManage {

    @Autowired
    private SettingService settingService;

    @Override
    public OssSetting getOssSetting() {

        Setting setting = settingService.get(SettingConstant.LOCAL_OSS);
        if(setting==null|| StrUtil.isBlank(setting.getValue())){
            throw new ZwzException("您还未配置本地存储");
        }
        return new Gson().fromJson(setting.getValue(), OssSetting.class);
    }

    @Override
    @Deprecated
    public String pathUpload(String filePath, String key) {

        throw new ZwzException("暂不支持");
    }

    /**
     * 实则为路径上传
     * @param inputStream
     * @param key
     * @param file
     * @return
     */
    @Override
    public String inputStreamUpload(InputStream inputStream, String key, MultipartFile file) {

        OssSetting os = getOssSetting();
        String day = DateUtil.format(DateUtil.date(), "yyyyMMdd");
        String path = os.getFilePath() + "/" + day;
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File f = new File(path + "/" + key);
        if(f.exists()){
            throw new ZwzException("文件名已存在");
        }
        try {
            file.transferTo(f);
            return path + "/" + key;
        } catch (IOException e) {
            log.error(e.toString());
            throw new ZwzException("上传文件出错");
        }
    }

    /**
     * 注意此处需传入url
     * @param url
     * @param toKey
     * @return
     */
    @Override
    public String renameFile(String url, String toKey) {

        File old = new File(url);
        FileUtil.rename(old, toKey, false, true);
        return old.getParentFile() + "/" + toKey;
    }

    /**
     * 注意此处需传入url
     * @param url
     * @param toKey
     * @return
     */
    @Override
    public String copyFile(String url, String toKey) {

        File file = new File(url);
        String newUrl = file.getParentFile() + "/" + toKey;
        FileUtil.copy(file, new File(newUrl), true);
        return newUrl;
    }

    /**
     * 注意此处需传入url
     * @param url
     */
    @Override
    public void deleteFile(String url) {

        FileUtil.del(new File(url));
    }

    /**
     * 读取文件
     * @param url
     * @param response
     */
    public static void view(String url, HttpServletResponse response){

        File file = new File(url);
        if (!file.exists()) {
            throw new ZwzException("文件不存在");
        }

        try (FileInputStream is = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(is)) {

            OutputStream out = response.getOutputStream();
            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = bis.read(buf)) > 0) {
                out.write(buf, 0, bytesRead);
            }
        } catch (IOException e) {
            log.warn(e.toString());
            throw new ZwzException("读取下载文件出错");
        }
    }
}
