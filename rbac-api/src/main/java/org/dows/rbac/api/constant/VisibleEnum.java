package org.dows.rbac.api.constant;

public enum VisibleEnum {
    VISIBLE(0, "可见"),
    INVISIBLE(1, "不可见");

    private int code;
    private String desc;

    VisibleEnum(int code, String desc) {
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