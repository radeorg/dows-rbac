package org.dows.rbac.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.dows.rbac.api.RbacHandler;
import org.dows.rbac.api.RbacResources;
import org.dows.rbac.api.admin.request.SaveRbacPermissionRequest;
import org.dows.rbac.api.admin.request.SaveRbacRoleRequest;
import org.dows.rbac.api.annotation.RbacTrigger;
import org.dows.rbac.api.constant.ResourceEnum;
import org.dows.rbac.api.event.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
@Slf4j
public class RbacAspect {

    private final ApplicationEventPublisher publisher;
    private final ApplicationContext applicationContext;

    /*private final RbacHandler menuHandler;
    private final RbacHandler urisHandler;
    private final RbacHandler ruleHandler;
    private final RbacHandler roleHandler;*/
    private final RbacHandler commonHandler;

    @Pointcut("@annotation(org.dows.rbac.api.annotation.RbacTrigger) || @within(org.dows.rbac.api.annotation.RbacTrigger)")
    public void rbacPointcut() {
    }

    @Around("rbacPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object proceed = null;
//        try {
//
//        } catch (Exception e) {
//            log.error("error :{}", e.getMessage());
//            throw new BizException("权限修改异常");
//        }
        proceed = point.proceed();
        publisher.publishEvent(new CommonEvent(""));
        /*String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        RbacTrigger annotation = null;
        Method[] declaredMethods = point.getTarget().getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName)) {
                annotation = declaredMethod.getAnnotation(RbacTrigger.class);
                Class<? extends RbacHandler> handler = annotation.handler();
                if (handler.getName().equals("org.dows.rbac.api.RbacHandler")) {
                    // 更新对应用户的缓存 参数账号id
                    publisher.publishEvent(new RbacEvent(args));
                    break;
                }
                if (handler.getName().equals("org.dows.rbac.handler.RoleHandler")) {
                    // 更新对应用户的缓存 参数账号id
                    publisher.publishEvent(new RoleEvent(args));
                    break;
                }
                if (handler.getName().equals("org.dows.rbac.handler.RoleDeleteHandler")) {
                    // 更新对应用户的缓存 参数账号id
                    publisher.publishEvent(new RoleDeleteEvent(args));
                    break;
                }
                if (handler.getName().equals("org.dows.rbac.handler.RuleHandler")) {
                    // 更新对应用户的缓存 参数账号id
                    publisher.publishEvent(new RuleEvent(""));
                    break;
                }
                if (handler.getName().equals("org.dows.rbac.handler.CommonHandler")) {
                    // 更新对应用户的缓存 参数账号id
                    publisher.publishEvent(new CommonEvent(""));
                    break;
                }
            }
        }*/

        return proceed;
    }


    /*@EventListener(value = RuleEvent.class*//*condition = "#p0.getClass().equals('rbacEvent')"*//*)
    public void adminListen(RuleEvent rbacEvent) {
        ruleHandler.handle(null);
    }*/

    @EventListener(value = CommonEvent.class/*condition = "#p0.getClass().equals('rbacEvent')"*/)
    public void adminListen(CommonEvent commonEvent) {
        commonHandler.handle(commonEvent);
    }

    /*@EventListener(value = RoleEvent.class*//*condition = "#p0.getClass().equals('rbacEvent')"*//*)
    public void adminListen(RoleEvent rbacEvent) {
        List<Object> listObject = (List<Object>) Convert.toList(rbacEvent.getSource());
        List<SaveRbacRoleRequest> saveRbacRoles = null;
        if (CollectionUtil.isNotEmpty(listObject)) {
            saveRbacRoles = (List)listObject.get(0);
        }
//        List<SaveRbacRoleRequest> saveRbacRoles = (List)Arrays.asList(rbacEvent.getSource());
        Map<Long,SaveRbacRoleRequest> map = new HashMap();
        for (SaveRbacRoleRequest saveRbacRole : saveRbacRoles) {
            if(null != saveRbacRole.getRbacRoleId()) {
                map.put(saveRbacRole.getRbacRoleId(),saveRbacRole);
            }
        }
        roleHandler.handle(map);
    }


    @EventListener(value = RoleDeleteEvent.class*//*condition = "#p0.getClass().equals('rbacEvent')"*//*)
    public void adminListen(RoleDeleteEvent rbacEvent) {
        List<Object> listObject = (List<Object>) Convert.toList(rbacEvent.getSource());
        List<Long> roleIds = null;
        if (CollectionUtil.isNotEmpty(listObject)) {
            roleIds = (List)listObject.get(0);
        }
        roleHandler.handle(roleIds);
    }


    @EventListener(value = RbacEvent.class*//*condition = "#p0.getClass().equals('rbacEvent')"*//*)
    public void adminListen(RbacEvent rbacEvent) {
//        List<SaveRbacPermissionRequest> saveRbacPermissionRequests = (List<SaveRbacPermissionRequest>) (Convert.toList(rbacEvent.getSource()).get(0));
        List<Object> listObject = (List<Object>) (Convert.toList(rbacEvent.getSource()));
        List<SaveRbacPermissionRequest> saveRbacPermissionRequests = null;
        if(CollectionUtil.isNotEmpty(listObject)){
            saveRbacPermissionRequests = (List<SaveRbacPermissionRequest>) listObject.get(0);
        }
//        List<SaveRbacPermissionRequest> saveRbacPermissionRequests = new ArrayList<>(Arrays.asList((SaveRbacPermissionRequest) rbacEvent.getSource()));
        // 记录menu uri
        Map<Long, List<RbacResources>> updateMenu = new HashMap<>();
        Map<Long, List<RbacResources>> updateUri = new HashMap<>();
        for (SaveRbacPermissionRequest saveRbacPermissionRequest : saveRbacPermissionRequests) {
            List<RbacResources> rbacResources = saveRbacPermissionRequest.getRbacResource();
            if(CollectionUtil.isEmpty(rbacResources)){
                continue;
            }
            // 处理多个角色待更新的menu
            List<RbacResources> menus = updateMenu.get(saveRbacPermissionRequest.getRbacRoleId());
            if (menus == null) {
                menus = new ArrayList<>();
            }
            List<RbacResources> list = rbacResources.stream().filter(r -> r.getResourceType() == ResourceEnum.MENU.getCode()).toList();
            if(CollectionUtil.isNotEmpty(list)){
                menus.addAll(list);
            }
            updateMenu.put(saveRbacPermissionRequest.getRbacRoleId(), menus);

            // 处理多个角色待更新的uri
            List<RbacResources> uris = updateUri.get(saveRbacPermissionRequest.getRbacRoleId());
            if (uris == null) {
                uris = new ArrayList<>();
            }
            List<RbacResources> list1 = rbacResources.stream().filter(r -> r.getResourceType() == ResourceEnum.INTERFACE.getCode()).toList();
            if (CollectionUtil.isNotEmpty(list1)){
                uris.addAll(list1);
            }
            updateUri.put(saveRbacPermissionRequest.getRbacRoleId(), uris);
        }
        menuHandler.handle(updateMenu);
        urisHandler.handle(updateUri);
    }*/

}
