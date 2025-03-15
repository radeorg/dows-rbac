package org.dows.rbac;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.dows.rbac.handler.RbacDataPermissionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"org.dows.uat.account.mapper", "org.dows.uat.user.mapper", "org.dows.rbac.mapper", "org.dows.app.mapper"}, annotationClass = Mapper.class)
public class MybatisConfig {

    @Autowired
    @Lazy
//    private RbacDataPermissionHandler rbacDataPermissionHandler;
    private RbacDataPermissionHandler rbacDataPermissionHandler;

    /**
     * 分页插件
     */

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new DataPermissionInterceptor(rbacDataPermissionHandler));
//        interceptor.addInnerInterceptor(rbacDataPermissionHandler);
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}