package org.dows.rbac.mapper;

import org.dows.rbac.entity.RbacGroupEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资源组表 映射层。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Mapper
public interface RbacGroupMapper extends BaseMapper<RbacGroupEntity> {


}
