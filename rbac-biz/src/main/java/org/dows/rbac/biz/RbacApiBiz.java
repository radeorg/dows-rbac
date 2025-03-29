package org.dows.rbac.biz;

import com.mybatisflex.core.query.QueryChain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.api.InitResources;
import org.dows.rbac.api.InitUriResources;
import org.dows.rbac.api.RbacApi;
import org.dows.rbac.api.RoleResourceResponse;
import org.dows.rbac.api.admin.request.FindRbacResourcesRequest;
import org.dows.rbac.api.admin.request.SaveRbacRoleRequest;
import org.dows.rbac.api.admin.response.*;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.entity.RbacPermissionEntity;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.entity.RbacUriEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class RbacApiBiz implements RbacApi {
    @Override
    public List<RbacRoleResponse> getRoleInstanceByRoleIds(String appId, List<Long> roleIds) {
        List<RbacRoleResponse> responseList = new ArrayList<>();
        for(Long roleId : roleIds){
            List<RbacRoleEntity> rbacRoleEntities = QueryChain.of(RbacRoleEntity.class)
                    .eq(RbacRoleEntity::getAppId, appId, Objects.nonNull(appId))
                    .eq(RbacRoleEntity::getRbacRoleId, roleId, Objects.nonNull(roleId)).list();
            if(Objects.isNull(rbacRoleEntities) || rbacRoleEntities.size() == 0){
                continue;
            }

            for(RbacRoleEntity item : rbacRoleEntities) {
                RbacRoleResponse response = new RbacRoleResponse();
                BeanUtils.copyProperties(item, response);
                responseList.add(response);
            }

        }
        return responseList;
    }

    @Override
    public List<RbacUriResponse> getAllUrisByAppId(String appId) {
        List<RbacUriResponse> response = new ArrayList<>();
        if(Objects.isNull(appId)){
            return response;
        }
        List<RbacUriEntity> rbacRoleEntities = QueryChain.of(RbacUriEntity.class)
                .eq(RbacUriEntity::getAppId, appId, Objects.nonNull(appId)).list();
        BeanUtils.copyProperties(rbacRoleEntities, response);

        return response;
    }

    @Override
    public List<RoleResourceResponse> getUrisByRoleIds(String appId, List<Long> roleIds) {
        List<RoleResourceResponse> responseList = new ArrayList<>();
        for(Long roleId : roleIds){
            List<RbacPermissionEntity> rbacPermissionEntities = QueryChain.of(RbacPermissionEntity.class)
                    .eq(RbacPermissionEntity::getAppId, appId, Objects.nonNull(appId))
                    .eq(RbacPermissionEntity::getRbacRoleId, roleId, Objects.nonNull(roleId)).list();
            if(Objects.isNull(rbacPermissionEntities) || rbacPermissionEntities.size() == 0){
                continue;
            }

            RoleResourceResponse response = new RoleResourceResponse();
            response.setRoleId(roleId);
            List<String> authority = new ArrayList<>();
            for(RbacPermissionEntity item : rbacPermissionEntities) {
                Long resourceId = item.getResourceId();
                //资源类型[0:接口，1:菜单]
                if(Objects.nonNull(resourceId) &&  item.getResourceType() == 0){
                    List<RbacUriEntity> rbacruiEntities = QueryChain.of(RbacUriEntity.class)
                            .eq(RbacUriEntity::getRbacUriId, resourceId, Objects.nonNull(resourceId)).list();
                    if(Objects.isNull(rbacruiEntities) || rbacruiEntities.size() == 0){
                        continue;
                    }
                    for(RbacUriEntity uriItem : rbacruiEntities){
                        authority.add(uriItem.getJavaMethod());
                    }
                }else if(Objects.nonNull(resourceId) &&  item.getResourceType() == 1){
                    List<RbacMenuEntity> rbacMenuEntities = QueryChain.of(RbacMenuEntity.class)
                            .eq(RbacMenuEntity::getRbacMenuId, resourceId, Objects.nonNull(resourceId)).list();
                    if(Objects.isNull(rbacMenuEntities) || rbacMenuEntities.size() == 0){
                        continue;
                    }
                    for(RbacMenuEntity uriItem : rbacMenuEntities){
                        authority.add(uriItem.getMenuCode());
                    }
                }
            }
            response.setAuthority(authority);
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public String getMenu() {
        return "";
    }

    @Override
    public List<RbacResourcesQueryResponse> getResource(FindRbacResourcesRequest findRbacResources) {
        return List.of();
    }

    @Override
    public List<RbacPermissionResponse> getPermission(List<Long> roleIds) {
        return List.of();
    }

    @Override
    public List<String> getUriCode(List<Long> roleIds) {
        return List.of();
    }

    @Override
    public void saveResource(List<InitResources> resources) {

    }

    @Override
    public void initRoleUri(List<InitResources> resources, String roleCode, String appId) {

    }

    @Override
    public void initAppRole(List<SaveRbacRoleRequest> roleItems) {

    }

    @Override
    public List<RbacMenusResponse> listRoleMenusTree(List<Long> rbacRoleIds) {
        return List.of();
    }

    @Override
    public void saveUri(List<InitUriResources> initUriResources) {

    }

    @Override
    public Map<String, List<RbacUriRoleResponse>> getRoleUri() {
        return Map.of();
    }
}
