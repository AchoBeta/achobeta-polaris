SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
CREATE database if NOT EXISTS `achobeta_polaris` default character set utf8mb4;
use achobeta_polaris;


DROP TABLE IF EXISTS `position`;
CREATE TABLE IF NOT EXISTS `position` (
    -- 主键
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    -- 职位/分组id
    `position_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '职位/分组id',
    -- 职位/分组名称
    `position_name` VARCHAR(25) NOT NULL DEFAULT '' COMMENT '职位/分组名称',
    -- 团队ID
    `team_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '团队ID',
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
alter table position add unique index position_id_subordinate(position_id,subordinate);
create index idx_position_subordinate on position (subordinate);

DROP TABLE IF EXISTS `user_position`;
CREATE TABLE IF NOT EXISTS `user_position` (
    -- 主键
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    -- 用户id
    `user_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '用户id',
    -- 职位/分组id
    `position_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '职位/分组id',
	-- 团队id
	`team_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '团队id',
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
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '用户分组/职位关联表';
create index idx_user_id_position_id_team_id on user_position (user_id,position_id,team_id);


insert into position (position_id,position_name,subordinate,team_id) values ('0000','未选择团队','','0000');
insert into position (position_id,position_name,subordinate,team_id) values ('0001','AchoBeta1.0','','0001');
insert into position (position_id,position_name,subordinate,team_id) values ('0002','AchoBeta2.0','','0002');

insert into user_position (user_id,position_id,team_id) values ('1001','0000','0000');
insert into user_position (user_id,position_id,team_id) values ('1001','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1001','0002','0002');

insert into user_position (user_id,position_id,team_id) values ('1002','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1003','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1004','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1005','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1006','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1007','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1008','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1009','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1010','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1011','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1012','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1013','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1014','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1015','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1016','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1017','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1018','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1019','0001','0001');
insert into user_position (user_id,position_id,team_id) values ('1020','0001','0001');

insert into user_position (user_id,position_id,team_id) values ('1002','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1003','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1004','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1005','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1006','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1007','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1008','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1014','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1015','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1016','0002','0002');
insert into user_position (user_id,position_id,team_id) values ('1017','0002','0002');