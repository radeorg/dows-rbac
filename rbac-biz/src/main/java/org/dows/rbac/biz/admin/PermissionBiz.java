package org.dows.rbac.biz.admin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.TreeNode;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.app.api.AppContext;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.*;
import org.dows.rbac.api.admin.request.*;
import org.dows.rbac.api.admin.response.*;
import org.dows.rbac.api.annotation.RbacTrigger;
import org.dows.rbac.api.constant.ResourceEnum;
import org.dows.rbac.api.constant.StateEnum;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.entity.RbacPermissionEntity;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.entity.RbacUriEntity;
import org.dows.rbac.handler.*;
import org.dows.rbac.repository.RbacMenuRepository;
import org.dows.rbac.repository.RbacPermissionRepository;
import org.dows.rbac.repository.RbacRoleRepository;
import org.dows.rbac.repository.RbacUriRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lait.zhang
 * @description project descr:管理端:权限管理
 * @date 2024年2月27日 上午11:52:56
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionBiz implements RbacApi {
    private final RbacPermissionRepository rbacPermissionRepository;

    private final RbacUriRepository rbacUriRepository;

    private final RbacMenuRepository rbacMenusRepository;

    private final RbacRoleRepository rbacRoleRepository;

    private final UrisHandler urisHandler;

    private final ResourcesHandler resourcesHandler;

    private final MenuHandler menuHandler;

    private final RoleHandler roleHandler;

    private final RbacDataHandler rbacContext;

    private final PermissionHandler permissionHandler;

    private final RbacCache rbacCache;


    /**
     * @param
     * @return
     * @说明: 保存权限
     * 解除之前绑定，保存新的绑定
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    @RbacTrigger
    @Transactional
    public void save(List<SaveRbacPermissionRequest> saveRbacPermission) {
        Map<Long, List<SaveRbacPermissionRequest>> groupedPermissions = saveRbacPermission.stream()
                .collect(Collectors.groupingBy(SaveRbacPermissionRequest::getRbacRoleId));
        Set<Long> roleIds = groupedPermissions.keySet();
        List<RbacPermissionEntity> permissionByRoleIds = permissionHandler.getPermissionByRoleIds(CollUtil.newArrayList(roleIds));
        List<Long> removePermissionIds = permissionByRoleIds.stream().map(RbacPermissionEntity::getRbacPermissionId).toList();
        List<RbacPermissionEntity> rbacPermissionEntities = new ArrayList<>();
        for (SaveRbacPermissionRequest saveRbacPermissionRequest : saveRbacPermission) {
            List<RbacResources> rbacResource = saveRbacPermissionRequest.getRbacResource();
            for (RbacResources rbacResources : rbacResource) {
                RbacPermissionEntity rbacPermissionEntity = BeanConvert.beanConvert(saveRbacPermissionRequest, RbacPermissionEntity.class);
                rbacPermissionEntity.setAuthority(rbacResources.getAuthority())
                        .setResourceId(rbacResources.getResourceId())
                        .setResourceType(rbacResources.getResourceType())
                        .setState(rbacResources.getState());
                rbacPermissionEntities.add(rbacPermissionEntity);
            }
        }
        rbacPermissionRepository.saveOrUpdateBatch(rbacPermissionEntities);
        // 解除旧绑定
        permissionHandler.deleteByIds(removePermissionIds);
    }

    @Transactional
    public void savePermissionMenu(SaveRbacPermissionMenuRequest saveRbacPermissionMenuRequest) {
        RbacRoleEntity rbacRoleEntity = rbacRoleRepository.getById(saveRbacPermissionMenuRequest.getRoleId());
        if (Objects.isNull(rbacRoleEntity)) {
            throw new IllegalArgumentException("未找到对应角色信息");
        }
        List<Long> menuIds = saveRbacPermissionMenuRequest.getMenuIds();
        if (CollectionUtils.isEmpty(menuIds)) {
            throw new IllegalArgumentException("menuIds信息不能为空");
        }
        List<RbacPermissionEntity> rbacPermissionEntities = new ArrayList<>();
        menuIds.forEach(menuId -> {
            RbacMenuEntity rbacMenuEntity = rbacMenusRepository.getById(menuId);
            RbacPermissionEntity rbacPermissionEntity = new RbacPermissionEntity();
            rbacPermissionEntity.setRbacRoleId(saveRbacPermissionMenuRequest.getRoleId());
            rbacPermissionEntity.setRolePid(rbacRoleEntity.getPid());
            rbacPermissionEntity.setRoleName(rbacRoleEntity.getRoleName());
            rbacPermissionEntity.setRoleCode(rbacRoleEntity.getRoleCode());
            rbacPermissionEntity.setAppId(rbacRoleEntity.getAppId());
            rbacPermissionEntity.setAuthority(rbacMenuEntity.getCode());
            rbacPermissionEntity.setResourceType(ResourceEnum.MENU.getCode());
            rbacPermissionEntity.setResourceId(rbacMenuEntity.getRbacMenuId());
            rbacPermissionEntity.setState(StateEnum.AVAILABLE.getCode());
            rbacPermissionEntities.add(rbacPermissionEntity);
        });
        rbacPermissionRepository.saveOrUpdateBatch(rbacPermissionEntities);
    }

    @Transactional
    public void savePermissionModule(SaveRbacPermissionModuleRequest saveRbacPermissionModuleRequest) {
        RbacRoleEntity rbacRoleEntity = rbacRoleRepository.getById(saveRbacPermissionModuleRequest.getRoleId());
        if (Objects.isNull(rbacRoleEntity)) {
            throw new IllegalArgumentException("未找到对应角色信息");
        }
        List<Long> moduleIds = saveRbacPermissionModuleRequest.getModuleId();
        if (CollectionUtils.isEmpty(moduleIds)) {
            throw new IllegalArgumentException("moduleIds信息不能为空");
        }
        List<RbacPermissionEntity> rbacPermissionEntities = new ArrayList<>();
        moduleIds.forEach(moduleId -> {
            resourcesHandler.listByModuleIdAndResourceType(moduleId, ResourceEnum.MENU.getCode()).forEach(rbacResourcesEntity -> {
                RbacPermissionEntity rbacPermissionEntity = new RbacPermissionEntity();
                rbacPermissionEntity.setRbacRoleId(saveRbacPermissionModuleRequest.getRoleId());
                rbacPermissionEntity.setRolePid(rbacRoleEntity.getPid());
                rbacPermissionEntity.setRoleName(rbacRoleEntity.getRoleName());
                rbacPermissionEntity.setRoleCode(rbacRoleEntity.getRoleCode());
                rbacPermissionEntity.setAppId(rbacRoleEntity.getAppId());
                rbacPermissionEntity.setAuthority(rbacResourcesEntity.getCode());
                rbacPermissionEntity.setResourceType(ResourceEnum.MENU.getCode());
                rbacPermissionEntity.setResourceId(rbacResourcesEntity.getResourceId());
                rbacPermissionEntity.setState(StateEnum.AVAILABLE.getCode());
                rbacPermissionEntities.add(rbacPermissionEntity);
            });
        });
        rbacPermissionRepository.saveOrUpdateBatch(rbacPermissionEntities);
    }

    /**
     * @param
     * @return
     * @说明: 根据角色id查询应用的接口集
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public List<RbacUriResponse> listUrisByRoleId(Long rbacRoleId) {
        List<RbacUriEntity> rbacUriEntities = permissionHandler.getUriByRoleId(rbacRoleId);
        return BeanConvert.beanConvert(rbacUriEntities, RbacUriResponse.class);
    }

    /**
     * @param
     * @return
     * @说明: 根据角色id查询应用的菜单集
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public List<RbacMenusResponse> listMenusByRoleId(Long rbacRoleId) {
        return BeanConvert.beanConvert(getMenusByRoleId(rbacRoleId), RbacMenusResponse.class);
    }

    public List<RbacMenuEntity> getMenusByRoleId(Long rbacRoleId) {
        return permissionHandler.getMenusByRoleId(rbacRoleId);
    }

    /**
     * @param
     * @return
     * @说明: 根据角色查询权限
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public List<RbacPermissionResponse> listPermissionByRoleIds(List<Long> rbacRoleIds) {
        List<RbacPermissionResponse> rbacMenuEntityList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(rbacRoleIds)) {
            for (Long rbacRoleId : rbacRoleIds) {
                if (roleIsAvailable(rbacRoleId)) {
                    continue;
                }
                List<RbacMenuEntity> rbacMenuEntities = rbacCache.getMenuByRoleId(rbacRoleId);
                if (CollectionUtil.isNotEmpty(rbacMenuEntities)) {
                    List<RbacPermissionResponse> rbacPermissionResponsesMenu = BeanConvert.beanConvert(rbacMenuEntities, RbacPermissionResponse.class);
                    if (CollectionUtil.isNotEmpty(rbacPermissionResponsesMenu)) {
                        rbacMenuEntityList.addAll(rbacPermissionResponsesMenu);
                    }
                }
                List<RbacUriEntity> rbacUriEntities = rbacCache.getUriByRoleId(rbacRoleId);
                if (CollectionUtil.isNotEmpty(rbacUriEntities)) {
                    List<RbacPermissionResponse> rbacPermissionResponsesUri = BeanConvert.beanConvert(rbacUriEntities, RbacPermissionResponse.class);
                    if (CollectionUtil.isNotEmpty(rbacPermissionResponsesUri)) {
                        rbacMenuEntityList.addAll(rbacPermissionResponsesUri);
                    }
                }
            }
        }
        if (CollectionUtil.isEmpty(rbacMenuEntityList)) {
            List<RbacPermissionEntity> rbacPermissionEntity = permissionHandler.getPermissionByRoleIds(rbacRoleIds);
            if (CollectionUtil.isNotEmpty(rbacPermissionEntity)) {
                flushData();
            }
            return BeanConvert.beanConvert(rbacPermissionEntity, RbacPermissionResponse.class);
        }
        return rbacMenuEntityList;
    }

    /**
     * @param
     * @return
     * @说明: 查询权限树
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public List<RbacMenusResponse> listMenusTree() {
        String appId = AppContext.getAppId();
        List<RbacMenuEntity> rbacMenuEntityList = rbacMenusRepository.lambdaQuery()
                .eq(RbacMenuEntity::getAppId, appId)
                .eq(RbacMenuEntity::getState, StateEnum.AVAILABLE.getCode())
                .orderByDesc(RbacMenuEntity::getSorted)
                .list();
        return convertToTreeList(rbacMenuEntityList);
    }

    @Override
    public List<RbacMenusResponse> listRoleMenusTree(List<Long> rbacRoleIds) {
        List<RbacMenuEntity> rbacMenuEntityList = new ArrayList<>();
        if (CollectionUtil.isEmpty(rbacRoleIds)) {
            return Collections.emptyList();
        }
        for (Long rbacRoleId : rbacRoleIds) {
            if (roleIsAvailable(rbacRoleId)) {
                continue;
            }
            List<RbacMenuEntity> rbacMenuEntities = rbacCache.getMenuByRoleId(rbacRoleId);
            if (CollectionUtil.isNotEmpty(rbacMenuEntities)) {
                rbacMenuEntityList.addAll(rbacMenuEntities);
            }
        }
        if (CollectionUtil.isEmpty(rbacMenuEntityList)) {
            List<RbacPermissionEntity> list = permissionHandler.getPermissionByRoleIds(rbacRoleIds, ResourceEnum.MENU.getCode());
            if (CollectionUtil.isNotEmpty(list)) {
                List<Long> resourceIds = list.stream()
                        .map(RbacPermissionEntity::getResourceId)
                        .toList();
                if (CollectionUtil.isEmpty(resourceIds)) {
                    return Collections.emptyList();
                }
                // 根据资源id查询接口信息 过滤对应 visible  和 state状态的
                rbacMenuEntityList = rbacMenusRepository.listByIds(resourceIds);
                flushData();
            }
        }
        if (CollectionUtil.isNotEmpty(rbacMenuEntityList)) {
            rbacMenuEntityList = rbacMenuEntityList.stream()
                    .filter(item -> !item.getVisible() && !item.getState())
                    .sorted(Comparator.comparing(RbacMenuEntity::getSorted).reversed())
                    .toList();
        }
        return convertToTreeList(rbacMenuEntityList);
    }

    private List<RbacMenusResponse> convertToTreeList(List<RbacMenuEntity> menuList) {
        if (CollectionUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        List<RbacMenusResponse> treeList = new ArrayList<>();
        Map<Long, RbacMenusResponse> menuMap = new LinkedHashMap<>();

        // 将菜单列表转换为以菜单ID为键的Map
        for (RbacMenuEntity menu : menuList) {
            RbacMenusResponse rbacMenusResponse = new RbacMenusResponse();
            BeanUtils.copyProperties(menu, rbacMenusResponse);
            // 初始化子菜单
            rbacMenusResponse.setChildren(new ArrayList<>());
            menuMap.put(rbacMenusResponse.getRbacMenuId(), rbacMenusResponse);
        }

        // 构建树形菜单列表
        for (RbacMenusResponse rbacMenusResponse : menuMap.values()) {
            Long parentId = rbacMenusResponse.getPid();
            if (parentId != null && menuMap.containsKey(parentId)) {
                RbacMenusResponse parentMenu = menuMap.get(parentId);
                parentMenu.getChildren().add(rbacMenusResponse);
            } else {
                treeList.add(rbacMenusResponse);
            }
        }

        return treeList;
    }

    /**
     * @param
     * @return
     * @说明: 根据菜单查询接口集
     * @关联表:
     * @工时: 0H
     * @开发者:
     * @开始时间:
     * @创建时间: 2024年2月27日 上午11:52:56
     */
    public List<RbacUriResponse> listUrisByMenuId(Long rbacMenuId) {
        return permissionHandler.listUrisByMenuId(rbacMenuId);
    }

    @Override
    public List<RbacRoleResponse> getRole(List<Long> roleIds) {

        List<RbacRoleResponse> rbacRoleResponses = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(roleIds)) {
            for (Long rbacRoleId : roleIds) {
                if (roleIsAvailable(rbacRoleId)) {
                    continue;
                }
                RbacRoleEntity rbacRoleEntity = rbacCache.getRoleByRoleId(rbacRoleId);
                if (!Objects.isNull(rbacRoleEntity)) {
                    RbacRoleResponse rbacRoleResponse = BeanConvert.beanConvert(rbacRoleEntity, RbacRoleResponse.class);
                    rbacRoleResponses.add(rbacRoleResponse);
                }
            }
            if (CollectionUtil.isEmpty(rbacRoleResponses)) {
                List<RbacRoleEntity> rbacRoleEntities = roleHandler.getRoleByRoleIds(roleIds);
                if (CollectionUtil.isNotEmpty(rbacRoleEntities)) {
                    flushData();
                }
                return BeanConvert.beanConvert(rbacRoleEntities, RbacRoleResponse.class);
            }
        }
        return rbacRoleResponses;
    }

    public List<RbacRoleResponse> getRolesByAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<Long> roleIds = authorities.stream().map(GrantedAuthority::getAuthority).map(Long::parseLong).toList();
        return getRole(roleIds);
    }

    @Override
    public String getMenu() {
        return null;
    }

    @Override
    public List<RbacResourcesQueryResponse> getResource(FindRbacResourcesRequest findRbacResources) {
        return resourcesHandler.listByQuery(findRbacResources);
    }

    @Override
    public void saveUri(List<InitUriResources> initUriResources) {
        urisHandler.saveOrUpdate(initUriResources);
    }

    @Override
    public Map<String, List<RbacUriRoleResponse>> getRoleUri() {
        Map<String, List<RbacUriRoleResponse>> result = rbacContext.getRbacRoleUris();
        if (CollectionUtil.isNotEmpty(result)) {
            return result;
        }
        List<RbacUriRoleResponse> rbacUriRoleResponses = new ArrayList<>();
        List<RbacRoleEntity> allRoles = roleHandler.getAllRoles();
        for (RbacRoleEntity allRole : allRoles) {
            List<RbacUriResponse> rbacUriResponses = listUrisByRoleId(allRole.getRbacRoleId());
            if (CollectionUtil.isNotEmpty(rbacUriResponses)) {
                List<RbacUriRoleResponse> rbacUriRoleResponses1 = BeanConvert.beanConvert(rbacUriResponses, RbacUriRoleResponse.class);
                if (CollectionUtil.isNotEmpty(rbacUriRoleResponses1)) {
                    rbacUriRoleResponses1.forEach(response -> {
                        response.setRoleId(allRole.getRbacRoleId());
                    });
                    rbacUriRoleResponses.addAll(rbacUriRoleResponses1);
                }
            }
        }
        rbacContext.initData();
        return rbacUriRoleResponses.stream().collect(Collectors.groupingBy(RbacUriRoleResponse::getUrl));
    }

    @Override
    public List<RbacPermissionResponse> getPermission(List<Long> roleIds) {
        return listPermissionByRoleIds(roleIds);
    }

    public Boolean roleIsAvailable(Long roleId) {
        if (null == roleId) {
            return false;
        }
        RbacRoleEntity rbacRoleEntity = rbacCache.getRoleByRoleId(roleId);
        if (null == rbacRoleEntity) {
            rbacRoleEntity = rbacRoleRepository.getById(roleId);
        }
        if (null == rbacRoleEntity) {
            return false;
        }
        return rbacRoleEntity.getState();
    }

    @Override
    public List<String> getUriCode(List<Long> roleIds) {
        List<RbacUriEntity> rbacURiEntityList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(roleIds)) {
            for (Long rbacRoleId : roleIds) {
                if (roleIsAvailable(rbacRoleId)) {
                    continue;
                }
                List<RbacUriEntity> rbacPermissionResponsesUri = rbacCache.getUriByRoleId(rbacRoleId);
                if (CollectionUtil.isNotEmpty(rbacPermissionResponsesUri)) {
                    rbacURiEntityList.addAll(rbacPermissionResponsesUri);
                }
            }
            if (CollectionUtil.isEmpty(rbacURiEntityList)) {
                List<RbacPermissionEntity> list = permissionHandler.getPermissionByRoleIds(roleIds, ResourceEnum.INTERFACE.getCode());
                if (CollectionUtil.isNotEmpty(list)) {
                    List<Long> resourceIds = list.stream()
                            .map(RbacPermissionEntity::getResourceId)
                            .toList();
                    if (CollectionUtil.isEmpty(resourceIds)) {
                        return null;
                    }
                    // 根据资源id查询接口信息 过滤对应 visible  和 state状态的
                    rbacURiEntityList = rbacUriRepository.listByIds(resourceIds);
                    if (CollectionUtil.isNotEmpty(rbacURiEntityList)) {
                        rbacURiEntityList = rbacURiEntityList.stream().filter(item -> !item.getState()).toList();
                        flushData();
                    }
                }
            }
        }
        if (CollectionUtil.isEmpty(rbacURiEntityList)) {
            return null;
        }
        return rbacURiEntityList.stream().map(RbacUriEntity::getCode).toList();
    }


    @Override
    public void saveResource(List<InitResources> resources) {
        List<InitResources> uriResources = resources.stream()
                .filter(r -> r.getType() == 1)
                .toList();
        List<InitResources> menuResources = resources.stream()
                .filter(r -> r.getType() == 0)
                .toList();
        urisHandler.saveOrUpdateResource(uriResources);
        menuHandler.saveOrUpdateResource(menuResources);
    }

    @Override
    public List<RbacMenu> initAppMenu(List<TreeNode<String>> menus, String appId) {
        // 新加的菜单实体
        List<RbacMenuEntity> newMenuEntity = new ArrayList<>();
        // 需要更新的菜单实体
        List<RbacMenuEntity> updateMenuEntity = new ArrayList<>();
        List<RbacMenu> result = new ArrayList<>();
        StringBuilder idPath = new StringBuilder();
        StringBuilder namePath = new StringBuilder();
        StringBuilder codePath = new StringBuilder();
        for (TreeNode<String> menu : menus) {
            RbacMenuEntity byMenuCode = menuHandler.getByMenuCode(menu.getId());
            if (Objects.isNull(byMenuCode)) {

                RbacMenuEntity rbacMenuEntity = new RbacMenuEntity();
                long id = IdWorker.getId();
                rbacMenuEntity.setRbacMenuId(id);
                boolean hasParentMenu = false;
                if (null != menu.getParentId()) {
                    RbacMenuEntity parentMenu = menuHandler.getByMenuCode(menu.getParentId());
                    if (null != parentMenu) {
                        rbacMenuEntity.setPid(parentMenu.getRbacMenuId());
                        hasParentMenu = true;
                        idPath.append(parentMenu.getIdPath()).append("/").append(rbacMenuEntity.getRbacMenuId());
                        namePath.append(parentMenu.getNamePath()).append("/").append(menu.getName());
                        codePath.append(parentMenu.getCodePath()).append("/").append(menu.getId());
                    } else {
                        if (!CollectionUtils.isEmpty(newMenuEntity)) {
                            Optional<RbacMenuEntity> any = newMenuEntity.stream().filter(m -> m.getCode().equals(menu.getParentId())).findAny();
                            rbacMenuEntity.setPid(any.get().getRbacMenuId());
                            hasParentMenu = true;
                            idPath.append(any.get().getIdPath()).append("/").append(rbacMenuEntity.getRbacMenuId());
                            namePath.append(any.get().getNamePath()).append("/").append(menu.getName());
                            codePath.append(any.get().getCodePath()).append("/").append(menu.getId());
                        }
                    }
                }
                if (!hasParentMenu) {
                    rbacMenuEntity.setPid(0L);
                    idPath.append(rbacMenuEntity.getRbacMenuId());
                    namePath.append(menu.getName());
                    codePath.append(menu.getId());
                }

                rbacMenuEntity.setIdPath(idPath.toString())
                        .setNamePath(namePath.toString())
                        .setCodePath(codePath.toString())
                        .setCode(menu.getId())
                        .setName(menu.getName().toString())
                        .setPath(menu.getExtra().get("path").toString())
                        .setAppId(appId);
                newMenuEntity.add(rbacMenuEntity);

            } else {
                RbacMenuEntity parentMenu = menuHandler.getByMenuCode(menu.getParentId());
                if (null == parentMenu) {
                    log.info("未找到父类信息,父类编码{}", menu.getParentId());
                    byMenuCode.setPid(0L);
                    idPath.append(byMenuCode.getRbacMenuId());
                    namePath.append(menu.getName());
                    codePath.append(menu.getId());
                    byMenuCode.setIdPath(idPath.toString());
                    byMenuCode.setNamePath(namePath.toString());
                    byMenuCode.setCodePath(codePath.toString());
                } else {
                    byMenuCode.setPid(parentMenu.getRbacMenuId());
                    idPath.append(parentMenu.getIdPath()).append("/").append(byMenuCode.getRbacMenuId());
                    namePath.append(parentMenu.getNamePath()).append("/").append(menu.getName());
                    codePath.append(parentMenu.getCodePath()).append("/").append(menu.getId());
                    byMenuCode.setIdPath(idPath.toString());
                    byMenuCode.setNamePath(namePath.toString());
                    byMenuCode.setCodePath(codePath.toString());
                }
                byMenuCode.setPath(menu.getExtra().get("path").toString());
                byMenuCode.setName(menu.getName().toString());
                byMenuCode.setAppId(appId);
                updateMenuEntity.add(byMenuCode);
            }
            idPath.setLength(0);
            namePath.setLength(0);
            codePath.setLength(0);
        }
        if (!newMenuEntity.isEmpty()) {
            rbacMenusRepository.saveBatch(newMenuEntity);
            result.addAll(BeanConvert.beanConvert(newMenuEntity, RbacMenu.class));
        }
        if (!updateMenuEntity.isEmpty()) {
            rbacMenusRepository.updateBatchById(updateMenuEntity);
            result.addAll(BeanConvert.beanConvert(updateMenuEntity, RbacMenu.class));
        }
        return result;
    }

    @Override
    public void initRoleMenu(List<TreeNode<String>> menus, String roleCode, String appid) {
        List<RbacPermissionEntity> newPermissionsList = new ArrayList<>();
        List<RbacPermissionEntity> updatePermissionsList = new ArrayList<>();
        List<RbacRoleEntity> rolesList = roleEntityHandler(roleCode, appid);
        for (TreeNode<String> menu : menus) {
            RbacMenuEntity byMenuCode = menuHandler.getByMenuCode(menu.getId());
            if (null == byMenuCode) {
                log.error("未找到菜单，菜单编号{}", menu.getId());
                continue;
            }
            for (RbacRoleEntity rbacRoleEntity : rolesList) {
                RbacPermissionEntity byMenuCodeAndRoleCode = permissionHandler.getByMenuCodeAndRoleCode(menu.getId(), rbacRoleEntity.getRoleCode());
                if (null != byMenuCodeAndRoleCode) {
                    byMenuCodeAndRoleCode
                            .setRolePid(rbacRoleEntity.getPid())
                            .setRoleName(rbacRoleEntity.getRoleName());
                    updatePermissionsList.add(byMenuCodeAndRoleCode);
                } else {
                    RbacPermissionEntity rbacPermissionEntity = new RbacPermissionEntity()
                            .setRbacRoleId(rbacRoleEntity.getRbacRoleId())
                            .setRolePid(rbacRoleEntity.getPid())
                            .setResourceId(byMenuCode.getRbacMenuId())
                            .setRoleCode(rbacRoleEntity.getRoleCode())
                            .setRoleName(rbacRoleEntity.getRoleName())
                            .setAuthority(byMenuCode.getCode())
                            .setAppId(byMenuCode.getAppId())
                            .setResourceType(ResourceEnum.MENU.getCode());
                    newPermissionsList.add(rbacPermissionEntity);
                }
            }
        }
        if (!newPermissionsList.isEmpty()) {
            rbacPermissionRepository.saveBatch(newPermissionsList);
        }
        if (!updatePermissionsList.isEmpty()) {
            rbacPermissionRepository.updateBatchById(updatePermissionsList);
        }
    }

    @Override
    public void initRoleUri(List<InitResources> resources, String roleCode, String appId) {
        List<RbacPermissionEntity> newPermissionsList = new ArrayList<>();
        List<RbacPermissionEntity> updatePermissionsList = new ArrayList<>();
        List<RbacRoleEntity> rolesList = roleEntityHandler(roleCode, appId);
        for (InitResources resource : resources) {
            RbacUriEntity rbacUriEntity = urisHandler.getByUrlCode(resource.getCode());
            if (null == rbacUriEntity) {
                log.error("未找到接口，接口编号{}", resource.getCode());
                continue;
            }
            for (RbacRoleEntity rbacRoleEntity : rolesList) {
                RbacPermissionEntity byMenuCodeAndRoleCode = permissionHandler.getByMenuCodeAndRoleCode(resource.getCode(), rbacRoleEntity.getRoleCode());
                if (null != byMenuCodeAndRoleCode) {
                    byMenuCodeAndRoleCode
                            .setRolePid(rbacRoleEntity.getPid())
                            .setRoleName(rbacRoleEntity.getRoleName())
                            .setAppId(resource.getAppId());
                    updatePermissionsList.add(byMenuCodeAndRoleCode);
                } else {
                    RbacPermissionEntity rbacPermissionEntity = new RbacPermissionEntity()
                            .setRbacRoleId(rbacRoleEntity.getRbacRoleId())
                            .setRolePid(rbacRoleEntity.getPid())
                            .setResourceId(rbacUriEntity.getRbacUriId())
                            .setRoleCode(rbacRoleEntity.getRoleCode())
                            .setRoleName(rbacRoleEntity.getRoleName())
                            .setAuthority(rbacUriEntity.getCode())
                            .setAppId(rbacUriEntity.getAppId())
                            .setResourceType(ResourceEnum.INTERFACE.getCode());
                    newPermissionsList.add(rbacPermissionEntity);
                }
            }
        }
        if (!newPermissionsList.isEmpty()) {
            rbacPermissionRepository.saveBatch(newPermissionsList);
        }
        if (!updatePermissionsList.isEmpty()) {
            rbacPermissionRepository.updateBatchById(updatePermissionsList);
        }
    }

    private List<RbacRoleEntity> roleEntityHandler(String roleCode, String appid) {
        String[] roles = roleCode.split(",");
        List<RbacRoleEntity> rolesList = new ArrayList<>();
        // 查找存在的角色
        for (String role : roles) {
            RbacRoleEntity byRoleCode = roleHandler.getByRoleCode(role, appid);
            if (null == byRoleCode) {
                log.error("角色不存在，角色编码{}", role);
                continue;
            }
            rolesList.add(byRoleCode);
        }
        return rolesList;
    }

    @Override
    public void initAppRole(List<SaveRbacRoleRequest> roleItems) {
        List<RbacRoleEntity> newRoles = new ArrayList<>();
        List<RbacRoleEntity> updateRoles = new ArrayList<>();
        StringBuilder idPath = new StringBuilder();
        StringBuilder namePath = new StringBuilder();
        StringBuilder codePath = new StringBuilder();
        Map<Object, SaveRbacRoleRequest> uniqueMap = roleItems.stream()
                .collect(Collectors.toMap(
                        r -> r.getAppId() + ":" + r.getRoleCode(),
                        r -> r,
                        (existingValue, newValue) -> existingValue
                ));

        for (Map.Entry<Object, SaveRbacRoleRequest> entry : uniqueMap.entrySet()) {
            SaveRbacRoleRequest roleItem = entry.getValue();
            RbacRoleEntity existingRole = roleHandler.getByRoleCode(roleItem.getRoleCode(), roleItem.getAppId());
            if (existingRole == null) {
                RbacRoleEntity newRole = new RbacRoleEntity();
                long id = IdWorker.getId();
                newRole.setRoleCode(roleItem.getRoleCode());
                newRole.setRoleName(roleItem.getRoleName());
                newRole.setAppId(roleItem.getAppId());
                newRole.setRbacRoleId(id);
                boolean hasParent = false;
                if (null != roleItem.getParentRoleCode()) {
                    RbacRoleEntity parentRole = roleHandler.getByRoleCode(roleItem.getParentRoleCode(), roleItem.getAppId());
                    if (null != parentRole) {
                        newRole.setPid(parentRole.getRbacRoleId());
                        hasParent = true;
                        idPath.append(parentRole.getIdPath()).append("/").append(newRole.getRbacRoleId());
                        namePath.append(parentRole.getNamePath()).append("/").append(roleItem.getRoleName());
                        codePath.append(parentRole.getCodePath()).append("/").append(roleItem.getRoleCode());
                    }
                }
                if (!hasParent) {
                    newRole.setPid(0L);
                    idPath.append(newRole.getRbacRoleId());
                    namePath.append(newRole.getRoleName());
                    codePath.append(newRole.getRoleCode());
                }
                newRole.setIdPath(idPath.toString());
                newRole.setNamePath(namePath.toString());
                newRole.setCodePath(codePath.toString());

                newRoles.add(newRole);
            } else {
                RbacRoleEntity parentRole = roleHandler.getByRoleCode(roleItem.getParentRoleCode(), roleItem.getAppId());
                if (null == parentRole) {
                    log.info("未找到父类信息,父类编码{}", roleItem.getParentRoleCode());
                    idPath.append(existingRole.getRbacRoleId());
                    namePath.append(existingRole.getRoleName());
                    codePath.append(existingRole.getRoleCode());
                    existingRole.setIdPath(idPath.toString());
                    existingRole.setNamePath(namePath.toString());
                    existingRole.setCodePath(codePath.toString());
                } else {
                    existingRole.setPid(parentRole.getRbacRoleId());
                    idPath.append(parentRole.getIdPath()).append("/").append(existingRole.getRbacRoleId());
                    namePath.append(parentRole.getNamePath()).append("/").append(existingRole.getRoleName());
                    codePath.append(parentRole.getCodePath()).append("/").append(existingRole.getRoleCode());
                    existingRole.setIdPath(idPath.toString());
                    existingRole.setNamePath(namePath.toString());
                    existingRole.setCodePath(codePath.toString());
                }
                existingRole.setRoleName(roleItem.getRoleName());
                existingRole.setAppId(roleItem.getAppId());
                updateRoles.add(existingRole);
            }
            idPath.setLength(0);
            namePath.setLength(0);
            codePath.setLength(0);
        }

        if (!newRoles.isEmpty()) {
            rbacRoleRepository.saveBatch(newRoles);
        }

        if (!updateRoles.isEmpty()) {
            rbacRoleRepository.updateBatchById(updateRoles);
        }
    }

    @Async
    public void flushData() {
        rbacContext.initData();
    }


    public List<RbacMenusResponse> listAccountMenusTree() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String) {
            return new ArrayList<>();
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<Long> roleIds = authorities.stream().map(GrantedAuthority::getAuthority).map(Long::parseLong).toList();
        return listRoleMenusTree(roleIds);
    }
}