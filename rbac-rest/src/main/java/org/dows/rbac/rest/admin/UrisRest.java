package org.dows.rbac.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.rbac.api.admin.request.SaveUrisRequest;
import org.dows.rbac.api.admin.response.RbacUriResponse;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.biz.admin.UrisBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限接口集管理
 * @date 2024年2月27日 上午11:52:56
 */
@Menu(name = "应用链接", code = "rbac.uri.instance", path = "")
@RequiredArgsConstructor
@RestController
@Tag(name = "权限接口集管理", description = "权限接口集管理")
public class UrisRest {
    private final UrisBiz urisBiz;

    /**
     * 批量更新和插入
     *
     * @param
     * @return
     */
    @Operation(summary = "批量更新和插入")
    @PostMapping("v1/admin/uris/save")
    public void save(@RequestBody @Validated List<SaveUrisRequest> saveUris) {
        urisBiz.save(saveUris);
    }

    /**
     * 根据appid查询应用的接口集
     *
     * @param
     * @return
     */
    @Operation(summary = "根据appid查询应用的接口集")
    @GetMapping("v1/admin/uris/listByAppId")
    public List<RbacUriResponse> listByAppId() {
        return urisBiz.listByAppId();
    }

    /**
     * 删除
     *
     * @param
     * @return
     */
    @Operation(summary = "删除")
    @DeleteMapping("v1/admin/uris/deleteById")
    public void deleteByIds(@RequestParam List<Long> uriIds) {
        urisBiz.deleteByIds(uriIds);
    }


}