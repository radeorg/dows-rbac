package org.dows.rbac.constant;

import lombok.Getter;

@Getter
public enum ResourceType {
    INTERFACE(0, "rbac_uri", "接口"),
    MENU(1, "rbac_menu", "菜单");

    private final int code;
    private final String description;
    private final String source;

    ResourceType(int code, String source, String description) {
        this.code = code;
        this.source = source;
        this.description = description;
    }

    public static ResourceType getByCode(int code) {
        for (ResourceType type : ResourceType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Type code: " + code);
    }

}