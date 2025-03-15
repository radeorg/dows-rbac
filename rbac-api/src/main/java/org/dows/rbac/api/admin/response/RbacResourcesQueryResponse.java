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
@Schema(name = "RbacResourcesQuery 对象", title = "权限模块资源查询返回")
public class RbacResourcesQueryResponse {
    @Schema(title = "模块资源ID")
    private Long rbacResourcesId;

    @Schema(title = "权限模块ID")
    private Long rbacModuleId;

    @Schema(title = "资源ID")
    private Long resourceId;

    @Schema(title = "资源名称")
    private String name;

    @Schema(title = "资源CODE")
    private String code;

    @Schema(title = "应用id")
    private String appId;

    @Schema(name = "methodName", title = "方法名")
    private String methodName;

    @Schema(title = "资源类型[0:接口，1:菜单]")
    private Integer resourceType;

    @Schema(title = "状态")
    private Integer state;


}
