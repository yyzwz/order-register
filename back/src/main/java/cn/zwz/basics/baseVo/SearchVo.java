package cn.zwz.basics.baseVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 前端日期特殊处理
 * @author 郑为中
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开始日期", notes = "yyyy-MM-dd")
    private String startDate;

    @ApiModelProperty(value = "结束日期", notes = "yyyy-MM-dd")
    private String endDate;
}
