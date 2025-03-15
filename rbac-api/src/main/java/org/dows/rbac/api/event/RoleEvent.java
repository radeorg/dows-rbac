package org.dows.rbac.api.event;

import lombok.Getter;
import org.dows.rbac.api.RbacHandler;
import org.springframework.context.ApplicationEvent;

public class RoleEvent extends ApplicationEvent {

    @Getter
    private Class<? extends RbacHandler> rbacHandler;
    private Object[] args;

    public RoleEvent(Object[] args) {
        super(args);
    }

    public RoleEvent(Class<? extends RbacHandler> handler, Object[] args) {
        super(args);
        this.rbacHandler = handler;
    }
}