package org.dows.rbac.api.admin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "RbacUriRoleResponse 对象", title = "接口角色列表")
public class RbacUriRoleResponse {

    @Schema(name = "roleId", title = "角色ID")
    private Long roleId;

    @Schema(name = "rbacUriId", title = "资源id")
    private Long rbacUriId;

    @Schema(name = "rbacMenuId", title = "菜单ID")
    private Long rbacMenuId;

    @Schema(name = "name", title = "资源名称")
    private String name;

    @Schema(name = "code", title = "资源CODE")
    private String code;

    @Schema(name = "url", title = "资源链接")
    private String url;

    @Schema(name = "appId", title = "应用id")
    private String appId;
}
