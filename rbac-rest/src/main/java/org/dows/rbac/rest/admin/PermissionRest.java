package org.dows.rbac.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.framework.api.annotation.Namespace;
import org.dows.rbac.api.admin.request.SaveRbacPermissionMenuRequest;
import org.dows.rbac.api.admin.request.SaveRbacPermissionModuleRequest;
import org.dows.rbac.api.admin.request.SaveRbacPermissionRequest;
import org.dows.rbac.api.admin.response.RbacMenusResponse;
import org.dows.rbac.api.admin.response.RbacPermissionResponse;
import org.dows.rbac.api.admin.response.RbacRoleResponse;
import org.dows.rbac.api.admin.response.RbacUriResponse;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.biz.admin.PermissionBiz;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限管理
 * @date 2024年2月27日 上午11:52:56
 */
@Namespace(module = "rbac", name = "应用授权", code = "rbac.permission", path = "")
@RequiredArgsConstructor
@RestController
@Tag(name = "权限管理", description = "权限管理")
public class PermissionRest {
    private final PermissionBiz permissionBiz;

    /**
     * 保存权限
     *
     * @param
     * @return
     */
    @Operation(summary = "保存权限")
    @PostMapping("v1/admin/permission/save")
    public void save(@RequestBody @Validated List<SaveRbacPermissionRequest> saveRbacPermission) {
/*        RbacEventRequest<SaveRbacPermissionRequest> rbacEventRequest = new RbacEventRequest<>();
        rbacEventRequest.setParams(saveRbacPermission);*/
        permissionBiz.save(saveRbacPermission);
    }

    @Operation(summary = "角色通过菜单来添加权限")
    @PostMapping("v1/admin/permission/savePermissionMenu")
    public void savePermissionMenu(@RequestBody @Validated SaveRbacPermissionMenuRequest saveRbacPermissionMenuRequest) {
        permissionBiz.savePermissionMenu(saveRbacPermissionMenuRequest);
    }


    @Operation(summary = "角色通过权限模块添加权限")
    @PostMapping("v1/admin/permission/savePermissionModule")
    public void savePermissionModule(@RequestBody @Validated SaveRbacPermissionModuleRequest saveRbacPermissionModuleRequest) {
        permissionBiz.savePermissionModule(saveRbacPermissionModuleRequest);
    }

    /**
     * 根据角色id查询应用的接口集
     *
     * @param
     * @return
     */
    @Operation(summary = "根据角色id查询应用的接口集")
    @GetMapping("v1/admin/permission/listUrisByRoleId")
    public List<RbacUriResponse> listUrisByRoleId(@Validated Long rbacRoleId) {
        return permissionBiz.listUrisByRoleId(rbacRoleId);
    }

    /**
     * 根据角色id查询应用的菜单集
     *
     * @param
     * @return
     */
    @Operation(summary = "根据角色id查询应用的菜单集")
    @GetMapping("v1/admin/permission/listMenusByRoleId")
    public List<RbacMenusResponse> listMenusByRoleId(@Validated Long rbacRoleId) {
        return permissionBiz.listMenusByRoleId(rbacRoleId);
    }

    /**
     * 根据角色查询权限
     *
     * @param
     * @return
     */
    @Operation(summary = "根据角色查询权限")
    @GetMapping("v1/admin/permission/listPermissionByRoleId")
    public List<RbacPermissionResponse> listPermissionByRoleId(@Validated List<Long> rbacRoleIds) {
        return permissionBiz.listPermissionByRoleIds(rbacRoleIds);
    }

    /**
     * 查询权限树
     *
     * @param
     * @return
     */
    @Operation(summary = "查询权限树")
    @GetMapping("v1/admin/permission/listMenusTree")
    public List<RbacMenusResponse> listMenusTree() {
        return permissionBiz.listMenusTree();
    }

//    @Operation(summary = "查询角色菜单树")
//    @GetMapping("v1/admin/permission/listRoleMenusTree")
//    public List<RbacMenusResponse> listRoleMenusTree(@RequestParam List<Long> roleIds) {
//        return permissionBiz.listRoleMenusTree(roleIds);
//    }

    @Operation(summary = "查询账号菜单树")
    @GetMapping("v1/admin/permission/listAccountMenusTree")
    public List<RbacMenusResponse> listAccountMenusTree() {
        return permissionBiz.listAccountMenusTree();
    }

    /**
     * 根据菜单查询接口集
     *
     * @param
     * @return
     */
    @Operation(summary = "根据菜单查询接口集")
    @GetMapping("v1/admin/permission/listUrisByMenuId")
    public List<RbacUriResponse> listUrisByMenuId(@Validated Long rbacMenuId) {
        return permissionBiz.listUrisByMenuId(rbacMenuId);
    }







}