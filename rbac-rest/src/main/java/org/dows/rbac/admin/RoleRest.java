package org.dows.rbac.admin;

import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.api.RbacRoleApi;
import org.dows.rbac.api.admin.request.SaveRbacRoleRequest;
import org.dows.rbac.api.admin.response.RbacRoleResponse;
import org.dows.rbac.biz.admin.RoleBiz;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限角色集管理
 * @date 2024年2月27日 上午11:52:56
 */
@Slf4j
//@Namespace(module = "rbac", name = "应用角色", code = "rbac.role", path = "")
@RequiredArgsConstructor
@RestController
@Tag(name = "权限角色集管理", description = "权限角色集管理")
public class RoleRest implements RbacRoleApi {
    private final RoleBiz roleBiz;

    /**
     * 创建或更新角色
     *
     * @param
     * @return
     */
    @Operation(summary = "创建或更新角色")
    @PostMapping("v1/rbac/role/save")
    @Override
    public List<RbacRoleResponse> saveOrUpdateRole(@RequestBody @Validated List<SaveRbacRoleRequest> saveRbacRoles) {
        return roleBiz.saveOrUpdateRole(saveRbacRoles);
    }


    /**
     * 创建或更新角色
     *
     * @param
     * @return
     */
    @Operation(summary = "创建或更新角色")
    @PostMapping("v1/admin/role/save")
    public void save(@RequestBody @Validated List<SaveRbacRoleRequest> saveRbacRole) {
        // 返回所有被创建成功的角色
        List<RbacRoleResponse> save = roleBiz.saveOrUpdateRole(saveRbacRole);
        log.debug("创建或更新角色成功:{}", JSONUtil.toJsonStr(save));
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
//    @Operation(summary = "分页查询")
//    @PostMapping("v1/admin/role/paging")
//    public PageResponse<RbacRoleResponse> paging(@RequestBody @Validated PageRequest<FindRbacRoleRequest> findRbacRole) {
//        return roleBiz.paging(findRbacRole);
//    }

    /**
     * 根据Id删除权 限角色集
     *
     * @param
     * @return
     */
//    @Operation(summary = "根据Id删除权 限角色集")
//    @DeleteMapping("v1/admin/role/deleteById")
//    public void deleteByIds(@RequestParam List<Long> rbacRoleIds) {
//        roleBiz.deleteByIds(rbacRoleIds);
//    }

    /**
     * 根据id查询
     *
     * @param
     * @return
     */
//    @Operation(summary = "根据id查询")
//    @GetMapping("v1/admin/role/getById")
//    public RbacRoleResponse getById(@RequestParam Long rbacRoleId) {
//        return roleBiz.getById(rbacRoleId);
//    }

//    @Operation(summary = "查询账号角色")
//    @GetMapping("v1/admin/permission/getRole")
//    public List<RbacRoleResponse> getRoles() {
//        return roleBiz.getRolesByAccount();
//    }


}