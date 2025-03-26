package org.dows.rbac.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MethodSignature {
    private String javaMethodName;
    private String httpMethodName;
    private String path;
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