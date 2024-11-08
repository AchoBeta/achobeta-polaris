SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
drop database if exists achobeta_polaris;
create database achobeta_polaris character set utf8mb4 collate utf8mb4_bin;
use achobeta_polaris;

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
	-- 主键
		`id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '主键',
		-- 用户id
		`user_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '用户id',
		-- 用户名
		`user_name` VARCHAR ( 16 ) NOT NULL DEFAULT '' COMMENT '用户姓名',
		-- 手机号
		`phone` CHAR ( 11 ) NOT NULL DEFAULT '' COMMENT '手机号码',
		-- 性别 为0-未选择；1-男；2-女
		`gender` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '性别，0-未选择；1-男；2-女',
		-- 身份证 为了X用字符串
		`id_card` VARCHAR ( 18 ) NOT NULL DEFAULT '' COMMENT '身份证',
		-- 邮箱
		`email` VARCHAR ( 64 ) NOT NULL DEFAULT '' COMMENT '邮箱',
		-- 年级
		`grade` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '年级',
		-- 专业
		`major` VARCHAR ( 64 ) NOT NULL DEFAULT '' COMMENT '专业',
		-- 学号
		`student_id` VARCHAR ( 13 ) NOT NULL DEFAULT '' COMMENT '学号',
		-- 实习 就职 经历
		`experience` TEXT COMMENT '实习/就职经历',
		-- 当前状况描述
		`current_status` TEXT COMMENT '现状',
		-- 加入时间
		`entry_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
		-- 点赞数量
		`like_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数量',
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
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '用户表';
-- 用户id唯一索引
CREATE UNIQUE INDEX uk_user_user_id ON `user`(user_id);
-- 手机号唯一索引
CREATE UNIQUE INDEX uk_user_phone ON `user`(phone);
