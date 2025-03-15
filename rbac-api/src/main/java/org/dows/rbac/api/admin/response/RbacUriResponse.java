package org.dows.rbac.api.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description
 * @date
 */
@Data
@NoArgsConstructor
@Schema(name = "RbacUriQuery 对象", title = "uri资源查询返回")
public class RbacUriResponse {
    @Schema(title = "资源id")
    private Long rbacUriId;

    @Schema(title = "资源名称")
    private String name;

    @Schema(title = "资源CODE")
    private String code;

    @Schema(name = "label", title = "页面功能标签[按钮、链接]")
    private String label;

    @Schema(title = "资源链接")
    private String url;

    @Schema(name = "configJson", title = "json数据集")
    private String configJson;

    @Schema(title = "应用id")
    private String appId;

    @Schema(name = "methodName", title = "方法名")
    private String methodName;

    @Schema(title = "描述")
    private String descr;

    @Schema(title = "是否共享[0:不共享,1:共享]")
    private Integer shared;

    @Schema(title = "状态")
    private Integer state;


}
