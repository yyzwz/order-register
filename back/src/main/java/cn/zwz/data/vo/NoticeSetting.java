package cn.zwz.data.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 郑为中
 */
@Data
public class NoticeSetting implements Serializable{

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "停留时长")
    private Integer duration;

    @ApiModelProperty(value = "展示页面")
    private String position;

    @ApiModelProperty(value = "公告状态")
    private Boolean open;
}
