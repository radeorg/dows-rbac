package org.dows.rbac.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisMapper;
import org.dows.rbac.entity.RbacRoleEntity;

/**
 * 角色集(RbacRole)表数据库访问层
 *
 * @author lait
 * @since 2024-02-27 11:58:39
 */
@Mapper
public interface RbacRoleMapper extends MybatisMapper<RbacRoleEntity> {

}

