package org.dows.rbac.repository.impl;

import org.dows.framework.crud.mybatis.MybatisRepositoryImpl;
import org.dows.rbac.entity.RbacModuleEntity;
import org.dows.rbac.mapper.RbacModuleMapper;
import org.dows.rbac.repository.RbacModuleRepository;
import org.springframework.stereotype.Service;


/**
 * 权限模块(RbacModule)表服务实现类
 *
 * @author lait
 * @since 2024-02-27 11:58:38
 */
@Service("rbacModuleRepository")
public class RbacModuleRepositoryImpl extends MybatisRepositoryImpl<RbacModuleMapper, RbacModuleEntity> implements RbacModuleRepository {

}

