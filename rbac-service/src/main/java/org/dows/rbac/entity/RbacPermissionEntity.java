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
 * 角色权限集表 实体类。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "角色权限集表")
@Table(value = "rbac_permission")
public class RbacPermissionEntity extends BaseEntity<RbacPermissionEntity> {

    /**
     * 权限ID
     */
    @Schema(description = "权限ID")
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long rbacPermissionId;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @Column(value = "rbac_role_id")
    private Long rbacRoleId;

    /**
     * 父角色ID(继承时该字段有值)
     */
    @Schema(description = "父角色ID(继承时该字段有值)")
    @Column(value = "role_pid")
    private Long rolePid;

    /**
     * 资源ID
     */
    @Schema(description = "资源ID")
    @Column(value = "resource_id")
    private Long resourceId;

    /**
     * 应用id 从角色冗余
     */
    @Schema(description = "应用id 从角色冗余")
    @Column(value = "app_id")
    private String appId;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Column(value = "description")
    private String description;

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
