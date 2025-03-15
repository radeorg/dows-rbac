package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.cache.caffeine.CaffeineTemplate;
import org.dows.rbac.api.RbacHandler;
import org.dows.rbac.api.constant.UserInfoEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleDeleteHandler implements RbacHandler {

    private final RbacCache rbacCache;

    @Override
    public void handle(Object orgs) {
        List<Long> roleIds = (List)orgs;
        if(CollectionUtil.isEmpty(roleIds)){
            return;
        }
        for (Long roleId : roleIds) {
            rbacCache.evictCache(UserInfoEnum.RBAC_ROLE.getKey(), roleId);
        }
        List<Long> cacheRoleIds = rbacCache.getAllRoleIds();
        if(CollectionUtil.isNotEmpty(cacheRoleIds)){
            List<Long> cacheRoleIdsWithoutRoleIds = cacheRoleIds.stream()
                    .filter(id -> !roleIds.contains(id))
                    .toList();
            rbacCache.putCache(UserInfoEnum.RBAC_ALL_ROLE_ID.getKey(), UserInfoEnum.RBAC_ALL_ROLE_ID.getKey(),cacheRoleIdsWithoutRoleIds);
        }
    }

    @Override
    public boolean supportResourceType(Integer resourceType) {
        return false;
    }
}
