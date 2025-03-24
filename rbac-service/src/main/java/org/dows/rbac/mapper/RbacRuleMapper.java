package org.dows.rbac.mapper;

import org.dows.rbac.entity.RbacRuleEntity;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据规则表 映射层。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Mapper
public interface RbacRuleMapper extends BaseMapper<RbacRuleEntity> {


}
