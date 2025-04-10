package org.dows.rbac.handler;


import com.tangzc.mybatisflex.core.FieldTypeHandler;

import java.lang.reflect.Field;

public class MyMetaObjectHandler implements FieldTypeHandler {


    @Override
    public Class<?> getDateType(Class<?> clazz, Field field) {
        return null;
    }
}