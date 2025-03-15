package org.dows.rbac.api.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description
 * @date
 */
@Data
@NoArgsConstructor
@Schema(name = "SaveRbacMenus 对象", title = "菜单创建")
public class SaveRbacMenusRequest {
    @Schema(title = "菜单ID")
    private Long rbacMenuId;

    @Schema(title = "菜单父ID")
    private Long pid;

    @NotNull(message = "菜单名称[name]菜单名称不能为空")
    @Schema(title = "菜单名称")
    private String name;

    @NotNull(message = "菜单CODE[code]菜单CODE不能为空")
    @Schema(title = "菜单CODE")
    private String code;

    @Schema(title = "id路径")
    private String idPath;

    @Schema(title = "名称路径")
    private String namePath;

    @Schema(title = "菜单路径URI[menuPath]")
    private String codePath;

    @Schema(title = "图标")
    private String icon;

    @Schema(title = "打开类型[0:page,1:api,2:......]")
    private Integer openType;

    @Schema(title = "父级id路径")
    private String preIdPath;

    @Schema(title = "父级名称路径")
    private String preNamePath;

    @Schema(title = "父级菜单路径URI[menuPath]")
    private String preCodePath;

    @Schema(title = "前端路由路径")
    private String path;

    @Schema(name = "configJson", title = "json数据集")
    private String configJson;

    @Schema(title = "应用id")
    private String appId;

    @Schema(title = "描述")
    private String descr;

    @Schema(title = "排序")
    private Integer sorted;

    @Schema(title = "是否隐藏")
    private Integer visible;

    @Schema(title = "是否框架")
    private Integer isframe;

    @Schema(title = "状态")
    private Integer state;

    @Schema(title = "层级")
    private Integer level;


}
