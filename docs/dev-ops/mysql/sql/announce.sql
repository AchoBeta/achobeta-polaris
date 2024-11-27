SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
CREATE database if NOT EXISTS `achobeta_polaris` default character set utf8mb4;
use achobeta_polaris;

/*Table structure for table `announce` */

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
