package org.dows.rbac.biz.admin;

import lombok.RequiredArgsConstructor;
import org.dows.rbac.request.FindRbacModuleRequest;
import org.dows.rbac.request.SaveRbacModuleRequest;
import org.dows.rbac.response.RbacMoudleQueryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限模块管理
 * @date 2024年2月27日 上午11:52:56
 */
@RequiredArgsConstructor
@Service
public class ModuleBiz {
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
       /* List<RbacModuleEntity> rbacModuleEntities = BeanConvert.beanConvert(saveRbacModule, RbacModuleEntity.class);
        rbacModuleService.saveOrUpdateBatch(rbacModuleEntities);*/
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
       /* RbacModuleEntity rbacModuleEntity = rbacModuleService.getById(rbacModuleId);
        return BeanConvert.beanConvert(rbacModuleEntity, RbacMoudleQueryResponse.class);*/
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
    public List<RbacMoudleQueryResponse> listByQuery(FindRbacModuleRequest findRbacModule) {
        /*List<RbacModuleEntity> rbacModuleEntities = rbacModuleService.lambdaQuery()
                .eq(Objects.nonNull(findRbacModule.getRbacModuleId()), RbacModuleEntity::getRbacModuleId, findRbacModule.getRbacModuleId())
                .eq(Objects.nonNull(findRbacModule.getAppId()), RbacModuleEntity::getAppId, findRbacModule.getAppId())
                .like(Objects.nonNull(findRbacModule.getModuleName()), RbacModuleEntity::getModuleName, findRbacModule.getModuleName())
                .list();

        List<RbacMoudleQueryResponse> responseList = BeanConvert.beanConvert(rbacModuleEntities, RbacMoudleQueryResponse.class);
        return responseList;*/
        return null;
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
        //rbacModuleService.removeByIds(rbacModuleIds);
    }
}