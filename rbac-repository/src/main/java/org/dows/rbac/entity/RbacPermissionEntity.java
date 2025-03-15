package org.dows.rbac.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 角色权限集(RbacPermission)实体类
 *
 * @author lait
 * @since 2024-02-27 11:58:38
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "RbacPermission", title = "角色权限集")
@TableName("rbac_permission")
public class RbacPermissionEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacPermissionId", title = "权限ID")
    private Long rbacPermissionId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacRoleId", title = "角色id")
    private Long rbacRoleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rolePid", title = "父角色ID(继承时该字段有值)")
    private Long rolePid;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "resourceId", title = "资源ID")
    private Long resourceId;

    @Schema(name = "roleCode", title = "角色CODE")
    private String roleCode;

    @Schema(name = "roleName", title = "角色名字")
    private String roleName;

    @Schema(name = "authority", title = "资源权限码")
    private String authority;

    @Schema(name = "appId", title = "应用id 从角色冗余")
    private String appId;

    @Schema(name = "descr", title = "描述")
    private String descr;

    @Schema(name = "resourceType", title = "资源类型[0:接口，1:菜单]")
    private Integer resourceType;

    @Schema(name = "state", title = "状态")
    private Integer state;

    @Schema(name = "ver", title = "乐观锁, 默认: 0")
    private Integer ver;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @Schema(name = "deleted", title = "逻辑删除  0未删除  1 删除")
    private Boolean deleted;

    @TableField(fill = FieldFill.INSERT)
    @Schema(name = "dt", title = "创建，更新，删除时间")
    private Date dt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(name = "account_id", title = "账号ID")
    private Long accountId;

}

