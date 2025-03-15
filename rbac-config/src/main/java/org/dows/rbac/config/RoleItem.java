package org.dows.rbac.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 3/20/2024 5:12 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleItem extends RbacItem {
    private String parentCode;
}

