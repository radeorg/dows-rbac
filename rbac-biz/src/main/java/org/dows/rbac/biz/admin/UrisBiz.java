package org.dows.rbac.biz.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.api.admin.request.SaveUrisRequest;
import org.dows.rbac.api.admin.response.RbacUriResponse;
import org.dows.rbac.handler.a.PermissionHandler;
import org.dows.rbac.handler.a.UrisHandler;
import org.dows.rbac.service.RbacUriService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限接口集管理
 * @date 2024年2月27日 上午11:52:56
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UrisBiz {
    private final RbacUriService rbacUriService;

    private final UrisHandler urisHandler;

    private final PermissionHandler permissionHandler;

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
    // todo 触发通知缓存变化
    //@RbacTrigger(handler = CommonHandler.class)
    @Transactional
    public void save(List<SaveUrisRequest> saveUris) {
        for (SaveUrisRequest uris : saveUris) {
            if (urisHandler.hasUriName(uris.getName(), uris.getAppId())) {
                throw new IllegalArgumentException("菜单名称已存在");
            }
        }
        urisHandler.save(saveUris);
    }

    /**
     * @param
     * @return
     * @说明: 根据appid查询应用的接口集
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public List<RbacUriResponse> listByAppId() {
        /*String appId = AppContext.getAppId();
        if (Objects.isNull(appId)) {
            throw new BizException("appId不能为空");
        }
        List<RbacUriEntity> rbacUriEntities = rbacUriService.lambdaQuery()
                .eq(Objects.nonNull(appId), RbacUriEntity::getAppId, appId)
                .list();
        return BeanConvert.beanConvert(rbacUriEntities, RbacUriResponse.class);*/
        return null;
    }

    /**
     * @param
     * @return
     * @说明: 删除
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    // todo 触发通知
//    @RbacTrigger(handler = CommonHandler.class)
    @Transactional
    public void deleteByIds(List<Long> uriIds) {
       /* List<RbacPermissionEntity> permissions = permissionHandler.getPermissionByResourceIds(uriIds, ResourceEnum.INTERFACE.getCode());
        if (CollectionUtil.isNotEmpty(permissions)) {
            log.info("存在角色绑定接口，不能删除");
            throw new BizException("存在角色绑定接口，不能删除");
        }
        rbacUriService.removeByIds(uriIds);*/
    }
}