package org.dows.rbac.api.constant;

public enum StateEnum {
    AVAILABLE(0, "可用"),
    UNAVAILABLE(1, "不可用");

    private final int code;
    private final String desc;

    StateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}