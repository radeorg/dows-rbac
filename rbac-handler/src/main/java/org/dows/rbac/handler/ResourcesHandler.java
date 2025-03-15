package org.dows.rbac.handler;

import lombok.RequiredArgsConstructor;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.InitResources;
import org.dows.rbac.api.admin.request.FindRbacResourcesRequest;
import org.dows.rbac.api.admin.response.RbacResourcesQueryResponse;
import org.dows.rbac.entity.RbacResourcesEntity;
import org.dows.rbac.repository.RbacMenuRepository;
import org.dows.rbac.repository.RbacResourcesRepository;
import org.dows.rbac.repository.RbacUriRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ResourcesHandler {
    private final RbacResourcesRepository rbacResourcesRepository;

    private final RbacMenuRepository rbacMenuRepository;
    private final RbacUriRepository rbacUriRepository;

    public List<RbacResourcesQueryResponse> listByQuery(FindRbacResourcesRequest findRbacResources) {
        List<RbacResourcesEntity> rbacResourcesEntities = rbacResourcesRepository.lambdaQuery()
                .eq(Objects.nonNull(findRbacResources.getResourceId()), RbacResourcesEntity::getResourceId, findRbacResources.getResourceId())
                .eq(Objects.nonNull(findRbacResources.getResourceType()), RbacResourcesEntity::getResourceType, findRbacResources.getResourceType())
                .eq(Objects.nonNull(findRbacResources.getAppId()), RbacResourcesEntity::getAppId, findRbacResources.getAppId())
                .eq(Objects.nonNull(findRbacResources.getState()), RbacResourcesEntity::getState, findRbacResources.getState())
                .eq(Objects.nonNull(findRbacResources.getRbacModuleId()), RbacResourcesEntity::getRbacModuleId, findRbacResources.getRbacModuleId())
                .like(Objects.nonNull(findRbacResources.getName()), RbacResourcesEntity::getName, findRbacResources.getName())
                .list();

        return BeanConvert.beanConvert(rbacResourcesEntities, RbacResourcesQueryResponse.class);
    }


    public List<RbacResourcesEntity> listByModuleIdAndResourceType(Long moduleId, Integer resourceType) {
        return rbacResourcesRepository.lambdaQuery()
                .eq(Objects.nonNull(moduleId), RbacResourcesEntity::getRbacModuleId, moduleId)
                .eq(Objects.nonNull(resourceType), RbacResourcesEntity::getResourceType, resourceType)
                .list();
    }

    public void saveOrUpdateResource(List<InitResources> resources) {


    }

}