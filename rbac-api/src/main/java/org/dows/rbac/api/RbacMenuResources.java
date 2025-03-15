package org.dows.rbac.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(title = "资源集合[menu,uri]")
@Data
@NoArgsConstructor
public class RbacMenuResources {
    @Schema(title = "资源ID")
    private Long resourceId;

    @NotNull(message = "权限码[authority]权限码不能为空")
    @Schema(title = "权限码")
    private String authority;

    @Schema(title = "资源类型[0:接口，1:菜单]")
    private Integer resourceType;

    @Schema(title = "状态")
    private Integer state;
}