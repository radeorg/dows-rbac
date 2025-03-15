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
public class InitMenuResources implements InitResources {

    private Long id;
    private Long pid;
    private String name;
    private String packageName;
    private String path;
    private String menuName;
    private String code;
    private String method;
    private String appId;
    private Integer type;

    @Override
    public Integer getType() {
        return 0;
    }



}

