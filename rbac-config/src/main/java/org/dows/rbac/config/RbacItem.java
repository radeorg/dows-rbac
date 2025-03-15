package org.dows.rbac.config;

import lombok.Data;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 3/20/2024 5:13 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class RbacItem {
    // 应用ID
    private String appId;
    // 名称
    private String name;
    // code
    private String code;

}

