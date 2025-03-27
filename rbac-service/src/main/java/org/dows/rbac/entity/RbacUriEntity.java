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
 * 接口集表 实体类。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "接口集表")
@Table(value = "rbac_uri")
public class RbacUriEntity extends BaseEntity<RbacUriEntity> {

    /**
     * 接口ID
     */
    @Schema(description = "接口ID")
    @Id(keyType = KeyType.Generator,value= KeyGenerators.flexId)
    private Long rbacUriId;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单ID")
    @Column(value = "rbac_menu_id")
    private Long rbacMenuId;

    /**
     * 类方法
     */
    @Schema(description = "类方法")
    @Column(value = "java_method")
    private String javaMethod;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    @Column(value = "http_method")
    private String httpMethod;

    /**
     * 资源标识
     */
    @Schema(description = "资源标识")
    @Column(value = "uri")
    private String uri;

    /**
     * 概要
     */
    @Schema(description = "概要")
    @Column(value = "summary")
    private String summary;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @Column(value = "description")
    private String description;

    /**
     * 入参
     */
    @Schema(description = "入参")
    @Column(value = "inputs")
    private String inputs;

    /**
     * 出参
     */
    @Schema(description = "出参")
    @Column(value = "output")
    private String output;

    /**
     * 页面功能标签[按钮、链接]
     */
    @Schema(description = "页面功能标签[按钮、链接]")
    @Column(value = "label")
    private String label;

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
