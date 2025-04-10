package org.dows.rbac.open;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.rbac.api.InitResources;
import org.dows.rbac.model.InitUriResources;
import org.dows.rbac.api.RbacApi;
import org.dows.rbac.model.RoleResourceResponse;
import org.dows.rbac.request.FindRbacResourcesRequest;
import org.dows.rbac.request.SaveRbacRoleRequest;
import org.dows.rbac.biz.RbacApiBiz;
import org.dows.rbac.response.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/open/rbac")
@Tag(name = "Rbac管理接口", description = "Rbac管理接口")
@RequiredArgsConstructor
public class RbacApiRest implements RbacApi {
    private final RbacApiBiz rbacApiBiz;

    @GetMapping("/get/RoleInstance")
    @Operation(summary = "通过RoleId获取RbacRole实例")
    public List<RbacRoleResponse> getRoleInstanceByRoleIds(@RequestParam String appId, @RequestParam List<Long> roleIds) {
        return rbacApiBiz.getRoleInstanceByRoleIds(appId, roleIds);
    }

    @GetMapping("/get/AllUris")
    @Operation(summary = "通过AppId获取RbacUri资源")
    public List<RbacUriResponse> getAllUrisByAppId(@RequestParam String appId) {
        return rbacApiBiz.getAllUrisByAppId(appId);
    }

    @GetMapping("/get/RoleUris")
    @Operation(summary = "通过RoleIds获取RbacUri资源")
    public List<RoleResourceResponse> getUrisByRoleIds(@RequestParam String appId, @RequestParam List<Long> roleIds) {
        return rbacApiBiz.getUrisByRoleIds(appId, roleIds);
    }


    @Override
    public String getMenu() {
        return "";
    }

    @Override
    public List<RbacResourcesQueryResponse> getResource(FindRbacResourcesRequest findRbacResources) {
        return List.of();
    }

    @Override
    public List<RbacPermissionResponse> getPermission(List<Long> roleIds) {
        return List.of();
    }

    @Override
    public List<String> getUriCode(List<Long> roleIds) {
        return List.of();
    }

    @Override
    public void saveResource(List<InitResources> resources) {

    }

    @Override
    public void initRoleUri(List<InitResources> resources, String roleCode, String appId) {

    }

    @Override
    public void initAppRole(List<SaveRbacRoleRequest> roleItems) {

    }

    @Override
    public List<RbacMenusResponse> listRoleMenusTree(List<Long> rbacRoleIds) {
        return List.of();
    }

    @Override
    public void saveUri(List<InitUriResources> initUriResources) {

    }

    @Override
    public Map<String, List<RbacUriRoleResponse>> getRoleUri() {
        return Map.of();
    }

}
