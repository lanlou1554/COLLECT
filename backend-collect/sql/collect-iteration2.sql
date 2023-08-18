-- MySQL dump 10.13  Distrib 5.7.37, for Linux (x86_64)
--
-- Host: localhost    Database: collect
-- ------------------------------------------------------
-- Server version       5.7.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flaw`
--

DROP TABLE IF EXISTS `flaw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flaw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reportId` int(11) NOT NULL,
  `description` varchar(1023) COLLATE utf8mb4_bin DEFAULT NULL,
  `stepDes` varchar(1023) COLLATE utf8mb4_bin DEFAULT NULL,
  `deviceInfo` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `score` double DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL,
  `path` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `scoreNum` int(11) DEFAULT NULL,
  `taskId` int(11) DEFAULT NULL,
  `appendContent` varchar(1023) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `flaw_report_info__fk` (`reportId`),
  KEY `flaw_task_info_id_fk` (`taskId`),
  CONSTRAINT `flaw_report_info__fk` FOREIGN KEY (`reportId`) REFERENCES `report_info` (`id`) ON DELETE CASCADE,
  CONSTRAINT `flaw_task_info_id_fk` FOREIGN KEY (`taskId`) REFERENCES `task_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `flaw_evaluation`
--

DROP TABLE IF EXISTS `flaw_evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flaw_evaluation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flawId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `content` varchar(1023) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `flaw_evaluation_flaw_id_fk` (`flawId`),
  KEY `flaw_evaluation_user_info_id_fk` (`userId`),
  CONSTRAINT `flaw_evaluation_flaw_id_fk` FOREIGN KEY (`flawId`) REFERENCES `flaw` (`id`) ON DELETE CASCADE,
  CONSTRAINT `flaw_evaluation_user_info_id_fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `flaw_pic`
--

DROP TABLE IF EXISTS `flaw_pic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flaw_pic` (
  `flawId` int(11) NOT NULL,
  `pictureURL` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`flawId`,`pictureURL`),
  CONSTRAINT `flaw_pic_flaw__fk` FOREIGN KEY (`flawId`) REFERENCES `flaw` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_info`
--

DROP TABLE IF EXISTS `report_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `report_info_task_info__fk` (`taskId`),
  KEY `report_info_user_info__fk` (`userId`),
  CONSTRAINT `report_info_task_info__fk` FOREIGN KEY (`taskId`) REFERENCES `task_info` (`id`) ON DELETE CASCADE,
  CONSTRAINT `report_info_user_info__fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rule`
--

--DROP TABLE IF EXISTS `rule`;
--/*!40101 SET @saved_cs_client     = @@character_set_client */;
--/*!40101 SET character_set_client = utf8 */;
--CREATE TABLE `rule` (
--  `id` int(11) NOT NULL AUTO_INCREMENT,
--  `name` varchar(255) DEFAULT NULL,
--  `isUsing` tinyint(1) DEFAULT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rule_factor`
--

--DROP TABLE IF EXISTS `rule_factor`;
--/*!40101 SET @saved_cs_client     = @@character_set_client */;
--/*!40101 SET character_set_client = utf8 */;
--CREATE TABLE `rule_factor` (
--  `ruleId` int(11) NOT NULL,
--  `factorName` varchar(255) NOT NULL,
--  `factorWeight` double DEFAULT NULL,
--  PRIMARY KEY (`ruleId`,`factorName`),
--  CONSTRAINT `rule_factor_rule_id_fk` FOREIGN KEY (`ruleId`) REFERENCES `rule` (`id`) ON DELETE CASCADE
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `score` (
  `userId` int(11) NOT NULL,
  `flawId` int(11) NOT NULL,
  `socre` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`,`flawId`),
  KEY `score_flaw_id_fk` (`flawId`),
  CONSTRAINT `score_flaw_id_fk` FOREIGN KEY (`flawId`) REFERENCES `flaw` (`id`) ON DELETE CASCADE,
  CONSTRAINT `score_user_info_id_fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `similarity`
--

DROP TABLE IF EXISTS `similarity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `similarity` (
  `flawId1` int(11) NOT NULL,
  `flawId2` int(11) NOT NULL,
  `similarity` double DEFAULT NULL,
  PRIMARY KEY (`flawId1`,`flawId2`),
  KEY `similarity_flaw_id_fk_2` (`flawId2`),
  CONSTRAINT `similarity_flaw_id_fk` FOREIGN KEY (`flawId1`) REFERENCES `flaw` (`id`) ON DELETE CASCADE,
  CONSTRAINT `similarity_flaw_id_fk_2` FOREIGN KEY (`flawId2`) REFERENCES `flaw` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_info`
--

--DROP TABLE IF EXISTS `task_info`;
--/*!40101 SET @saved_cs_client     = @@character_set_client */;
--/*!40101 SET character_set_client = utf8 */;
--CREATE TABLE `task_info` (
--  `id` int(11) NOT NULL AUTO_INCREMENT,
--  `userId` int(11) NOT NULL,
--  `type` int(11) NOT NULL,
--  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
--  `description` varchar(1023) COLLATE utf8mb4_bin DEFAULT NULL,
--  `startTime` timestamp NULL DEFAULT NULL,
--  `endTime` timestamp NULL DEFAULT NULL,
--  `workerNum` int(11) NOT NULL,
--  `exeFileName` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
--  `reqDocName` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
--  `createTime` timestamp NULL DEFAULT NULL,
--  PRIMARY KEY (`id`),
--  KEY `task_info_user_info__fk` (`userId`),
--  CONSTRAINT `task_info_user_info__fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_tag`
--

--DROP TABLE IF EXISTS `task_tag`;
--/*!40101 SET @saved_cs_client     = @@character_set_client */;
--/*!40101 SET character_set_client = utf8 */;
--CREATE TABLE `task_tag` (
--  `taskId` int(11) NOT NULL,
--  `tag` int(11) NOT NULL,
--  PRIMARY KEY (`taskId`,`tag`),
--  CONSTRAINT `task_tag_task_info_id_fk` FOREIGN KEY (`taskId`) REFERENCES `task_info` (`id`) ON DELETE CASCADE
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_user`
--

DROP TABLE IF EXISTS `task_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_user` (
  `taskId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`taskId`,`userId`),
  KEY `task_user_user_info__fk` (`userId`),
  CONSTRAINT `task_user_task_info__fk` FOREIGN KEY (`taskId`) REFERENCES `task_info` (`id`) ON DELETE CASCADE,
  CONSTRAINT `task_user_user_info__fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_info`
--

--DROP TABLE IF EXISTS `user_info`;
--/*!40101 SET @saved_cs_client     = @@character_set_client */;
--/*!40101 SET character_set_client = utf8 */;
--CREATE TABLE `user_info` (
--  `phone` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
--  `type` int(11) NOT NULL,
--  `id` int(11) NOT NULL AUTO_INCREMENT,
--  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
--  `username` varchar(255) COLLATE utf8mb4_bin NOT NULL,
--  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
--  PRIMARY KEY (`id`),
--  UNIQUE KEY `user_info_username_uindex` (`username`)
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_tag`
--

--DROP TABLE IF EXISTS `user_tag`;
--/*!40101 SET @saved_cs_client     = @@character_set_client */;
--/*!40101 SET character_set_client = utf8 */;
--CREATE TABLE `user_tag` (
--  `userId` int(11) NOT NULL,
--  `tag` int(11) NOT NULL,
--  PRIMARY KEY (`userId`,`tag`),
--  CONSTRAINT `user_tag_user_info_id_fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-29 17:53:33

--INSERT INTO `user_info` values('15161768556',2,1,'admin123','admin','591354379@qq.com'),
--('17790538663',0,2,'00000000','employer','employer@163.com'),
--('15028390642',1,3,'00000000','worker','worker@163.com');
----#('12345698765',1,4,'00000000','worker1','worker1@163.com'),
----#('13579246801',1,5,'00000000','worker2','worker2@163.com');
--
--INSERT INTO `rule` values(1,'默认规则',true);
--INSERT INTO `rule_factor` values(1,'任务紧迫度',0.2),(1,'任务相似度',0.3),(1,'用户相似度',0.4);

# --insert into `task_info` values(1,2,1,'任务1','描述1','2022-04-01','2022-04-07',20,null,null,'2022-03-31');
# --insert into `task_info` values(2,2,1,'任务2','描述2','2022-04-02','2022-04-08',30,null,null,'2022-03-31');
# --insert into `task_info` values(3,2,1,'任务3','描述3','2022-04-03','2022-04-09',40,null,null,'2022-03-31');
# --
# --INSERT into `task_user` values(1,3),(2,3),(3,3),(1,4),(2,4),(3,4),(1,5),(2,5),(3,5);
# --
# --Insert into `report_info` values(1,1,3,'任务1的报告1','2022-04-02');
# --Insert into `report_info` values(2,1,4,'任务1的报告2','2022-04-03');
# --Insert into `report_info` values(3,1,5,'任务1的报告3','2022-04-04');
# --
# --INSERT into `flaw`values(1,1,'任务1的报告1的缺陷1','任务1的报告1的缺陷1的步骤描述','任务1的报告1的缺陷1的设备信息',2.0,true,'1',1,1,'任务1的报告1的缺陷1的补充内容');
# --INSERT into `flaw`values(2,1,'任务1的报告1的缺陷2','任务1的报告1的缺陷2的步骤描述','任务1的报告1的缺陷2的设备信息',2.0,true,'2',1,1,'任务1的报告1的缺陷2的补充内容');
# --INSERT into `flaw`values(3,1,'任务1的报告1的缺陷3','任务1的报告1的缺陷3的步骤描述','任务1的报告1的缺陷3的设备信息',2.0,true,'3',1,1,'任务1的报告1的缺陷3的补充内容');
# --INSERT into `flaw`values(4,1,'任务1的报告1的缺陷4','任务1的报告1的缺陷4的步骤描述','任务1的报告1的缺陷4的设备信息',2.0,true,'4',1,1,'任务1的报告1的缺陷4的补充内容');
# --
# --INSERT into `flaw`values(5,2,'任务1的报告2的缺陷1','任务1的报告2的缺陷1的步骤描述','任务1的报告2的缺陷1的设备信息',2.0,true,'1,5',1,1,'任务1的报告2的缺陷1的补充内容');
# --INSERT into `flaw`values(6,2,'任务1的报告2的缺陷2','任务1的报告2的缺陷2的步骤描述','任务1的报告2的缺陷2的设备信息',2.0,true,'1,5,6',1,1,'任务1的报告2的缺陷2的补充内容');
# --INSERT into `flaw`values(7,2,'任务1的报告2的缺陷3','任务1的报告2的缺陷3的步骤描述','任务1的报告2的缺陷3的设备信息',2.0,true,'1,7',1,1,'任务1的报告2的缺陷3的补充内容');
# --
# --INSERT into `flaw`values(8,3,'任务1的报告3的缺陷1','任务1的报告3的缺陷1的步骤描述','任务1的报告3的缺陷1的设备信息',2.0,true,'2,8',1,1,'任务1的报告3的缺陷1的补充内容');
# --INSERT into `flaw`values(9,3,'任务1的报告3的缺陷2','任务1的报告3的缺陷2的步骤描述','任务1的报告3的缺陷2的设备信息',2.0,true,'9',1,1,'任务1的报告3的缺陷2的补充内容');
# --
# --INSERT into `score`values(4,1,2.0),(4,2,2.0),(4,3,2.0),(4,4,2.0),
# --(5,5,2.0),(5,6,2.0),(5,7,2.0),
# --(3,8,2.0),(3,9,2.0);
# --
# --INSERT into `similarity` values(2,1,0.0),(3,1,0.0),(3,2,0.2),(4,1,0.1),(4,2,0.0),(4,3,0.0),
# --                                (5,1,0.6),(9,1,0.2),(9,2,0.3),(9,3,0.4),(9,4,0.6),(9,5,0.7),(9,6,0.8),(9,7,0.9);