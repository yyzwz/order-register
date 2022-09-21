package cn.zwz.basics.baseVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * HTTP回显VO类
 * @author 郑为中
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回主数据",notes = "泛型")
    private T result;

    @ApiModelProperty(value = "是否请求成功",notes = "true为请求成功,false为请求失败")
    private boolean success;

    @ApiModelProperty(value = "返回状态代码",notes = "默认200为成功")
    private Integer code;

    @ApiModelProperty(value = "时间戳",notes = "当前系统的时间戳")
    private long timestamp = System.currentTimeMillis();

    @ApiModelProperty(value = "提示信息",notes = "额外的提示信息")
    private String message;
}
