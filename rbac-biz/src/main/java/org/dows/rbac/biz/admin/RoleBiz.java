package org.dows.rbac.biz.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.handler.RoleHandler;
import org.dows.rbac.request.SaveRbacRoleRequest;
import org.dows.rbac.response.RbacRoleResponse;
import org.dows.rbac.service.RbacPermissionService;
import org.dows.rbac.service.RbacRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限角色集管理
 * @date 2024年2月27日 上午11:52:56
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleBiz {
    private final RbacRoleService rbacRoleService;

    private final RoleHandler roleHandler;

    private final RbacPermissionService rbacPermissionService;

//    private final AccountApi accountApi;

//    private final PermissionBiz permissionBiz;

    /**
     * @param
     * @return
     * @说明: 创建或更新角色
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
//    @RbacTrigger(handler = RoleHandler.class)
    @Transactional
    public List<RbacRoleResponse> saveOrUpdateRole(List<SaveRbacRoleRequest> saveRbacRoles) {
        List<RbacRoleEntity> saveOrUpdates = new ArrayList<>();
        List<String> roleNamesToCheck = new ArrayList<>();
        List<Long> roleIdsToCheck = new ArrayList<>();
        // 收集需要检查的角色名和角色ID
        saveRbacRoles.forEach(rbacRole -> {
            if (rbacRole.getRbacRoleId() == null || rbacRole.getRbacRoleId() == 0) {
                roleNamesToCheck.add(rbacRole.getRoleName());
            } else {
                roleIdsToCheck.add(rbacRole.getRbacRoleId());
            }
        });
        // 查询数据库中是否已经存在这些角色名,如果已经存在则不再增加
        List<RbacRoleEntity> existingRoleNames = roleHandler.checkRoleByNames(roleNamesToCheck);
        List<String> roleNames = existingRoleNames.stream().map(RbacRoleEntity::getRoleName).toList();
        // 批量查询所有需要更新的角色
        List<RbacRoleEntity> existingRoleIds = roleHandler.getRoleByIds(roleIdsToCheck);
        // 将查询结果存储在映射中，以便快速查找
        Map<Long, RbacRoleEntity> existingRoleMap = existingRoleIds.stream()
                .collect(Collectors.toMap(RbacRoleEntity::getRbacRoleId, role -> role));
        // 检查角色名是否已存在，并收集需要保存或更新的对象
        saveRbacRoles.forEach(rbacRole -> {
            if (rbacRole.getRbacRoleId() == null || rbacRole.getRbacRoleId() == 0) { // 收集新增对象
                if (roleNames.contains(rbacRole.getRoleName())) {
                    log.info("角色名称已存在: {},将不会被创建", rbacRole.getRoleName());
                    //throw new IllegalArgumentException("角色名称已存在: " + rbacRole.getRoleName());
                } else {
                    RbacRoleEntity newRole = BeanUtil.copyProperties(rbacRole, RbacRoleEntity.class);
                    newRole.setRbacRoleId(null);
                    saveOrUpdates.add(newRole);
                }
            } else { // 收集待更新对象
                RbacRoleEntity existingRole = existingRoleMap.get(rbacRole.getRbacRoleId());
                if (existingRole == null) {
                    log.info("未找到对应的角色ID: {},将不会被更新", rbacRole.getRbacRoleId());
                    //throw new IllegalArgumentException("未找到对应的角色ID: " + rbacRole.getRbacRoleId());
                } else {
                    RbacRoleEntity updatedRole = BeanUtil.copyProperties(rbacRole, RbacRoleEntity.class);
                    saveOrUpdates.add(updatedRole);
                }
            }
        });
        if (CollectionUtil.isEmpty(saveOrUpdates)) {
            return List.of();
        }
        // 统一批量保存或更新
        rbacRoleService.saveOrUpdateBatch(saveOrUpdates);
        return BeanUtil.copyToList(saveOrUpdates, RbacRoleResponse.class);
    }


    /**
     * @param
     * @return
     * @说明: 通过appid查询角色
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public List<RbacRoleResponse> listByAppId(String appId) {
       /* List<RbacRoleEntity> rbacRoleEntities = rbacRoleService.lambdaQuery()
                .eq(Objects.nonNull(appId), RbacRoleEntity::getAppId, appId)
                .list();
        return BeanConvert.beanConvert(rbacRoleEntities, RbacRoleResponse.class);*/
        return null;
    }

    /**
     * @param
     * @return
     * @说明: 分页查询
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    /*public PageResponse<RbacRoleResponse> paging(PageRequest<FindRbacRoleRequest> findRbacRoleRqeust) {
        FindRbacRoleRequest findRbacRole = findRbacRoleRqeust.getQueryObject();
        Page<RbacRoleEntity> page = rbacRoleService.lambdaQuery()
                .eq(Objects.nonNull(findRbacRole.getRbacRoleId()), RbacRoleEntity::getRbacRoleId, findRbacRole.getRbacRoleId())
                .eq(Objects.nonNull(findRbacRole.getPid()), RbacRoleEntity::getPid, findRbacRole.getPid())
                .eq(Objects.nonNull(findRbacRole.getAppId()), RbacRoleEntity::getAppId, findRbacRole.getAppId())
                .eq(Objects.nonNull(findRbacRole.getState()), RbacRoleEntity::getState, findRbacRole.getState())
                .like(Objects.nonNull(findRbacRole.getRoleName()), RbacRoleEntity::getRoleName, findRbacRole.getRoleName())
//                .between(Objects.nonNull(sortRule.getStartTime())&&Objects.nonNull(sortRule.getEndTime()), RbacRoleEntity::getDt,sortRule.getStartTime(),sortRule.getEndTime())
                .page(findRbacRoleRqeust.toPage());

        Page<RbacRoleResponse> result = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        List<RbacRoleResponse> eqptModelResponses = BeanConvert.beanConvert(page.getRecords(), RbacRoleResponse.class);
        result.setRecords(eqptModelResponses);
        return new PageResponse<>(result);
    }*/

    /**
     * @param
     * @return
     * @说明: 根据Id删除权 限角色集
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
   /* @RbacTrigger(handler = RoleDeleteHandler.class)
    @Transactional
    public void deleteByIds(List<Long> rbacRoleIds) {
        // 判断角色是否绑定用户
        List<AccountRoleRelationResponse> accountRoleRelationResponses = accountApi.listUsingRole(rbacRoleIds);
        if (CollectionUtils.isNotEmpty(accountRoleRelationResponses)) {
            log.info("角色删除异常,当前角色存在绑定的用户: {},角色: {}", accountRoleRelationResponses.get(0).getPrincipalName(), accountRoleRelationResponses.get(0).getRoleName());
            throw new IllegalArgumentException("该角色已绑定用户");
        }
        rbacRoleService.removeByIds(rbacRoleIds);
    }*/

    /**
     * @param
     * @return
     * @说明: 根据id查询
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    /*public RbacRoleResponse getById(Long rbacRoleId) {
        RbacRoleEntity rbacRoleEntity = rbacRoleService.getById(rbacRoleId);
        return BeanConvert.beanConvert(rbacRoleEntity, RbacRoleResponse.class);
    }*/

//    public List<RbacRoleResponse> getRolesByAccount() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        List<Long> roleIds = authorities.stream().map(GrantedAuthority::getAuthority).map(Long::parseLong).toList();
//        return permissionBiz.getRole(roleIds);
//    }
}