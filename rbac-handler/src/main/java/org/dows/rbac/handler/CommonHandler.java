package org.dows.rbac.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.cache.caffeine.CaffeineTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonHandler implements org.dows.rbac.api.RbacHandler {

    private final RbacDataHandler rbacContext;


    @Override
    public void handle(Object rbacResources) {
        rbacContext.initData();
    }

    @Override
    public boolean supportResourceType(Integer resourceType) {
        return false;
    }
}
