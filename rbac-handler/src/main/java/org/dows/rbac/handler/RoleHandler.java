package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.api.RbacHandler;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.service.RbacRoleService;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleHandler implements RbacHandler {

    private final RbacRoleService rbacRoleService;


    private final RbacCache rbacCache;


    public RbacRoleEntity getByRoleCode(String roleCode, String appid) {
      /*  if (StrUtil.isBlank(roleCode)) {
            return null;
        }
        return rbacRoleService.lambdaQuery()
                .eq(RbacRoleEntity::getRoleCode, roleCode)
                .eq(Objects.nonNull(appid), RbacRoleEntity::getAppId, appid)
                .last("limit 1").oneOpt().orElse(null);*/
        return null;
    }

    public List<RbacRoleEntity> getRoleByRoleIds(List<Long> roleIds) {
        /*return rbacRoleService.lambdaQuery()
                .in(RbacRoleEntity::getRbacRoleId, roleIds)
                .eq(RbacRoleEntity::getState, StateEnum.AVAILABLE.getCode())
                .list();*/
        return null;
    }

    public List<RbacRoleEntity> getAllRoles() {
        /*return rbacRoleService.lambdaQuery()
                .eq(RbacRoleEntity::getState, StateEnum.AVAILABLE.getCode())
                .list();*/
        return null;
    }

    public List<RbacRoleEntity> getByRoleName(String roleName, Integer state, String appId) {
        /*return rbacRoleService.lambdaQuery()
                .eq(Objects.nonNull(roleName), RbacRoleEntity::getRoleName, roleName)
                .eq(Objects.nonNull(state), RbacRoleEntity::getState, state)
                .eq(Objects.nonNull(appId), RbacRoleEntity::getAppId, appId)
                .list();*/
        return null;
    }

    public Boolean hasRoleName(String roleName, String appId) {
        List<RbacRoleEntity> byRoleName = getByRoleName(roleName, null, appId);
        return CollectionUtil.isNotEmpty(byRoleName);
    }

//    public

    @Override
    public void handle(Object rbacResources) {
        /*Map<Long, SaveRbacRoleRequest> map = (Map) rbacResources;
        Set<Long> roleIds = map.keySet();
        for (Long roleId : roleIds) {
            SaveRbacRoleRequest saveRbacRoleRequest = map.get(roleId);
            RbacRoleEntity rbacRoleEntity = BeanConvert.beanConvert(saveRbacRoleRequest, RbacRoleEntity.class);
            rbacCache.putCache(UserInfoEnum.RBAC_ROLE.getKey(), roleId, rbacRoleEntity);
        }
        List<Long> cacheRoleIds = rbacCache.getAllRoleIds();
        if (CollectionUtil.isEmpty(cacheRoleIds)) {
            cacheRoleIds = new ArrayList<>();
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long roleId : roleIds) {
            if (!cacheRoleIds.contains(roleId)) {
                missingIds.add(roleId);
            }
        }
        cacheRoleIds.addAll(missingIds);
        rbacCache.putCache(UserInfoEnum.RBAC_ALL_ROLE_ID.getKey(), UserInfoEnum.RBAC_ALL_ROLE_ID.getKey(), cacheRoleIds);*/
    }

    @Override
    public boolean supportResourceType(Integer resourceType) {
        return false;
    }
}
