package a;

import lombok.RequiredArgsConstructor;
import org.dows.rbac.service.RbacMenuService;
import org.dows.rbac.service.RbacUriService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResourcesHandler {

    private final RbacMenuService rbacMenuService;
    private final RbacUriService rbacUriService;

    /*public List<RbacResourcesQueryResponse> listByQuery(FindRbacResourcesRequest findRbacResources) {
        List<RbacResourcesEntity> rbacResourcesEntities = rbacResourcesService.lambdaQuery()
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
        return rbacResourcesService.lambdaQuery()
                .eq(Objects.nonNull(moduleId), RbacResourcesEntity::getRbacModuleId, moduleId)
                .eq(Objects.nonNull(resourceType), RbacResourcesEntity::getResourceType, resourceType)
                .list();
    }

    public void saveOrUpdateResource(List<InitResources> resources) {


    }*/

}