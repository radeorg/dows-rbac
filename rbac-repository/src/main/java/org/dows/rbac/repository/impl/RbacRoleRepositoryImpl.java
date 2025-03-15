package org.dows.rbac.repository.impl;

import org.dows.framework.crud.mybatis.MybatisRepositoryImpl;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.mapper.RbacRoleMapper;
import org.dows.rbac.repository.RbacRoleRepository;
import org.springframework.stereotype.Service;


/**
 * 角色集(RbacRole)表服务实现类
 *
 * @author lait
 * @since 2024-02-27 11:58:39
 */
@Service("rbacRoleRepository")
public class RbacRoleRepositoryImpl extends MybatisRepositoryImpl<RbacRoleMapper, RbacRoleEntity> implements RbacRoleRepository {

}

