package org.dows.rbac.api;

public interface RbacHandler {
    void handle(Object rbacResources);

    boolean supportResourceType(Integer resourceType);

}
