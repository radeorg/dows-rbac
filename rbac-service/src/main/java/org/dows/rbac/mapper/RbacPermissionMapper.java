package org.dows.rbac.mapper;

import org.dows.rbac.entity.RbacPermissionEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限集表 映射层。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Mapper
public interface RbacPermissionMapper extends BaseMapper<RbacPermissionEntity> {


}
