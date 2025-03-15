package org.dows.rbac.biz.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.exceptions.BizException;
import org.dows.framework.crud.api.model.PageRequest;
import org.dows.framework.crud.api.model.PageResponse;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.admin.request.FindRbacMenusRequest;
import org.dows.rbac.api.admin.request.SaveRbacMenusRequest;
import org.dows.rbac.api.admin.response.RbacMenusResponse;
import org.dows.rbac.api.annotation.RbacTrigger;
import org.dows.rbac.api.constant.ResourceEnum;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.entity.RbacPermissionEntity;
import org.dows.rbac.handler.CommonHandler;
import org.dows.rbac.handler.MenuHandler;
import org.dows.rbac.handler.PermissionHandler;
import org.dows.rbac.handler.RoleHandler;
import org.dows.rbac.repository.RbacMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限菜单集管理
 * @date 2024年2月27日 上午11:52:56
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MenusBiz {
    private final RbacMenuRepository rbacMenusRepository;

    private final PermissionHandler permissionHandler;

    private final MenuHandler menuHandler;

    private final Integer FIRST_LEVEL = 1;

    private final Long ROOT_PID = 0L;

    /**
     * @param
     * @return
     * @说明: 批量更新和插入
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    // todo 触发缓存变化
    @RbacTrigger(handler = CommonHandler.class)
    @Transactional
    public void save(List<SaveRbacMenusRequest> saveRbacMenus) {

        saveRbacMenus.forEach(rbacMenuEntity -> {
            if(null == rbacMenuEntity.getRbacMenuId()){
                if (menuHandler.hasMenuName(rbacMenuEntity.getName(),rbacMenuEntity.getAppId())) {
                    throw new IllegalArgumentException("菜单名称已存在");
                }
                long id = IdWorker.getId();
                rbacMenuEntity.setRbacMenuId(id);
            }
            StringBuilder idPath = new StringBuilder();
            StringBuilder namePath = new StringBuilder();
            StringBuilder codePath = new StringBuilder();
            if (FIRST_LEVEL.equals(rbacMenuEntity.getLevel())) {
                idPath.append(rbacMenuEntity.getRbacMenuId());
                namePath.append(rbacMenuEntity.getName());
                codePath.append(rbacMenuEntity.getCode());
                rbacMenuEntity.setPid(ROOT_PID);
            } else {
                if (Objects.nonNull(rbacMenuEntity.getPreIdPath()) && Objects.nonNull(rbacMenuEntity.getPreNamePath()) && Objects.nonNull(rbacMenuEntity.getPreCodePath())) {
                    idPath.append(rbacMenuEntity.getPreIdPath()).append("/").append(rbacMenuEntity.getRbacMenuId());
                    namePath.append(rbacMenuEntity.getPreNamePath()).append("/").append(rbacMenuEntity.getName());
                    codePath.append(rbacMenuEntity.getPreCodePath()).append("/").append(rbacMenuEntity.getCode());
                } else {
                    if (Objects.isNull(rbacMenuEntity.getPid())) {
                        throw new IllegalArgumentException("父类id为空");
                    }
                    RbacMenuEntity preRbacMenuEntity = rbacMenusRepository.getById(rbacMenuEntity.getPid());
                    if (Objects.isNull(preRbacMenuEntity)) {
                        throw new IllegalArgumentException("未找到对应父类信息");
                    }
                    idPath.append(preRbacMenuEntity.getIdPath()).append("/").append(rbacMenuEntity.getRbacMenuId());
                    namePath.append(preRbacMenuEntity.getNamePath()).append("/").append(rbacMenuEntity.getName());
                    codePath.append(preRbacMenuEntity.getCodePath()).append("/").append(rbacMenuEntity.getCode());
                }
            }
            rbacMenuEntity.setIdPath(idPath.toString());
            rbacMenuEntity.setNamePath(namePath.toString());
            rbacMenuEntity.setCodePath(codePath.toString());
        });
        List<RbacMenuEntity> rbacMenusEntities = BeanConvert.beanConvert(saveRbacMenus, RbacMenuEntity.class);
        rbacMenusRepository.saveOrUpdateBatch(rbacMenusEntities);
    }

    /**
     * @param
     * @return
     * @说明: 根据appid查询应用的菜单集
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public List<RbacMenusResponse> listByAppId(String appId) {
        List<RbacMenuEntity> menusEntities = rbacMenusRepository.lambdaQuery()
                .eq(Objects.nonNull(appId), RbacMenuEntity::getAppId, appId)
                .list();

        return BeanConvert.beanConvert(menusEntities, RbacMenusResponse.class);
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
    public PageResponse<RbacMenusResponse> paging(PageRequest<FindRbacMenusRequest> findRbacMenus) {

        FindRbacMenusRequest queryObject = findRbacMenus.getQueryObject();
        Page<RbacMenuEntity> page = rbacMenusRepository.lambdaQuery()
                .eq(Objects.nonNull(queryObject.getRbacMenuId()), RbacMenuEntity::getRbacMenuId, queryObject.getRbacMenuId())
                .eq(Objects.nonNull(queryObject.getPid()), RbacMenuEntity::getPid, queryObject.getPid())
                .eq(Objects.nonNull(queryObject.getAppId()), RbacMenuEntity::getAppId, queryObject.getAppId())
                .eq(Objects.nonNull(queryObject.getState()), RbacMenuEntity::getState, queryObject.getState())
                .like(Objects.nonNull(queryObject.getName()), RbacMenuEntity::getName, queryObject.getName())
                .between(Objects.nonNull(queryObject.getStartTime()) && Objects.nonNull(queryObject.getEndTime()), RbacMenuEntity::getDt, queryObject.getStartTime(), queryObject.getEndTime())
                .page(findRbacMenus.toPage());

        Page<RbacMenusResponse> result = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        List<RbacMenusResponse> eqptModelResponses = BeanConvert.beanConvert(page.getRecords(), RbacMenusResponse.class);
        result.setRecords(eqptModelResponses);
        return new PageResponse<>(result);
    }

    /**
     * @param
     * @return
     * @说明: 根据ID删除菜单集
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    // todo 触发缓存变化
    @RbacTrigger(handler = CommonHandler.class)
    @Transactional
    public void deleteByIds(List<Long> rbacMenusIds) {
        List<RbacPermissionEntity> permissions = permissionHandler.getPermissionByResourceIds(rbacMenusIds, ResourceEnum.MENU.getCode());
        if(CollectionUtil.isNotEmpty(permissions)){
            log.info("存在角色绑定，不能删除");
            throw new BizException("存在角色绑定，不能删除");
        }
        rbacMenusRepository.removeByIds(rbacMenusIds);
    }
}