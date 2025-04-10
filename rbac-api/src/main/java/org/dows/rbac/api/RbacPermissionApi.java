package org.dows.rbac.api;

import org.dows.rbac.response.RbacPermissionResponse;

import java.util.List;

public interface RbacPermissionApi {

    default List<RbacPermissionResponse> configPermission(String appId, ConfigRbacPermissionRequest configRbacPermissionRequest) {
        throw new UnsupportedOperationException("not implemented");
    }
}
