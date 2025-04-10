package org.dows.rbac.api;

import org.dows.rbac.request.SaveRbacRoleRequest;
import org.dows.rbac.response.RbacRoleResponse;

import java.util.List;

public interface RbacRoleApi {

    default List<RbacRoleResponse> saveOrUpdateRole(List<SaveRbacRoleRequest> saveRbacRoles) {
        return null;
    }




}
