/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.36 : Database - blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`blog` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `blog`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `likes_number` int NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `views_number` int NOT NULL DEFAULT '0' COMMENT '浏览量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态 （草稿、已发布）',
  `create_user` int NOT NULL COMMENT '用户id',
  `category_id` int NOT NULL COMMENT '分类id',
  `state` int NOT NULL DEFAULT '1' COMMENT '状态 1为正常 0为封禁',
  `check` int NOT NULL DEFAULT '0' COMMENT '审核 1为通过 0为未通过',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `article_create_user` (`create_user`) USING BTREE,
  KEY `article_category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `article` */

insert  into `article`(`id`,`title`,`content`,`likes_number`,`views_number`,`create_time`,`update_time`,`status`,`create_user`,`category_id`,`state`,`check`) values 
(1,'sbzcy','11',0,0,'2024-06-24 11:00:31',NULL,'11',1,1,1,0),
(2,'zg','11',0,0,'2024-06-25 20:10:28',NULL,'11',1,1,1,0);

/*Table structure for table `article_picture` */

DROP TABLE IF EXISTS `article_picture`;

CREATE TABLE `article_picture` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '图片id',
  `article_id` int NOT NULL COMMENT '文章id',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `pic_article_id` (`article_id`) USING BTREE,
  CONSTRAINT `pic_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `article_picture` */

/*Table structure for table `article_report` */

DROP TABLE IF EXISTS `article_report`;

CREATE TABLE `article_report` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '表id',
  `article_id` int NOT NULL COMMENT '文章ID',
  `manage_id` int NOT NULL COMMENT '被分配的管理员ID',
  `cause` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '举报原因',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '0表示未操作，1表示冻结，-1表示无问题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `article_report` */

insert  into `article_report`(`id`,`article_id`,`manage_id`,`cause`,`state`) values 
(1,1,2,'sbzcy',1);

/*Table structure for table `article_review` */

DROP TABLE IF EXISTS `article_review`;

CREATE TABLE `article_review` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '此表ID',
  `article_id` int NOT NULL COMMENT '文章ID',
  `manage_id` int NOT NULL COMMENT '被分配的管理员ID',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否完成审查0未审查-1未通过1通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `article_review` */

insert  into `article_review`(`id`,`article_id`,`manage_id`,`state`) values 
(1,1,2,-1),
(2,2,2,1);

/*Table structure for table `attention` */

DROP TABLE IF EXISTS `attention`;

CREATE TABLE `attention` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '关注id',
  `create_user` int NOT NULL COMMENT '关注人 用户id',
  `concerned` int NOT NULL COMMENT '被关注人 用户id',
  `create_time` datetime NOT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `attention_create_user` (`create_user`) USING BTREE,
  KEY `attention_concerned` (`concerned`) USING BTREE,
  CONSTRAINT `attention_concerned` FOREIGN KEY (`concerned`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `attention_create_user` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `attention` */

/*Table structure for table `audit_article` */

DROP TABLE IF EXISTS `audit_article`;

CREATE TABLE `audit_article` (
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `audit_article` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类表id',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `category` */

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论表id',
  `article_id` int NOT NULL COMMENT '文章id',
  `create_user` int NOT NULL COMMENT '用户id',
  `comment_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `comment_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '评论图片',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `comment_user_id` (`create_user`) USING BTREE,
  KEY `comment_article_id` (`article_id`) USING BTREE,
  CONSTRAINT `comment_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `comment_user_id` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `comment` */

/*Table structure for table `likes` */

DROP TABLE IF EXISTS `likes`;

CREATE TABLE `likes` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '点赞id',
  `article_id` int NOT NULL COMMENT '文章id',
  `create_user` int NOT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `likes_user_id` (`create_user`) USING BTREE,
  KEY `likes_article_id` (`article_id`) USING BTREE,
  CONSTRAINT `likes_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `likes_user_id` FOREIGN KEY (`create_user`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `likes` */

/*Table structure for table `manage` */

DROP TABLE IF EXISTS `manage`;

CREATE TABLE `manage` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `username` char(15) NOT NULL,
  `password` char(15) NOT NULL,
  `status` int NOT NULL DEFAULT '0' COMMENT '身份1为超级管理员0普通管理员',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否在线',
  `mail` varchar(255) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `manage` */

insert  into `manage`(`id`,`username`,`password`,`status`,`remarks`,`state`,`mail`) values 
(1,'sagiri','123456',1,'超级管理员，老板账号',0,'sagiri0512@qq.com'),
(2,'赵晨云','123456',0,'sbzcy，ycsbz',1,'551730641@qq.com'),
(3,'段有美','123456',0,'dg',1,NULL),
(4,'张三','123456',0,NULL,0,NULL),
(5,'李四','123456',0,NULL,0,NULL),
(6,'王五','123456',0,NULL,0,NULL),
(7,'张一','123456',0,NULL,0,NULL);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `sender` int NOT NULL COMMENT '发件人 用户id',
  `recipient` int NOT NULL COMMENT '收件人 用户id',
  `message_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容 不允许为空',
  `create_time` datetime NOT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `message_sender` (`sender`) USING BTREE,
  KEY `message_recipient` (`recipient`) USING BTREE,
  CONSTRAINT `message_recipient` FOREIGN KEY (`recipient`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `message_sender` FOREIGN KEY (`sender`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `message` */

/*Table structure for table `root_mail_code` */

DROP TABLE IF EXISTS `root_mail_code`;

CREATE TABLE `root_mail_code` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员验证码表ID',
  `manage_mail` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员邮箱',
  `mail_code` char(7) COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱验证码6位',
  `code_time` timestamp NOT NULL COMMENT '验证码发送的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `root_mail_code` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户_主键',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录用户名 5-16位',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录密码 5-16位',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户昵称 默认为id ',
  `user_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户头像',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `state` int NOT NULL DEFAULT '1' COMMENT '状态 1为正常 0为被封禁',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
