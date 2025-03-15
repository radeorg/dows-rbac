package org.dows.rbac.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * @description: name: '系统管理'
 * code:  'system'
 * scanPackage:</br>
 * @author: lait.zhang@gmail.com
 * @date: 3/20/2024 9:12 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@ConfigurationProperties(prefix = "dows.app")
@Data
public class AppSetting {
    /**
     * 启动时是否更新菜单
     */

    @NestedConfigurationProperty
    private List<AppItem> items;

}

