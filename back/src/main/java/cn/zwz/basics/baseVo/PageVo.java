package cn.zwz.basics.baseVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页VO类
 * @author 郑为中
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "排序名称", notes = "排序的字段名")
    private String sort;

    @ApiModelProperty(value = "页码编号", notes = "即展示第几页")
    private int pageNumber;

    @ApiModelProperty(value = "排序类型", notes = "升序asc,降序desc")
    private String order;

    @ApiModelProperty(value = "每页个数", notes = "建议设置为15")
    private int pageSize;
}
