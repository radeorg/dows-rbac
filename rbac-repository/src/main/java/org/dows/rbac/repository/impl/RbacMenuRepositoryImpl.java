package org.dows.rbac.repository.impl;

import org.dows.framework.crud.mybatis.MybatisRepositoryImpl;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.mapper.RbacMenuMapper;
import org.dows.rbac.repository.RbacMenuRepository;
import org.springframework.stereotype.Service;


/**
 * 菜单集(RbacMenu)表服务实现类
 *
 * @author lait
 * @since 2024-02-27 11:58:38
 */
@Service("rbacMenuRepository")
public class RbacMenuRepositoryImpl extends MybatisRepositoryImpl<RbacMenuMapper, RbacMenuEntity> implements RbacMenuRepository {

}

