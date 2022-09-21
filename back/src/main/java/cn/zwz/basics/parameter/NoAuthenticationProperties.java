package cn.zwz.basics.parameter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑为中
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "release")
public class NoAuthenticationProperties {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "不鉴权的URL")
    private List<String> authentication = new ArrayList<>();

    @ApiModelProperty(value = "不限流的URL")
    private List<String> limiting = new ArrayList<>();
}
