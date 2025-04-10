package org.dows.rbac.biz;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.annotation.RbacTrigger;
import org.dows.rbac.entity.RbacPermissionEntity;
import org.dows.rbac.handler.RbacPermissionHandler;
import org.dows.rbac.request.ConfigPermissionRequest;
import org.dows.rbac.response.ConfigPermissionResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RbacPermissionBiz {
    private final RbacPermissionHandler rbacPermissionHandler;


    /**
     * @param
     * @return
     * @说明: 保存权限
     * 解除之前绑定，保存新的绑定
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    @RbacTrigger
    @Transactional
    public List<ConfigPermissionResponse> config(List<ConfigPermissionRequest> configPermissionRequests) {
        // 解除之前绑定
        // 保存角色与权限绑定关系
        // 解除之前绑定
        List<Long> roleIds = configPermissionRequests.stream()
                .map(ConfigPermissionRequest::getRbacRoleId)
                .distinct()
                .collect(Collectors.toList());

        if (!roleIds.isEmpty()) {
            rbacPermissionHandler.removeByRoleIds(roleIds);
        }

        // 预构建 RbacPermissionEntity 对象
        List<RbacPermissionEntity> rbacPermissionEntities = new ArrayList<>();
        configPermissionRequests.forEach(request -> {
            request.getResourceIds().forEach(resourceId -> {
                RbacPermissionEntity entity = new RbacPermissionEntity();
                entity.setRbacRoleId(request.getRbacRoleId());
                entity.setResourceType(request.getResourceType().getCode());
                entity.setState(0);
                entity.setResourceId(resourceId);
                rbacPermissionEntities.add(entity);
            });
        });
        // 保存新的绑定
        if (!rbacPermissionEntities.isEmpty()) {
            rbacPermissionHandler.saveBatch(rbacPermissionEntities);
        }
        // 构建返回对象
        return BeanUtil.copyToList(rbacPermissionEntities, ConfigPermissionResponse.class);
    }
}