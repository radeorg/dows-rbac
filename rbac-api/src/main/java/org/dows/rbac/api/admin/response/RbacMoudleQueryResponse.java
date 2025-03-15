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
@Schema(name = "RbacMoudleQuery 对象", title = "权限模块查询返回")
public class RbacMoudleQueryResponse {
    @Schema(title = "权限模块ID")
    private Long rbacModuleId;

    @Schema(title = "模块|组名称")
    private String moduleName;

    @Schema(title = "模块|组CODE")
    private String moduleCode;

    @Schema(title = "应用id")
    private String appId;

    @Schema(title = "状态")
    private Integer state;


}
