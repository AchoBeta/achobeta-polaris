SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
drop database if exists achobeta_polaris;
create database achobeta_polaris character set utf8mb4 collate utf8mb4_bin;
use achobeta_polaris;

-- 成员表
DROP TABLE IF EXISTS `member`;
CREATE TABLE IF NOT EXISTS `member` (
    -- 主键
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    -- 团队成员id
    `user_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '团队成员id',
    -- 团队名称
    `team_name` VARCHAR(36) NOT NULL COMMENT '团队名称',
    -- 创建时间
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '团队成员表';
-- 团队成员id索引
CREATE INDEX idx_member_user_id ON `member` ( user_id );


