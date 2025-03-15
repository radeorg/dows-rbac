package org.dows.rbac.api.admin.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @description
 * @date
 */
@Data
@NoArgsConstructor
@Schema(name = "SaveUris 对象", title = "资源创建")
public class SaveUrisRequest {
    @Schema(title = "资源id")
    private Long rbacUriId;

    @NotNull(message = "资源名称[name]资源名称不能为空")
    @Schema(title = "资源名称")
    private String name;

    @NotNull(message = "资源CODE[code]资源CODE不能为空")
    @Schema(title = "资源CODE")
    private String code;

    @NotNull(message = "资源链接[url]资源链接不能为空")
    @Schema(title = "资源链接")
    private String url;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(name = "rbacMenuId", title = "菜单ID")
    private Long rbacMenuId;

    @Schema(name = "label", title = "页面功能标签[按钮、链接]")
    private String label;

    @Schema(name = "configJson", title = "json数据集")
    private String configJson;

    @Schema(title = "应用id")
    private String appId;

    @Schema(title = "描述")
    private String descr;

    @Schema(title = "是否共享[0:不共享,1:共享]")
    private Boolean shared;

    @Schema(title = "状态")
    private Boolean state;


}
