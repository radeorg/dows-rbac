package org.dows.rbac.open;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.rbac.api.RbacPermissionApi;
import org.dows.rbac.biz.RbacPermissionBiz;
import org.dows.rbac.request.ConfigPermissionRequest;
import org.dows.rbac.response.ConfigPermissionResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "RbacPermissionRest", description = "权限配置")
@RequiredArgsConstructor
public class RbacPermissionRest implements RbacPermissionApi {

    private final RbacPermissionBiz rbacPermissionBiz;

    @Operation(summary = "权限配置")
    @PostMapping("/v1/rbac/permission/config")
    @Override
    public List<ConfigPermissionResponse> configPermission(@RequestBody List<ConfigPermissionRequest> configPermissionRequests) {
        return rbacPermissionBiz.config(configPermissionRequests);
    }
}
