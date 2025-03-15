package org.dows.rbac.api.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(name = "添加权限资源模块对象", title = "添加权限资源模块信息")
public class SaveRbacModuleResourcesRequest {


    @Schema(title = "模块ID")
    private Long rbacModuleId;

    @Schema(title = "模块菜单IDs")
    private List<Long> rbacMenusIds;

    @Schema(title = "模块接口IDs")
    private List<Long> rbacUrisIds;
}
