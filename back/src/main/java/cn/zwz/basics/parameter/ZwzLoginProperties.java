package cn.zwz.basics.parameter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @author 郑为中
 */
@Data
@Configuration
public class ZwzLoginProperties {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否单点登录")
    private Boolean ssoFlag = true;

    @ApiModelProperty(value = "是否保存权限")
    private Boolean saveRoleFlag = true;

    @ApiModelProperty(value = "用户Token过期天数", notes = "保存Token的时间,Token没了也不能自动登录")
    private Integer userTokenInvalidDays = 30;

    @ApiModelProperty(value = "用户保存登陆天数", notes = "选择了自动登录,记录保存的时间")
    private Integer userSaveLoginTokenDays = 7;

    @ApiModelProperty(value = "最大登陆失败次数")
    private Integer maxLoginFailTimes = 10;

    @ApiModelProperty(value = "登陆失败超限后锁定分钟")
    private Integer loginFailMaxThenLockTimes = 10;

    @ApiModelProperty(value = "全局限流")
    private Boolean allLimiting = false;

    @ApiModelProperty(value = "全局限流个数")
    private Integer allLimitingSize = 100;

    @ApiModelProperty(value = "全局限流单位时长")
    private Long allLimitingTime = 1000L;

    @ApiModelProperty(value = "单IP限流")
    private Boolean oneLimiting = false;

    @ApiModelProperty(value = "单IP限流个数")
    private Integer oneLimitingSize = 100;

    @ApiModelProperty(value = "单IP限流单位时长")
    private Long oneLimitingTime = 1000L;

    public static final String HTTP_HEADER = "accessToken";

    public static final String SAVE_LOGIN_PRE = "saveLogin";

    public static final String HTTP_TOKEN_PRE = "ZWZ_TOKEN_PRE:";

    public static final String USER_TOKEN_PRE = "ZWZ_USER_TOKEN:";
}
