package org.dows.rbac.config;

import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.RbacInitializable;
import org.dows.rbac.UriSignature;
import org.dows.rbac.properties.RbacProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
@EnableConfigurationProperties(RbacProperties.class)
public class RbacConfig {
    private final RequestMappingInfoHandlerMapping requestMappingHandlerMapping;

    private final RbacProperties rbacProperties;

    private final RbacInitializable rbacInitializable;

    @Value("${spring.application.appId}")
    private String appId;

    /**
     * 扫描并返回所有需要权限处理的接口资源
     * 这里模拟扫描，借助 org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping
     */
    @Bean
    public List<UriSignature> initRbacUri() {
        List<UriSignature> list = buildRbacUri();
        String initModel = rbacProperties.getUris().getInitModel();
        if (initModel.equals("always")) {
            rbacInitializable.initRbacUri(list);
        }
        return list;
    }

    public List<UriSignature> buildRbacUri() {
        // 接下来要添加到数据库的资源
        List<UriSignature> list = new LinkedList<>();
        List<String> scanPackages = rbacProperties.getUris().getScanPackages();
        // 校验 scanPackages 是否有效
        if (scanPackages == null || scanPackages.isEmpty()) {
            log.warn("scanPackages is empty or null, no resources will be scanned.");
            return list;
        }

        // 获取所有接口信息，并开始遍历
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        if (handlerMethods.isEmpty()) {
            log.warn("No handler methods found in requestMappingHandlerMapping.");
            return list;
        }

        Set<String> validPackages = scanPackages.stream()
                .filter(pkg -> pkg != null && !pkg.trim().isEmpty())
                .collect(Collectors.toSet());

        handlerMethods.forEach((info, handlerMethod) -> {
            String packageName = handlerMethod.getBeanType().getPackageName();
            if (validPackages.isEmpty()) {
                return;
            }

            for (String pkg : validPackages) {
                if (packageName.startsWith(pkg)) {
                    list.add(extracted(info, handlerMethod));
                    break;
                }
            }
        });
        // todo 保存数据库，生成 lock（如果初始话成功，不在初始化）
        try {
            Files.writeString(Path.of(System.getProperty("user.dir")).resolve("menu.json"),JSONUtil.toJsonPrettyStr(list));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private UriSignature extracted(RequestMappingInfo info, HandlerMethod handlerMethod) {
        //{GET [/v1/admin/menus/listByAppId]}
        String key = info.toString();
        String[] restUri = key.replaceAll("[\\{\\}\\[\\]]", "").split(" ");
        String httpMethod1 = restUri[0];
        String path1 = restUri[1];
        String javaMethodName = handlerMethod.toString().split("\\(")[0];

        Method method = handlerMethod.getMethod();
        Operation operation = method.getAnnotation(Operation.class);
        String description = operation.description();
        String summary = operation.summary();
        UriSignature uriSignature = MethodSignatureResolver.parse(method);
        uriSignature.setDescription(description);
        uriSignature.setSummary(summary);
        uriSignature.setJavaMethod(javaMethodName);
        uriSignature.setHttpMethod(httpMethod1);
        uriSignature.setUri(path1);
        uriSignature.setAppId(appId);
        log.debug("方法签名: {}", JSONUtil.toJsonPrettyStr(uriSignature));
        return uriSignature;
    }
}
