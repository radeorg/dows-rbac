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
 * 接口集表 实体类。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "接口集表")
@Table(value = "rbac_uri")
public class RbacUriEntity extends BaseEntity<RbacUriEntity> {

    /**
     * 接口ID
     */
    @Schema(description = "接口ID")
    @Id(keyType = KeyType.Auto)
    private Long rbacUriId;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    @Column(value = "rbac_menu_id")
    private Long rbacMenuId;

    /**
     * 接口名称
     */
    @Schema(description = "接口名称")
    @Column(value = "uri_name")
    private String uriName;

    /**
     * 接口CODE
     */
    @Schema(description = "接口CODE")
    @Column(value = "uri_code")
    private String uriCode;

    /**
     * 页面功能标签[按钮、链接]
     */
    @Schema(description = "页面功能标签[按钮、链接]")
    @Column(value = "label")
    private String label;

    /**
     * 接口链接
     */
    @Schema(description = "接口链接")
    @Column(value = "uri")
    private String uri;

    /**
     * JSON数据集
     */
    @Schema(description = "JSON数据集")
    @Column(value = "config_json")
    private String configJson;

    /**
     * 应用id
     */
    @Schema(description = "应用id")
    @Column(value = "app_id")
    private String appId;

    /**
     * 自定义
     */
    @Schema(description = "自定义")
    @Column(value = "customed")
    private Integer customed;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Column(value = "description")
    private String description;

    /**
     * 乐观锁, 默认: 0
     */
    @Schema(description = "乐观锁, 默认: 0")
    @Column(value = "ver")
    private Integer ver;

    /**
     * 是否共享[0:不共享,1:共享]
     */
    @Schema(description = "是否共享[0:不共享,1:共享]")
    @Column(value = "shared")
    private Integer shared;

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
