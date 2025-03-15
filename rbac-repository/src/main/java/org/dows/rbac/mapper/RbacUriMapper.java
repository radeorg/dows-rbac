package org.dows.rbac.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisMapper;
import org.dows.rbac.entity.RbacUriEntity;

/**
 * 接口集(RbacUri)表数据库访问层
 *
 * @author lait
 * @since 2024-02-27 11:58:39
 */
@Mapper
public interface RbacUriMapper extends MybatisMapper<RbacUriEntity> {

}

