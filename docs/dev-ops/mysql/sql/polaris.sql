SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
CREATE database if NOT EXISTS `achobeta_polaris` default character set utf8mb4;
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
	`entry_time` DATE COMMENT '加入时间',
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


insert into user (user_id,phone,user_name) values ('1001','13708885777','超级管理员');
insert into user (user_id,phone,user_name) values ('1002','13708885778','AchoBeta1.0管理员');
insert into user (user_id,phone,user_name) values ('1003','13708885779','AchoBeta2.0管理员');
insert into user (user_id,phone,user_name) values ('1004','13708885770','牛一');
insert into user (user_id,phone,user_name) values ('1005','13708885721','妞儿');
insert into user (user_id,phone,user_name) values ('1006','13708885722','牛三');
insert into user (user_id,phone,user_name) values ('1007','13708885723','牛四');
insert into user (user_id,phone,user_name) values ('1008','13708885724','牛五');
insert into user (user_id,phone,user_name) values ('1009','13708885726','牛八');
insert into user (user_id,phone,user_name) values ('1010','13708885727','王大春');
insert into user (user_id,phone,user_name) values ('1011','13708885711','王尼玛');
insert into user (user_id,phone,user_name) values ('1012','13708885712','李艳');
insert into user (user_id,phone,user_name) values ('1013','13708885713','张古泉');
insert into user (user_id,phone,user_name) values ('1014','13708885714','和风');
insert into user (user_id,phone,user_name) values ('1015','13708885715','唐武');
insert into user (user_id,phone,user_name) values ('1016','13708885716','小二');
insert into user (user_id,phone,user_name) values ('1017','13708885717','小李');
insert into user (user_id,phone,user_name) values ('1018','13708885718','小王');
insert into user (user_id,phone,user_name) values ('1019','13708885719','八哥');
insert into user (user_id,phone,user_name) values ('1020','13708885720','卢本伟');

DROP TABLE IF EXISTS `announce`;

CREATE TABLE `announce` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `announce_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告ID',
  `title` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `body` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告内容',
  `type` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类型(0为全体，其他数字为团队id)',
  `target_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类型(0为全体，其他数字为用户id)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0-未删除;1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `announce_id` (`announce_id`),
  UNIQUE KEY `uk_announce_id` (`announce_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公告表';

/*Data for the table `announce` */

insert  into `announce`(`id`,`announce_id`,`title`,`body`,`type`,`target_id`,`create_time`,`update_time`,`is_deleted`) values

(1,'1','java从入门到入土','快跑！！！','0','0','2024-11-04 21:44:51','2024-11-04 21:44:51',0),

(2,'2','Linux从开发到开炮','快跑！！！','0','0','2024-11-04 21:44:51','2024-11-04 21:44:51',0),

(3,'3','爬虫从入门到入狱','快跑！！！','0','0','2024-11-04 21:44:51','2024-11-04 21:44:51',0);


/*Table structure for table `device` */

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `device_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备ID',
  `device_name` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备名',
  `user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
  `is_cancel` tinyint(3) unsigned DEFAULT '0' COMMENT '自动登录,0否，1是',
  `ip` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ip',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0-未删除;1-已删除',
  `finger_printing` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备指纹',
  PRIMARY KEY (`id`),
  KEY `idx_device_id` (`device_id`),
  KEY `idx_finger_printing` (`finger_printing`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备表';

/*Data for the table `device` */

insert  into `device`(`id`,`device_id`,`device_name`,`user_id`,`is_cancel`,`ip`,`create_time`,`update_time`,`is_deleted`,`finger_printing`) values

(5,'1','设备1','1',0,'196.1013','2024-11-09 14:30:01','2024-11-27 14:24:11',0,'1'),

(6,'2','设备2','1',0,'196.1013','2024-11-09 14:30:01','2024-11-27 14:24:12',0,'2'),

(7,'3','设备3','1',0,'196.1013','2024-11-09 14:30:01','2024-11-27 14:24:13',0,'3'),

(8,'4','设备4','1',0,'196.1013','2024-11-09 14:30:01','2024-11-27 14:24:14',0,'4'),

(9,'5','123','1',1,'196.128.1.1','2024-11-25 19:04:08','2024-11-27 14:24:22',0,'5');

DROP TABLE IF EXISTS `like`;

CREATE TABLE `like` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `to_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '被点赞用户id',
  `user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '点赞用户id',
  `is_liked` tinyint(3) unsigned DEFAULT '1' COMMENT '是否点赞，0未点，1已点(已点)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0-未删除;1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_to_id` (`user_id`,`to_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点赞表';

/*Data for the table `like` */

insert  into `like`(`id`,`to_id`,`user_id`,`is_liked`,`create_time`,`update_time`,`is_deleted`) values

(3,'2','1',0,'2024-11-19 16:48:53','2024-11-26 23:45:43',0);

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



DROP TABLE IF EXISTS `user_announce`;

CREATE TABLE `user_announce` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
  `announce_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告ID',
  `is_read` tinyint(3) unsigned DEFAULT '0' COMMENT '是否已读，0未读，1已读',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0-未删除;1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_create_time_announce_id_is_read` (`user_id`,`create_time`,`announce_id`,`is_read`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公告和用户的联系';

/*Data for the table `user_announce` */

insert  into `user_announce`(`id`,`user_id`,`announce_id`,`is_read`,`create_time`,`update_time`,`is_deleted`) values

(1,'1','1',1,'2024-11-04 22:22:16','2024-11-14 13:58:03',0),

(2,'1','2',1,'2024-11-04 22:22:16','2024-11-17 13:05:40',0),

(3,'1','3',1,'2024-11-04 22:22:16','2024-11-17 13:05:40',0),

(4,'2','1',1,'2024-11-04 22:22:16','2024-11-17 01:20:59',0),

(5,'2','2',0,'2024-11-04 22:22:16','2024-11-04 22:22:16',0),

(6,'2','3',0,'2024-11-04 22:22:16','2024-11-04 22:22:16',0),

(7,'3','1',0,'2024-11-04 22:22:16','2024-11-04 22:22:16',0),

(8,'3','2',0,'2024-11-04 22:22:16','2024-11-04 22:22:16',0),

(9,'3','3',0,'2024-11-04 22:22:16','2024-11-04 22:22:16',0);
