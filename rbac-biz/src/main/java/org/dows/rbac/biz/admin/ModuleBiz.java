package org.dows.rbac.biz.admin;

import lombok.RequiredArgsConstructor;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.admin.request.FindRbacModuleRequest;
import org.dows.rbac.api.admin.request.SaveRbacModuleRequest;
import org.dows.rbac.api.admin.response.RbacMoudleQueryResponse;
import org.dows.rbac.entity.RbacModuleEntity;
import org.dows.rbac.repository.RbacModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限模块管理
 * @date 2024年2月27日 上午11:52:56
 */
@RequiredArgsConstructor
@Service
public class ModuleBiz {
    private final RbacModuleRepository rbacModuleRepository;

    /**
     * @param
     * @return
     * @说明: 保存权限模块
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    @Transactional
    public void save(List<SaveRbacModuleRequest> saveRbacModule) {
        List<RbacModuleEntity> rbacModuleEntities = BeanConvert.beanConvert(saveRbacModule, RbacModuleEntity.class);
        rbacModuleRepository.saveOrUpdateBatch(rbacModuleEntities);
    }

    /**
     * @param
     * @return
     * @说明: 根据模块Id查询
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public RbacMoudleQueryResponse getById(Long rbacModuleId) {
        RbacModuleEntity rbacModuleEntity = rbacModuleRepository.getById(rbacModuleId);
        return BeanConvert.beanConvert(rbacModuleEntity, RbacMoudleQueryResponse.class);
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
    public List<RbacMoudleQueryResponse> listByQuery(FindRbacModuleRequest findRbacModule) {
        List<RbacModuleEntity> rbacModuleEntities = rbacModuleRepository.lambdaQuery()
                .eq(Objects.nonNull(findRbacModule.getRbacModuleId()), RbacModuleEntity::getRbacModuleId, findRbacModule.getRbacModuleId())
                .eq(Objects.nonNull(findRbacModule.getAppId()), RbacModuleEntity::getAppId, findRbacModule.getAppId())
                .like(Objects.nonNull(findRbacModule.getModuleName()), RbacModuleEntity::getModuleName, findRbacModule.getModuleName())
                .list();

        List<RbacMoudleQueryResponse> responseList = BeanConvert.beanConvert(rbacModuleEntities, RbacMoudleQueryResponse.class);
        return responseList;
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
    public void deleteByIds(List<Long> rbacModuleIds) {
        rbacModuleRepository.removeByIds(rbacModuleIds);
    }
}