package org.dows.rbac.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.dows.rade.crud.BaseEntity;

import java.util.Date;

/**
 * 角色实例表 实体类。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "角色实例表")
@Table(value = "rbac_role")
public class RbacRoleEntity extends BaseEntity<RbacRoleEntity> {

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @Id(keyType = KeyType.Auto)
    private Long rbacRoleId;

    /**
     * 角色父ID(角色组|继承)
     */
    @Schema(description = "角色父ID(角色组|继承)")
    @Column(value = "pid")
    private Long pid;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @Column(value = "role_name")
    private String roleName;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @Column(value = "role_code")
    private String roleCode;

    /**
     * 角色图标
     */
    @Schema(description = "角色图标")
    @Column(value = "role_icon")
    private String roleIcon;

    /**
     * id路径
     */
    @Schema(description = "id路径")
    @Column(value = "id_path")
    private String idPath;

    /**
     * 名称路径
     */
    @Schema(description = "名称路径")
    @Column(value = "name_path")
    private String namePath;

    /**
     * 菜单路径URI[menuPath]
     */
    @Schema(description = "菜单路径URI[menuPath]")
    @Column(value = "code_path")
    private String codePath;

    /**
     * 应用id
     */
    @Schema(description = "应用id")
    @Column(value = "app_id")
    private String appId;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Column(value = "description")
    private String description;

    /**
     * 角色级别
     */
    @Schema(description = "角色级别")
    @Column(value = "role_level")
    private Integer roleLevel;

    /**
     * 当前角色是否继承父角色对应的权限
     */
    @Schema(description = "当前角色是否继承父角色对应的权限")
    @Column(value = "inherit")
    private Integer inherit;

    /**
     * 状态
     */
    @Schema(description = "状态")
    @Column(value = "state")
    private Integer state;

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
