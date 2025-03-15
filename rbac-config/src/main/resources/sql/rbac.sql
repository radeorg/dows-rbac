-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `shdy_rbac`;
USE `shdy_rbac`;

drop table if exists `rbac_role`;
CREATE TABLE IF NOT EXISTS `rbac_role`
(
    `rbac_role_id` bigint(19) NOT NULL COMMENT '角色id',
    `pid`          bigint(19)  DEFAULT NULL COMMENT '角色父ID(角色组|继承)',
    `role_name`    varchar(64) DEFAULT NULL COMMENT '角色名称',
    `pyCode`       varchar(64) DEFAULT NULL COMMENT '角色名称首字母拼音码',
    `role_code`    varchar(64) DEFAULT NULL COMMENT '角色编码',
    `id_path`      varchar(64) DEFAULT NULL COMMENT 'id路径',
    `name_path`    varchar(64) DEFAULT NULL COMMENT '名称路径',
    `code_path`    varchar(64) DEFAULT NULL COMMENT '菜单路径URI[menuPath]',
    `icon`         varchar(64) DEFAULT NULL COMMENT '角色图标',
    `app_id`       varchar(64) DEFAULT NULL COMMENT '应用id',
    `descr`        varchar(64) DEFAULT NULL COMMENT '描述',
    `role_level`   integer(2)  DEFAULT NULL COMMENT '角色级别',
    `inherit`      tinyint(4)  DEFAULT NULL COMMENT '当前角色是否继承父角色对应的权限',
    `state`        tinyint(4)  DEFAULT NULL COMMENT '状态',
    `deleted`      tinyint(4)  DEFAULT NULL COMMENT '逻辑删除  0未删除  1 删除',
    `dt`           datetime    DEFAULT NULL COMMENT '创建，更新，删除时间',
    PRIMARY KEY (`rbac_role_id`)
) ENGINE = InnoDB COMMENT ='角色集';

drop table if exists `rbac_permission`;
CREATE TABLE IF NOT EXISTS `rbac_permission`
(
    `rbac_permission_id` bigint(19) NOT NULL COMMENT '权限ID',
    `rbac_role_id`       bigint(19)  DEFAULT NULL COMMENT '角色id',
    `role_pid`           bigint(19)  DEFAULT NULL COMMENT '父角色ID(继承时该字段有值)',
    `resource_id`        bigint(19)  DEFAULT NULL COMMENT '资源ID',
    `role_code`          varchar(64) DEFAULT NULL COMMENT '角色CODE',
    `role_name`          varchar(64) DEFAULT NULL COMMENT '角色名字',
    `authority`          varchar(64) DEFAULT NULL COMMENT '资源权限码',
    `app_id`             varchar(64) DEFAULT NULL COMMENT '应用id 从角色冗余',
    `descr`              varchar(64) DEFAULT NULL COMMENT '描述',
    `resource_type`      integer(2)  DEFAULT NULL COMMENT '资源类型[0:接口，1:菜单]',
    `state`              integer(2)  DEFAULT NULL COMMENT '状态',
    `ver`                integer(2)  DEFAULT NULL COMMENT '乐观锁, 默认: 0',
    `deleted`            tinyint(4)  DEFAULT NULL COMMENT '逻辑删除  0未删除  1 删除',
    `dt`                 datetime    DEFAULT NULL COMMENT '创建，更新，删除时间',
    PRIMARY KEY (`rbac_permission_id`)
) ENGINE = InnoDB COMMENT ='角色权限集';

drop table if exists `rbac_module`;
CREATE TABLE IF NOT EXISTS `rbac_module`
(
    `rbac_module_id` bigint(19) NOT NULL COMMENT '权限模块ID',
    `module_name`    varchar(64) DEFAULT NULL COMMENT '模块|组名称',
    `module_code`    varchar(64) DEFAULT NULL COMMENT '模块|组CODE',
    `app_id`         varchar(64) DEFAULT NULL COMMENT '应用id',
    `state`          integer(2)  DEFAULT NULL COMMENT '状态',
    `ver`            integer(2)  DEFAULT NULL COMMENT '乐观锁, 默认: 0',
    `deleted`        tinyint(4)  DEFAULT NULL COMMENT '逻辑删除  0未删除  1 删除',
    `dt`             datetime    DEFAULT NULL COMMENT '创建，更新，删除时间',
    PRIMARY KEY (`rbac_module_id`)
) ENGINE = InnoDB COMMENT ='权限模块';

drop table if exists `rbac_resources`;
CREATE TABLE IF NOT EXISTS `rbac_resources`
(
    `rbac_resourcesid` bigint(19) NOT NULL COMMENT '模块资源ID',
    `rbac_module_id`   bigint(19)  DEFAULT NULL COMMENT '权限模块ID',
    `resource_id`      bigint(19)  DEFAULT NULL COMMENT '资源ID',
    `name`             varchar(64) DEFAULT NULL COMMENT '资源名称',
    `code`             varchar(64) DEFAULT NULL COMMENT '资源CODE',
    `app_id`           varchar(64) DEFAULT NULL COMMENT '应用id',
    `resource_type`    integer(2)  DEFAULT NULL COMMENT '资源类型[0:接口，1:菜单]',
    `state`            integer(2)  DEFAULT NULL COMMENT '状态',
    `ver`              integer(2)  DEFAULT NULL COMMENT '乐观锁, 默认: 0',
    `deleted`          tinyint(4)  DEFAULT NULL COMMENT '逻辑删除  0未删除  1 删除',
    `dt`               datetime    DEFAULT NULL COMMENT '创建，更新，删除时间',
    PRIMARY KEY (`rbac_resourcesid`)
) ENGINE = InnoDB COMMENT ='模块资源';

drop table if exists `rbac_menu`;
CREATE TABLE IF NOT EXISTS `rbac_menu`
(
    `rbac_menu_id` bigint(19) NOT NULL COMMENT '菜单ID',
    `pid`          bigint(19)  DEFAULT NULL COMMENT '菜单父ID',
    `name`         varchar(64) DEFAULT NULL COMMENT '菜单名称',
    `code`         varchar(64) DEFAULT NULL COMMENT '菜单CODE',
    `id_path`      varchar(64) DEFAULT NULL COMMENT 'id路径',
    `name_path`    varchar(64) DEFAULT NULL COMMENT '名称路径',
    `code_path`    varchar(64) DEFAULT NULL COMMENT '菜单路径URI[menuPath]',
    `json_config`  varchar(64) DEFAULT NULL COMMENT 'json数据集',
    `app_id`       varchar(64) DEFAULT NULL COMMENT '应用id',
    `descr`        varchar(64) DEFAULT NULL COMMENT '描述',
    `sorted`       integer(2)  DEFAULT NULL COMMENT '排序',
    `ver`          integer(2)  DEFAULT NULL COMMENT '乐观锁, 默认: 0',
    `visible`      tinyint(4)  DEFAULT NULL COMMENT '是否隐藏',
    `isframe`      tinyint(4)  DEFAULT NULL COMMENT '是否框架',
    `state`        tinyint(4)  DEFAULT NULL COMMENT '状态',
    `deleted`      tinyint(4)  DEFAULT NULL COMMENT '逻辑删除  0未删除  1 删除',
    `dt`           datetime    DEFAULT NULL COMMENT '创建，更新，删除时间',
    PRIMARY KEY (`rbac_menu_id`)
) ENGINE = InnoDB COMMENT ='菜单集';

drop table if exists `rbac_uri`;
CREATE TABLE IF NOT EXISTS `rbac_uri`
(
    `rbac_uri_id`  bigint(19) NOT NULL COMMENT '资源id',
    `rbac_menu_id` bigint(19)  DEFAULT NULL COMMENT '菜单ID',
    `name`         varchar(64) DEFAULT NULL COMMENT '资源名称',
    `code`         varchar(64) DEFAULT NULL COMMENT '资源CODE',
    `label`        varchar(64) DEFAULT NULL COMMENT '页面功能标签[按钮、链接]',
    `url`          varchar(64) DEFAULT NULL COMMENT '资源链接',
    `json_config`  varchar(64) DEFAULT NULL COMMENT 'JSON数据集',
    `app_id`       varchar(64) DEFAULT NULL COMMENT '应用id',
    `descr`        varchar(64) DEFAULT NULL COMMENT '描述',
    `ver`          integer(2)  DEFAULT NULL COMMENT '乐观锁, 默认: 0',
    `shared`       tinyint(4)  DEFAULT NULL COMMENT '是否共享[0:不共享,1:共享]',
    `state`        tinyint(4)  DEFAULT NULL COMMENT '状态',
    `deleted`      tinyint(4)  DEFAULT NULL COMMENT '逻辑删除  0未删除  1 删除',
    `dt`           datetime    DEFAULT NULL COMMENT '创建，更新，删除时间',
    PRIMARY KEY (`rbac_uri_id`)
) ENGINE = InnoDB COMMENT ='接口集';