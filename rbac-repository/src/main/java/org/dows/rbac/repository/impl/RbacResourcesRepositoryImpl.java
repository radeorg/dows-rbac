package org.dows.rbac.repository.impl;

import org.dows.framework.crud.mybatis.MybatisRepositoryImpl;
import org.dows.rbac.entity.RbacResourcesEntity;
import org.dows.rbac.mapper.RbacResourcesMapper;
import org.dows.rbac.repository.RbacResourcesRepository;
import org.springframework.stereotype.Service;


/**
 * 模块资源(RbacResources)表服务实现类
 *
 * @author lait
 * @since 2024-02-27 11:58:38
 */
@Service("rbacResourcesRepository")
public class RbacResourcesRepositoryImpl extends MybatisRepositoryImpl<RbacResourcesMapper, RbacResourcesEntity> implements RbacResourcesRepository {

}

