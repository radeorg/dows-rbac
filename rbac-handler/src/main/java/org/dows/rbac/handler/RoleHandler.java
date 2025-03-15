package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.RbacHandler;
import org.dows.rbac.api.admin.request.SaveRbacRoleRequest;
import org.dows.rbac.api.annotation.RbacTrigger;
import org.dows.rbac.api.constant.StateEnum;
import org.dows.rbac.api.constant.UserInfoEnum;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.repository.RbacRoleRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleHandler implements RbacHandler {

    private final RbacRoleRepository rbacRoleRepository;


    private final RbacCache rbacCache;


    public RbacRoleEntity getByRoleCode(String roleCode, String appid) {
        if (StrUtil.isBlank(roleCode)) {
            return null;
        }
        return rbacRoleRepository.lambdaQuery()
                .eq(RbacRoleEntity::getRoleCode, roleCode)
                .eq(Objects.nonNull(appid), RbacRoleEntity::getAppId, appid)
                .last("limit 1").oneOpt().orElse(null);
    }

    public List<RbacRoleEntity> getRoleByRoleIds(List<Long> roleIds) {
        return rbacRoleRepository.lambdaQuery()
                .in(RbacRoleEntity::getRbacRoleId, roleIds)
                .eq(RbacRoleEntity::getState, StateEnum.AVAILABLE.getCode())
                .list();
    }

    public List<RbacRoleEntity> getAllRoles() {
        return rbacRoleRepository.lambdaQuery()
                .eq(RbacRoleEntity::getState, StateEnum.AVAILABLE.getCode())
                .list();
    }

    public List<RbacRoleEntity> getByRoleName(String roleName,Integer state,String appId) {
        return rbacRoleRepository.lambdaQuery()
                .eq(Objects.nonNull(roleName),RbacRoleEntity::getRoleName, roleName)
                .eq(Objects.nonNull(state),RbacRoleEntity::getState, state)
                .eq(Objects.nonNull(appId),RbacRoleEntity::getAppId, appId)
                .list();
    }

    public Boolean hasRoleName(String roleName,String appId) {
        List<RbacRoleEntity> byRoleName = getByRoleName(roleName, null, appId);
        return CollectionUtil.isNotEmpty(byRoleName);
    }

//    public

    @Override
    public void handle(Object rbacResources) {
        Map<Long, SaveRbacRoleRequest> map = (Map)rbacResources;
        Set<Long> roleIds = map.keySet();
        for (Long roleId : roleIds) {
            SaveRbacRoleRequest saveRbacRoleRequest = map.get(roleId);
            RbacRoleEntity rbacRoleEntity = BeanConvert.beanConvert(saveRbacRoleRequest, RbacRoleEntity.class);
            rbacCache.putCache(UserInfoEnum.RBAC_ROLE.getKey(), roleId, rbacRoleEntity);
        }
        List<Long> cacheRoleIds = rbacCache.getAllRoleIds();
        if(CollectionUtil.isEmpty(cacheRoleIds)){
            cacheRoleIds = new ArrayList<>();
        }
        List<Long> missingIds = new ArrayList<>();
        for (Long roleId : roleIds) {
            if (!cacheRoleIds.contains(roleId)) {
                missingIds.add(roleId);
            }
        }
        cacheRoleIds.addAll(missingIds);
        rbacCache.putCache(UserInfoEnum.RBAC_ALL_ROLE_ID.getKey(), UserInfoEnum.RBAC_ALL_ROLE_ID.getKey(),cacheRoleIds);
    }
    @Override
    public boolean supportResourceType(Integer resourceType) {
        return false;
    }
}
