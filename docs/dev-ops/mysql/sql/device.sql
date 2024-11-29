SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
CREATE database if NOT EXISTS `achobeta_polaris` default character set utf8mb4;
use achobeta_polaris;

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
