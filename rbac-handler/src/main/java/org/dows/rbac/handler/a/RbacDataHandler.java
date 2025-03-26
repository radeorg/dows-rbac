package org.dows.rbac.handler.a;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.api.admin.response.RbacUriRoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RbacDataHandler {


    private final RbacCache rbacCache;

    private final RoleHandler roleHandler;

    private final RuleHandler ruleHandler;

    private final PermissionHandler PermissionHandler;

    public Map<String, List<RbacUriRoleResponse>> getRbacRoleUris() {
        /*List<RbacUriRoleResponse> rbacUriRoleResponses = new ArrayList<>();
        List<Long> roleIds = rbacCache.getAllRoleIds();
        if (CollectionUtil.isEmpty(roleIds)) {
            return null;
        }
        for (Long roleId : roleIds) {
            List<RbacUriEntity> rbacUriEntities = rbacCache.getUriByRoleId(roleId);
            if (CollectionUtil.isNotEmpty(rbacUriEntities)) {
                List<RbacUriRoleResponse> rbacUriRoleResponses1 = BeanConvert.beanConvert(rbacUriEntities, RbacUriRoleResponse.class);
                if (CollectionUtil.isNotEmpty(rbacUriRoleResponses1)) {
                    rbacUriRoleResponses1.forEach(response -> {
                        response.setRoleId(roleId);
                    });
                    rbacUriRoleResponses.addAll(rbacUriRoleResponses1);
                }
            }

        }
        return rbacUriRoleResponses.stream().collect(Collectors.groupingBy(RbacUriRoleResponse::getUrl));*/
        return null;
    }


    @PostConstruct
    public void initData() {
        /*log.info("初始化角色缓存接口和菜单信息");
        List<RbacRoleEntity> allRoles = roleHandler.getAllRoles();
        List<Long> roleIds = new ArrayList<>();
        for (RbacRoleEntity role : allRoles) {
            List<RbacUriEntity> rbacUriEntities = PermissionHandler.getUriByRoleId(role.getRbacRoleId());
            List<RbacMenuEntity> rbacMenuEntities = PermissionHandler.getMenusByRoleId(role.getRbacRoleId());
            rbacCache.putUriByRoleId(role.getRbacRoleId(), rbacUriEntities);
            rbacCache.putMenuByRoleId(role.getRbacRoleId(), rbacMenuEntities);
            rbacCache.putRoleByRoleId(role.getRbacRoleId(), role);
            roleIds.add(role.getRbacRoleId());
        }
        rbacCache.putAllRoleIdByRoleId(roleIds);
        List<RbacRuleEntity> rules = ruleHandler.getRules(new FindRbacRulesRequest());
        rbacCache.putAllRules(rules);
        log.info("初始化角色缓存接口和菜单信息结束");*/
    }


}
