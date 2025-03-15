package org.dows.rbac.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.framework.api.annotation.Namespace;
import org.dows.rbac.api.admin.request.FindRbacGroupRequest;
import org.dows.rbac.api.admin.request.SaveRbacGroupRequest;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.biz.admin.GroupBiz;
import org.dows.rbac.entity.RbacGroupEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限组
 * @date 2024年2月26日 上午9:44:35
 */

@Namespace(module = "rbac", name = "权限组", code = "rbac.group", path = "/")
@RequiredArgsConstructor
@RestController
@Tag(name = "权限组", description = "权限组")
public class GroupRest {
    private final GroupBiz groupBiz;

    /**
     * 批量更新和插入
     *
     * @param saveRbacGroup 保存的权限组请求列表
     * @return void
     */
    @Operation(summary = "批量更新和插入")
    @PostMapping(value = "v1/admin/group/save")
    public void save(@RequestBody @Validated List<SaveRbacGroupRequest> saveRbacGroup) {
        groupBiz.save(saveRbacGroup);
    }

    /**
     * 根据群组Id查询
     *
     * @param rbacGroupId 群组Id
     * @return RbacGroupEntity
     */
    @Operation(summary = "根据群组Id查询")
    @GetMapping("v1/admin/group/getById")
    public RbacGroupEntity getById(@RequestParam Long rbacGroupId) {
        return groupBiz.getById(rbacGroupId);
    }

    /**
     * 根据条件查询
     *
     * @param findRbacGroup 查询条件
     * @return List<RbacGroupEntity>
     */
    @Operation(summary = "根据条件查询")
    @PostMapping("v1/admin/group/listByQuery")
    public List<RbacGroupEntity> listByQuery(@RequestBody FindRbacGroupRequest findRbacGroup) {
        return groupBiz.listByQuery(findRbacGroup);
    }

    /**
     * 根据Id删除
     *
     * @param rbacGroupIds 待删除的群组Id列表
     * @return void
     */
    @Operation(summary = "根据Id删除")
    @DeleteMapping("v1/admin/group/deleteById")
    public void deleteByIds(@RequestParam List<Long> rbacGroupIds) {
        groupBiz.deleteByIds(rbacGroupIds);
    }
}