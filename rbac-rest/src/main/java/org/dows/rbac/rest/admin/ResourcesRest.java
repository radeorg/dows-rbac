//package org.dows.rbac.rest.admin;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.dows.rbac.api.Menu;
//import org.dows.rbac.api.admin.request.SaveRbacModuleResourcesRequest;
//import org.dows.rbac.api.admin.request.SaveRbacResourcesRequest;
//import org.dows.rbac.api.admin.response.RbacResourcesQueryResponse;
//import org.dows.rbac.api.admin.request.FindRbacResourcesRequest;
//import org.dows.rbac.biz.admin.ResourcesBiz;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
//* @description project descr:管理端:模块资源管理
//*
//* @author lait.zhang
//* @date 2024年2月27日 上午11:52:56
//*/
//@Menu(name = "", code = "rbac.uri.instance", path = "")
//@RequiredArgsConstructor
//@RestController
//@Tag(name = "模块资源管理", description = "模块资源管理")
//public class ResourcesRest {
//    private final ResourcesBiz resourcesBiz;
//
//    /**
//    * 保存权限模块资源
//    * @param
//    * @return
//    */
//    @Transactional
//    @Operation(summary = "保存权限模块资源")
//    @PostMapping("v1/admin/resources/save")
//    public void save(@RequestBody @Validated List<SaveRbacResourcesRequest> saveRbacResources ) {
//        resourcesBiz.save(saveRbacResources);
//    }
//
//    @Transactional
//    @Operation(summary = "保存权限模块资源")
//    @PostMapping("v1/admin/resources/saveRbacModuleResources")
//    public void saveRbacModuleResources(@RequestBody @Validated SaveRbacModuleResourcesRequest saveRbacModuleResourcesRequest) {
//        resourcesBiz.saveRbacModuleResources(saveRbacModuleResourcesRequest);
//    }
//
//
//    /**
//    * 根据模块资源Id查询
//    * @param
//    * @return
//    */
//    @Operation(summary = "根据模块资源Id查询")
//    @GetMapping("v1/admin/resources/getById")
//    public RbacResourcesQueryResponse getById(@RequestParam Long rbacResourcesId) {
//        return resourcesBiz.getById(rbacResourcesId);
//    }
//
//    /**
//    * 根据条件查询
//    * @param
//    * @return
//    */
//    @Operation(summary = "根据条件查询")
//    @PostMapping("v1/admin/resources/listByQuery")
//    public List<RbacResourcesQueryResponse> listByQuery(@RequestBody FindRbacResourcesRequest findRbacResources) {
//        return resourcesBiz.listByQuery(findRbacResources);
//    }
//
//    /**
//    * 根据Id删除
//    * @param
//    * @return
//    */
//    @Transactional
//    @Operation(summary = "根据Id删除")
//    @DeleteMapping("v1/admin/resources/deleteById")
//    public void deleteByIds(@RequestParam List<Long> rbacResourcesIds ) {
//        resourcesBiz.deleteByIds(rbacResourcesIds);
//    }
//
//
//}