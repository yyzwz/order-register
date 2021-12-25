package cn.zwz.modules.base.controller;

import cn.zwz.common.constant.SettingConstant;
import cn.zwz.common.utils.CommonUtil;
import cn.zwz.common.utils.ResultUtil;
import cn.zwz.common.vo.*;
import cn.zwz.modules.base.entity.Setting;
import cn.zwz.modules.base.service.SettingService;
import cn.zwz.modules.base.vo.*;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 郑为中
 */
@Slf4j
@RestController
@Api(description = "基本配置接口")
@RequestMapping("/zwz/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @RequestMapping(value = "/seeSecret/{settingName}", method = RequestMethod.GET)
    @ApiOperation(value = "查看私密配置")
    public Result<Object> seeSecret(@PathVariable String settingName) {

        String result = "";
        Setting setting = settingService.get(settingName);
        if(setting==null||StrUtil.isBlank(setting.getValue())){
            return ResultUtil.error("配置不存在");
        }
        if(settingName.equals(SettingConstant.QINIU_OSS)||settingName.equals(SettingConstant.ALI_OSS)
                ||settingName.equals(SettingConstant.TENCENT_OSS)||settingName.equals(SettingConstant.MINIO_OSS)){

            result = new Gson().fromJson(setting.getValue(), OssSetting.class).getSecretKey();
        } else if(settingName.equals(SettingConstant.ALI_SMS)){

            result = new Gson().fromJson(setting.getValue(), SmsSetting.class).getSecretKey();
        } else if(settingName.equals(SettingConstant.VAPTCHA_SETTING)){

            result = new Gson().fromJson(setting.getValue(), VaptchaSetting.class).getSecretKey();
        }
        return ResultUtil.data(result);
    }

    @RequestMapping(value = "/oss/check", method = RequestMethod.GET)
    @ApiOperation(value = "检查OSS配置")
    public Result<Object> osscheck() {

        Setting setting = settingService.get(SettingConstant.OSS_USED);
        if(setting==null||StrUtil.isBlank(setting.getValue())){
            return ResultUtil.error(501, "您还未配置第三方OSS服务");
        }
        return ResultUtil.data(setting.getValue());
    }

    @RequestMapping(value = "/oss/{serviceName}", method = RequestMethod.GET)
    @ApiOperation(value = "查看OSS配置")
    public Result<OssSetting> oss(@PathVariable String serviceName) {

        Setting setting = new Setting();
        if(serviceName.equals(SettingConstant.QINIU_OSS)||serviceName.equals(SettingConstant.ALI_OSS)
                ||serviceName.equals(SettingConstant.TENCENT_OSS)||serviceName.equals(SettingConstant.MINIO_OSS)
                ||serviceName.equals(SettingConstant.LOCAL_OSS)){
            setting = settingService.get(serviceName);
        }
        if(setting==null||StrUtil.isBlank(setting.getValue())){
            return new ResultUtil<OssSetting>().setData(null);
        }
        OssSetting ossSetting = new Gson().fromJson(setting.getValue(), OssSetting.class);
        ossSetting.setSecretKey("**********");
        return new ResultUtil<OssSetting>().setData(ossSetting);
    }

    @RequestMapping(value = "/sms/{serviceName}", method = RequestMethod.GET)
    @ApiOperation(value = "查看短信配置")
    public Result<SmsSetting> sms(@PathVariable String serviceName) {

        Setting setting = new Setting();
        if(serviceName.equals(SettingConstant.ALI_SMS)){
            setting = settingService.get(SettingConstant.ALI_SMS);
        }
        if(setting==null||StrUtil.isBlank(setting.getValue())){
            return new ResultUtil<SmsSetting>().setData(null);
        }
        SmsSetting smsSetting = new Gson().fromJson(setting.getValue(), SmsSetting.class);
        smsSetting.setSecretKey("**********");
        if(smsSetting.getType()!=null){
            Setting code = settingService.get(CommonUtil.getSmsTemplate(smsSetting.getType()));
            smsSetting.setTemplateCode(code.getValue());
        }
        return new ResultUtil<SmsSetting>().setData(smsSetting);
    }

    @RequestMapping(value = "/sms/templateCode/{type}", method = RequestMethod.GET)
    @ApiOperation(value = "查看短信模板配置")
    public Result<String> smsTemplateCode(@PathVariable Integer type) {

        String templateCode = "";
        if(type!=null){
            String template = CommonUtil.getSmsTemplate(type);
            Setting setting = settingService.get(template);
            if(StrUtil.isNotBlank(setting.getValue())){
                templateCode = setting.getValue();
            }
        }
        return new ResultUtil<String>().setData(templateCode);
    }

    @RequestMapping(value = "/vaptcha", method = RequestMethod.GET)
    @ApiOperation(value = "查看vaptcha配置")
    public Result<VaptchaSetting> vaptcha() {

        Setting setting = settingService.get(SettingConstant.VAPTCHA_SETTING);
        if(setting==null||StrUtil.isBlank(setting.getValue())){
            return new ResultUtil<VaptchaSetting>().setData(null);
        }
        VaptchaSetting vaptchaSetting = new Gson().fromJson(setting.getValue(), VaptchaSetting.class);
        vaptchaSetting.setSecretKey("**********");
        return new ResultUtil<VaptchaSetting>().setData(vaptchaSetting);
    }

    @RequestMapping(value = "/other", method = RequestMethod.GET)
    @ApiOperation(value = "查看其他配置")
    public Result<OtherSetting> other() {

        Setting setting = settingService.get(SettingConstant.OTHER_SETTING);
        if(setting==null||StrUtil.isBlank(setting.getValue())){
            return new ResultUtil<OtherSetting>().setData(null);
        }
        OtherSetting otherSetting = new Gson().fromJson(setting.getValue(), OtherSetting.class);
        return new ResultUtil<OtherSetting>().setData(otherSetting);
    }

    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    @ApiOperation(value = "查看公告配置")
    public Result<NoticeSetting> notice() {

        Setting setting = settingService.get(SettingConstant.NOTICE_SETTING);
        if(setting==null||StrUtil.isBlank(setting.getValue())){
            return new ResultUtil<NoticeSetting>().setData(null);
        }
        NoticeSetting noticeSetting = new Gson().fromJson(setting.getValue(), NoticeSetting.class);
        return new ResultUtil<NoticeSetting>().setData(noticeSetting);
    }

    @RequestMapping(value = "/oss/set", method = RequestMethod.POST)
    @ApiOperation(value = "OSS配置")
    public Result<Object> ossSet(OssSetting ossSetting) {

        String name = ossSetting.getServiceName();
        Setting setting = settingService.get(name);
        if(name.equals(SettingConstant.QINIU_OSS)||name.equals(SettingConstant.ALI_OSS)
                ||name.equals(SettingConstant.TENCENT_OSS)||name.equals(SettingConstant.MINIO_OSS)){

            // 判断是否修改secrectKey 保留原secrectKey 避免保存***加密字符
            if(StrUtil.isNotBlank(setting.getValue())&&!ossSetting.getChanged()){
                String secrectKey = new Gson().fromJson(setting.getValue(), OssSetting.class).getSecretKey();
                ossSetting.setSecretKey(secrectKey);
            }
        }
        setting.setValue(new Gson().toJson(ossSetting));
        settingService.saveOrUpdate(setting);
        Setting used = settingService.get(SettingConstant.OSS_USED);
        used.setValue(name);
        settingService.saveOrUpdate(used);
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/sms/set", method = RequestMethod.POST)
    @ApiOperation(value = "短信配置")
    public Result<Object> smsSet(SmsSetting smsSetting) {

        if(smsSetting.getServiceName().equals(SettingConstant.ALI_SMS)){
            // 阿里
            Setting setting = settingService.get(SettingConstant.ALI_SMS);
            if(StrUtil.isNotBlank(setting.getValue())&&!smsSetting.getChanged()){
                String secrectKey = new Gson().fromJson(setting.getValue(), SmsSetting.class).getSecretKey();
                smsSetting.setSecretKey(secrectKey);
            }
            if(smsSetting.getType()!=null){
                Setting codeSetting = settingService.get(CommonUtil.getSmsTemplate(smsSetting.getType()));
                codeSetting.setValue(smsSetting.getTemplateCode());
                settingService.saveOrUpdate(codeSetting);
            }
            smsSetting.setType(null);
            smsSetting.setTemplateCode(null);
            setting.setValue(new Gson().toJson(smsSetting));
            settingService.saveOrUpdate(setting);

            Setting used = settingService.get(SettingConstant.SMS_USED);
            used.setValue(SettingConstant.ALI_SMS);
            settingService.saveOrUpdate(used);
        }
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/vaptcha/set", method = RequestMethod.POST)
    @ApiOperation(value = "vaptcha配置")
    public Result<Object> vaptchaSet(VaptchaSetting vaptchaSetting) {

        Setting setting = settingService.get(SettingConstant.VAPTCHA_SETTING);
        if(StrUtil.isNotBlank(setting.getValue())&&!vaptchaSetting.getChanged()){
            String key = new Gson().fromJson(setting.getValue(), VaptchaSetting.class).getSecretKey();
            vaptchaSetting.setSecretKey(key);
        }
        setting.setValue(new Gson().toJson(vaptchaSetting));
        settingService.saveOrUpdate(setting);
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/other/set", method = RequestMethod.POST)
    @ApiOperation(value = "其他配置")
    public Result<Object> otherSet(OtherSetting otherSetting) {

        Setting setting = settingService.get(SettingConstant.OTHER_SETTING);
        setting.setValue(new Gson().toJson(otherSetting));
        settingService.saveOrUpdate(setting);
        return ResultUtil.data(null);
    }

    @RequestMapping(value = "/notice/set", method = RequestMethod.POST)
    @ApiOperation(value = "其他配置")
    public Result<Object> noticeSet(NoticeSetting noticeSetting) {

        Setting setting = settingService.get(SettingConstant.NOTICE_SETTING);
        setting.setValue(new Gson().toJson(noticeSetting));
        settingService.saveOrUpdate(setting);
        return ResultUtil.data(null);
    }
}
