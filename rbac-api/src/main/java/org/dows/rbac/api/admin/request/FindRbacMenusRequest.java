package org.dows.rbac.api.admin.request;

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
@Schema(name = "FindRbacMenus 对象", title = "菜单查询信息")
public class FindRbacMenusRequest extends BaseFindRequest {
    @Schema(title = "菜单ID")
    private Long rbacMenuId;

    @Schema(title = "菜单父ID")
    private Long pid;

    @Schema(title = "菜单名称")
    private String name;

    @Schema(title = "菜单CODE")
    private String code;

    @Schema(title = "应用id")
    private String appId;

    @Schema(title = "是否隐藏")
    private Integer visible;

    @Schema(title = "是否框架")
    private Integer isframe;

    @Schema(title = "状态")
    private Integer state;

}
