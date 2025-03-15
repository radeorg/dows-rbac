package org.dows.rbac.api.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "FindRbacRules 对象", title = "规则查询信息")
public class FindRbacRulesRequest extends BaseFindRequest {

    @Schema(name = "rbacRuleId", title = "数据规则ID")
    private Long rbacRuleId;

    @Schema(name = "rbacRoleId", title = "角色ID")
    private Long rbacRoleId;

    @Schema(name = "roleCode", title = "角色CODE")
    private String roleCode;

    @Schema(name = "ruleDescr", title = "数据描述")
    private String ruleDescr;

    @Schema(name = "dataTable", title = "数据表名称")
    private String dataTable;

    @Schema(name = "appId", title = "应用 id")
    private String appId;

    @Schema(name = "expression", title = "基于元数据构成的规则表达式json")
    private String expression;

    @Schema(name = "lastExpression", title = "末尾表达式")
    private String lastExpression;

    @Schema(name = "sorted", title = "排序")
    private Integer sorted;

    @Schema(name = "ruleLevel", title = "规则等级")
    private Integer ruleLevel;


}