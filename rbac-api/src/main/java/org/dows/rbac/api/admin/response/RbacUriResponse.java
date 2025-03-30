package org.dows.rbac.api.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author
 * @description
 * @date
 */
@Data
@NoArgsConstructor
@Schema(name = "RbacUriQuery 对象", title = "uri资源查询返回")
public class RbacUriResponse {

    @Schema(description = "接口ID")
    private Long rbacUriId;

    @Schema(description = "菜单ID")
    private Long rbacMenuId;

    @Schema(description = "类方法")
    private String javaMethod;

    @Schema(description = "请求方法")
    private String httpMethod;

    @Schema(description = "资源标识")
    private String uri;

    @Schema(description = "概要")
    private String summary;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "入参")
    private String inputs;

    @Schema(description = "出参")
    private String output;

    @Schema(description = "页面功能标签[按钮、链接]")
    private String label;

    @Schema(description = "JSON数据集")
    private String configJson;

    @Schema(description = "应用id")
    private String appId;

    @Schema(description = "自定义")
    private Integer customed;

    @Schema(description = "乐观锁, 默认: 0")
    private Integer ver;

    @Schema(description = "是否共享[0:不共享,1:共享]")
    private Integer shared;

    @Schema(description = "状态")
    private Integer state;

    /**
     * 逻辑删除  0未删除  1 删除
     */
    @Schema(description = "逻辑删除  0未删除  1 删除")
    private Integer deleted;

    @Schema(description = "时间戳")
    private Date ts;
}
