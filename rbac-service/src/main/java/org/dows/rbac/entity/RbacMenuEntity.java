package org.dows.rbac.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;

import org.dows.rade.crud.BaseEntity;

/**
 * 菜单集表 实体类。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "菜单集表")
@Table(value = "rbac_menu")
public class RbacMenuEntity extends BaseEntity<RbacMenuEntity> {

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    @Id(keyType = KeyType.Auto)
    private Long rbacMenuId;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    @Column(value = "rbac_role_id")
    private Long rbacRoleId;

    /**
     * 菜单父ID
     */
    @Schema(description = "菜单父ID")
    @Column(value = "pid")
    private Long pid;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    @Column(value = "menu_name")
    private String menuName;

    /**
     * 菜单CODE
     */
    @Schema(description = "菜单CODE")
    @Column(value = "menu_code")
    private String menuCode;

    /**
     * ID路径
     */
    @Schema(description = "ID路径")
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
     * 配置JSON{vue:compent-path}
     */
    @Schema(description = "配置JSON{vue:compent-path}")
    @Column(value = "config_json")
    private String configJson;

    /**
     * 图标
     */
    @Schema(description = "图标")
    @Column(value = "menu_icon")
    private String menuIcon;

    /**
     * 前端路由路径
     */
    @Schema(description = "前端路由路径")
    @Column(value = "menu_path")
    private String menuPath;

    /**
     * 跳转路径
     */
    @Schema(description = "跳转路径")
    @Column(value = "redirect")
    private String redirect;

    /**
     * 应用id
     */
    @Schema(description = "应用id")
    @Column(value = "app_id")
    private String appId;

    /**
     * 排序
     */
    @Schema(description = "排序")
    @Column(value = "seq")
    private Integer seq;

    /**
     * 打开类型[0:page,1:api,2:......]
     */
    @Schema(description = "打开类型[0:page,1:api,2:......]")
    @Column(value = "open_type")
    private Integer openType;

    /**
     * 乐观锁, 默认: 0
     */
    @Schema(description = "乐观锁, 默认: 0")
    @Column(value = "ver")
    private Integer ver;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    @Column(value = "visible")
    private Integer visible;

    /**
     * 是否框架
     */
    @Schema(description = "是否框架")
    @Column(value = "isframe")
    private Integer isframe;

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
