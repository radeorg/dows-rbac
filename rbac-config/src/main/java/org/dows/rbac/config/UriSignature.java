package org.dows.rbac.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UriSignature {

    private String appId;
    private String javaMethod;
    private String httpMethod;
    private String uri;
    // 参数列表
    private List<ParameterMeta> parameters = new ArrayList<>();
    // 返回类型元数据
    private ParameterMeta returnType;

    @Override
    public String toString() {
        return "MethodSignature{\n" +
                "parameters=" + parameters + ",\n" +
                "returnType=" + returnType + "\n" +
                '}';
    }
}