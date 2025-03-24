package org.dows.rbac.service.impl;


import org.dows.rade.crud.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.dows.rbac.service.RbacPermissionService;
import org.dows.rbac.entity.RbacPermissionEntity;
import org.dows.rbac.mapper.RbacPermissionMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 角色权限集表 服务层实现。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Service
public class RbacPermissionServiceImpl extends BaseServiceImpl<RbacPermissionMapper, RbacPermissionEntity> implements RbacPermissionService {

}