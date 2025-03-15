package org.dows.rbac.repository.impl;

import org.dows.framework.crud.mybatis.MybatisRepositoryImpl;
import org.dows.rbac.entity.RbacRuleEntity;
import org.dows.rbac.mapper.RbacRuleMapper;
import org.dows.rbac.repository.RbacRuleRepository;
import org.springframework.stereotype.Service;

@Service("rbacRuleRepository")
public class RbacRuleRepositoryImpl extends MybatisRepositoryImpl<RbacRuleMapper, RbacRuleEntity> implements RbacRuleRepository {
}