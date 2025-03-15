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
@Schema(name = "FindRbacRole 对象", title = "角色查询信息")
public class FindRbacRoleRequest {
    @Schema(title = "角色id")
    private Long rbacRoleId;

    @Schema(title = "角色父ID(角色组|继承)")
    private Long pid;

    @Schema(title = "角色名称")
    private String roleName;

    @Schema(title = "角色名称首字母拼音码")
    private String pyCode;

    @Schema(title = "角色编码")
    private String roleCode;

    @Schema(title = "应用id")
    private String appId;

    @Schema(title = "状态")
    private Integer state;


}
