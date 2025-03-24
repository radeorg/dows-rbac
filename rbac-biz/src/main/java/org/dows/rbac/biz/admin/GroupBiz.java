package org.dows.rbac.biz.admin;

import lombok.RequiredArgsConstructor;
import org.dows.rbac.api.admin.request.FindRbacGroupRequest;
import org.dows.rbac.api.admin.request.SaveRbacGroupRequest;
import org.dows.rbac.entity.RbacGroupEntity;
import org.dows.rbac.service.RbacGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限组
 * @date 2024年2月26日 上午9:44:35
 */
@RequiredArgsConstructor
@Service
public class GroupBiz {
    private final RbacGroupService rbacGroupService;

    /**
     * @param saveRbacGroup 保存的RbacGroupRequest列表
     * @return void
     * @说明: 批量更新和插入
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月26日 上午9:44:35
     */
    @Transactional
    public void save(List<SaveRbacGroupRequest> saveRbacGroup) {
//        List<RbacGroupEntity> rbacGroupEntities = BeanConvert.beanConvert(saveRbacGroup, RbacGroupEntity.class);
//        rbacGroupService.saveOrUpdateBatch(rbacGroupEntities);
    }

    /**
     * @param rbacGroupId 群组Id
     * @return RbacGroupEntity
     * @说明: 根据群组Id查询
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月26日 上午9:44:35
     */
    public RbacGroupEntity getById(Long rbacGroupId) {
        return rbacGroupService.getById(rbacGroupId);
    }

    /**
     * @param findRbacGroup 查询条件
     * @return List<RbacGroupEntity>
     * @说明: 根据条件查询
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月26日 上午9:44:35
     */
    public List<RbacGroupEntity> listByQuery(FindRbacGroupRequest findRbacGroup) {
        /*return rbacGroupService.lambdaQuery()
                .eq(Objects.nonNull(findRbacGroup.getRbacGroupId()), RbacGroupEntity::getRbacGroupId, findRbacGroup.getRbacGroupId())
                .eq(Objects.nonNull(findRbacGroup.getAppId()), RbacGroupEntity::getAppId, findRbacGroup.getAppId())
                .like(Objects.nonNull(findRbacGroup.getGroupName()), RbacGroupEntity::getGroupName, findRbacGroup.getGroupName())
                .list();*/
        return null;
    }

    /**
     * @param rbacGroupIds 群组Id列表
     * @return void
     * @说明: 根据Id删除
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月26日 上午9:44:35
     */
    @Transactional
    public void deleteByIds(List<Long> rbacGroupIds) {
        rbacGroupService.removeByIds(rbacGroupIds);
    }
}