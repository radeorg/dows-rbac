package org.dows.rbac.api;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 3/19/2024 1:55 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
public interface InitResources {
    Long getId();

    Long getPid();

    /**
     * 资源类型
     *
     * @return
     */
    Integer getType();

    String getPath();

    String getName();
    String getMenuName();

    String getPackageName();

    String getCode();

    String getMethod();

    String getAppId();
}
