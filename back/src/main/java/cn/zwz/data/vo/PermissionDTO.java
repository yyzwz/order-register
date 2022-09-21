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
public class PermissionDTO {

    @ApiModelProperty(value = "页面路径")
    private String path;

    @ApiModelProperty(value = "菜单标题")
    private String title;
}
