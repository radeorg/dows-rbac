package org.dows;

import org.dows.rbac.RbacApplication;
import org.dows.rbac.repository.RbacPermissionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description: </br>
 * @author: lait.zhang@gmail.com
 * @date: 4/1/2024 10:16 AM
 * @history: </br>
 * <author>      <time>      <version>    <desc>
 * 修改人姓名      修改时间        版本号       描述
 */
@SpringBootTest(classes = RbacApplication.class)
public class TestRbac {


    @Autowired
    private RbacPermissionRepository rbacPermissionRepository;

    @Test
    public void testMssqlQuery() {
//        ScanPointEntity scanPointEntity = scanPointRepository.getById("36CA7D71-9CFD-43E7-9A5E-001993D26366");
        rbacPermissionRepository.list();
//        System.out.println(scanPointEntity);

    }
}

