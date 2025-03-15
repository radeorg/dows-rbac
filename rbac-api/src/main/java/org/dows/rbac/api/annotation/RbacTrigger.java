package org.dows.rbac.api.annotation;

import org.dows.rbac.api.RbacHandler;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RbacTrigger {

    Class<? extends RbacHandler> handler() default RbacHandler.class;

}
