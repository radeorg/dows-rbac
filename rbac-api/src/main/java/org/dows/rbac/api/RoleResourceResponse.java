package org.dows.rbac.api;

import lombok.Data;

import java.util.List;


@Data
public class RoleResourceResponse {
    private Long roleId;
    private List<String> authority;

}
