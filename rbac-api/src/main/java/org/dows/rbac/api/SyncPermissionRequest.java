package org.dows.rbac.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/2/2024 5:15 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Data
public class SyncPermissionRequest {
    private String appId;

    // 角色ID:需要更新的资源集合
    private Map<Long, List<RbacResources>> permissionMap;


}

