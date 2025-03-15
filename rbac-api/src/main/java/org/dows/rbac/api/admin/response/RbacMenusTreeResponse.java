package org.dows.rbac.api.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description
 * @date
 */
@Data
@NoArgsConstructor
@Schema(name = "RbacMenusTree 对象", title = "菜单树查询返回")
public class RbacMenusTreeResponse {
    @Schema(title = "菜单ID")
    private Long rbacMenuId;

    @Schema(title = "菜单父ID")
    private Long pid;

    @Schema(title = "菜单名称")
    private String name;

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

    @Schema(title = "json数据集")
    private String jsonConfig;

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


}
