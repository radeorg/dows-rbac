package org.dows.rbac.api;


public class RbacContext {

    public static Boolean flag = false;

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setAppId(String appId) {
        threadLocal.set(appId);
    }

    public static String getAppId() {
        return threadLocal.get();
    }

    public static void removeAppId() {
        threadLocal.remove();
    }
}
