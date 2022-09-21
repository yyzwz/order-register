package cn.zwz.basics.baseVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 验证字段VO类
 * @author 郑为中
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldValidate implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "请求路径")
    private String validateUrl;

    @ApiModelProperty(value = "操作标识")
    private String operation;

    @ApiModelProperty(value = "完整请求路径")
    private String fullUrl;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "登陆邮箱")
    private String email;
}
