package org.dows.rbac.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.*;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 3/20/2024 9:14 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties({InitSetting.class})
@Configuration
@Data
public class RbacConfig /*implements InitializingBean*/ {
    private final MenuSetting menuSetting = new MenuSetting();
    private final RoleSetting roleSetting = new RoleSetting();
    private final UriSetting uriSetting = new UriSetting();
    // 初始化设置
    private final InitSetting initSetting;

    @PostConstruct
    public void init() throws IOException {
        if(initSetting.isInitScan()){
            log.info("菜单角色接口配置文件扫描开始...");
            Map<String, InitSetting.AppItem> init = initSetting.getInit();
            Map<String, String> fileName = initSetting.getConfigFile();
            if(null == init || null == fileName){
                return;
            }
            Yaml yaml = new Yaml();
            for (Map.Entry<String, InitSetting.AppItem> stringAppItemEntry : init.entrySet()) {
                String key = stringAppItemEntry.getKey();
                String ymlFileName = null;
                Set<String> keys = fileName.keySet();
                for (String configFileKey : keys) {
                    String configFileName = fileName.get(configFileKey);
                    ymlFileName = String.format(configFileName, key);
                    if (ymlFileName.startsWith("classpath://")) {
                        ClassPathResource classPathResource = new ClassPathResource(ymlFileName.substring("classpath://".length()));
                        Map<String, Object> load = yaml.load(classPathResource.getInputStream());
                        if ("rbac".equalsIgnoreCase(configFileKey)) {
                            List<MenuItem> menuItems = getConfigObject(load, "dows.menu.items", MenuItem.class);
                            if(CollectionUtil.isNotEmpty(menuItems)){
                                menuSetting.getItems().addAll(menuItems);
                            }
                            List<UriItem> uriItems = getConfigObject(load, "dows.uri.items", UriItem.class);
                            if(CollectionUtil.isNotEmpty(uriItems)){
                                uriSetting.getItems().addAll(uriItems);
                            }
                            List<RoleItem> roleItems = getConfigObject(load, "dows.role.items", RoleItem.class);
                            if(CollectionUtil.isNotEmpty(roleItems)){
                                roleSetting.getItems().addAll(roleItems);
                            }
                        }
                    }
                    if (ymlFileName.startsWith("file://")) {
                        FileSystemResource fileSystemResource = new FileSystemResource(ymlFileName);
                        Object load = yaml.load(fileSystemResource.getInputStream());
                    }
                    if (ymlFileName.startsWith("http://")) {

                    }
                }
            }
            log.info("菜单角色接口配置文件扫描结束...");
        }
    }

    private <T> List<T> getConfigObject(Map<String, Object> yml, String cron, Class<T> clazz) {
        String[] split = cron.split("\\.");
        Map<String, Object> info = new HashMap<>();
        Object result = null;
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                info = (Map<String, Object>) yml.get(split[i]);
            } else if (i == split.length - 1) {
                result = info.get(split[i]);
            } else {
                info = (Map<String, Object>) info.get(split[i]);
            }
            if (info == null) {
                return null;
            }
        }
        List<T> resultList = new ArrayList<>();
        if (result instanceof List) {
            List results = (List<T>) result;
            for (Object o : results) {
                resultList.add(BeanUtil.toBean(o, clazz));
            }
        }
        return resultList;
    }

    public List<String> getUpdateItemsByAppIdAndItem(String appId, String item) {
        Map<String, InitSetting.AppItem> init = initSetting.getInit();
        if (null != init) {
            for (Map.Entry<String, InitSetting.AppItem> stringAppItemEntry : init.entrySet()) {
                if (stringAppItemEntry.getKey().equals(appId)) {
                    if ("menu".equalsIgnoreCase(item)) {
                        InitItem menu = stringAppItemEntry.getValue().getMenu();
                        if (menu.getUpdate()) {
                            return menu.getItems();
                        }
                    }
                    if ("uri".equalsIgnoreCase(item)) {
                        InitItem uri = stringAppItemEntry.getValue().getUri();
                        if (uri.getUpdate()) {
                            return uri.getItems();
                        }
                    }
                    if ("role".equalsIgnoreCase(item)) {
                        InitItem role = stringAppItemEntry.getValue().getRole();
                        if (role.getUpdate()) {
                            return role.getItems();
                        }
                    }
                }
            }
        }

        return new ArrayList<>();
        /*initSetting.getInit().forEach((k, v) -> {
            if (k.equals(appId)) {
                if ("menu".equalsIgnoreCase(item)) {
                    InitItem menu = v.getMenu();
                    if (menu.getUpdate()) {
                        List<String> menuItems = menu.getItems();
                        // 增加到对应的集合中
                        return;
                    }
                }
                if ("uri".equalsIgnoreCase(item)) {
                    InitItem uri = v.getUri();
                    if (uri.getUpdate()) {
                        List<String> uriItems = uri.getItems();
                        // 增加到对应的集合中
                    }
                }
                if ("role".equalsIgnoreCase(item)) {
                    InitItem role = v.getRole();
                    if (role.getUpdate()) {
                        List<String> roleItems = role.getItems();
                        // 增加到对应的集合中
                    }
                }
            }
        });*/
    }

    public List<UriItem> getUriPackages() {
        return uriSetting.getItems();
    }


    public List<MenuItem> getMenuItems() {
        return menuSetting.getItems();
    }

    public List<RoleItem> getRoleItems() {
        return roleSetting.getItems();
    }

    public Boolean getRoleUpdate() {
        return menuSetting.getUpdate();
    }

    public Boolean getMenuUpdate() {
        return menuSetting.getUpdate();
    }


//    @Override
//    public void afterPropertiesSet() throws Exception {
//
//    }
}

