package org.dows.rbac.api.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dows.rbac.api.RbacResources;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @description
 * @date
 */
@Data
@NoArgsConstructor
@Schema(name = "SaveRbacPermission 对象", title = "权限创建，如果permissionTyp为0，uriId不能为空，为1menuId不能为空")
public class SaveRbacPermissionRequest {
    @Schema(title = "权限ID")
    private Long rbacPermissionId;

    @Schema(title = "角色id")
    private Long rbacRoleId;

    @Schema(title = "父角色ID(继承时该字段有值)")
    private Long rolePid;

    @Schema(title = "角色CODE")
    private String roleCode;

    @Schema(title = "角色名字")
    private String roleName;

    @Schema(title = "应用id 从角色冗余")
    private String appId;

    @Schema(title = "描述")
    private String descr;

    @Schema(title = "资源集合[menu,uri,rule]")
    List<RbacResources> rbacResource;


    @Schema(title = "资源ID")
    private Long resourceId;

    @NotNull(message = "权限码[authority]权限码不能为空")
    @Schema(title = "权限码")
    private String authority;

    @Schema(title = "资源类型[0:接口，1:菜单]")
    private Integer resourceType;

    @Schema(title = "状态")
    private Integer state;


}
