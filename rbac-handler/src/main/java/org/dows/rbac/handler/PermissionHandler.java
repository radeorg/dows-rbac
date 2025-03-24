package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.api.admin.response.RbacUriResponse;
import org.dows.rbac.api.constant.ResourceEnum;
import org.dows.rbac.api.constant.StateEnum;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.entity.RbacPermissionEntity;
import org.dows.rbac.entity.RbacUriEntity;
import org.dows.rbac.service.RbacMenuService;
import org.dows.rbac.service.RbacPermissionService;
import org.dows.rbac.service.RbacUriService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionHandler {

    private final RbacUriService rbacUriService;

    private final RbacMenuService rbacMenusService;

    private final RbacPermissionService rbacPermissionService;
//    private final AacApi aacApi;

    public RbacPermissionEntity getByMenuCodeAndRoleCode(String menuCode, String roleCode) {
        /*if (StrUtil.isBlank(menuCode) || StrUtil.isBlank(roleCode)) {
            return null;
        }
        return rbacPermissionService.lambdaQuery()
                .eq(RbacPermissionEntity::getAuthority, menuCode)
                .eq(RbacPermissionEntity::getRoleCode, roleCode)
                .last("limit 1").oneOpt().orElse(null);*/
        return null;
    }

    public void deleteByIds(List<Long> ids) {
        if (CollectionUtil.isNotEmpty(ids)) {
            rbacPermissionService.removeByIds(ids);
        }
    }

    public List<RbacPermissionEntity> getAllPermissions() {
        /*return rbacPermissionService.lambdaQuery()
                .eq(RbacPermissionEntity::getState, StateEnum.AVAILABLE)
                .list();*/
        return null;
    }

    public List<RbacUriResponse> listUrisByMenuId(Long rbacMenuId) {
       /* List<RbacUriEntity> rbacUriEntityList = rbacUriService.lambdaQuery()
                .eq(Objects.nonNull(rbacMenuId), RbacUriEntity::getRbacMenuId, rbacMenuId)
                .eq(RbacUriEntity::getState, StateEnum.AVAILABLE)
                .list();

        return BeanConvert.beanConvert(rbacUriEntityList, RbacUriResponse.class);*/
        return null;
    }

    public List<RbacUriEntity> getUriByRoleId(Long rbacRoleId) {
        /*List<RbacPermissionEntity> list = rbacPermissionService.lambdaQuery()
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
        return rbacUriService.listByIds(resourceIds);*/
        return null;
    }


    public List<RbacPermissionEntity> getPermissionByResourceIds(List<Long> resourceIds, Integer resourceType) {
        if (CollectionUtil.isEmpty(resourceIds)) {
            return null;
        }
        /*return rbacPermissionService.lambdaQuery()
                .in(RbacPermissionEntity::getResourceId, resourceIds)
                .eq(RbacPermissionEntity::getResourceType, resourceType)
                .list();*/
        return null;
    }

    public List<RbacPermissionEntity> getPermissionByRoleIds(List<Long> rbacRoleIds) {
        /*return rbacPermissionService.lambdaQuery()
                .in(RbacPermissionEntity::getRbacRoleId, rbacRoleIds)
                .eq(RbacPermissionEntity::getState, StateEnum.AVAILABLE)
                .list();*/
        return null;
    }

    public List<RbacPermissionEntity> getPermissionByRoleIds(List<Long> rbacRoleIds, Integer resourceType) {
        /*return rbacPermissionService.lambdaQuery()
                .in(RbacPermissionEntity::getRbacRoleId, rbacRoleIds)
                .eq(Objects.nonNull(resourceType), RbacPermissionEntity::getResourceType, resourceType)
                .eq(RbacPermissionEntity::getState, StateEnum.AVAILABLE)
                .list();*/
        return null;
    }

    public List<RbacMenuEntity> getMenusByRoleId(Long rbacRoleId) {
        /*List<RbacPermissionEntity> list = rbacPermissionService.lambdaQuery()
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
        return rbacMenusService.listByIds(resourceIds);*/
        return null;
    }

}
