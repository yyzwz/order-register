package cn.zwz.data.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 郑为中
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色ID")
    private String id;

    @ApiModelProperty(value = "角色备注")
    private String description;
}
