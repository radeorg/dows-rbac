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
 * 菜单集(RbacMenu)实体类
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
@Schema(name = "RbacMenu", title = "菜单集")
@TableName("rbac_menu")
public class RbacMenuEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacMenuId", title = "菜单ID")
    private Long rbacMenuId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "pid", title = "菜单父ID")
    private Long pid;

    @Schema(name = "name", title = "菜单名称")
    private String name;

    @Schema(name = "code", title = "菜单CODE")
    private String code;

    @Schema(name = "idPath", title = "id路径")
    private String idPath;

    @Schema(title = "图标")
    private String icon;

    @Schema(title = "打开类型[0:page,1:api,2:......]")
    private Integer openType;

    @Schema(name = "namePath", title = "名称路径")
    private String namePath;

    @Schema(name = "codePath", title = "菜单路径URI[menuPath]")
    private String codePath;

    @Schema(title = "前端路由路径")
    private String path;

    @Schema(name = "configJson", title = "json数据集")
    private String configJson;

    @Schema(name = "appId", title = "应用id")
    private String appId;

    @Schema(name = "descr", title = "描述")
    private String descr;

    @Schema(name = "sorted", title = "排序")
    private Integer sorted;

    @Schema(name = "ver", title = "乐观锁, 默认: 0")
    private Integer ver;

    @Schema(name = "visible", title = "是否隐藏")
    private Boolean visible;

    @Schema(name = "isframe", title = "是否框架")
   private Integer isframe;

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

    @Schema(title = "层级")
    private Integer level;

}

