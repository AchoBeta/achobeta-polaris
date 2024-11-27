SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
drop database if exists achobeta_polaris;
create database achobeta_polaris character set utf8mb4 collate utf8mb4_bin;
use achobeta_polaris;

/*Table structure for table `user_announce` */

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
