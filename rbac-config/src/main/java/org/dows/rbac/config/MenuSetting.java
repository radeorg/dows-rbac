package org.dows.rbac.config;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
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
//@ConfigurationProperties(prefix = "dows.menu")
@Data
public class MenuSetting {
    /**
     * 启动时是否更新菜单
     */
    private Boolean update = false;

    @NestedConfigurationProperty
    private final List<MenuItem> items = new ArrayList<>();

}

