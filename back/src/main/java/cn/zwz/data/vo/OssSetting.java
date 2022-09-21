package cn.zwz.data.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 郑为中
 */
@Data
public class OssSetting implements Serializable{

    @ApiModelProperty(value = "服务商")
    private String serviceName;

    @ApiModelProperty(value = "域名")
    private String endpoint;

    @ApiModelProperty(value = "http")
    private String http;

    @ApiModelProperty(value = "本地存储路径")
    private String filePath;
}
