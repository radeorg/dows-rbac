package org.dows.rbac.api.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "角色模块关系添加对象", title = "角色模块关系添加信息")
public class SaveRbacPermissionModuleRequest {

    @Schema(title = "角色ID")
    private Long roleId;

    @Schema(title = "模块ID")
    private List<Long> moduleId;
}
