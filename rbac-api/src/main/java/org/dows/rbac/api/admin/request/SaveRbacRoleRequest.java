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
@Schema(name = "SaveRbacRole 对象", title = "角色创建")
public class SaveRbacRoleRequest {
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

    @Schema(title = "编码路径")
    private String codePath;

    @Schema(title = "父级id路径")
    private String preIdPath;

    @Schema(title = "父级名称路径")
    private String preNamePath;

    @Schema(title = "父级编码路径")
    private String preCodePath;

    @Schema(title = "角色图标")
    private String icon;

    @Schema(title = "应用id")
    private String appId;

    @Schema(title = "描述")
    private String descr;

    @Schema(title = "角色级别")
    private Integer roleLevel;

    @Schema(title = "当前角色是否继承父角色对应的权限")
    private Boolean inherit;

    @Schema(title = "状态")
    private Boolean state;

    @Schema(title = "父角色编码")
    private String parentRoleCode;


}
