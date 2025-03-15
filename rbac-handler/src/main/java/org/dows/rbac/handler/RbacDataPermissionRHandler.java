package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import org.dows.rbac.api.constant.DataScopeEnum;
import org.dows.rbac.entity.RbacRuleEntity;
import org.dows.rbac.repository.RbacRoleRepository;
import org.dows.rbac.repository.RbacRuleRepository;
import org.dows.uat.api.AccountApi;
import org.dows.uat.api.admin.response.AccountOrgIdsResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 数据权限控制器
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RbacDataPermissionRHandler implements MultiDataPermissionHandler {
    private final RbacRoleRepository rbacRoleRepository;
    private final RbacRuleRepository rbacRuleRepository;
    private final AccountApi accountApi;

    private final String GROUP_COLUMN = "group_id";
    private final String OWNER_COLUMN = "owner_id";

    @Override
    @SneakyThrows
    public Expression getSqlSegment(Expression where, String mappedStatementId) {


        Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(StringPool.DOT)));
        String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(StringPool.DOT) + 1);
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
//            DataPermission annotation = method.getAnnotation(DataPermission.class);
//            // 如果没有注解或者是超级管理员，直接返回
//            if (annotation == null || SecurityUtils.isRoot()) {
//                return where;
//            }
//            if (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName)) {
//                return dataScopeFilter(annotation.deptAlias(), annotation.deptIdColumnName(), annotation.userAlias(), annotation.userIdColumnName(), where);
//            }
        }
        return where;
    }

    @Override
    @SneakyThrows
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        where = new HexValue("1 =1");
        if ("rbac_permission".equals(table.toString())) {
            // 获取角色集
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return where;
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<Expression> groupIds = new ArrayList<>();
            List<Expression> groupIdsAll = new ArrayList<>();
            Map<String, Object> attributes = null;
            if (!authorities.isEmpty()) {
                OAuth2UserAuthority firstAuthority = (OAuth2UserAuthority) authorities.stream().findFirst().orElse(null);
                attributes = firstAuthority.getAttributes();
            }

            Long accountInstanceId = (Long) attributes.get("accountInstanceId");
            List<Long> roleIds = (List<Long>) attributes.get("roleIds");
            AccountOrgIdsResponse accountOrgIdsResponse = accountApi.getOrgIdsByAccountId(accountInstanceId, true);
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
            StringBuilder appendSqlStr = new StringBuilder();
            List<RbacRuleEntity> rbacRuleEntities = rbacRuleRepository.lambdaQuery()
                    .in(Objects.nonNull(roleIds), RbacRuleEntity::getRbacRoleId, roleIds)
                    .list();
            Integer initDataScope = 99;
            // 查看表是否匹配
            for (RbacRuleEntity rbacRuleEntity : rbacRuleEntities) {
                if (Objects.nonNull(rbacRuleEntity.getDataTable()) && rbacRuleEntity.getDataTable().equalsIgnoreCase(table.toString())) {
                    if (Objects.nonNull(rbacRuleEntity.getExpression())) {
                        appendSqlStr.append(rbacRuleEntity.getExpression());
                    }
                    if (Objects.nonNull(rbacRuleEntity.getDataScop()) && rbacRuleEntity.getDataScop() < initDataScope) {
                        initDataScope = rbacRuleEntity.getDataScop();
                    }
                }
            }
            DataScopeEnum dataScopeEnum = DataScopeEnum.getByValue(initDataScope);
            switch (dataScopeEnum) {
                case ALL:
                    return where;
                case DEPT:
                    InExpression inExpression = new InExpression(new Column(GROUP_COLUMN), new ExpressionList(groupIds));
                    where = new AndExpression(where, inExpression);
                    break;
                case SELF:
                    EqualsTo selfEqualsTo = new EqualsTo();
                    selfEqualsTo.setLeftExpression(new Column(OWNER_COLUMN));
                    selfEqualsTo.setRightExpression(new LongValue(accountInstanceId));
                    where = new AndExpression(where, selfEqualsTo);
                    break;
                // 默认部门及子部门数据权限
                case DEPT_AND_SUB:
                    InExpression inExpression2 = new InExpression(new Column(GROUP_COLUMN), new ExpressionList(groupIdsAll));
                    where = new AndExpression(where, inExpression2);
                    break;
                default: {
                }
                break;
            }

            try {
                if (StrUtil.isNotBlank(appendSqlStr.toString())) {
                    Expression appendExpression = CCJSqlParserUtil.parseCondExpression(appendSqlStr.toString());
                    return new AndExpression(where, appendExpression);
                }
            } catch (JSQLParserException e) {
                log.error("Error parsing", e);
                throw new RuntimeException(e);
            }
        }
//        // 拼接appId
//        String appId = RbacContext.getAppId();
//        if (StrUtil.isNotBlank(appId)) {
//            log.info("appId {}",appId);
//            Expression appendExpression = CCJSqlParserUtil.parseCondExpression(appId);
//            return new AndExpression(where, appendExpression);
//        }else{
//            log.error("appId 为空");
//        }

        return where;
    }


    /**
     * 构建过滤条件
     *
     * @param where 当前查询条件
     * @return 构建后查询条件
     */
    @SneakyThrows
    public static Expression dataScopeFilter(String deptAlias, String deptIdColumnName, String userAlias, String userIdColumnName, Expression where) {


        String deptColumnName = StrUtil.isNotBlank(deptAlias) ? (deptAlias + StringPool.DOT + deptIdColumnName) : deptIdColumnName;
        String userColumnName = StrUtil.isNotBlank(userAlias) ? (userAlias + StringPool.DOT + userIdColumnName) : userIdColumnName;

        // 获取当前用户的数据权限
//        Integer dataScope = SecurityUtils.getDataScope();
//
//        DataScopeEnum dataScopeEnum = IBaseEnum.getEnumByValue(dataScope, DataScopeEnum.class);
//
//        Long deptId, userId;
//        String appendSqlStr;
//        switch (dataScopeEnum) {
//            case ALL:
//                return where;
//            case DEPT:
//                deptId = SecurityUtils.getDeptId();
//                appendSqlStr = deptColumnName + StringPool.EQUALS + deptId;
//                break;
//            case SELF:
//                userId = SecurityUtils.getUserId();
//                appendSqlStr = userColumnName + StringPool.EQUALS + userId;
//                break;
//            // 默认部门及子部门数据权限
//            default:
//                deptId = SecurityUtils.getDeptId();
//                appendSqlStr = deptColumnName + " IN ( SELECT id FROM sys_dept WHERE id = " + deptId + " OR FIND_IN_SET( " + deptId + " , tree_path ) )";
//                break;
//        }
//        if (StrUtil.isBlank(appendSqlStr)) {
//            return where;
//        }

//        Expression appendExpression = CCJSqlParserUtil.parseCondExpression(appendSqlStr);
        Expression appendExpression = CCJSqlParserUtil.parseCondExpression("");

        if (where == null) {
            return appendExpression;
        }

        return new AndExpression(where, appendExpression);
    }


}

