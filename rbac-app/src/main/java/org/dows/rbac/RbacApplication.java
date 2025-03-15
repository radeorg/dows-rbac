package org.dows.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author
 * @description
 * @date 2024年2月26日 上午9:44:35
 */
@SpringBootApplication(scanBasePackages = {"org.dows.framework", "org.dows.uat", "org.dows.rbac", "org.dows.aac", "com.shdy.admin"})
public class RbacApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }
}

