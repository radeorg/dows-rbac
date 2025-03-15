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
 * 角色权限组(资源)(RbacGroup)实体类
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
@Schema(name = "RbacGroup", title = "角色权限组(资源)")
@TableName("rbac_group")
public class RbacGroupEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacGroupId", title = "角色权限组ID")
    private Long rbacGroupId;

    @Schema(name = "groupName", title = "组名称")
    private String groupName;

    @Schema(name = "groupCode", title = "组CODE")
    private String groupCode;

    @Schema(name = "appId", title = "应用id")
    private String appId;

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

