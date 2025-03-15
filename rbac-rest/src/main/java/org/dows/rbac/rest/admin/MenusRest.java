package org.dows.rbac.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.framework.crud.api.model.PageRequest;
import org.dows.framework.crud.api.model.PageResponse;
import org.dows.rbac.api.admin.request.FindRbacMenusRequest;
import org.dows.rbac.api.admin.request.SaveRbacMenusRequest;
import org.dows.rbac.api.admin.response.RbacMenusResponse;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.biz.admin.MenusBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限菜单集管理
 * @date 2024年2月27日 上午11:52:56
 */
@Menu(name = "应用菜单", code = "menu", path = "/")
@RequiredArgsConstructor
@RestController
@Tag(name = "权限菜单集管理", description = "权限菜单集管理")
public class MenusRest {
    private final MenusBiz menusBiz;

    /**
     * 批量更新和插入
     *
     * @param
     * @return
     */

    @Operation(summary = "批量更新和插入")
    @PostMapping("v1/admin/menus/save")
    public void save(@RequestBody @Validated List<SaveRbacMenusRequest> saveRbacMenus) {
        menusBiz.save(saveRbacMenus);
    }

    /**
     * 根据appid查询应用的菜单集
     *
     * @param
     * @return
     */
    @Operation(summary = "根据appid查询应用的菜单集")
    @GetMapping("v1/admin/menus/listByAppId")
    public List<RbacMenusResponse> listByAppId(@RequestParam String appId) {
        return menusBiz.listByAppId(appId);
    }

    /**
     * 分页查询
     *
     * @param
     * @return
     */
    @Operation(summary = "分页查询")
    @PostMapping("v1/admin/menus/paging")
    public PageResponse<RbacMenusResponse> paging(@RequestBody @Validated PageRequest<FindRbacMenusRequest> findRbacMenus) {
        return menusBiz.paging(findRbacMenus);
    }

    /**
     * 根据ID删除菜单集
     *
     * @param
     * @return
     */

    @Operation(summary = "根据ID删除菜单集")
    @DeleteMapping("v1/admin/menus/deleteById")
    public void deleteByIds(@RequestParam List<Long> rbacMenusIds) {
        menusBiz.deleteByIds(rbacMenusIds);
    }


}