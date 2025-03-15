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
@Schema(name = "SaveRbacGroup 对象", title = "保存或更新权限组")
public class SaveRbacGroupRequest {
    @Schema(title = "角色权限组ID")
    private Long rbacGroupId;

    @Schema(title = "组名称")
    private String groupName;

    @Schema(title = "组CODE")
    private String groupCode;

    @Schema(title = "状态")
    private Integer state;

    @Schema(name = "appId", title = "应用id")
    private String appId;


}
