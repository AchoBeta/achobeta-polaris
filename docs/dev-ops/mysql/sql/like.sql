SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
CREATE database if NOT EXISTS `achobeta_polaris` default character set utf8mb4;
use achobeta_polaris;

/*Table structure for table `like` */

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
