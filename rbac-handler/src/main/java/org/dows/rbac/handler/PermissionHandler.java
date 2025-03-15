package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.admin.response.RbacMenusResponse;
import org.dows.rbac.api.admin.response.RbacUriResponse;
import org.dows.rbac.api.constant.ResourceEnum;
import org.dows.rbac.api.constant.StateEnum;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.entity.RbacPermissionEntity;
import org.dows.rbac.entity.RbacUriEntity;
import org.dows.rbac.repository.RbacMenuRepository;
import org.dows.rbac.repository.RbacPermissionRepository;
import org.dows.rbac.repository.RbacUriRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionHandler {

    private final RbacUriRepository rbacUriRepository;

    private final RbacMenuRepository rbacMenusRepository;

    private final RbacPermissionRepository rbacPermissionRepository;
//    private final AacApi aacApi;

    public RbacPermissionEntity getByMenuCodeAndRoleCode(String menuCode, String roleCode) {
        if (StrUtil.isBlank(menuCode) || StrUtil.isBlank(roleCode)) {
            return null;
        }
        return rbacPermissionRepository.lambdaQuery()
                .eq(RbacPermissionEntity::getAuthority, menuCode)
                .eq(RbacPermissionEntity::getRoleCode, roleCode)
                .last("limit 1").oneOpt().orElse(null);
    }

    public void deleteByIds(List<Long> ids) {
        if (CollectionUtil.isNotEmpty(ids)) {
            rbacPermissionRepository.removeByIds(ids);
        }
    }

    public List<RbacPermissionEntity> getAllPermissions() {
        return rbacPermissionRepository.lambdaQuery()
                .eq(RbacPermissionEntity::getState, StateEnum.AVAILABLE)
                .list();
    }

    public List<RbacUriResponse> listUrisByMenuId(Long rbacMenuId) {
        List<RbacUriEntity> rbacUriEntityList = rbacUriRepository.lambdaQuery()
                .eq(Objects.nonNull(rbacMenuId), RbacUriEntity::getRbacMenuId, rbacMenuId)
                .eq(RbacUriEntity::getState, StateEnum.AVAILABLE)
                .list();

        return BeanConvert.beanConvert(rbacUriEntityList, RbacUriResponse.class);
    }

    public List<RbacUriEntity> getUriByRoleId(Long rbacRoleId) {
        List<RbacPermissionEntity> list = rbacPermissionRepository.lambdaQuery()
                .eq(RbacPermissionEntity::getRbacRoleId, rbacRoleId)
                .eq(RbacPermissionEntity::getResourceType, ResourceEnum.INTERFACE.getCode())
                .eq(RbacPermissionEntity::getState, StateEnum.AVAILABLE.getCode())
                .list();
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        List<Long> resourceIds = list.stream().map(RbacPermissionEntity::getResourceId)
                .toList();
        if (CollectionUtil.isEmpty(resourceIds)) {
            return null;
        }
        return rbacUriRepository.listByIds(resourceIds);
    }


    public List<RbacPermissionEntity> getPermissionByResourceIds(List<Long> resourceIds,Integer resourceType) {
        if (CollectionUtil.isEmpty(resourceIds)) {
            return null;
        }
        return rbacPermissionRepository.lambdaQuery()
                .in(RbacPermissionEntity::getResourceId, resourceIds)
                .eq(RbacPermissionEntity::getResourceType, resourceType)
                .list();
    }

    public List<RbacPermissionEntity> getPermissionByRoleIds(List<Long> rbacRoleIds) {
        return rbacPermissionRepository.lambdaQuery()
                .in(RbacPermissionEntity::getRbacRoleId, rbacRoleIds)
                .eq(RbacPermissionEntity::getState,StateEnum.AVAILABLE)
                .list();
    }

    public List<RbacPermissionEntity> getPermissionByRoleIds(List<Long> rbacRoleIds,Integer resourceType) {
        return rbacPermissionRepository.lambdaQuery()
                .in(RbacPermissionEntity::getRbacRoleId, rbacRoleIds)
                .eq(Objects.nonNull(resourceType),RbacPermissionEntity::getResourceType,resourceType)
                .eq(RbacPermissionEntity::getState,StateEnum.AVAILABLE)
                .list();
    }

    public List<RbacMenuEntity> getMenusByRoleId(Long rbacRoleId) {
        List<RbacPermissionEntity> list = rbacPermissionRepository.lambdaQuery()
                .eq(RbacPermissionEntity::getRbacRoleId, rbacRoleId)
                .eq(RbacPermissionEntity::getResourceType, ResourceEnum.MENU.getCode())
                .eq(RbacPermissionEntity::getState, StateEnum.AVAILABLE.getCode())
                .list();
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        List<Long> resourceIds = list.stream().map(RbacPermissionEntity::getResourceId)
                .toList();
        if (CollectionUtil.isEmpty(resourceIds)) {
            return null;
        }
        return rbacMenusRepository.listByIds(resourceIds);
    }

}
