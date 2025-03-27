package org.dows.rbac.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.dows.rade.crud.BaseEntity;

import java.util.Date;

/**
 * 资源组表 实体类。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "资源组表")
@Table(value = "rbac_group")
public class RbacGroupEntity extends BaseEntity<RbacGroupEntity> {

    /**
     * 角色权限组ID
     */
    @Schema(description = "角色权限组ID")
    @Id(keyType = KeyType.Generator,value= KeyGenerators.flexId)
    private Long rbacGroupId;

    /**
     * 组名称
     */
    @Schema(description = "组名称")
    @Column(value = "group_name")
    private String groupName;

    /**
     * 组CODE
     */
    @Schema(description = "组CODE")
    @Column(value = "group_code")
    private String groupCode;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Column(value = "description")
    private String description;

    /**
     * 应用id
     */
    @Schema(description = "应用id ")
    @Column(value = "app_id")
    private String appId;

    /**
     * 资源ID集合逗号分割
     */
    @Schema(description = "资源ID集合逗号分割")
    @Column(value = "resource_ids")
    private String resourceIds;

    /**
     * 资源表
     */
    @Schema(description = "资源表")
    @Column(value = "resource_table")
    private String resourceTable;

    /**
     * 资源类型[0:接口，1:菜单]
     */
    @Schema(description = "资源类型[0:接口，1:菜单]")
    @Column(value = "resource_type")
    private Integer resourceType;

    /**
     * 状态
     */
    @Schema(description = "状态")
    @Column(value = "state")
    private Integer state;

    /**
     * 乐观锁, 默认: 0
     */
    @Schema(description = "乐观锁, 默认: 0")
    @Column(value = "ver")
    private Integer ver;

    /**
     * 逻辑删除  0未删除  1 删除
     */
    @Schema(description = "逻辑删除  0未删除  1 删除")
    @Column(value = "deleted")
    private Integer deleted;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    @Column(value = "ts")
    private Date ts;


}
