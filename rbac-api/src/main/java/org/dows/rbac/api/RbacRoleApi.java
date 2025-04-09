package org.dows.rbac.api;

import org.dows.rbac.api.admin.request.SaveRbacRoleRequest;
import org.dows.rbac.api.admin.response.RbacRoleResponse;

import java.util.List;

public interface RbacRoleApi {

    default List<RbacRoleResponse> saveOrUpdateRole(List<SaveRbacRoleRequest> saveRbacRoles) {
        return null;
    }

}
