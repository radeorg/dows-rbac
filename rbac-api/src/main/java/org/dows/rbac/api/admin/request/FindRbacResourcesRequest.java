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
@Schema(name = "FindRbacResources 对象", title = "权限模块资源信息")
public class FindRbacResourcesRequest {

    @Schema(name = "rbacResourcesId", title = "模块资源ID")
    private Long rbacResourcesId;

    @Schema(name = "rbacModuleId", title = "权限模块ID")
    private Long rbacModuleId;

    @Schema(name = "resourceId", title = "资源ID")
    private Long resourceId;

    @Schema(name = "name", title = "资源名称")
    private String name;

    @Schema(name = "code", title = "资源CODE")
    private String code;

    @Schema(name = "appId", title = "应用id")
    private String appId;

    @Schema(name = "resourceType", title = "资源类型[0:接口，1:菜单]")
    private Integer resourceType;

    @Schema(name = "state", title = "状态")
    private Integer state;

}
