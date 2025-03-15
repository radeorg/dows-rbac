package org.dows.rbac.handler;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.cache.caffeine.CaffeineTemplate;
import org.dows.rbac.api.constant.StateEnum;
import org.dows.rbac.api.constant.UserInfoEnum;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.entity.RbacRoleEntity;
import org.dows.rbac.entity.RbacRuleEntity;
import org.dows.rbac.entity.RbacUriEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RbacCache {
    private final CaffeineTemplate caffeineTemplate;


//    public Object getCacheValue(String cacheName, Object key) {
//        return caffeineTemplate.getCacheValue(cacheName, key);
//    }

    public void putCache(String cacheName, Object key, Object value) {
        caffeineTemplate.putCache(cacheName, key, value);
    }

    public void evictCache(String cacheName, Object key) {
        caffeineTemplate.evictCache(cacheName, key);
    }

    public void clearCaches(String cacheName) {
        caffeineTemplate.clearCaches(cacheName);
    }

    public List<Long> getAllRoleIds(){
        Object cacheValue = caffeineTemplate.getCacheValue(UserInfoEnum.RBAC_ALL_ROLE_ID.getKey(), UserInfoEnum.RBAC_ALL_ROLE_ID.getKey());
        if(cacheValue == null) {
            return null;
        }
        return (List<Long>) cacheValue;
    }

//    public Map<String,Object> getUserInfo(String accountName){
//        return (Map)caffeineTemplate.getCacheValue(UserInfoEnum.USER_INFO.getKey(),accountName);
//    }


    public List<RbacUriEntity> getUriByRoleId(Long roleId){
        if(!checkRoleAvailable(roleId)){
            return null;
        }
        Object cacheValue = caffeineTemplate.getCacheValue(UserInfoEnum.ROLE_URI.getKey(), roleId);
        if(cacheValue == null) {
            return null;
        }
        return (List<RbacUriEntity>) cacheValue;
    }

    public List<RbacMenuEntity> getMenuByRoleId(Long roleId){
        if(!checkRoleAvailable(roleId)){
            return null;
        }
        Object cacheValue = caffeineTemplate.getCacheValue(UserInfoEnum.ROLE_MENU.getKey(), roleId);
        if(cacheValue == null) {
            return null;
        }
        return (List<RbacMenuEntity>) cacheValue;
    }

    public List<RbacRuleEntity> getAllRules(){
        Object cacheValue = caffeineTemplate.getCacheValue(UserInfoEnum.RBAC_ALL_ROLES.getKey(), UserInfoEnum.RBAC_ALL_ROLES.getKey());
        if(cacheValue == null) {
            return null;
        }
        return (List<RbacRuleEntity>) cacheValue;
    }

    public RbacRoleEntity getRoleByRoleId(Long roleId){
        Object cacheValue = caffeineTemplate.getCacheValue(UserInfoEnum.RBAC_ROLE.getKey(), roleId);
        if(cacheValue == null) {
            return null;
        }
        return (RbacRoleEntity) cacheValue;
    }

    public void putUriByRoleId(Long roleId, List<RbacUriEntity> rbacUriEntities){
        caffeineTemplate.putCache(UserInfoEnum.ROLE_URI.getKey(), roleId, rbacUriEntities);
    }

    public void putMenuByRoleId(Long roleId, List<RbacMenuEntity> rbacMenuEntities){
        caffeineTemplate.putCache(UserInfoEnum.ROLE_MENU.getKey(), roleId, rbacMenuEntities);
    }

    public void putRoleByRoleId(Long roleId, RbacRoleEntity role){
        caffeineTemplate.putCache(UserInfoEnum.RBAC_ROLE.getKey(), roleId, role);
    }

    public void putAllRoleIdByRoleId(List<Long> roleIds){
        caffeineTemplate.putCache(UserInfoEnum.RBAC_ALL_ROLE_ID.getKey(),UserInfoEnum.RBAC_ALL_ROLE_ID.getKey(),roleIds);
    }

    public void putAllRules(List<RbacRuleEntity> rules){
        caffeineTemplate.putCache(UserInfoEnum.RBAC_ALL_ROLES.getKey(),UserInfoEnum.RBAC_ALL_ROLES.getKey(),rules);
    }

    public Boolean checkRoleAvailable(Long roleId){
        RbacRoleEntity roleByRoleId = getRoleByRoleId(roleId);
        return roleByRoleId.getState().equals(Boolean.FALSE);
    }
}
