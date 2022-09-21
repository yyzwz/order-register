package cn.zwz.basics.code.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 郑为中
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entity {

    @ApiModelProperty(value = "控制器层包")
    private String controllerPackage;

    @ApiModelProperty(value = "服务层实现包")
    private String serviceImplPackage;

    @ApiModelProperty(value = "服务层接口包")
    private String servicePackage;

    @ApiModelProperty(value = "数据链路层包")
    private String daoPackage;

    @ApiModelProperty(value = "实体VO包")
    private String entityPackage;

    @ApiModelProperty(value = "类名")
    private String className;

    @ApiModelProperty(value = "类名小写")
    private String classNameLowerCase;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "主键数据类型")
    private String primaryKeyType;

    @ApiModelProperty(value = "是否树状")
    private Boolean isTree;

    @ApiModelProperty(value = "是否流程")
    private Boolean activiti;
}
