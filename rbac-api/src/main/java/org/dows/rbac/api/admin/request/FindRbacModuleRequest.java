package org.dows.rbac.api.admin.request;

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
@Schema(name = "FindRbacModle 对象", title = "权限模块信息")
public class FindRbacModuleRequest extends BaseFindRequest {

    @Schema(name = "rbacModuleId", title = "权限模块ID")
    private Long rbacModuleId;

    @Schema(name = "moduleName", title = "模块|组名称")
    private String moduleName;

    @Schema(name = "moduleCode", title = "模块|组CODE")
    private String moduleCode;

    @Schema(name = "appId", title = "应用id")
    private String appId;

    @Schema(name = "state", title = "状态")
    private Integer state;

}
