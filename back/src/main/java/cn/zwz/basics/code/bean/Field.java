package cn.zwz.basics.code.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 实体类字段
 * @author 郑为中
 */
@Data
public class Field {

    @ApiModelProperty(value = "字段名称")
    private String name;

    @ApiModelProperty(value = "字段标识")
    private String field;

    @ApiModelProperty(value = "字段层级")
    private String level;

    @ApiModelProperty(value = "是否在表格显示")
    private Boolean tableShow;

    @ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "是否可搜索")
    private Boolean searchable;

    @ApiModelProperty(value = "是否可添加编辑")
    private Boolean editable;

    @ApiModelProperty(value = "字段类型")
    private String type;

    @ApiModelProperty(value = "搜索层级")
    private String searchLevel;

    @ApiModelProperty(value = "搜索栏类型")
    private String searchType;

    @ApiModelProperty(value = "默认排序规则")
    private String defaultSortType;

    @ApiModelProperty(value = "字段")
    private Boolean validate;

    @ApiModelProperty(value = "是否为默认排序")
    private Boolean defaultSort;

    @ApiModelProperty(value = "是否可排序")
    private Boolean sortable;
}
