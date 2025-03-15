package org.dows.rbac.api.constant;

import lombok.Getter;

/**
 * 数据权限枚举
 */
@Getter
public enum DataScope {

    /**
     * value 越小，数据权限范围越大
     */
    ALL(0, "所有数据"),
    GROUP_AND_SUB(1, "组及子组数据"),
    GROUP(2, "本组数据"),
    SELF(3, "本人数据");

    private Integer value;

    private String label;

    DataScope(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
