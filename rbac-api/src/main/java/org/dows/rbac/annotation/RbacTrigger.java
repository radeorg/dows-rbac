package org.dows.rbac.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RbacTrigger {

    //Class<? extends RbacHandler> handler() default RbacHandler.class;

}
