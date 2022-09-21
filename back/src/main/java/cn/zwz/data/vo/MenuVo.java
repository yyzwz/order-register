package cn.zwz.data.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 郑为中
 */
@Data
public class MenuVo {

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单ID")
    private String id;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "父菜单ID")
    private String parentId;

    @ApiModelProperty(value = "菜单层级")
    private Integer level;

    @ApiModelProperty(value = "是否强制显示")
    private Boolean showAlways;

    @ApiModelProperty(value = "菜单类型",notes = "1具体操作 -1顶部菜单 0页面")
    private Integer type;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "页面路径")
    private String path;

    @ApiModelProperty(value = "PC端图标")
    private String icon;

    @ApiModelProperty(value = "按钮类型")
    private String buttonType;

    @ApiModelProperty(value = "网页链接")
    private String url;

    @ApiModelProperty(value = "子权限列表")
    private List<String> permTypes;

    @ApiModelProperty(value = "子菜单列表")
    private List<MenuVo> children;
}
