package org.dows.rbac.config;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.dows.rbac.api.InitResources;
import org.dows.rbac.api.InitUriResources;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.api.annotation.Uri;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 3/20/2024 10:00 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@RequiredArgsConstructor
@Configuration
public class RbacScanner {
    private final RequestMappingInfoHandlerMapping requestMappingHandlerMapping;
    private final RbacConfig rbacConfig;

    /**
     * 扫描菜单
     *
     * @param basePackage
     * @return
     */
    public Set<Class<?>> scanMenu(String basePackage) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Menu.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(basePackage);

        Set<Class<?>> classes = new HashSet<>();
        for (BeanDefinition component : components) {
            try {
                classes.add(Class.forName(component.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }


    /**
     * 扫描并返回所有需要权限处理的接口资源
     * 这里模拟扫描，借助 org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping
     */
    @Bean("resources")
    public List<InitResources> getAuthResources() {
        // 接下来要添加到数据库的资源
        List<InitResources> list = new LinkedList<>();
        // 拿到所有接口信息，并开始遍历
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        List<UriItem> uriPackages = rbacConfig.getUriPackages();
        handlerMethods.forEach((info, handlerMethod) -> {
            String appId = "";
            // 如果未配置则进行全表扫描
            if (!CollectionUtils.isEmpty(uriPackages)) {
                boolean matched = false;
                for (UriItem uriItem : uriPackages) {
                    List<String> scanPackages = uriItem.getScanPackages();
                    // 以什么开头
                    for (String scanPackage : scanPackages) {
                        if (handlerMethod.getBeanType().getPackageName().startsWith(scanPackage)) {
                            matched = true;
                            appId = uriItem.getAppId();
                            break;
                        }
                    }
                }
                if (!matched) {
                    return;
                }
            }
            // 拿到类(模块)上的权限注解（可填可不填）
            Class<?> beanType = handlerMethod.getBeanType();
            Menu menu = beanType.getAnnotation(Menu.class);
            Uri moduleUri = beanType.getAnnotation(Uri.class);
            // 拿到接口方法上的权限注解
            Method method = handlerMethod.getMethod();
            Uri methodUri = method.getAnnotation(Uri.class);
            Operation operation = method.getAnnotation(Operation.class);
            String name;
            // package.class.method or classAuth.code.methodAuth.code
            String code;
            if (operation != null) {
                name = operation.summary();
            } else {
                name = method.getName();
            }
            if (moduleUri != null) {
                code = moduleUri.code();
            } else {
                code = beanType.getName();
            }

            if (methodUri != null) {
                code = code + "." + methodUri.code();
            } else {
                code = code + "." + method.getName();
            }

            // 拿到该接口方法的请求方式(GET、POST等)
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            // 如果一个接口方法标记了多个请求方式，权限id是无法识别的，不进行处理
            if (methods.size() != 1) {
                return;
            }
            // 将请求方式和路径用`:`拼接起来，以区分接口。比如：GET:/user/{id}、POST:/user/{id}
            String httpMethod = methods.toArray()[0].toString();
//            String path = httpMethod + ":" + info.getPathPatternsCondition().getPatterns().toArray()[0];
            String path = info.getPathPatternsCondition().getPatterns().toArray()[0] + "";
            // 将权限名、资源路径、资源类型组装成资源对象，并添加集合中
            InitUriResources resource = InitUriResources.builder()
                    .path(path)
                    .name(name)
                    .code(code)
                    .appId(appId)
                    .method(httpMethod)
                    .menuName(menu != null ? menu.name() : "")
                    .packageName(handlerMethod.getBeanType().getPackageName())
                    //.id() // 用code 代替确保唯一性
                    .build();
            list.add(resource);
        });
        return list;
    }
}
