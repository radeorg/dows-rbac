package org.dows.rbac.rest.tenant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.rbac.api.admin.request.SaveRbacGroupRequest;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.biz.admin.GroupBiz;
import org.dows.rbac.rest.admin.MenusRest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限组
 * @date 2024年2月26日 上午9:44:35
 */
@Menu(name = "权限集合", code = "rbac.group", path = "/", parent = MenusRest.class)
@RequiredArgsConstructor
@RestController
@Tag(name = "权限集合", description = "权限集合")
public class TenantGroupRest {
    private final GroupBiz groupBiz;

    /**
     * 批量更新和插入
     *
     * @param
     * @return
     */
    @Operation(summary = "批量更新和插入")
    @PostMapping(value = "v1/tenant/group/save")
    public void save(@RequestBody @Validated List<SaveRbacGroupRequest> saveRbacGroup) {
        groupBiz.save(saveRbacGroup);
    }


}