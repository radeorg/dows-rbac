package org.dows.rbac.biz.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rbac.response.RbacRulesResponse;
import org.dows.rbac.service.RbacRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RulesBiz {
    private final RbacRuleService rbacRuleService;
//    private final RuleHandler ruleHandler;

//    public void save(List<SaveRbacRulesRequest> saveRbacRules) {
//        ruleHandler.save(saveRbacRules);
//    }

    public List<RbacRulesResponse> listByAppId(String appId) {
       /* List<RbacRuleEntity> rulesEntities = rbacRuleService.lambdaQuery()
                .eq(Objects.nonNull(appId), RbacRuleEntity::getAppId, appId)
                .list();
        return BeanConvert.beanConvert(rulesEntities, RbacRulesResponse.class);*/
        return null;
    }

    public List<RbacRulesResponse> listByRoleIds(List<Long> roleIds) {
        /*List<RbacRuleEntity> rulesEntities = rbacRuleService.lambdaQuery()
                .in(Objects.nonNull(roleIds), RbacRuleEntity::getRbacRoleId, roleIds)
                .list();
        return BeanConvert.beanConvert(rulesEntities, RbacRulesResponse.class);*/
        return null;
    }

    /*public PageResponse<RbacRulesResponse> paging(PageRequest<FindRbacRulesRequest> findRbacRules) {
        FindRbacRulesRequest queryObject = findRbacRules.getQueryObject();
        Page<RbacRuleEntity> page = rbacRuleService.lambdaQuery()
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
    }*/

//    public void deleteByIds(List<Long> rbacRulesIds) {
//        ruleHandler.deleteByIds(rbacRulesIds);
//    }
}