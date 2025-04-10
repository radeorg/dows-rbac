package org.dows.rbac.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.dows.rade.crud.BaseEntity;

import java.util.Date;

/**
 * 数据规则表 实体类。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "数据规则表")
@Table(value = "rbac_rule")
public class RbacRuleEntity extends BaseEntity<RbacRuleEntity> {

    /**
     * 数据规则ID
     */
    @Schema(description = "数据规则ID")
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long rbacRuleId;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    @Column(value = "rbac_role_id")
    private Long rbacRoleId;

    /**
     * 角色CODE
     */
    @Schema(description = "角色CODE")
    @Column(value = "role_code")
    private String roleCode;

    /**
     * 规则描述
     */
    @Schema(description = "规则描述")
    @Column(value = "rule_description")
    private String ruleDescription;

    /**
     * 数据表名称
     */
    @Schema(description = "数据表名称")
    @Column(value = "data_table")
    private String dataTable;

    /**
     * 应用 id
     */
    @Schema(description = "应用 id")
    @Column(value = "app_id")
    private String appId;

    /**
     * 基于元数据构成的规则表达式json
     */
    @Schema(description = "基于元数据构成的规则表达式json")
    @Column(value = "expression")
    private String expression;

    /**
     * 筛选字段逗号分割
     */
    @Schema(description = "筛选字段逗号分割")
    @Column(value = "selects")
    private String selects;

    /**
     * 数据范围[0:所有数据,1:所在组及子组数据,2:所在组数据,3:本人数据]
     */
    @Schema(description = "数据范围[0:所有数据,1:所在组及子组数据,2:所在组数据,3:本人数据]")
    @Column(value = "data_scope")
    private Integer dataScope;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @Column(value = "sorted")
    private Integer sorted;

    /**
     * 末尾Expression
     */
    @Schema(description = "末尾Expression")
    @Column(value = "last_expression")
    private String lastExpression;

    /**
     * 乐观锁, 默认: 0
     */
    @Schema(description = "乐观锁, 默认: 0")
    @Column(value = "ver")
    private Integer ver;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    @Schema(description = "是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0")
    @Column(value = "deleted")
    private Integer deleted;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    @Column(value = "ts")
    private Date ts;


}
