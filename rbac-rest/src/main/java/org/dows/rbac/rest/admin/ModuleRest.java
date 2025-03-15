package org.dows.rbac.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.rbac.api.admin.request.FindRbacModuleRequest;
import org.dows.rbac.api.admin.request.SaveRbacModuleRequest;
import org.dows.rbac.api.admin.response.RbacMoudleQueryResponse;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.biz.admin.ModuleBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限模块管理
 * @date 2024年2月27日 上午11:52:56
 */
@Menu(name = "权限模块", code = "module", path = "/")
@RequiredArgsConstructor
@RestController
@Tag(name = "权限模块管理", description = "权限模块管理")
public class ModuleRest {
    private final ModuleBiz moduleBiz;

    /**
     * 保存权限模块
     *
     * @param
     * @return
     */

    @Operation(summary = "保存权限模块")
    @PostMapping("v1/admin/module/save")
    public void save(@RequestBody @Validated List<SaveRbacModuleRequest> saveRbacModule) {
        moduleBiz.save(saveRbacModule);
    }

    /**
     * 根据模块Id查询
     *
     * @param
     * @return
     */
    @Operation(summary = "根据模块Id查询")
    @GetMapping("v1/admin/module/getById")
    public RbacMoudleQueryResponse getById(@RequestParam Long rbacModuleId) {
        return moduleBiz.getById(rbacModuleId);
    }

    /**
     * 根据条件查询
     *
     * @param
     * @return
     */
    @Operation(summary = "根据条件查询")
    @PostMapping("v1/admin/module/listByQuery")
    public List<RbacMoudleQueryResponse> listByQuery(@RequestBody FindRbacModuleRequest findRbacModule) {
        return moduleBiz.listByQuery(findRbacModule);
    }

    /**
     * 根据Id删除
     *
     * @param
     * @return
     */

    @Operation(summary = "根据Id删除")
    @DeleteMapping("v1/admin/module/deleteById")
    public void deleteByIds(@RequestParam List<Long> rbacModuleIds) {
        moduleBiz.deleteByIds(rbacModuleIds);
    }


}