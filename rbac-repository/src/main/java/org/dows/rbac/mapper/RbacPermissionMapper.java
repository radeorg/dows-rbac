package org.dows.rbac.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisMapper;
import org.dows.rbac.entity.RbacPermissionEntity;

/**
 * 角色权限集(RbacPermission)表数据库访问层
 *
 * @author lait
 * @since 2024-02-27 11:58:38
 */
@Mapper
public interface RbacPermissionMapper extends MybatisMapper<RbacPermissionEntity> {

}

