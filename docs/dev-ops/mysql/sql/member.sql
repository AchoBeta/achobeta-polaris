SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
CREATE database if NOT EXISTS `achobeta_polaris` default character set utf8mb4;
use achobeta_polaris;

-- 成员表
DROP TABLE IF EXISTS `member`;
CREATE TABLE IF NOT EXISTS `member` (
    -- 主键
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    -- 团队成员id
    `user_id` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '团队成员id',
    -- 团队ID
    `team_id` VARCHAR(36) NOT NULL COMMENT '团队ID',
    -- 创建时间
    `create_by` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '创建者',
    -- 更新者
    `update_by` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '更新者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    -- 更新时间
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 软删除标识 0-未删除 1-已删除
    `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-未删除;1-已删除'
) ENGINE = InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '团队成员表';
-- 团队成员id索引
CREATE INDEX idx_member_user_id ON `member` ( user_id );


insert into member (user_id,team_id) values ('1001','0000');
insert into member (user_id,team_id) values ('1001','0001');
insert into member (user_id,team_id) values ('1001','0002');

insert into member (user_id,team_id) values ('1002','0001');
insert into member (user_id,team_id) values ('1003','0001');
insert into member (user_id,team_id) values ('1004','0001');
insert into member (user_id,team_id) values ('1005','0001');
insert into member (user_id,team_id) values ('1006','0001');
insert into member (user_id,team_id) values ('1007','0001');
insert into member (user_id,team_id) values ('1008','0001');
insert into member (user_id,team_id) values ('1009','0001');
insert into member (user_id,team_id) values ('1010','0001');
insert into member (user_id,team_id) values ('1011','0001');
insert into member (user_id,team_id) values ('1012','0001');
insert into member (user_id,team_id) values ('1013','0001');
insert into member (user_id,team_id) values ('1014','0001');
insert into member (user_id,team_id) values ('1015','0001');
insert into member (user_id,team_id) values ('1016','0001');
insert into member (user_id,team_id) values ('1017','0001');
insert into member (user_id,team_id) values ('1018','0001');
insert into member (user_id,team_id) values ('1019','0001');
insert into member (user_id,team_id) values ('1020','0001');

insert into member (user_id,team_id) values ('1002','0002');
insert into member (user_id,team_id) values ('1003','0002');
insert into member (user_id,team_id) values ('1004','0002');
insert into member (user_id,team_id) values ('1005','0002');
insert into member (user_id,team_id) values ('1006','0002');
insert into member (user_id,team_id) values ('1007','0002');
insert into member (user_id,team_id) values ('1008','0002');
insert into member (user_id,team_id) values ('1014','0002');
insert into member (user_id,team_id) values ('1015','0002');
insert into member (user_id,team_id) values ('1016','0002');
insert into member (user_id,team_id) values ('1017','0002');