package org.dows.rbac.constant;

public enum OrderEnum {
    ASC("asc"),
    DESC("desc");

    private final String value;

    OrderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}