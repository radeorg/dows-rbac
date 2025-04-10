package org.dows.rbac.biz.admin;

import lombok.RequiredArgsConstructor;
import org.dows.rbac.request.FindRbacResourcesRequest;
import org.dows.rbac.request.SaveRbacModuleResourcesRequest;
import org.dows.rbac.request.SaveRbacResourcesRequest;
import org.dows.rbac.response.RbacResourcesQueryResponse;
import org.dows.rbac.service.RbacMenuService;
import org.dows.rbac.service.RbacUriService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:模块资源管理
 * @date 2024年2月27日 上午11:52:56
 */
@RequiredArgsConstructor
@Service
public class ResourcesBiz {

    private final RbacMenuService rbacMenusService;

    private final RbacUriService rbacUriService;

//    private final ResourcesHandler resourcesHandler;

    /**
     * @param
     * @return
     * @说明: 保存权限模块资源
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    @Transactional
    public void save(List<SaveRbacResourcesRequest> saveRbacResources) {
        /*List<RbacResourcesEntity> rbacResourcesEntities = BeanConvert.beanConvert(saveRbacResources, RbacResourcesEntity.class);
        rbacResourcesService.saveOrUpdateBatch(rbacResourcesEntities);*/
    }

    public void saveRbacModuleResources(SaveRbacModuleResourcesRequest saveRbacModuleResourcesRequest) {
        /*RbacModuleEntity rbacModuleEntity = rbacModuleService.getById(saveRbacModuleResourcesRequest.getRbacModuleId());
        if (Objects.isNull(rbacModuleEntity)) {
            throw new IllegalArgumentException("未找到权限模块信息");
        }
        List<RbacResourcesEntity> rbacResourcesEntities = new ArrayList<>();
        List<Long> rbacMenusIds = saveRbacModuleResourcesRequest.getRbacMenusIds();
        if (!CollectionUtils.isEmpty(rbacMenusIds)) {
            rbacMenusIds.forEach(rbacResourcesId -> {
                RbacMenuEntity rbacMenuEntity = rbacMenusService.getById(rbacResourcesId);
                RbacResourcesEntity rbacResourcesEntity = new RbacResourcesEntity();
                rbacResourcesEntity.setRbacModuleId(saveRbacModuleResourcesRequest.getRbacModuleId());
                rbacResourcesEntity.setResourceId(rbacMenuEntity.getRbacMenuId());
                rbacResourcesEntity.setName(rbacMenuEntity.getName());
                rbacResourcesEntity.setCode(rbacMenuEntity.getCode());
                rbacResourcesEntity.setAppId(rbacMenuEntity.getAppId());
                rbacResourcesEntity.setResourceType(ResourceEnum.MENU.getCode());
                rbacResourcesEntity.setState(StateEnum.AVAILABLE.getCode());
                rbacResourcesEntities.add(rbacResourcesEntity);
            });
        }
        List<Long> rbacUrisIds = saveRbacModuleResourcesRequest.getRbacUrisIds();
        if (!CollectionUtils.isEmpty(rbacUrisIds)) {
            rbacUrisIds.forEach(rbacUrisId -> {
                RbacUriEntity rbacUriEntity = rbacUriService.getById(rbacUrisId);
                RbacResourcesEntity rbacResourcesEntity = new RbacResourcesEntity();
                rbacResourcesEntity.setRbacModuleId(saveRbacModuleResourcesRequest.getRbacModuleId());
                rbacResourcesEntity.setResourceId(rbacUriEntity.getRbacMenuId());
                rbacResourcesEntity.setName(rbacUriEntity.getName());
                rbacResourcesEntity.setCode(rbacUriEntity.getCode());
                rbacResourcesEntity.setAppId(rbacUriEntity.getAppId());
                rbacResourcesEntity.setResourceType(ResourceEnum.INTERFACE.getCode());
                rbacResourcesEntity.setState(StateEnum.AVAILABLE.getCode());
                rbacResourcesEntities.add(rbacResourcesEntity);
            });
        }
        rbacResourcesService.saveOrUpdateBatch(rbacResourcesEntities);*/
    }

    /**
     * @param
     * @return
     * @说明: 根据模块资源Id查询
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public RbacResourcesQueryResponse getById(Long rbacResourcesId) {
        /*RbacResourcesEntity rbacResourcesEntity = rbacResourcesService.getById(rbacResourcesId);
        return BeanConvert.beanConvert(rbacResourcesEntity, RbacResourcesQueryResponse.class);*/
        return null;
    }

    /**
     * @param
     * @return
     * @说明: 根据条件查询
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public List<RbacResourcesQueryResponse> listByQuery(FindRbacResourcesRequest findRbacResources) {
        //return resourcesHandler.listByQuery(findRbacResources);
        return new ArrayList<>();
    }
/*
    public List<RbacResourcesEntity> listByModuleIdAndResourceType(Long moduleId, Integer resourceType) {
        return resourcesHandler.listByModuleIdAndResourceType(moduleId, resourceType);
    }*/

    /**
     * @param
     * @return
     * @说明: 根据Id删除
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
   /* public void deleteByIds(List<Long> rbacResourcesIds) {
        rbacResourcesService.removeByIds(rbacResourcesIds);
    }*/
}