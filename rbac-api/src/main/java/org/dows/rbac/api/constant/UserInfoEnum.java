package org.dows.rbac.api.constant;

public enum UserInfoEnum {
    USER_INFO("aac:user:info", "用户信息"),
    ROLE_ID("aac:role:id", "角色ID"),
    SECURITY_CONTEXT("aac:security:context", "上下文"),
    ROLE_MENU("rbac:role:menu", "菜单"),
    ROLE_URI("rbac:role:uri", "接口"),
    RBAC_ALL_ROLE_ID("rbac:all:role:id", "所有角色ids"),
    RBAC_ALL_ROLES("rbac:all:rules", "所有规则"),
    RBAC_ROLE("rbac:role", "角色");

    private final String key;
    private final String description;

    UserInfoEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public static UserInfoEnum getByCode(String code) {
        for (UserInfoEnum type : UserInfoEnum.values()) {
            if (type.key.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Type code: " + code);
    }
}