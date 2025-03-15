package org.dows.rbac.biz.admin;

import lombok.RequiredArgsConstructor;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.admin.request.FindRbacResourcesRequest;
import org.dows.rbac.api.admin.request.SaveRbacModuleResourcesRequest;
import org.dows.rbac.api.admin.request.SaveRbacResourcesRequest;
import org.dows.rbac.api.admin.response.RbacResourcesQueryResponse;
import org.dows.rbac.api.constant.ResourceEnum;
import org.dows.rbac.api.constant.StateEnum;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.entity.RbacModuleEntity;
import org.dows.rbac.entity.RbacResourcesEntity;
import org.dows.rbac.entity.RbacUriEntity;
import org.dows.rbac.handler.ResourcesHandler;
import org.dows.rbac.repository.RbacMenuRepository;
import org.dows.rbac.repository.RbacModuleRepository;
import org.dows.rbac.repository.RbacResourcesRepository;
import org.dows.rbac.repository.RbacUriRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lait.zhang
 * @description project descr:管理端:模块资源管理
 * @date 2024年2月27日 上午11:52:56
 */
@RequiredArgsConstructor
@Service
public class ResourcesBiz {
    private final RbacResourcesRepository rbacResourcesRepository;

    private final RbacModuleRepository rbacModuleRepository;

    private final RbacMenuRepository rbacMenusRepository;

    private final RbacUriRepository rbacUriRepository;

    private final ResourcesHandler resourcesHandler;

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
        List<RbacResourcesEntity> rbacResourcesEntities = BeanConvert.beanConvert(saveRbacResources, RbacResourcesEntity.class);
        rbacResourcesRepository.saveOrUpdateBatch(rbacResourcesEntities);
    }

    public void saveRbacModuleResources(SaveRbacModuleResourcesRequest saveRbacModuleResourcesRequest) {
        RbacModuleEntity rbacModuleEntity = rbacModuleRepository.getById(saveRbacModuleResourcesRequest.getRbacModuleId());
        if (Objects.isNull(rbacModuleEntity)) {
            throw new IllegalArgumentException("未找到权限模块信息");
        }
        List<RbacResourcesEntity> rbacResourcesEntities = new ArrayList<>();
        List<Long> rbacMenusIds = saveRbacModuleResourcesRequest.getRbacMenusIds();
        if (!CollectionUtils.isEmpty(rbacMenusIds)) {
            rbacMenusIds.forEach(rbacResourcesId -> {
                RbacMenuEntity rbacMenuEntity = rbacMenusRepository.getById(rbacResourcesId);
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
                RbacUriEntity rbacUriEntity = rbacUriRepository.getById(rbacUrisId);
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
        rbacResourcesRepository.saveOrUpdateBatch(rbacResourcesEntities);
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
        RbacResourcesEntity rbacResourcesEntity = rbacResourcesRepository.getById(rbacResourcesId);
        return BeanConvert.beanConvert(rbacResourcesEntity, RbacResourcesQueryResponse.class);
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
        return resourcesHandler.listByQuery(findRbacResources);
    }

    public List<RbacResourcesEntity> listByModuleIdAndResourceType(Long moduleId, Integer resourceType) {
        return resourcesHandler.listByModuleIdAndResourceType(moduleId, resourceType);
    }

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
    @Transactional
    public void deleteByIds(List<Long> rbacResourcesIds) {
        rbacResourcesRepository.removeByIds(rbacResourcesIds);
    }
}