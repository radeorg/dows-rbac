package org.dows.rbac.api.admin.request;

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
@Schema(name = "SaveRbacResources 对象", title = "保存权限模块资源")
public class SaveRbacResourcesRequest {
    @Schema(title = "模块资源ID")
    private Long rbacResourcesId;

    @Schema(title = "权限模块ID")
    private Long rbacModuleId;

    @NotNull(message = "资源ID[resourceId]不能为空")
    @Schema(title = "资源ID")
    private Long resourceId;

    @NotNull(message = "资源名称[name]不能为空")
    @Schema(title = "资源名称")
    private String name;

    @NotNull(message = "资源CODE[code]不能为空")
    @Schema(title = "资源CODE")
    private String code;

    @Schema(title = "应用id")
    private String appId;

    @Schema(title = "资源类型[0:接口，1:菜单]")
    private Integer resourceType;

    @Schema(title = "状态")
    private Integer state;


}
