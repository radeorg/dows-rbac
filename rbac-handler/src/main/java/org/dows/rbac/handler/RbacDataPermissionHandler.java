package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.dows.aac.api.AacUser;
import org.dows.framework.api.exceptions.BizException;
import org.dows.rbac.api.RbacContext;
import org.dows.rbac.api.admin.request.FindRbacRulesRequest;
import org.dows.rbac.api.constant.DataScopeEnum;
import org.dows.rbac.entity.RbacRuleEntity;
import org.dows.uat.api.AccountApi;
import org.dows.uat.api.admin.response.AccountOrgIdsResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RbacDataPermissionHandler implements InnerInterceptor {

    private final RuleHandler ruleHandler;
    private final RbacCache rbacCache;
    private final AccountApi accountApi;
    private Boolean ENABLED_SCOPE = false;
    private final String GROUP_COLUMN = "group_id";
    private final String OWNER_COLUMN = "owner_id";


    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter,
                            RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
//        InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
//        if(!isCondition(ms,boundSql)){
//            return;
//        }
//        if(null == SecurityContextHolder.getContext()){
//            return;
//        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (null == authentication) {
//            return;
//        }
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        if (CollectionUtil.isEmpty(authorities)) {
//            return;
//        }
//
//        List<RbacRuleEntity> allRules = ruleHandler.getRules(new FindRbacRulesRequest());
//        // 没有配置规则直接返回
//        if (CollectionUtil.isEmpty(allRules)) {
//            return;
//        }
//
//        Statement statement = null;
//        try {
//            statement =  CCJSqlParserUtil.parse(boundSql.getSql());
//        } catch (JSQLParserException e) {
//            e.printStackTrace();
//        }
//        Map<String,List<RbacRuleEntity>> map = tableHandler(statement, allRules, authorities);
//        // 如果map沒有元素就返回
//        if (CollectionUtil.isEmpty(map)) {
//            return;
//        }
//        Set<String> tables = map.keySet();
//        List<String> allowedFields = new ArrayList<>();
//        StringBuilder expressions = new StringBuilder();
//        String lastExpressions = null;
//        Boolean addedExpression = false;
//        Integer initDataScope = DataScopeEnum.NONE.getValue();
//        for (String table : tables) {
//            List<RbacRuleEntity> result = map.get(table);
//            if(CollectionUtil.isEmpty(result)) {
//                continue;
//            }
//            Optional<RbacRuleEntity> maxRuleLevelEntity = result.stream()
//                    .filter(entity -> entity.getRuleLevel() != null)
//                    .max(Comparator.comparing(RbacRuleEntity::getRuleLevel));
//            RbacRuleEntity rbacRuleEntity = null;
//            rbacRuleEntity = maxRuleLevelEntity.orElseGet(() -> result.get(0));
//            String selects = rbacRuleEntity.getSelects();
//            String[] split = selects.split(",");
//            for (String field : split) {
//                allowedFields.add(table+"."+field);
//            }
//            if(!addedExpression){
//                if(StrUtil.isNotEmpty(rbacRuleEntity.getLastExpression())){
//                    addedExpression = true;
//                    lastExpressions = rbacRuleEntity.getLastExpression();
//                }
//            }
//            if(StrUtil.isNotEmpty(rbacRuleEntity.getExpression())){
//                expressions.append(" "+rbacRuleEntity.getExpression()+" ");
//            }
//            if (Objects.nonNull(rbacRuleEntity.getDataScop()) && rbacRuleEntity.getDataScop() < initDataScope) {
//                initDataScope = rbacRuleEntity.getDataScop();
//            }
//        }
//
//
//        // 加入where
//        Select selectStatement = (Select) statement;
//        PlainSelect plain = (PlainSelect) selectStatement.getSelectBody();
//        Expression where_expression = plain.getWhere();
//        String whereSql = expressions.toString();
//        if(StrUtil.isNotEmpty(whereSql)){
//            try {
//                whereExpressionHandler(where_expression,plain,CCJSqlParserUtil.parseCondExpression(whereSql));
//            } catch (JSQLParserException e) {
//                log.error("sql解析异常 {}",e.getMessage());
//                throw new BizException("sql解析异常");
//            }
//        }
//        dataScopeHandler(authentication,initDataScope,plain);
//
//        if(StrUtil.isNotBlank(lastExpressions)){
//            Limit limit = new Limit();
//            limit.setRowCount(new LongValue(Long.valueOf(lastExpressions)));
//            plain.setLimit(limit);
//        }
//
//        // 限制查询的字段
//        String modifiedSql = limitQueryFields(plain.toString(), allowedFields);
//        // 加入之后的限制表达式
////        if(StrUtil.isNotBlank(lastExpressions)){
////            modifiedSql = addDynamicCondition(modifiedSql, lastExpressions);
////        }
//
//        // 将修改后的SQL语句设置回BoundSql对象中
//        PluginUtils.mpBoundSql(boundSql).sql(modifiedSql);

    }

    private boolean isCondition(MappedStatement ms,BoundSql boundSql){
        // 跳过初始化
        if (!RbacContext.flag) {
            return false;
        }
        // 判断是否为查询操作
        if (ms.getSqlCommandType() != SqlCommandType.SELECT) {
            return false;
        }

        if (null == SecurityContextHolder.getContext()) {
            return false;
        }
        if(boundSql.getSql().toUpperCase().contains("COUNT(*)")){
            return false;
        }
        return true;
    }

    private void dataScopeHandler(Authentication authentication,Integer initDataScope,PlainSelect plain){
        //数据范围过滤
        if(ENABLED_SCOPE){
            AacUser aacUser = (AacUser)authentication.getPrincipal();
            if(null == aacUser){
                throw new UsernameNotFoundException("用户不存在");
            }
            Long accountInstanceId = aacUser.getAccountId();
            List<Expression> groupIds = new ArrayList<>();
            List<Expression> groupIdsAll = new ArrayList<>();
            AccountOrgIdsResponse accountOrgIdsResponse = accountApi.getOrgIdsByAccountId(accountInstanceId, true);
            if(null!=accountOrgIdsResponse){
                List<Long> orgIds = accountOrgIdsResponse.getAccountOrgId();
                List<Long> childrenOrgIds = accountOrgIdsResponse.getChildrenOrgIds();
                for (Long orgId : orgIds) {
                    groupIds.add(new LongValue(orgId));
                }
                if (CollectionUtils.isEmpty(childrenOrgIds)) {
                    childrenOrgIds = orgIds;
                } else {
                    if (CollectionUtil.isNotEmpty(orgIds)) {
                        childrenOrgIds.addAll(orgIds);
                    }
                }
                for (Long l : childrenOrgIds) {
                    groupIdsAll.add(new LongValue(l));
                }
            }
            scopeDataHandler(plain,initDataScope,groupIds,groupIdsAll,accountInstanceId);
        }
    }

    private Map<String,List<RbacRuleEntity>> tableHandler(Statement statement,List<RbacRuleEntity> allRules,Collection<? extends GrantedAuthority> authorities){

        // 获取table
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(statement);
        Map<String,List<RbacRuleEntity>> map = new HashMap<>();
        // 获取不同的表对应的规则
        Map<String, List<RbacRuleEntity>> collect = allRules.stream().collect(Collectors.
                groupingBy(RbacRuleEntity::getDataTable));
        Set<String> ruleTables = collect.keySet();
        for (String ruleTable : ruleTables) {
            for (String tableName : tableList) {
                if (tableName.equals(ruleTable)) {
                    List<RbacRuleEntity> result = new ArrayList<>();
                    List<RbacRuleEntity> rbacRuleEntities = collect.get(tableName);
                    for (RbacRuleEntity rbacRuleEntity : rbacRuleEntities) {
                        for (GrantedAuthority authority : authorities) {
                            if (authority.getAuthority().equals(String.valueOf(rbacRuleEntity.getRbacRoleId()))) {
                                result.add(rbacRuleEntity);
                            }
                        }
                    }
                    // 当前角色有对应表的规则进行保存
                    if(CollectionUtil.isNotEmpty(result)) {
                        map.put(tableName,result);
                    }
                }
            }

        }
        return map;
    }

    public void whereExpressionHandler(Expression where_expression,PlainSelect plain,Expression newExpression){
        if (null == where_expression) {
            plain.setWhere(newExpression);
        }else {
            plain.setWhere(new AndExpression(plain.getWhere(), newExpression));
        }
    }

    public void scopeDataHandler(PlainSelect plain,Integer initDataScope,List<Expression> groupIds,
                                 List<Expression> groupIdsAll,Long accountInstanceId){
        DataScopeEnum dataScopeEnum = DataScopeEnum.getByValue(initDataScope);
        switch (dataScopeEnum) {
            case ALL:
                break;
            case DEPT:
                whereExpressionHandler(plain.getWhere(),plain,new InExpression(new Column(GROUP_COLUMN),
                        new ExpressionList(groupIds)));
                break;
            case SELF:
                EqualsTo selfEqualsTo = new EqualsTo();
                selfEqualsTo.setLeftExpression(new Column(OWNER_COLUMN));
                selfEqualsTo.setRightExpression(new LongValue(accountInstanceId));
                whereExpressionHandler(plain.getWhere(),plain,selfEqualsTo);
                break;
            // 默认部门及子部门数据权限
            case DEPT_AND_SUB:
                InExpression inExpression2 = new InExpression(new Column(GROUP_COLUMN),
                        new ExpressionList(groupIdsAll));
                whereExpressionHandler(plain.getWhere(),plain,inExpression2);
                break;
            default: {
            }
            break;
        }
    }


    // 提取SQL语句中的表名
    private String extractEndSql(String sql) {
        // 实现提取SQL语句中的表名的逻辑，这里简单示例为假设表名在FROM后面
        int fromIndex = sql.toUpperCase().indexOf("FROM ")+5;
        return sql.substring(fromIndex);
//        int endIndex = tableStr.indexOf(" ");
//        return tableStr.substring(0, endIndex);
    }

    // 根据权限信息和表名限制查询字段的方法
    private String limitQueryFields(String originalSql, List<String> allowedFields) {
        // 实现根据权限信息和表名限制查询字段的逻辑，这里简单示例为替换SELECT字段
        String selectRegex = "(?i)SELECT\\s+.*?\\s+FROM\\s+";
        String replacement = "SELECT " + String.join(",", allowedFields) + " FROM ";
        return originalSql.replaceAll(selectRegex, replacement);
    }

    //
    private String addDynamicCondition(String sql, String expression) {
        // 实现根据是否有where后面的条件过滤来动态添加条件的逻辑，这里简单示例为在WHERE后面添加额外条件
        return sql + expression;
    }
}
