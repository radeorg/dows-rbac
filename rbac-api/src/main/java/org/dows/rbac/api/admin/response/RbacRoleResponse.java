package org.dows.rbac.api.admin.response;

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
@Schema(name = "RbacRole 对象", title = "角色信息")
public class RbacRoleResponse {
    @Schema(title = "角色id")
    private Long rbacRoleId;

    @Schema(title = "角色父ID(角色组|继承)")
    private Long pid;

    @NotNull(message = "角色名称[roleName]角色名称不能为空")
    @Schema(title = "角色名称")
    private String roleName;

    @Schema(title = "角色名称首字母拼音码")
    private String pyCode;

    @NotNull(message = "角色编码[roleCode]角色编码不能为空")
    @Schema(title = "角色编码")
    private String roleCode;

    @Schema(title = "id路径")
    private String idPath;

    @Schema(title = "名称路径")
    private String namePath;

    @Schema(title = "菜单路径URI[menuPath]")
    private String codePath;

    @Schema(title = "应用id")
    private String appId;

    @Schema(title = "描述")
    private String descr;

    @Schema(title = "角色级别")
    private Integer roleLevel;

    @Schema(title = "当前角色是否继承父角色对应的权限")
    private Integer inherit;

    @Schema(title = "状态")
    private Integer state;


}
