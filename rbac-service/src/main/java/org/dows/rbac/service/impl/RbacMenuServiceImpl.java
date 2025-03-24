package org.dows.rbac.service.impl;


import org.dows.rade.crud.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.dows.rbac.service.RbacMenuService;
import org.dows.rbac.entity.RbacMenuEntity;
import org.dows.rbac.mapper.RbacMenuMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

/**
 * 菜单集表 服务层实现。
 *
 * @author lait.zhang@gmail.com
 * @since 1.0
 */
@Service
public class RbacMenuServiceImpl extends BaseServiceImpl<RbacMenuMapper, RbacMenuEntity> implements RbacMenuService {

}