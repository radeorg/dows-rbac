package org.dows.rbac.handler;

import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.rade.init.ResourceInitializer;
import org.dows.rade.model.UriSignature;
import org.dows.rbac.entity.RbacUriEntity;
import org.dows.rbac.service.RbacUriService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class RbacUriInitializer implements ResourceInitializer<UriSignature> {
    private final RbacUriService rbacUriService;
    @Value("${spring.application.appId}")
    private String appId;

    public void init(List<UriSignature> uriSignatures) {
        if(uriSignatures == null || uriSignatures.isEmpty()){
            return;
        }
        //List<RbacUriEntity> rbacUriEntities = rbacUriService.list();
        List<RbacUriEntity> rbacUriEntities = new ArrayList<>();
        uriSignatures.forEach(uriSignature -> {
            // todo 保存数据库
            RbacUriEntity rbacUriEntity = RbacUriEntity.builder()
                    .appId(uriSignature.getAppId())
                    .uri(uriSignature.getUri())
                    .httpMethod(uriSignature.getHttpMethod())
                    .javaMethod(uriSignature.getJavaMethod())
                    .summary(uriSignature.getSummary())
                    .customed(0)
                    .description(uriSignature.getDescription())
                    .inputs(JSONUtil.toJsonStr(uriSignature.getInputs()))
                    .output(JSONUtil.toJsonStr(uriSignature.getOutput()))
                    .build();
            rbacUriEntities.add(rbacUriEntity);

        });
        // 先删除
        rbacUriService.remove(QueryWrapper.create()
                .eq(RbacUriEntity::getAppId, appId)
                .eq(RbacUriEntity::getCustomed, 0));
        rbacUriService.saveBatch(rbacUriEntities);
    }
}
