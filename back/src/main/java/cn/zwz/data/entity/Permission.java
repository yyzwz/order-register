package cn.zwz.data.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
import cn.zwz.basics.parameter.CommonConstant;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 郑为中
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_permission")
@TableName("a_permission")
@ApiModel(value = "菜单权限")
public class Permission extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单层级")
    private Integer level;

    @ApiModelProperty(value = "启用状态")
    private Integer status = 0;

    @ApiModelProperty(value = "菜单按钮类型")
    private Integer type;

    @ApiModelProperty(value = "前端组件名称")
    private String component;

    @ApiModelProperty(value = "页面路径")
    private String path;

    @ApiModelProperty(value = "PC端图标")
    private String icon;

    @ApiModelProperty(value = "父节点ID")
    private String parentId;

    @ApiModelProperty(value = "按钮类型")
    private String buttonType;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "菜单排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "节点展开状态")
    private Boolean expand = true;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "结点选中状态")
    private Boolean selected = false;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "结点勾选状态")
    private Boolean checked = false;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "子菜单列表")
    private List<Permission> children;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "菜单子权限列表")
    private List<String> permTypes;
}