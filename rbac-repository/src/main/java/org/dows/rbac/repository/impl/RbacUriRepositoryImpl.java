package org.dows.rbac.repository.impl;

import org.dows.framework.crud.mybatis.MybatisRepositoryImpl;
import org.dows.rbac.entity.RbacUriEntity;
import org.dows.rbac.mapper.RbacUriMapper;
import org.dows.rbac.repository.RbacUriRepository;
import org.springframework.stereotype.Service;


/**
 * 接口集(RbacUri)表服务实现类
 *
 * @author lait
 * @since 2024-02-27 11:58:39
 */
@Service("rbacUriRepository")
public class RbacUriRepositoryImpl extends MybatisRepositoryImpl<RbacUriMapper, RbacUriEntity> implements RbacUriRepository {

}

