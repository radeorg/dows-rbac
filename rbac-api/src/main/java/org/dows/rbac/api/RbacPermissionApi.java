package org.dows.rbac.api;

import org.dows.rbac.request.ConfigPermissionRequest;
import org.dows.rbac.response.ConfigPermissionResponse;

import java.util.List;

public interface RbacPermissionApi {

    default List<ConfigPermissionResponse> configPermission(List<ConfigPermissionRequest> configPermissionRequests) {
        throw new UnsupportedOperationException("not implemented");
    }
}
