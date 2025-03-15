package org.dows.rbac.repository.impl;

import org.dows.framework.crud.mybatis.MybatisRepositoryImpl;
import org.dows.rbac.entity.RbacPermissionEntity;
import org.dows.rbac.mapper.RbacPermissionMapper;
import org.dows.rbac.repository.RbacPermissionRepository;
import org.springframework.stereotype.Service;


/**
 * 角色权限集(RbacPermission)表服务实现类
 *
 * @author lait
 * @since 2024-02-27 11:58:38
 */
@Service("rbacPermissionRepository")
public class RbacPermissionRepositoryImpl extends MybatisRepositoryImpl<RbacPermissionMapper, RbacPermissionEntity> implements RbacPermissionRepository {

}

