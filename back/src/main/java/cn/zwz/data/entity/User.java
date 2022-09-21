package cn.zwz.data.entity;

import cn.zwz.basics.baseClass.ZwzBaseEntity;
import cn.zwz.basics.parameter.CommonConstant;
import cn.zwz.data.vo.PermissionDTO;
import cn.zwz.data.vo.RoleDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author 郑为中
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_user")
@TableName("a_user")
@ApiModel(value = "用户")
public class User extends ZwzBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    @NotNull(message = "姓名不能为空")
    @Size(max = 20, message = "姓名长度不能超过20")
    private String nickname;

    @ApiModelProperty(value = "账号")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]{4,16}$", message = "账号长度不合法")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "密码强度")
    @Column(length = 2)
    private String passStrength;

    @ApiModelProperty(value = "手机号")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式错误")
    private String mobile;

    @ApiModelProperty(value = "部门ID")
    private String departmentId;

    @ApiModelProperty(value = "部门")
    private String departmentTitle;

    @ApiModelProperty(value = "邮箱")
    @Pattern(regexp = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "邮箱格式错误")
    private String email;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "区县")
    private String address;

    @ApiModelProperty(value = "用户类型")
    private Integer type;

    @ApiModelProperty(value = "个人门户")
    private String myDoor;

    @ApiModelProperty(value = "启用状态")
    private Integer status = CommonConstant.USER_STATUS_NORMAL;

    @ApiModelProperty(value = "头像")
    private String avatar = CommonConstant.USER_DEFAULT_AVATAR;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "是否默认角色")
    private Integer defaultRole;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有的菜单列表")
    private List<PermissionDTO> permissions;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有的角色列表")
    private List<RoleDTO> roles;
}
