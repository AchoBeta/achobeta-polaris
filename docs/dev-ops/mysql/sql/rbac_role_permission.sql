SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
CREATE database if NOT EXISTS `achobeta_polaris` default character set utf8mb4;
use achobeta_polaris;


-- 角色表
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
    -- 主键id
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键id',
		-- 角色id
		`role_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '角色id',
    -- 角色名称
    `role_name` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '角色名称',
		-- 团队id
		`team_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '团队id',
    -- 创建时间
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '角色表';
create index idx_role_team_id on role(team_id);

-- 用户-角色-关联表
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
    -- 主键id
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键id',
    -- 用户id
    `user_id`  VARCHAR(36) NOT NULL DEFAULT '' COMMENT  '用户id',
    -- 角色id
    `role_id`  VARCHAR(36) NOT NULL DEFAULT '' COMMENT  '角色id',
    -- 创建时间
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '用户-角色';
create index idx_user_role_user_id_role_id on user_role(user_id, role_id);

DROP TABLE IF EXISTS `permission`;
CREATE TABLE IF NOT EXISTS `permission` (
    -- 主键id
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键id',
    -- 权限id
    `permission_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '权限id',
    -- 权限名称
    `permission_name` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '权限名称',
    -- 创建时间
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
)  ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '权限';
create unique index uk_permission_permission_id on permission(permission_id);

-- 角色-用户-关联表
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE IF NOT EXISTS `role_permission` (
    -- '主键id'
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键id',
    -- 角色id
    `role_id`  VARCHAR(36) NOT NULL DEFAULT '' COMMENT '角色id',
    -- 权限id
    `permission_id`  VARCHAR(36) NOT NULL DEFAULT '' COMMENT '权限id',
    -- 创建时间
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '角色-权限';
create index idx_user_role_role_id_permission_id on role_permission(role_id, permission_id);

-- 用户-权限-关联表
DROP TABLE IF EXISTS `user_permission`;
CREATE TABLE IF NOT EXISTS `user_permission` (
    -- '主键id'
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键id',
    -- 用户id
    `user_id`  VARCHAR(36) NOT NULL DEFAULT '' COMMENT '用户id',
    -- 权限id
    `permission_id`  VARCHAR(36) NOT NULL DEFAULT '' COMMENT '权限id',
    -- 创建时间
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '用户-权限';


insert into user_role (user_id,role_id) values ('1001','000');
insert into user_role (user_id,role_id) values ('1002','001');
insert into user_role (user_id,role_id) values ('1003','002');

insert into role (role_id,role_name) values ('000','超级管理员');
insert into role (role_id,role_name,team_id) values ('001','AchoBeta1.0管理员','0001');
insert into role (role_id,role_name,team_id) values ('002','AchoBeta2.0管理员','0002');

insert into permission (permission_id,permission_name) values ('2001','SUPER');
insert into permission (permission_id,permission_name) values ('2002','MEMBER');
insert into permission (permission_id,permission_name) values ('2003','STRUCTURE');
insert into permission (permission_id,permission_name) values ('2004','USER');
insert into permission (permission_id,permission_name) values ('2005','MEMBER_MODIFY');
insert into permission (permission_id,permission_name) values ('2006','MEMBER_ADD');
insert into permission (permission_id,permission_name) values ('2007','MEMBER_DELETE');
insert into permission (permission_id,permission_name) values ('2008','STRUCTURE_MODIFY');
insert into permission (permission_id,permission_name) values ('2009','TEAM_ADD');
insert into permission (permission_id,permission_name) values ('2010','TEAM_DELETE');
insert into permission (permission_id,permission_name) values ('2011','MEMBER_LIST');
insert into permission (permission_id,permission_name) values ('2012','MEMBER_DETAIL');
insert into permission (permission_id,permission_name) values ('2013','STRUCTURE_VIEW');
insert into permission (permission_id,permission_name) values ('2014','ROLE_LIST');
insert into permission (permission_id,permission_name) values ('2015','AUTH');

insert into role_permission (role_id,permission_id) values ('000','2001');
insert into role_permission (role_id,permission_id) values ('000','2002');
insert into role_permission (role_id,permission_id) values ('000','2003');
insert into role_permission (role_id,permission_id) values ('000','2004');
insert into role_permission (role_id,permission_id) values ('000','2005');
insert into role_permission (role_id,permission_id) values ('000','2006');
insert into role_permission (role_id,permission_id) values ('000','2007');
insert into role_permission (role_id,permission_id) values ('000','2008');
insert into role_permission (role_id,permission_id) values ('000','2009');
insert into role_permission (role_id,permission_id) values ('000','2010');
insert into role_permission (role_id,permission_id) values ('000','2011');
insert into role_permission (role_id,permission_id) values ('000','2012');
insert into role_permission (role_id,permission_id) values ('000','2013');
insert into role_permission (role_id,permission_id) values ('000','2014');
insert into role_permission (role_id,permission_id) values ('000','2015');

insert into role_permission (role_id,permission_id) values ('001','2002');
insert into role_permission (role_id,permission_id) values ('001','2003');
insert into role_permission (role_id,permission_id) values ('001','2005');
insert into role_permission (role_id,permission_id) values ('001','2006');
insert into role_permission (role_id,permission_id) values ('001','2007');
insert into role_permission (role_id,permission_id) values ('001','2008');
insert into role_permission (role_id,permission_id) values ('001','2011');
insert into role_permission (role_id,permission_id) values ('001','2012');
insert into role_permission (role_id,permission_id) values ('001','2013');
insert into role_permission (role_id,permission_id) values ('001','2014');

insert into role_permission (role_id,permission_id) values ('002','2002');
insert into role_permission (role_id,permission_id) values ('002','2003');
insert into role_permission (role_id,permission_id) values ('002','2005');
insert into role_permission (role_id,permission_id) values ('002','2006');
insert into role_permission (role_id,permission_id) values ('002','2007');
insert into role_permission (role_id,permission_id) values ('002','2008');
insert into role_permission (role_id,permission_id) values ('002','2011');
insert into role_permission (role_id,permission_id) values ('002','2012');
insert into role_permission (role_id,permission_id) values ('002','2013');
insert into role_permission (role_id,permission_id) values ('002','2014');