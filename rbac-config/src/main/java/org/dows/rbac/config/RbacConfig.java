package org.dows.rbac.config;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class RbacConfig {
    private final RequestMappingInfoHandlerMapping requestMappingHandlerMapping;

    @Value("${spring.application.appId}")
    private String appId;

    @Value("${dows.rbac.uris.scanPackages}")
    private List<String> scanPackages;

    /**
     * 扫描并返回所有需要权限处理的接口资源
     * 这里模拟扫描，借助 org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping
     */
    @Bean("uriResources")
    public List<UriSignature> getAuthResources() {
        // 接下来要添加到数据库的资源
        List<UriSignature> list = new LinkedList<>();
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
        });
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
        UriSignature uriSignature = MethodSignatureResolver.parse(method);
        uriSignature.setJavaMethod(javaMethodName);
        uriSignature.setHttpMethod(httpMethod1);
        uriSignature.setUri(path1);
        uriSignature.setAppId(appId);
        log.debug("方法签名: {}", JSONUtil.toJsonPrettyStr(uriSignature));
        return uriSignature;
    }
}
