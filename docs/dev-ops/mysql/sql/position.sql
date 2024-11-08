SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
drop database if exists achobeta_polaris;
create database achobeta_polaris character set utf8mb4 collate utf8mb4_bin;
use achobeta_polaris;

DROP TABLE IF EXISTS `position`;
CREATE TABLE IF NOT EXISTS `position` (
    -- 主键
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    -- 职位/分组id
    `position_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '职位/分组id',
    -- 职位/分组名称
    `position_name` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '职位/分组名称',
		-- 团队名称
		`team_name` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '团队名称',
    -- 团队架构中的等级 0-根节点/团队 1 2 3 4
    `level` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '团队架构中的等级 0-根节点/团队 1 2 3 4',
    -- 子节点/下级id
    `subordinate` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '子节点/下级id',
    -- 创建者
    `create_by` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '创建者',
    -- 更新者
    `update_by` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '更新者',
    -- 创建时间
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '职位/分组';
-- 职位/分组id唯一索引
CREATE INDEX uk_position_position_id ON `position` ( position_id );

DROP TABLE IF EXISTS `user_position`;
CREATE TABLE IF NOT EXISTS `user_position` (
    -- 主键
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
		-- 用户id
		`user_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '用户id',
		-- 职位/分组id
		`position_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '职位/分组id',
		-- 创建时间
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '用户分组/职位关联表';