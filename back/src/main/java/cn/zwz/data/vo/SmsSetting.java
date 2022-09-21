package cn.zwz.data.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 郑为中
 */
@Data
public class SmsSetting implements Serializable{

    @ApiModelProperty(value = "短信配置名称")
    private String serviceName;

    @ApiModelProperty(value = "键KEY")
    private String secretKey;

    @ApiModelProperty(value = "钥匙AK")
    private String accessKey;

    @ApiModelProperty(value = "场景")
    private Integer type;

    @ApiModelProperty(value = "签名内容")
    private String signName;

    @ApiModelProperty(value = "是否改变secrectKey")
    private Boolean changed;

    @ApiModelProperty(value = "模版code")
    private String templateCode;
}
