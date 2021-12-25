package cn.zwz.modules.base.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 郑为中
 */
@Data
public class SmsSetting implements Serializable{

    @ApiModelProperty(value = "服务商")
    private String serviceName;

    @ApiModelProperty(value = "ak")
    private String accessKey;

    @ApiModelProperty(value = "sk")
    private String secretKey;

    @ApiModelProperty(value = "签名")
    private String signName;

    @ApiModelProperty(value = "bucket空间")
    private String bucket;

    @ApiModelProperty(value = "使用场景类型")
    private Integer type;

    @ApiModelProperty(value = "模版code")
    private String templateCode;

    @ApiModelProperty(value = "是否改变secrectKey")
    private Boolean changed;
}
