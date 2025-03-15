package org.dows.rbac.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuItem extends RbacItem {

    // 多个角色用","分割
    private String roles;
    // 路径
    private String path;
    // 骚包
    private List<String> scanPackages;
}