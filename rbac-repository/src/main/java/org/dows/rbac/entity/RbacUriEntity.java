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
 * 接口集(RbacUri)实体类
 *
 * @author lait
 * @since 2024-02-27 11:58:39
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "RbacUri", title = "接口集")
@TableName("rbac_uri")
public class RbacUriEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacUriId", title = "资源id")
    private Long rbacUriId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacMenuId", title = "菜单ID")
    private Long rbacMenuId;

    @Schema(name = "name", title = "资源名称")
    private String name;

    @Schema(name = "code", title = "资源CODE")
    private String code;

    @Schema(name = "label", title = "页面功能标签[按钮、链接]")
    private String label;

    @Schema(name = "url", title = "资源链接")
    private String url;

    @Schema(name = "configJson", title = "json数据集")
    private String configJson;

    @Schema(name = "appId", title = "应用id")
    private String appId;

    @Schema(name = "descr", title = "描述")
    private String descr;

    @Schema(name = "methodName", title = "方法名")
    private String methodName;

    @Schema(name = "ver", title = "乐观锁, 默认: 0")
    private Integer ver;

    @Schema(name = "shared", title = "是否共享[0:不共享,1:共享]")
    private Boolean shared;

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

