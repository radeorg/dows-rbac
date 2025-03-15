package org.dows.rbac.biz.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.api.model.PageRequest;
import org.dows.framework.crud.api.model.PageResponse;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.admin.request.FindRbacRulesRequest;
import org.dows.rbac.api.admin.request.SaveRbacRulesRequest;
import org.dows.rbac.api.admin.response.RbacRulesResponse;
import org.dows.rbac.api.annotation.RbacTrigger;
import org.dows.rbac.entity.RbacRuleEntity;
import org.dows.rbac.handler.CommonHandler;
import org.dows.rbac.handler.RuleHandler;
import org.dows.rbac.repository.RbacRuleRepository;
import org.dows.uat.api.AccountApi;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class RulesBiz {
    private final RbacRuleRepository rbacRuleRepository;
    private final RuleHandler ruleHandler;

    public void save(List<SaveRbacRulesRequest> saveRbacRules) {
        ruleHandler.save(saveRbacRules);
    }

    public List<RbacRulesResponse> listByAppId(String appId) {
        List<RbacRuleEntity> rulesEntities = rbacRuleRepository.lambdaQuery()
                .eq(Objects.nonNull(appId), RbacRuleEntity::getAppId, appId)
                .list();
        return BeanConvert.beanConvert(rulesEntities, RbacRulesResponse.class);
    }

    public List<RbacRulesResponse> listByRoleIds(List<Long> roleIds) {
        List<RbacRuleEntity> rulesEntities = rbacRuleRepository.lambdaQuery()
                .in(Objects.nonNull(roleIds), RbacRuleEntity::getRbacRoleId, roleIds)
                .list();
        return BeanConvert.beanConvert(rulesEntities, RbacRulesResponse.class);
    }

    public PageResponse<RbacRulesResponse> paging(PageRequest<FindRbacRulesRequest> findRbacRules) {
        FindRbacRulesRequest queryObject = findRbacRules.getQueryObject();
        Page<RbacRuleEntity> page = rbacRuleRepository.lambdaQuery()
                .eq(Objects.nonNull(queryObject.getRbacRuleId()), RbacRuleEntity::getRbacRuleId, queryObject.getRbacRuleId())
                .eq(Objects.nonNull(queryObject.getRbacRoleId()), RbacRuleEntity::getRbacRoleId, queryObject.getRbacRoleId())
                .eq(Objects.nonNull(queryObject.getAppId()), RbacRuleEntity::getAppId, queryObject.getAppId())
                .like(Objects.nonNull(queryObject.getRuleDescr()), RbacRuleEntity::getRuleDescr, queryObject.getRuleDescr())
                .between(Objects.nonNull(queryObject.getStartTime()) && Objects.nonNull(queryObject.getEndTime()), RbacRuleEntity::getDt, queryObject.getStartTime(), queryObject.getEndTime())
                .page(findRbacRules.toPage());
        Page<RbacRulesResponse> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<RbacRulesResponse> ruleResponses = BeanConvert.beanConvert(page.getRecords(), RbacRulesResponse.class);
        result.setRecords(ruleResponses);
        return new PageResponse<>(result);
    }

    public void deleteByIds(List<Long> rbacRulesIds) {
        ruleHandler.deleteByIds(rbacRulesIds);
    }
}