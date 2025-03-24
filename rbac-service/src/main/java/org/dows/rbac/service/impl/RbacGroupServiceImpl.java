package org.dows.rbac.service.impl;


import org.dows.rade.crud.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.dows.rbac.service.RbacGroupService;
import org.dows.rbac.entity.RbacGroupEntity;
import org.dows.rbac.mapper.RbacGroupMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 资源组表 服务层实现。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Service
public class RbacGroupServiceImpl extends BaseServiceImpl<RbacGroupMapper, RbacGroupEntity> implements RbacGroupService {

}