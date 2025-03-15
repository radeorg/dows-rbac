package org.dows.rbac.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 3/18/2024 6:29 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Uri {
    /**
     * 权限id，模块id + 方法id需要唯一
     */
    long id();

    /**
     * 授权code
     *
     * @return
     */
    String code();

    /**
     * 授权名称
     */
    String name();
}
