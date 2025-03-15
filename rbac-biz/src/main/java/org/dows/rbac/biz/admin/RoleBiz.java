package org.dows.rbac.biz.admin;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.api.model.PageRequest;
import org.dows.framework.crud.api.model.PageResponse;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.admin.request.FindRbacRoleRequest;
import org.dows.rbac.api.admin.request.SaveRbacRoleRequest;
import org.dows.rbac.api.admin.response.RbacRoleResponse;
import org.dows.rbac.api.annotation.RbacTrigger;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.handler.RoleDeleteHandler;
import org.dows.rbac.handler.RoleHandler;
import org.dows.rbac.repository.RbacPermissionRepository;
import org.dows.rbac.repository.RbacRoleRepository;
import org.dows.uat.api.AccountApi;
import org.dows.uat.api.admin.response.AccountRoleRelationResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限角色集管理
 * @date 2024年2月27日 上午11:52:56
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleBiz {
    private final RbacRoleRepository rbacRoleRepository;

    private final RoleHandler roleHandler;

    private final RbacPermissionRepository rbacPermissionRepository;

    private final AccountApi accountApi;

    private final Integer FIRST_LEVEL = 1;

    private final Long ROOT_PID = 0L;

    private final PermissionBiz permissionBiz;

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
    @RbacTrigger(handler = RoleHandler.class)
    @Transactional
    public void save(List<SaveRbacRoleRequest> saveRbacRoles) {
        saveRbacRoles.forEach(saveRbacRoleRequest -> {
            if(null == saveRbacRoleRequest.getRbacRoleId()){
                if (roleHandler.hasRoleName(saveRbacRoleRequest.getRoleName(),saveRbacRoleRequest.getAppId())) {
                    throw new IllegalArgumentException("角色名称已存在");
                }
                long id = IdWorker.getId();
                saveRbacRoleRequest.setRbacRoleId(id);
            }
            StringBuilder idPath = new StringBuilder();
            StringBuilder namePath = new StringBuilder();
            StringBuilder codePath = new StringBuilder();
            if (saveRbacRoleRequest.getPid() == 0 || null == saveRbacRoleRequest.getPid()) {
                idPath.append(saveRbacRoleRequest.getRbacRoleId());
                namePath.append(saveRbacRoleRequest.getRoleName());
                codePath.append(saveRbacRoleRequest.getRoleCode());
                saveRbacRoleRequest.setPid(ROOT_PID);
            } else {
                if (Objects.nonNull(saveRbacRoleRequest.getPreIdPath()) && Objects.nonNull(saveRbacRoleRequest.getPreNamePath()) && Objects.nonNull(saveRbacRoleRequest.getPreCodePath())) {
                    idPath.append(saveRbacRoleRequest.getPreIdPath()).append("/").append(saveRbacRoleRequest.getRbacRoleId());
                    namePath.append(saveRbacRoleRequest.getPreNamePath()).append("/").append(saveRbacRoleRequest.getRoleName());
                    codePath.append(saveRbacRoleRequest.getPreCodePath()).append("/").append(saveRbacRoleRequest.getRoleCode());
                } else {
                    if (Objects.isNull(saveRbacRoleRequest.getPid())) {
                        throw new IllegalArgumentException("父类id为空");
                    }
                    RbacRoleEntity preRbacRoleEntity = rbacRoleRepository.getById(saveRbacRoleRequest.getPid());
                    if (Objects.isNull(preRbacRoleEntity)) {
                        throw new IllegalArgumentException("未找到对应父类信息");
                    }
                    idPath.append(preRbacRoleEntity.getIdPath()).append("/").append(saveRbacRoleRequest.getRbacRoleId());
                    namePath.append(preRbacRoleEntity.getNamePath()).append("/").append(saveRbacRoleRequest.getRoleName());
                    codePath.append(preRbacRoleEntity.getCodePath()).append("/").append(saveRbacRoleRequest.getCodePath());
                }
            }
            saveRbacRoleRequest.setIdPath(idPath.toString());
            saveRbacRoleRequest.setNamePath(namePath.toString());
            saveRbacRoleRequest.setCodePath(codePath.toString());
        });
        List<RbacRoleEntity> rbacRoleEntities = BeanConvert.beanConvert(saveRbacRoles, RbacRoleEntity.class);
        rbacRoleRepository.saveOrUpdateBatch(rbacRoleEntities);
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
        List<RbacRoleEntity> rbacRoleEntities = rbacRoleRepository.lambdaQuery()
                .eq(Objects.nonNull(appId), RbacRoleEntity::getAppId, appId)
                .list();
        return BeanConvert.beanConvert(rbacRoleEntities, RbacRoleResponse.class);
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
    public PageResponse<RbacRoleResponse> paging(PageRequest<FindRbacRoleRequest> findRbacRoleRqeust) {
        FindRbacRoleRequest findRbacRole = findRbacRoleRqeust.getQueryObject();
        Page<RbacRoleEntity> page = rbacRoleRepository.lambdaQuery()
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
    }

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
    @RbacTrigger(handler = RoleDeleteHandler.class)
    @Transactional
    public void deleteByIds(List<Long> rbacRoleIds) {
        // 判断角色是否绑定用户
        List<AccountRoleRelationResponse> accountRoleRelationResponses = accountApi.listUsingRole(rbacRoleIds);
        if (CollectionUtils.isNotEmpty(accountRoleRelationResponses)) {
            log.info("角色删除异常,当前角色存在绑定的用户: {},角色: {}", accountRoleRelationResponses.get(0).getPrincipalName(), accountRoleRelationResponses.get(0).getRoleName());
            throw new IllegalArgumentException("该角色已绑定用户");
        }
        rbacRoleRepository.removeByIds(rbacRoleIds);
    }

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
    public RbacRoleResponse getById(Long rbacRoleId) {
        RbacRoleEntity rbacRoleEntity = rbacRoleRepository.getById(rbacRoleId);
        return BeanConvert.beanConvert(rbacRoleEntity, RbacRoleResponse.class);
    }

    public List<RbacRoleResponse> getRolesByAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<Long> roleIds = authorities.stream().map(GrantedAuthority::getAuthority).map(Long::parseLong).toList();
        return permissionBiz.getRole(roleIds);
    }
}