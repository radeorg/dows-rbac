package org.dows.rbac.handler;


import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rade.context.AppContext;
import org.dows.rbac.entity.RbacPermissionEntity;
import org.dows.rbac.service.RbacPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RbacPermissionHandler {

    private final RbacPermissionService rbacPermissionService;

    public void removeByRoleIds(List<Long> roleIds) {
        log.info("删除权限角色：{} 对应的资源权限", roleIds);
        QueryWrapper in = QueryWrapper.create()
                .eq(RbacPermissionEntity::getAppId, AppContext.getAppId())
                .in(RbacPermissionEntity::getRbacRoleId, roleIds);
        boolean remove = rbacPermissionService.remove(in);
    }

    public void saveBatch(List<RbacPermissionEntity> rbacPermissionEntities) {
        rbacPermissionService.saveBatch(rbacPermissionEntities);
    }
}
