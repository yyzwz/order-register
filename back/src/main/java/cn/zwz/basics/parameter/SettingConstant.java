package cn.zwz.basics.parameter;

/**
 * @author 郑为中
 */
public interface SettingConstant {

    /**
     * 当前使用OSS
     */
    String OSS_USED = "OSS_USED";

    /**
     * 七牛OSS配置
     */
    String QINIU_OSS = "QINIU_OSS";

    /**
     * 阿里OSS配置
     */
    String ALI_OSS = "ALI_OSS";

    /**
     * 腾讯COS配置
     */
    String TENCENT_OSS = "TENCENT_OSS";

    /**
     * 本地OSS配置
     */
    String LOCAL_OSS = "LOCAL_OSS";

    /**
     * Minio配置
     */
    String MINIO_OSS = "MINIO_OSS";

    /**
     * 当前使用短信
     */
    String SMS_USED = "SMS_USED";

    /**
     * 阿里短信配置
     */
    String ALI_SMS = "ALI_SMS";

    /**
     * 阿里短信 通用
     */
    String ALI_SMS_COMMON = "ALI_SMS_COMMON";

    /**
     * 阿里短信 登录验证码
     */
    String ALI_SMS_LOGIN = "ALI_SMS_LOGIN";

    /**
     * 阿里短信 注册验证码
     */
    String ALI_SMS_REGIST = "ALI_SMS_REGIST";

    /**
     * 阿里短信 修改手机
     */
    String ALI_SMS_CHANGE_MOBILE = "ALI_SMS_CHANGE_MOBILE";

    /**
     * 阿里短信 修改密码
     */
    String ALI_SMS_CHANG_PASS = "ALI_SMS_CHANG_PASS";

    /**
     * 阿里短信 重置密码
     */
    String ALI_SMS_RESET_PASS = "ALI_SMS_RESET_PASS";

    /**
     * 阿里短信 工作流消息
     */
    String ALI_SMS_ACTIVITI = "ALI_SMS_ACTIVITI";

    /**
     * 邮箱配置
     */
    String EMAIL_SETTING = "EMAIL_SETTING";

    /**
     * vapthca验证码配置
     */
    String VAPTCHA_SETTING = "VAPTCHA_SETTING";

    /**
     * 其他配置
     */
    String OTHER_SETTING = "OTHER_SETTING";

    /**
     * 公告配置
     */
    String NOTICE_SETTING = "NOTICE_SETTING";

    /**
     * VAPTCHA验证链接
     */
    String VAPTCHA_URL = "http://0.vaptcha.com/verify";
}
