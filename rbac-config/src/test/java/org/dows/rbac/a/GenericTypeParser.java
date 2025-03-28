package org.dows.rbac.a;

import org.dows.rade.model.UriSignature;

import java.lang.reflect.*;

public class GenericTypeParser {

    public static void parseGenericReturnType(Method method, UriSignature uriSignature) {
        Type returnType = method.getGenericReturnType();

        if (returnType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) returnType;

            // 获取原始类型 (如List)
            Class<?> rawType = (Class<?>) pType.getRawType();
            System.out.println("原始类型: " + rawType.getName());

            // 获取泛型参数
            Type[] typeArgs = pType.getActualTypeArguments();
            for (Type typeArg : typeArgs) {
                System.out.println("泛型参数: " + typeArg.getTypeName());

                // 如果泛型参数本身也是参数化类型
                if (typeArg instanceof ParameterizedType) {
                    ParameterizedType nestedType = (ParameterizedType) typeArg;
                    System.out.println("  嵌套泛型: " + nestedType.getRawType().getTypeName());
                }
            }
        } else if (returnType instanceof Class<?>) {
            System.out.println("非泛型类型: " + ((Class<?>) returnType).getName());
        } else if (returnType instanceof TypeVariable) {
            System.out.println("类型变量: " + ((TypeVariable<?>) returnType).getName());
        } else if (returnType instanceof WildcardType) {
            System.out.println("通配符类型: " + returnType.getTypeName());
        }
    }
}