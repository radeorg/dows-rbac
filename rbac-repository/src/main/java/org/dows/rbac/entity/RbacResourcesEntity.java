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
 * 模块资源(RbacResources)实体类
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
@Schema(name = "RbacResources", title = "模块资源")
@TableName("rbac_resources")
public class RbacResourcesEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacResourcesId", title = "模块资源ID")
    private Long rbacResourcesId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacModuleId", title = "权限模块ID")
    private Long rbacModuleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "resourceId", title = "资源ID")
    private Long resourceId;

    @Schema(name = "name", title = "资源名称")
    private String name;

    @Schema(name = "code", title = "资源CODE")
    private String code;

    @Schema(name = "appId", title = "应用id")
    private String appId;

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

