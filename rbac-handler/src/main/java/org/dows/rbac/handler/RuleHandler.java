package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.RbacHandler;
import org.dows.rbac.api.admin.request.FindRbacRulesRequest;
import org.dows.rbac.api.admin.request.SaveRbacRulesRequest;
import org.dows.rbac.entity.RbacRuleEntity;
import org.dows.rbac.repository.RbacRuleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/2/2024 7:16 PM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RuleHandler implements RbacHandler {

    private final RbacRuleRepository rbacRuleRepository;

    private final RbacCache rbacCache;
    @Override
    public void handle(Object rbacResources) {

    }

    @Override
    public boolean supportResourceType(Integer resourceType) {
        return false;
    }


    @Cacheable(value = "rules", key = "#root.method.name + '_' + T(java.math.BigInteger).valueOf(#findRbacRulesRequest.hashCode()).toString(32)")
    public List<RbacRuleEntity> getRules(FindRbacRulesRequest findRbacRulesRequest){
        return rbacRuleRepository.lambdaQuery()
                .eq(Objects.nonNull(findRbacRulesRequest.getRbacRuleId()), RbacRuleEntity::getRbacRuleId, findRbacRulesRequest.getRbacRuleId())
                .eq(Objects.nonNull(findRbacRulesRequest.getRbacRoleId()), RbacRuleEntity::getRbacRoleId, findRbacRulesRequest.getRbacRoleId())
                .eq(Objects.nonNull(findRbacRulesRequest.getDataTable()), RbacRuleEntity::getDataTable, findRbacRulesRequest.getDataTable())
                .eq(Objects.nonNull(findRbacRulesRequest.getAppId()), RbacRuleEntity::getAppId, findRbacRulesRequest.getAppId())
                .like(Objects.nonNull(findRbacRulesRequest.getRuleDescr()), RbacRuleEntity::getRuleDescr, findRbacRulesRequest.getRuleDescr())
                .list();
    }

    @CacheEvict(value = "rules", allEntries = true)
    @Transactional
    public void deleteByIds(List<Long> rbacRulesIds) {
        rbacRuleRepository.removeByIds(rbacRulesIds);
    }

    @CacheEvict(value = "rules", allEntries = true)
    @Transactional
    public void save(List<SaveRbacRulesRequest> saveRbacRules) {
        for (SaveRbacRulesRequest saveRbacRule : saveRbacRules) {
            if(null == saveRbacRule.getRbacRuleId()){
                FindRbacRulesRequest findRbacRulesRequest = new FindRbacRulesRequest();
                findRbacRulesRequest.setAppId(saveRbacRule.getAppId());
                findRbacRulesRequest.setRbacRoleId(saveRbacRule.getRbacRoleId());
                findRbacRulesRequest.setDataTable(saveRbacRule.getDataTable());
                List<RbacRuleEntity> rules = getRules(findRbacRulesRequest);
                if(CollectionUtil.isNotEmpty(rules)){
                    throw new IllegalArgumentException("对应表名和角色有对应的规则配置");
                }
            }
        }

        List<RbacRuleEntity> rbacRuleEntities = BeanConvert.beanConvert(saveRbacRules, RbacRuleEntity.class);
        rbacRuleRepository.saveOrUpdateBatch(rbacRuleEntities);
    }

//    public List<RbacRuleEntity> getAllRules() {
//        return getRules(new FindRbacRulesRequest());
//    }
}

