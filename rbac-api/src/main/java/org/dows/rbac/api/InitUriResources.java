package org.dows.rbac.api;

import lombok.Builder;
import lombok.Data;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 3/19/2024 1:55 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Builder
@Data
public class InitUriResources implements InitResources {
    private Long id;
    private Long pid;
    private String name;
    private String path;
    private String code;
    private String method;
    private Integer type;
    private String appId;
    private String packageName;
    private String menuName;

    @Override
    public Integer getType() {
        return 1;
    }
}

