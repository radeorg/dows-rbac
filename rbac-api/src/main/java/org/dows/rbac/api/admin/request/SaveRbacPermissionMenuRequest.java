package org.dows.rbac.api.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "角色权限关系信息", title = "角色权限关系信息")
public class SaveRbacPermissionMenuRequest {

    @NotNull
    @Schema(description = "角色ID")
    private Long roleId;

    @NotNull
    @Schema(description = "菜单ID")
    private List<Long> menuIds;
}
