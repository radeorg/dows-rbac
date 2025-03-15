package org.dows.rbac.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisMapper;
import org.dows.rbac.entity.RbacRuleEntity;

@Mapper
public interface RbacRuleMapper extends MybatisMapper<RbacRuleEntity> {
}