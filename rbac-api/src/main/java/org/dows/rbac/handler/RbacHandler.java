package org.dows.rbac.handler;

public interface RbacHandler {
    void handle(Object rbacResources);

    boolean supportResourceType(Integer resourceType);

}
