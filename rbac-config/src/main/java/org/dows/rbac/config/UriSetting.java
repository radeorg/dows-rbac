package org.dows.rbac.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 3/20/2024 5:17 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@ConfigurationProperties(prefix = "dows.uri")
@Data
public class UriSetting {

    /**
     * 启动时是否更新角色
     */
    private Boolean update = false;

    @NestedConfigurationProperty
    private final List<UriItem> items = new ArrayList<>();
}

