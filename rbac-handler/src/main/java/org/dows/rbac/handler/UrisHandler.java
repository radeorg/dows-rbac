package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.cache.caffeine.CaffeineTemplate;
import org.dows.framework.crud.mybatis.utils.BeanConvert;
import org.dows.rbac.api.*;
import org.dows.rbac.api.admin.request.SaveUrisRequest;
import org.dows.rbac.api.admin.response.RbacUriResponse;
import org.dows.rbac.api.constant.ResourceEnum;
import org.dows.rbac.api.constant.StateEnum;
import org.dows.rbac.api.constant.UserInfoEnum;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.entity.RbacUriEntity;
import org.dows.rbac.repository.RbacMenuRepository;
import org.dows.rbac.repository.RbacUriRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UrisHandler {
    private final RbacUriRepository rbacUriRepository;
    private final MenuHandler menuHandler;

    private final RbacCache rbacCache;


    public void save(List<SaveUrisRequest> saveUris) {
        List<RbacUriEntity> urisEntities = BeanConvert.beanConvert(saveUris, RbacUriEntity.class);
        rbacUriRepository.saveOrUpdateBatch(urisEntities);
    }

    public void saveOrUpdate(List<InitUriResources> initUriResources) {


    }

    public RbacUriEntity getByUrlCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        return rbacUriRepository.lambdaQuery()
                .eq(RbacUriEntity::getCode, code)
                .last("limit 1").oneOpt().orElse(null);
    }

    public List<RbacUriResponse> getByIds(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return null;
        }
        List<RbacUriEntity> list = rbacUriRepository.lambdaQuery()
                .in(RbacUriEntity::getRbacUriId, ids)
                .eq(RbacUriEntity::getState, StateEnum.AVAILABLE)
                .list();
        return BeanConvert.beanConvert(list,RbacUriResponse.class);
    }


    public void saveOrUpdateResource(List<InitResources> resources) {
        List<RbacUriEntity> newUris = new ArrayList<>();
        List<RbacUriEntity> updateUris = new ArrayList<>();
        List<RbacUriEntity> uriList = rbacUriRepository.list();
        List<String> codes = uriList.stream().map(RbacUriEntity::getCode).toList();
        for (InitResources uris : resources) {
            if (StringUtils.isEmpty(uris.getCode())) {
                log.info("UrisHandler saveOrUpdate Code为空{},name:{}", uris.getCode(), uris.getName());
                continue;
            }
            List<RbacMenuEntity> byMenuName = menuHandler.getByMenuName(uris.getMenuName(), StateEnum.AVAILABLE.getCode(), uris.getAppId());
            Long menuId = 0L;
            if (CollectionUtil.isNotEmpty(byMenuName)) {
                menuId = byMenuName.get(0).getRbacMenuId();
            }
            if (codes.contains(uris.getCode())) {
                Optional<RbacUriEntity> first = uriList.stream().filter(r -> r.getCode().equals(uris.getCode())).findFirst();
                if (first.isPresent()) {
                    RbacUriEntity rbacUriEntity = first.get();
                    rbacUriEntity.setName(uris.getName());
                    rbacUriEntity.setMethodName(uris.getMethod());
                    rbacUriEntity.setUrl(uris.getPath());
                    rbacUriEntity.setRbacMenuId(menuId);
                    updateUris.add(rbacUriEntity);
                }
            } else {
                RbacUriEntity rbacUriEntity = new RbacUriEntity();
                rbacUriEntity.setCode(uris.getCode());
                rbacUriEntity.setName(uris.getName());
                rbacUriEntity.setUrl(uris.getPath());
                rbacUriEntity.setMethodName(uris.getMethod());
                rbacUriEntity.setAppId(uris.getAppId());
                rbacUriEntity.setRbacMenuId(menuId);
                newUris.add(rbacUriEntity);
            }
        }
        if (!newUris.isEmpty()) {
            if (!updateUris.isEmpty()) {
                newUris.addAll(updateUris);
            }
            rbacUriRepository.saveOrUpdateBatch(newUris);
        }
    }

    public List<RbacUriEntity> getAll() {
        return rbacUriRepository.lambdaQuery()
                .eq(RbacUriEntity::getState,StateEnum.AVAILABLE).list();
    }

    public List<RbacUriEntity> getByUriName(String uriName, Integer state, String appId) {
        return rbacUriRepository.lambdaQuery()
                .eq(Objects.nonNull(uriName), RbacUriEntity::getName, uriName)
                .eq(Objects.nonNull(state), RbacUriEntity::getState, state)
                .eq(Objects.nonNull(appId), RbacUriEntity::getAppId, appId)
                .list();
    }

    public Boolean hasUriName(String uriName, String appId) {
        List<RbacUriEntity> byUriName = getByUriName(uriName, null, appId);
        return CollectionUtil.isNotEmpty(byUriName);
    }


    /*@Override
//    public void handle(Map<Long, List<RbacUriResources>> rbacResources) {
    public void handle(Object args) {
        Map<Long, List<RbacResources>> rbacResources = (Map<Long, List<RbacResources>>)args;
        Set<Long> roleIds = rbacResources.keySet();
        for (Long roleId : roleIds) {
            List<RbacResources> rbacUriResources = rbacResources.get(roleId);
            if(CollectionUtil.isNotEmpty(rbacUriResources)){
                List<Long> list = rbacUriResources.stream().map(RbacResources::getResourceId).toList();
                rbacCache.putCache(UserInfoEnum.ROLE_URI.getKey(), roleId, getByIds(list));
            }else{
                rbacCache.evictCache(UserInfoEnum.ROLE_URI.getKey(), roleId);
            }
        }
    }

    @Override
    public boolean supportResourceType(Integer resourceType) {
        return resourceType.equals(ResourceEnum.INTERFACE.getCode());
    }*/
}
