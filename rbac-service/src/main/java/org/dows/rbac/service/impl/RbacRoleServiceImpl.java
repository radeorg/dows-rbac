package org.dows.rbac.service.impl;


import org.dows.rade.crud.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.dows.rbac.service.RbacRoleService;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.mapper.RbacRoleMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 角色实例表 服务层实现。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Service
public class RbacRoleServiceImpl extends BaseServiceImpl<RbacRoleMapper, RbacRoleEntity> implements RbacRoleService {

}