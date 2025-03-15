package org.dows.rbac.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dows.framework.crud.api.model.PageRequest;
import org.dows.framework.crud.api.model.PageResponse;
import org.dows.rbac.api.admin.request.FindRbacRulesRequest;
import org.dows.rbac.api.admin.request.SaveRbacRulesRequest;
import org.dows.rbac.api.admin.response.RbacRulesResponse;
import org.dows.rbac.api.annotation.Menu;
import org.dows.rbac.biz.admin.RulesBiz;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Menu(name = "数据权限", code = "rbac.data.rule", path = "")
@RequiredArgsConstructor
@RestController
@Tag(name = "数据规则管理", description = "数据规则管理")
public class RulesRest {
    private final RulesBiz rulesBiz;

    /**
     * 批量更新和插入
     *
     * @param saveRbacRules
     */
    @Operation(summary = "批量更新和插入")
    @PostMapping("v1/admin/rules/save")
    public void save(@RequestBody @Validated List<SaveRbacRulesRequest> saveRbacRules) {
        rulesBiz.save(saveRbacRules);
    }

    /**
     * 根据appid查询应用的数据规则集
     *
     * @param appId
     * @return
     */
    @Operation(summary = "根据appid查询应用的数据规则集")
    @GetMapping("v1/admin/rules/listByAppId")
    public List<RbacRulesResponse> listByAppId(@RequestParam String appId) {
        return rulesBiz.listByAppId(appId);
    }

    /**
     * 分页查询
     *
     * @param findRbacRules
     * @return
     */
    @Operation(summary = "分页查询")
    @PostMapping("v1/admin/rules/paging")
    public PageResponse<RbacRulesResponse> paging(@RequestBody @Validated PageRequest<FindRbacRulesRequest> findRbacRules) {
        return rulesBiz.paging(findRbacRules);
    }

    /**
     * 根据ID删除数据规则集
     *
     * @param rbacRulesIds
     */
    @Operation(summary = "根据ID删除数据规则集")
    @DeleteMapping("v1/admin/rules/deleteById")
    public void deleteByIds(@RequestParam List<Long> rbacRulesIds) {
        rulesBiz.deleteByIds(rbacRulesIds);
    }
}