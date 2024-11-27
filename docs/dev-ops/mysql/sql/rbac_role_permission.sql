SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
drop database if exists achobeta_polaris;
create database achobeta_polaris character set utf8mb4 collate utf8mb4_bin;
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
