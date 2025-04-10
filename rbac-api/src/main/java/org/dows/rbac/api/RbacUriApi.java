package org.dows.rbac.api;

import org.dows.rbac.request.RbacUriRequest;
import org.dows.rbac.response.RbacUriResponse;

import java.util.List;

public interface RbacUriApi {

    default List<RbacUriResponse> getUriResourcesByRoleInstanceId(String appId, RbacUriRequest saveRbacRoles) {


        return null;
    }
}
