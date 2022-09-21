package cn.zwz.basics.parameter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * URL验证
 * @author 郑为中
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "intercept")
public class CaptchaProperties {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "需要图片验证码验证的接口")
    private List<String> verification = new ArrayList<>();

    @ApiModelProperty(value = "需要企微验证码验证的接口")
    private List<String> wechat = new ArrayList<>();
}
