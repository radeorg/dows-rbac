package org.dows.rbac;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dows.rade.crud.BaseEntity;
import org.dows.rbac.handler.FieldFillListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MyBatisFlexConfig {

    @Bean
    public FieldFillListener fieldFillListener() {
        return new FieldFillListener();
    }

    @Value("${rade.log.sql.printSql:false}")
    private boolean printSql;

    public MyBatisFlexConfig() {
        if (printSql) {
            //开启审计功能
            AuditManager.setAuditEnable(true);
            //设置 SQL 审计收集器
            AuditManager.setMessageCollector(auditMessage ->
                    log.info("{},{}ms", auditMessage.getFullSql(), auditMessage.getElapsedTime())
            );
        }
    }

    @PostConstruct
    public void initFlexGlobalConfig() {
        // 获取全局配置
        FlexGlobalConfig defaultConfig = FlexGlobalConfig.getDefaultConfig();
        // 为所有 BaseEntity 的子类添加监听器
        defaultConfig.registerInsertListener(fieldFillListener(), BaseEntity.class);
        defaultConfig.registerUpdateListener(fieldFillListener(), BaseEntity.class);
    }
}