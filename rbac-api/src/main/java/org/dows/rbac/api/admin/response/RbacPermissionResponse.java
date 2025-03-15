package org.dows.rbac.api.admin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Schema(name = "RbacPermissionQuery 对象", title = "权限信息（菜单和接口信息聚合）")
public class RbacPermissionResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacPermissionId", title = "权限ID")
    private Long rbacPermissionId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacRoleId", title = "角色id")
    private Long rbacRoleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rolePid", title = "父角色ID(继承时该字段有值)")
    private Long rolePid;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "resourceId", title = "资源ID")
    private Long resourceId;

    @Schema(name = "roleCode", title = "角色CODE")
    private String roleCode;

    @Schema(name = "roleName", title = "角色名字")
    private String roleName;

    @Schema(name = "authority", title = "资源权限码")
    private String authority;

    @Schema(name = "appId", title = "应用id 从角色冗余")
    private String appId;

    @Schema(name = "descr", title = "描述")
    private String descr;

    @Schema(name = "resourceType", title = "资源类型[0:接口，1:菜单]")
    private Integer resourceType;

    @Schema(name = "state", title = "状态")
    private Integer state;

}
