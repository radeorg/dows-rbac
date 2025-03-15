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
@Schema(name = "SaveRbacModule 对象", title = "保存权限模块")
public class SaveRbacModuleRequest {
    @Schema(title = "权限模块ID")
    private Long rbacModuleId;

    @NotNull(message = "模块|组名称[moduleName]名称不能为空")
    @Schema(title = "模块|组名称")
    private String moduleName;

    @NotNull(message = "模块|组CODE[moduleCode]CODE不能为空")
    @Schema(title = "模块|组CODE")
    private String moduleCode;

    @Schema(title = "应用id")
    private String appId;

    @Schema(title = "状态")
    private Integer state;


}
