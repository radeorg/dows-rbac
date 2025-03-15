package org.dows.rbac.api;

import cn.hutool.core.lang.tree.TreeNode;
import org.dows.rbac.api.admin.request.FindRbacResourcesRequest;
import org.dows.rbac.api.admin.request.SaveRbacRoleRequest;
import org.dows.rbac.api.admin.response.*;

import java.util.List;
import java.util.Map;

/**
 * @description: </br>
 * 1.获取角色
 * 2.获取菜单
 * 3.获取资源
 * 4.获取权限
 * @author: lait.zhang@gmail.com
 * @date: 2/28/2024 9:55 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */

public interface RbacApi {

    /**
     * 获取角色
     *
     * @return
     */
    List<RbacRoleResponse> getRole(List<Long> roleIds);

    /**
     * 获取菜单
     *
     * @return
     */
    String getMenu();

    /**
     * 获取资源
     *
     * @return
     */
    List<RbacResourcesQueryResponse> getResource(FindRbacResourcesRequest findRbacResources);

    /**
     * 获取权限
     *
     * @return
     */
    List<RbacPermissionResponse> getPermission(List<Long> roleIds);

    List<String> getUriCode(List<Long> roleIds);


    /**
     * 保存或更新资源[uri,menu]
     * 先删除所有操作权限类型的权限资源，待会再新增资源，以实现全量更新（注意哦，数据库中不要设置外键，否则会删除失败）
     *
     * @param resources
     */
    void saveResource(List<InitResources> resources);

    List<RbacMenu> initAppMenu(List<TreeNode<String>> menus, String appId);

    void initRoleMenu(List<TreeNode<String>> menus, String roleCode, String appId);

    void initRoleUri(List<InitResources> resources, String roleCode, String appId);

    void initAppRole(List<SaveRbacRoleRequest> roleItems);

    /**
     * 根据角色Ids获取树形菜单
     *
     * @param rbacRoleIds
     * @return
     */
    List<RbacMenusResponse> listRoleMenusTree(List<Long> rbacRoleIds);

    void saveUri(List<InitUriResources> initUriResources);

    Map<String, List<RbacUriRoleResponse>> getRoleUri();

}
