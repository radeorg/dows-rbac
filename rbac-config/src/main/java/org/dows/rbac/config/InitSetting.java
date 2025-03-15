package org.dows.rbac.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @description: 初始化设置</ br>
 * @author: lait.zhang@gmail.com
 * @date: 3/21/2024 5:42 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@ConfigurationProperties(prefix = "dows.rbac")
@Data
public class InitSetting {

    // 是否开启初始化扫描
    private boolean initScan;

    private Map<String, AppItem> init;

    private Map<String, String> configFile;

    @Data
    public static class AppItem {
        private InitItem menu;
        private InitItem uri;
        private InitItem role;
    }

}

