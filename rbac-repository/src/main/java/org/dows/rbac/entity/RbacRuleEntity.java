package org.dows.rbac.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "RbacRule", title = "数据规则")
@TableName("rbac_rule")
public class RbacRuleEntity {

    @TableId(type = IdType.ASSIGN_ID)
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

    @Schema(name = "selects", title = "筛选字段','分割")
    private String selects;

    @Schema(name = "dataScop", title = "数据范围")
    private Integer dataScop;

    @Schema(name = "sorted", title = "排序")
    private Integer sorted;

    @Schema(name = "ruleLevel", title = "规则等级")
    private Integer ruleLevel;

    @Schema(name = "ver", title = "乐观锁")
    private Integer ver;

    @Schema(name = "deleted", title = "是否逻辑删除")
    private Boolean deleted;

    @TableField(fill = FieldFill.INSERT)
    @Schema(name = "dt", title = "创建，更新，删除时间")
    private Date dt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(name = "account_id", title = "账号ID")
    private Long accountId;
}