package org.dows.rbac.repository.impl;

import org.dows.framework.crud.mybatis.MybatisRepositoryImpl;
import org.dows.rbac.entity.RbacGroupEntity;
import org.dows.rbac.mapper.RbacGroupMapper;
import org.dows.rbac.repository.RbacGroupRepository;
import org.springframework.stereotype.Service;


/**
 * 角色权限组(资源)(RbacGroup)表服务实现类
 *
 * @author lait
 * @since 2024-02-27 11:58:38
 */
@Service("rbacGroupRepository")
public class RbacGroupRepositoryImpl extends MybatisRepositoryImpl<RbacGroupMapper, RbacGroupEntity> implements RbacGroupRepository {

}

