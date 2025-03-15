package org.dows.rbac.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.dows.framework.api.annotation.Pinyin;

import java.util.Date;

/**
 * 角色集(RbacRole)实体类
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
@Schema(name = "RbacRole", title = "角色集")
@TableName("rbac_role")
public class RbacRoleEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacRoleId", title = "角色id")
    private Long rbacRoleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "pid", title = "角色父ID(角色组|继承)")
    private Long pid;


    @Schema(name = "roleName", title = "角色名称")
    private String roleName;

    @Pinyin(value = "roleName")
    @Schema(name = "pyCode", title = "角色名称首字母拼音码")
    private String pyCode;

    @Schema(name = "roleCode", title = "角色编码")
    private String roleCode;

    @Schema(name = "idPath", title = "id路径")
    private String idPath;

    @Schema(name = "namePath", title = "名称路径")
    private String namePath;

    @Schema(name = "codePath", title = "菜单路径URI[menuPath]")
    private String codePath;

    @Schema(name = "icon", title = "角色图标")
    private String icon;

    @Schema(name = "appId", title = "应用id")
    private String appId;

    @Schema(name = "descr", title = "描述")
    private String descr;

    @Schema(name = "roleLevel", title = "角色级别")
    private Integer roleLevel;

    @Schema(name = "inherit", title = "当前角色是否继承父角色对应的权限")
    private Boolean inherit;

    @Schema(name = "state", title = "状态")
    private Boolean state;

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

