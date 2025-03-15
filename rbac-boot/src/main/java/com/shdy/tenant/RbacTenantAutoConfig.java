package com.shdy.tenant;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 3/20/2024 10:41 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Configuration
@ComponentScan(basePackages = {"org.dows.rbac.mapper", "org.dows.rbac.repository",
        "org.dows.rbac.config", "org.dows.rbac.biz", "org.dows.rbac.handler", "org.dows.rbac.rest.tenant"})
public class RbacTenantAutoConfig {
}

