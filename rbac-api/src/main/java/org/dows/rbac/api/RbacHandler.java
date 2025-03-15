package org.dows.rbac.api;

import java.util.List;
import java.util.Map;

public interface RbacHandler {
    void handle(Object rbacResources);

    boolean supportResourceType(Integer resourceType);

}
