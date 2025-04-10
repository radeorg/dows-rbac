package org.dows.rbac.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dows.rbac.constant.ResourceType;

import java.util.List;


/**
 * bigint/rbacPermissionId/权限ID
 * pk/1/
 * required/1/
 * notnull/1/
 * bigint/rbacRoleId/角色id
 * bigint/rolePid/父角色ID(继承时该字段有值)
 * bigint/resourceId/资源ID
 * varchar/appId/应用id 从角色冗余
 * varchar/description/描述
 * varchar/resourceTable/资源表
 * integer/resourceType/资源类型[0:接口，1:菜单]
 * integer/state/状态
 * integer/ver/乐观锁, 默认: 0
 * tinyint/deleted/逻辑删除  0未删除  1 删除
 * datetime/ts/时间戳
 */
@Data
@NoArgsConstructor
@Schema(name = "ConfigRbacPermissionRequest 配置权限", title = "配置权限")
public class ConfigPermissionRequest {

    @JsonIgnore
    String appId;

    @NotNull
    @Schema(description = "角色ID")
    private Long rbacRoleId;

    @NotNull
    @Schema(description = "资源类型[0:接口，1:菜单]")
    private ResourceType resourceType;

    @NotNull
    @Schema(description = "资源ID集合")
    private List<Long> resourceIds;
}
