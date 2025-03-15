package org.dows.rbac.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.framework.api.annotation.Namespace;
import org.dows.framework.crud.api.model.PageRequest;
import org.dows.framework.crud.api.model.PageResponse;
import org.dows.rbac.api.admin.request.FindRbacRoleRequest;
import org.dows.rbac.api.admin.request.SaveRbacRoleRequest;
import org.dows.rbac.api.admin.response.RbacRoleResponse;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.biz.admin.RoleBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限角色集管理
 * @date 2024年2月27日 上午11:52:56
 */
@Namespace(module = "rbac", name = "应用角色", code = "rbac.role", path = "")
@RequiredArgsConstructor
@RestController
@Tag(name = "权限角色集管理", description = "权限角色集管理")
public class RoleRest {
    private final RoleBiz roleBiz;

    /**
     * 创建或更新角色
     *
     * @param
     * @return
     */
    @Operation(summary = "创建或更新角色")
    @PostMapping("v1/admin/role/save")
    public void save(@RequestBody @Validated List<SaveRbacRoleRequest> saveRbacRole) {
        roleBiz.save(saveRbacRole);
    }

    /**
     * 通过appid查询角色
     *
     * @param
     * @return
     */
    @Operation(summary = "通过appid查询角色")
    @GetMapping("v1/admin/role/listByAppId")
    public List<RbacRoleResponse> listByAppId(@Validated String appId) {
        return roleBiz.listByAppId(appId);
    }

    /**
     * 分页查询
     *
     * @param
     * @return
     */
    @Operation(summary = "分页查询")
    @PostMapping("v1/admin/role/paging")
    public PageResponse<RbacRoleResponse> paging(@RequestBody @Validated PageRequest<FindRbacRoleRequest> findRbacRole) {
        return roleBiz.paging(findRbacRole);
    }

    /**
     * 根据Id删除权 限角色集
     *
     * @param
     * @return
     */
    @Operation(summary = "根据Id删除权 限角色集")
    @DeleteMapping("v1/admin/role/deleteById")
    public void deleteByIds(@RequestParam List<Long> rbacRoleIds) {
        roleBiz.deleteByIds(rbacRoleIds);
    }

    /**
     * 根据id查询
     *
     * @param
     * @return
     */
    @Operation(summary = "根据id查询")
    @GetMapping("v1/admin/role/getById")
    public RbacRoleResponse getById(@RequestParam Long rbacRoleId) {
        return roleBiz.getById(rbacRoleId);
    }

    @Operation(summary = "查询账号角色")
    @GetMapping("v1/admin/permission/getRole")
    public List<RbacRoleResponse> getRoles() {
        return roleBiz.getRolesByAccount();
    }


}