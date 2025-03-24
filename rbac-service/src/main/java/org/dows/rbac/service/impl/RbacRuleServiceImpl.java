package org.dows.rbac.service.impl;


import org.dows.rade.crud.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.dows.rbac.service.RbacRuleService;
import org.dows.rbac.entity.RbacRuleEntity;
import org.dows.rbac.mapper.RbacRuleMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 数据规则表 服务层实现。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Service
public class RbacRuleServiceImpl extends BaseServiceImpl<RbacRuleMapper, RbacRuleEntity> implements RbacRuleService {

}