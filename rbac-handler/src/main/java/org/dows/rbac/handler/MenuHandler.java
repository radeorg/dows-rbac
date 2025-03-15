package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.cache.caffeine.CaffeineTemplate;
import org.dows.rbac.api.InitResources;
import org.dows.rbac.api.RbacHandler;
import org.dows.rbac.api.RbacMenuResources;
import org.dows.rbac.api.RbacResources;
import org.dows.rbac.api.constant.ResourceEnum;
import org.dows.rbac.api.constant.StateEnum;
import org.dows.rbac.api.constant.UserInfoEnum;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.repository.RbacMenuRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuHandler implements RbacHandler {

    private final RbacMenuRepository rbacMenusRepository;

    private final RbacCache rbacCache;

    public void saveOrUpdateResource(List<InitResources> resources) {
        List<RbacMenuEntity> newMenus = new ArrayList<>();
        List<RbacMenuEntity> updateMenus = new ArrayList<>();
        List<RbacMenuEntity> menuList = rbacMenusRepository.list();

        for (InitResources resource : resources) {
            if (StrUtil.isBlank(resource.getCode())) {
                log.info("UrisHandler saveOrUpdate Code为空{}, name:{}", resource.getCode(), resource.getName());
                continue;
            }

            Optional<RbacMenuEntity> existingMenu = menuList.stream()
                    .filter(menu -> menu.getCode().equals(resource.getCode()))
                    .findFirst();

            if (existingMenu.isPresent()) {
                RbacMenuEntity menuToUpdate = existingMenu.get();
                menuToUpdate.setName(resource.getName());
                menuToUpdate.setPath(resource.getPath());
                menuToUpdate.setAppId(resource.getAppId());
                updateMenus.add(menuToUpdate);
            } else {
                RbacMenuEntity rbacMenuEntity = new RbacMenuEntity();
                long id = IdWorker.getId();
                rbacMenuEntity.setRbacMenuId(id);
                rbacMenuEntity.setPid(resource.getPid());
                StringBuilder idPath = new StringBuilder();
                StringBuilder namePath = new StringBuilder();
                StringBuilder codePath = new StringBuilder();
                if (resource.getPid() == 0 || null == resource.getPid()) {
                    idPath.append(rbacMenuEntity.getRbacMenuId());
                    namePath.append(rbacMenuEntity.getName());
                    codePath.append(rbacMenuEntity.getCode());
                } else {
                    if (Objects.isNull(resource.getPid())) {
                        throw new IllegalArgumentException("父类id为空");
                    }
                    RbacMenuEntity preRbacMenuEntity = rbacMenusRepository.getById(rbacMenuEntity.getPid());
                    if (Objects.isNull(preRbacMenuEntity)) {
                        throw new IllegalArgumentException("未找到对应父类信息");
                    }
                    idPath.append(preRbacMenuEntity.getIdPath()).append("/").append(rbacMenuEntity.getRbacMenuId());
                    namePath.append(preRbacMenuEntity.getNamePath()).append("/").append(resource.getName());
                    codePath.append(preRbacMenuEntity.getCodePath()).append("/").append(resource.getCode());
                }
                rbacMenuEntity.setIdPath(idPath.toString());
                rbacMenuEntity.setNamePath(namePath.toString());
                rbacMenuEntity.setCodePath(codePath.toString());
                rbacMenuEntity.setCode(resource.getCode());
                rbacMenuEntity.setName(resource.getName());
                rbacMenuEntity.setPath(resource.getPath());
                rbacMenuEntity.setDescr(resource.getMethod());
                rbacMenuEntity.setAppId(resource.getAppId());
                newMenus.add(rbacMenuEntity);
            }
        }

        newMenus.addAll(updateMenus);
        if (!newMenus.isEmpty()) {
            rbacMenusRepository.saveOrUpdateBatch(newMenus);
        }
    }

    public List<RbacMenuEntity> getByIds(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return null;
        }
        return rbacMenusRepository.lambdaQuery()
                .in(RbacMenuEntity::getRbacMenuId,ids)
                .eq(RbacMenuEntity::getState, StateEnum.AVAILABLE)
                .list();
    }


    public RbacMenuEntity getByMenuCode(String menuCode) {
        if (StrUtil.isBlank(menuCode)) {
            return null;
        }
        List<RbacMenuEntity> list = rbacMenusRepository.lambdaQuery()
                .eq(RbacMenuEntity::getCode, menuCode)
                .eq(RbacMenuEntity::getState, StateEnum.AVAILABLE).list();

        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public List<RbacMenuEntity> getByMenuName(String menuName, Integer state, String appId) {
        return rbacMenusRepository.lambdaQuery()
                .eq(Objects.nonNull(menuName), RbacMenuEntity::getName, menuName)
                .eq(Objects.nonNull(state), RbacMenuEntity::getState, state)
                .eq(Objects.nonNull(appId), RbacMenuEntity::getAppId, appId)
                .list();
    }

    public Boolean hasMenuName(String menuName, String appId) {
        List<RbacMenuEntity> byMenuName = getByMenuName(menuName, null, appId);
        return CollectionUtil.isNotEmpty(byMenuName);
    }


    @Override
//    public void handle(Map<Long, List<RbacMenuResources>> rbacResources) {
    public void handle(Object args) {
        Map<Long, List<RbacResources>> rbacResources = (Map<Long, List<RbacResources>>)args;
        Set<Long> roleIds = rbacResources.keySet();
        for (Long roleId : roleIds) {
            List<RbacResources> rbacMenuResources = rbacResources.get(roleId);
            if(CollectionUtil.isNotEmpty(rbacMenuResources)){
                List<Long> list = rbacMenuResources.stream().map(RbacResources::getResourceId).toList();
                rbacCache.putCache(UserInfoEnum.ROLE_MENU.getKey(), roleId, getByIds(list));
            }else{
                rbacCache.evictCache(UserInfoEnum.ROLE_MENU.getKey(), roleId);
            }
        }
    }

    @Override
    public boolean supportResourceType(Integer resourceType) {
        return resourceType.equals(ResourceEnum.MENU.getCode());
    }
}
