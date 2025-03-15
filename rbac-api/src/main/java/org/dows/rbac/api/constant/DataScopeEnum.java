package org.dows.rbac.api.constant;

public enum DataScopeEnum {
    ALL(0, "所有数据"),
    DEPT_AND_SUB(1, "所在组及子组数据"),
    DEPT(2, "所在组数据"),
    SELF(3, "本人数据"),
    NONE(99, "其他");

    private final int value;
    private final String description;

    DataScopeEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static DataScopeEnum getByValue(int value) {
        for (DataScopeEnum scope : values()) {
            if (scope.value == value) {
                return scope;
            }
        }
        throw new IllegalArgumentException("Invalid DataScopeEnum value: " + value);
    }
}