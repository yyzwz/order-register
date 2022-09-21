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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author 郑为中
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_role")
@TableName("a_role")
@ApiModel(value = "角色")
public class Role extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "数据权限")
    private int dataType;

    @ApiModelProperty(value = "是否默认")
    private Boolean defaultRole;

    @ApiModelProperty(value = "角色备注")
    private String description;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "角色拥有菜单列表")
    private List<RolePermission> permissions;
}
