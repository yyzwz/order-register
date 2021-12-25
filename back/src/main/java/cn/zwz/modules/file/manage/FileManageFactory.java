package cn.zwz.modules.file.manage;

import cn.zwz.common.constant.CommonConstant;
import cn.zwz.common.constant.SettingConstant;
import cn.zwz.common.exception.ZwzException;
import cn.zwz.modules.base.entity.Setting;
import cn.zwz.modules.base.service.SettingService;
import cn.zwz.modules.file.manage.impl.*;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工厂模式
 * @author 郑为中
 */
@Component
public class FileManageFactory {

    @Autowired
    private SettingService settingService;

    @Autowired
    private LocalFileManage localFileManage;

    /**
     * 使用配置的服务上传时location传入null 管理文件时需传入存储位置location
     * @param location
     * @return
     */
    public FileManage getFileManage(Integer location){

        Setting setting = settingService.get(SettingConstant.OSS_USED);
        if(setting==null|| StrUtil.isBlank(setting.getValue())){
            throw new ZwzException("您还未配置OSS存储服务");
        }
        String type = setting.getValue();
        if((type.equals(SettingConstant.LOCAL_OSS)&&location==null)||CommonConstant.OSS_LOCAL.equals(location)){
            return localFileManage;
        }else{
            throw new ZwzException("暂不支持该存储配置，请检查配置");
        }
    }
}
