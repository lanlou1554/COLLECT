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
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flaw`
--

LOCK TABLES `flaw` WRITE;
/*!40000 ALTER TABLE `flaw` DISABLE KEYS */;
INSERT INTO `flaw` VALUES (1,1,'主界面中连续快速点击“更改设置”按钮，程序会卡死','首先，进入应用主界面；其次，连续点击右上角的“更改设置”按钮，注意一定要点的飞快。十几次后程序会无法响应，强制退出。','iphone11',-1,1,'1',0,8,''),(2,1,'内置跳转小程序打不开','在“发现页面”点击左下角“开心一下”，页面发生跳转，不过一直是白屏，无法打开','iphone11',-1,1,'2',0,8,''),(3,2,'主界面中连续快速点击“更改设置”按钮，程序会卡死','首先，进入应用主界面；其次，连续点击右上角的“更改设置”按钮，注意一定要点的飞快。十几次后程序会无法响应，强制退出。','iphone11',-1,1,'3',0,9,''),(4,2,'内置跳转小程序打不开','在“发现页面”点击左下角“开心一下”，页面发生跳转，不过一直是白屏，无法打开','iphone11',-1,1,'4',0,9,''),(5,3,'在主界面中，重复按“更改设置”按钮，程序会崩溃','首先进入主界面，然后非常快速地按“更改设置”按钮，几十次后程序崩溃','HUAWEI P40',-1,0,'',0,9,''),(6,3,'“开心一下”小程序打不开','进入“发现页面”，点击左下角“开心一下”，页面一直是白屏，无法打开','HUAWEI P40',-1,0,'',0,9,''),(7,3,'[集成管理]模块的[项目注册管理]页面， 查看项目立项信息时，出现未能加载类型ProjectGeneralView错误','在[项目管理]—> [项目基本信息管理]页面下；2）选择[已审核]TAB页； 3）在GRID列表中，选择任意一条项目立项信息，点击[查看]按钮；4）系统页面弹出：按钮报错信息“','HUAWEI P40',-1,1,'7',0,9,''),(8,4,'在主界面中，重复按“更改设置”按钮，程序会崩溃','首先进入主界面，然后非常快速地按“更改设置”按钮，几十次后程序崩溃','HUAWEI P40',4.5,1,'1,8',2,8,'一定要点的飞快！！'),(9,4,'“开心一下”小程序打不开','进入“发现页面”，点击左下角“开心一下”，页面一直是白屏，无法打开','HUAWEI P40',4,1,'2,9',2,8,''),(10,4,'[集成管理]模块的[项目注册管理]页面， 查看项目立项信息时，出现未能加载类型ProjectGeneralView错误','在[项目管理]—> [项目基本信息管理]页面下；2）选择[已审核]TAB页； 3）在GRID列表中，选择任意一条项目立项信息，点击[查看]按钮；4）系统页面弹出：按钮报错信息“','HUAWEI P40',1,1,'10',2,8,'不好意思，搞混了，大家忽视这个缺陷'),(11,5,'进入主界面中不断点击“更改设置”，程序会白屏','连续点击右上角的“更改设置”按钮，一定要点很快。一段时间后程序会整个崩溃。','ipad2020',-1,1,'1,11',0,8,''),(12,5,'这个文件夹并不能被剪切走','当这个文件夹被剪切走时，会出现这样的现象：这个文件夹并不能被剪切走，文件夹依然存在，只是未被加入到索引中，而被粘贴出现的新文件夹一切正常','windows10',-1,1,'12',0,8,''),(13,6,'主界面中一直点击“更改设置”，程序会卡死','连续点击右上角的“更改设置”按钮，记得点很快。一段时间后程序会整个崩溃。','iphone11',-1,1,'1,11,13',0,8,''),(14,6,'内置小程序“开心一下”无法打开','在“发现页面”点击左下角“开心一下”，页面发生跳转，但是一直没有响应','iphone11',-1,1,'2,14',0,8,''),(15,7,'主界面中不能很快点击“更改设置”按钮','在主界面中，快速点击右上角的“更改设置”按钮，一段时间后程序会强制退出。','HUAWEI P40',-1,1,'1,11,15',0,8,''),(16,7,'截图中文件夹并被剪切走出现异常现象','当这个文件夹被剪切走时，会出现这样的现象：文件夹依然存在，但是索引中没有此文件夹，而被粘贴出现的新文件夹一切正常','HUAWEI P40',-1,1,'12,16',0,8,'');
/*!40000 ALTER TABLE `flaw` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flaw_evaluation`
--

LOCK TABLES `flaw_evaluation` WRITE;
/*!40000 ALTER TABLE `flaw_evaluation` DISABLE KEYS */;
INSERT INTO `flaw_evaluation` VALUES (1,8,9,'思路挺好，但内容可以更加多一些'),(2,10,8,'这个缺陷和这个应用无关吧？'),(3,9,7,'喜欢！'),(4,10,7,'同意楼上');
/*!40000 ALTER TABLE `flaw_evaluation` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `flaw_pic`
--

LOCK TABLES `flaw_pic` WRITE;
/*!40000 ALTER TABLE `flaw_pic` DISABLE KEYS */;
INSERT INTO `flaw_pic` VALUES (1,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(2,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/FJKnfGnnFn.jpg'),(3,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/G3ewSPJKch.jpg'),(4,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/AEyS6rAYTz.jpg'),(5,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/yZP4tZiNc2.jpg'),(6,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/DTtWF6bnd6.jpg'),(7,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/JDTsTzyNsb.png'),(8,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(9,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/FJKnfGnnFn.jpg'),(10,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/bCZard65es.png'),(11,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(12,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/cNKhx2adcx.jpg'),(13,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(14,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/FJKnfGnnFn.jpg'),(15,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(16,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/cNKhx2adcx.jpg');
/*!40000 ALTER TABLE `flaw_pic` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_info`
--

LOCK TABLES `report_info` WRITE;
/*!40000 ALTER TABLE `report_info` DISABLE KEYS */;
INSERT INTO `report_info` VALUES (1,8,5,'workerTest1的报告','2022-04-02 09:01:40'),(2,9,5,'workerTest1的报告','2022-04-02 09:02:26'),(3,9,3,'worker的报告','2022-04-02 09:04:36'),(4,8,3,'worker的报告','2022-04-02 09:05:43'),(5,8,7,'workerTest2的报告','2022-04-02 09:07:14'),(6,8,8,'workerTest3的报告','2022-04-02 09:11:43'),(7,8,9,'workerTest4的报告','2022-04-02 09:13:18');
/*!40000 ALTER TABLE `report_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rule`
--

DROP TABLE IF EXISTS `rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `isUsing` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rule`
--

LOCK TABLES `rule` WRITE;
/*!40000 ALTER TABLE `rule` DISABLE KEYS */;
INSERT INTO `rule` VALUES (1,'默认规则',1);
/*!40000 ALTER TABLE `rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rule_factor`
--

DROP TABLE IF EXISTS `rule_factor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule_factor` (
  `ruleId` int(11) NOT NULL,
  `factorName` varchar(255) NOT NULL,
  `factorWeight` double DEFAULT NULL,
  PRIMARY KEY (`ruleId`,`factorName`),
  CONSTRAINT `rule_factor_rule_id_fk` FOREIGN KEY (`ruleId`) REFERENCES `rule` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rule_factor`
--

LOCK TABLES `rule_factor` WRITE;
/*!40000 ALTER TABLE `rule_factor` DISABLE KEYS */;
INSERT INTO `rule_factor` VALUES (1,'任务相似度',0.3),(1,'任务紧迫度',0.2),(1,'用户相似度',0.4);
/*!40000 ALTER TABLE `rule_factor` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` VALUES (7,9,5),(7,10,1),(8,8,5),(8,9,3),(8,10,1),(9,8,4);
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `similarity`
--

LOCK TABLES `similarity` WRITE;
/*!40000 ALTER TABLE `similarity` DISABLE KEYS */;
INSERT INTO `similarity` VALUES (1,2,0.83),(3,4,0.83),(7,3,0.59),(7,4,0.6),(8,1,0.94),(8,2,0.8),(8,10,0.57),(9,1,0.81),(9,2,0.93),(9,8,0.78),(9,10,0.58),(10,1,0.59),(10,2,0.6),(11,1,0.97),(11,2,0.85),(11,8,0.94),(11,9,0.84),(11,10,0.62),(11,12,0.69),(12,1,0.7),(12,2,0.72),(12,8,0.72),(12,9,0.69),(12,10,0.52),(13,1,0.96),(13,2,0.85),(13,8,0.94),(13,9,0.85),(13,10,0.61),(13,11,0.99),(13,12,0.7),(14,1,0.83),(14,2,0.97),(14,8,0.8),(14,9,0.96),(14,10,0.59),(14,11,0.85),(14,12,0.71),(14,13,0.86),(15,1,0.98),(15,2,0.86),(15,8,0.94),(15,9,0.83),(15,10,0.62),(15,11,0.97),(15,12,0.72),(15,13,0.97),(15,14,0.86),(16,1,0.74),(16,2,0.77),(16,8,0.76),(16,9,0.73),(16,10,0.54),(16,11,0.74),(16,12,0.99),(16,13,0.74),(16,14,0.75),(16,15,0.76);
/*!40000 ALTER TABLE `similarity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_info`
--

DROP TABLE IF EXISTS `task_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(1023) COLLATE utf8mb4_bin DEFAULT NULL,
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `workerNum` int(11) NOT NULL,
  `exeFileName` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `reqDocName` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `task_info_user_info__fk` (`userId`),
  CONSTRAINT `task_info_user_info__fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_info`
--

LOCK TABLES `task_info` WRITE;
/*!40000 ALTER TABLE `task_info` DISABLE KEYS */;
INSERT INTO `task_info` VALUES (4,2,0,'需要检测一个应用软件','主要是检测一个应用软件，有app和desktop版本','2022-04-01 16:00:00','2022-05-01 16:00:00',20,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/4dEpnbGfSN.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/B5FpWriiDk.pdf','2022-04-02 07:28:13'),(7,2,0,'测试一个app','一个学习课程的app，报酬丰厚','2022-04-01 16:00:00','2022-06-29 16:00:00',1,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/BHyJx82sZ7.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/b8wT8DPZBT.png','2022-04-02 07:34:20'),(8,2,0,'要测试一个应用，桌面版和手机版都要','同标题，不急，时间充裕，但是希望充分测试','2022-04-01 16:00:00','2022-08-10 16:00:00',20,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/JJe64TyYaK.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/zKazFyzXRX.pdf','2022-04-02 07:35:45'),(9,2,1,'app性能测试','如题，关于抽奖的app的性能测试','2022-04-01 16:00:00','2022-07-04 16:00:00',5,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/3kaz5TNGtj.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/4Pk68Wh22S.pdf','2022-04-02 07:37:26'),(10,2,1,'web性能测试','希望web在任何机器上响应时间都很好','2022-04-01 16:00:00','2022-06-02 16:00:00',12,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/ezWwxfAhFs.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/ZdXxfeb6Gh.pdf','2022-04-02 07:38:09');
/*!40000 ALTER TABLE `task_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_tag`
--

DROP TABLE IF EXISTS `task_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_tag` (
  `taskId` int(11) NOT NULL,
  `tag` int(11) NOT NULL,
  PRIMARY KEY (`taskId`,`tag`),
  CONSTRAINT `task_tag_task_info_id_fk` FOREIGN KEY (`taskId`) REFERENCES `task_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_tag`
--

LOCK TABLES `task_tag` WRITE;
/*!40000 ALTER TABLE `task_tag` DISABLE KEYS */;
INSERT INTO `task_tag` VALUES (4,0),(4,2),(7,0),(7,6),(8,0),(8,2),(9,1),(9,6),(10,1),(10,8);
/*!40000 ALTER TABLE `task_tag` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `task_user`
--

LOCK TABLES `task_user` WRITE;
/*!40000 ALTER TABLE `task_user` DISABLE KEYS */;
INSERT INTO `task_user` VALUES (8,3),(9,3),(8,5),(9,5),(8,7),(8,8),(4,9),(8,9);
/*!40000 ALTER TABLE `task_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `phone` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `type` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_info_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('15161768556',2,1,'admin123','admin','591354379@qq.com'),('17790538663',0,2,'00000000','employer','employer@163.com'),('15028390642',1,3,'00000000','worker','worker@163.com'),('17551158079',1,5,'00000000','workerTest1','test1@worker.com'),('17551158095',1,7,'00000000','workerTest2','test2@worker.com'),('17551158093',1,8,'00000000','workerTest3','test3@worker.com'),('17551158764',1,9,'00000000','workerTest4','test4@worker.com'),('66665555666',1,13,'00000000','workerTest5','5555@2222.3333');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tag`
--

DROP TABLE IF EXISTS `user_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_tag` (
  `userId` int(11) NOT NULL,
  `tag` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`tag`),
  CONSTRAINT `user_tag_user_info_id_fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tag`
--

LOCK TABLES `user_tag` WRITE;
/*!40000 ALTER TABLE `user_tag` DISABLE KEYS */;
INSERT INTO `user_tag` VALUES (3,2),(3,4),(5,2),(5,4),(7,2),(7,4),(8,2),(8,4),(9,2),(9,4);
/*!40000 ALTER TABLE `user_tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-03 12:22:46