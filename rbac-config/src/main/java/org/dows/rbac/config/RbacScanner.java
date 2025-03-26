package org.dows.rbac.config;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.config.a.RbacConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;
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
@Slf4j
@RequiredArgsConstructor
@Configuration
public class RbacScanner {
    private final RequestMappingInfoHandlerMapping requestMappingHandlerMapping;
    private final RbacConfig rbacConfig;

    @Value("${spring.application.appId}")
    private String appId;

    @Value("${dows.rbac.uris.scanPackages}")
    private List<String> scanPackages;

    /**
     * 扫描菜单
     *
     * @param basePackage
     * @return
     */
    public Set<Class<?>> scanMenu(String basePackage) {
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
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
    @Bean("uriResources")
    public List<MethodSignature> getAuthResources() {
        // 接下来要添加到数据库的资源
        List<MethodSignature> list = new LinkedList<>();
        // 拿到所有接口信息，并开始遍历
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, handlerMethod) -> {
            // 如果未配置则进行全表扫描
            if (!CollectionUtils.isEmpty(scanPackages)) {
                String packageName = handlerMethod.getBeanType().getPackageName();
                for (String pkg : scanPackages) {
                    // 以什么开头
                    if (packageName.startsWith(pkg)) {
                        list.add(extracted(info, handlerMethod));
                        break;
                    }
                }
            }
            /*// 拿到类(模块)上的权限注解（可填可不填）
            Class<?> beanType = handlerMethod.getBeanType();
            Menu menu = beanType.getAnnotation(Menu.class);
            Uri moduleUri = beanType.getAnnotation(Uri.class);
            // 拿到接口方法上的权限注解
            Method method1 = handlerMethod.getMethod();
            Uri methodUri = method1.getAnnotation(Uri.class);
            Operation operation = method1.getAnnotation(Operation.class);
            String name;
            // package.class.method or classAuth.code.methodAuth.code
            String code;
            if (operation != null) {
                name = operation.summary();
            } else {
                name = method1.getName();
            }
            if (moduleUri != null) {
                code = moduleUri.code();
            } else {
                code = beanType.getName();
            }

            if (methodUri != null) {
                code = code + "." + methodUri.code();
            } else {
                code = code + "." + method1.getName();
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
                    .build();*/
           // MethodSignature methodSignature = extracted(info, handlerMethod);
            //list.add(resource);
//            log.info("akjdlajd:{}",handlerMethod.getBeanType());
        });
        return list;
    }

    private static MethodSignature extracted(RequestMappingInfo info, HandlerMethod handlerMethod) {
        //{GET [/v1/admin/menus/listByAppId]}
        String key = info.toString();
        String[] restUri = key.replaceAll("[\\{\\}\\[\\]]", "").split(" ");
        String httpMethod1 = restUri[0];
        String path1 = restUri[1];
        String javaMethodName = handlerMethod.toString().split("\\(")[0];
        Method method = handlerMethod.getMethod();
        MethodSignature methodSignature = MethodSignatureResolver.parse(method);
        methodSignature.setJavaMethodName(javaMethodName);
        methodSignature.setHttpMethodName(httpMethod1);
        methodSignature.setPath(path1);
        log.info("方法签名 {}", JSONUtil.toJsonPrettyStr(methodSignature));
        return methodSignature;
    }
}
