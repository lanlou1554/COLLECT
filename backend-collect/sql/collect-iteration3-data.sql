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
) ENGINE=InnoDB AUTO_INCREMENT=1044 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flaw`
--

LOCK TABLES `flaw` WRITE;
/*!40000 ALTER TABLE `flaw` DISABLE KEYS */;
INSERT INTO `flaw` VALUES (1,1,'购物车板块，会出现购物车被莫名其妙清空问题','先进入购物车板块，不断点击右上角，购物车突然就被清空了','iphone11',-1,1,'1',0,8,''),(2,1,'支付页面速度很慢','选择物品进行支付的时候，如果随意向下滑动页面，页面支付速度变慢','iphone11',-1,1,'2',0,8,''),(3,2,'主界面中连续快速点击“更改设置”按钮，程序会卡死','首先，进入应用主界面；其次，连续点击右上角的“更改设置”按钮，注意一定要点的飞快。十几次后程序会无法响应，强制退出。','iphone11',-1,1,'3',0,9,''),(4,2,'内置跳转小程序打不开','在“发现页面”点击左下角“开心一下”，页面发生跳转，不过一直是白屏，无法打开','iphone11',-1,1,'4',0,9,''),(5,3,'在主界面中，重复按“更改设置”按钮，程序会崩溃','首先进入主界面，然后非常快速地按“更改设置”按钮，几十次后程序崩溃','HUAWEI P40',-1,1,'5',0,9,''),(6,3,'“开心一下”小程序打不开','进入“发现页面”，点击左下角“开心一下”，页面一直是白屏，无法打开','HUAWEI P40',-1,1,'6',0,9,''),(7,3,'[集成管理]模块的[项目注册管理]页面， 查看项目立项信息时，出现未能加载类型ProjectGeneralView错误','在[项目管理]—> [项目基本信息管理]页面下；2）选择[已审核]TAB页； 3）在GRID列表中，选择任意一条项目立项信息，点击[查看]按钮；4）系统页面弹出：按钮报错信息“','HUAWEI P40',-1,1,'7',0,9,''),(8,4,'购物车页面，右上角疯狂点击时，会有问题','从主页面到购物车板块，点击右上角空白处，购物车全部没有了','HUAWEI P40',4.5,1,'1,8',2,8,'一定要点的飞快！！'),(9,4,'支付页面向下滑动，一直加载','支付页面一直向下滑动，无法支付，一直加载','HUAWEI P40',4,1,'2,9',2,8,''),(10,4,'购物车数量变满后的白屏问题','把购物车装满后，放一段时间，有一定概率出现白屏，原因未知','HUAWEI P40',1,1,'10',2,8,'不好意思，搞混了，大家忽视这个缺陷'),(11,5,'购物车清空问题','点击购物车板块，点击右上角后被清空','ipad2020',-1,1,'1,11',0,8,''),(12,5,'购买体验问题，常常没有办法很快滑动','在购买页面，快速滑动的时候，往往会卡顿，购买体验很差','windows10',-1,1,'12',0,8,''),(13,6,'购物车页面出现问题','先进入购物车板块，不断点击右上角，购物车突然就被清空了','iphone11',-1,1,'1,11,13',0,8,''),(14,6,'支付页面速度很慢','选择物品进行支付的时候，如果随意向下滑动页面，页面支付速度变慢，一直加载','iphone11',-1,1,'2,14',0,8,''),(15,7,'购物车页面，右上角疯狂点击时，会有问题','从主页面到购物车板块，点击右上角空白处，购物车全部没有了','HUAWEI P40',-1,1,'1,11,15',0,8,''),(16,7,'购买体验问题，常常没有办法很快滑动','在购买页面，快速滑动的时候，往往会卡顿，购买体验很差','HUAWEI P40',-1,1,'12,16',0,8,''),(209,107,'电源耗电太快','这个APP比一般APP耗电快很多','HUAWEI P40',-1,1,'209',0,8,''),(541,272,'截图中文件夹并被剪切走出现异常现象','当这个文件夹被剪切走时，会出现这样的现象：文件夹依然存在，但是索引中没有此文件夹，而被粘贴出现的新文件夹一切正常','华为P30',-1,1,'541',0,10,''),(542,273,'截图中文件夹并被剪切走出现不正常现象','当这个文件夹被剪切走时，会出现这样的现象：文件夹依然存在，但是索引中没有此文件夹','华为P30',-1,1,'541,542',0,10,'');
/*!40000 ALTER TABLE `flaw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flaw_eva_like`
--

DROP TABLE IF EXISTS `flaw_eva_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flaw_eva_like` (
  `evaId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `support` int(11) DEFAULT '0',
  `oppose` int(11) DEFAULT '0',
  PRIMARY KEY (`evaId`,`userId`),
  KEY `flaw_eva_like_user_info_id_fk` (`userId`),
  CONSTRAINT `flaw_eva_like_flaw_evaluation_id_fk` FOREIGN KEY (`evaId`) REFERENCES `flaw_evaluation` (`id`) ON DELETE CASCADE,
  CONSTRAINT `flaw_eva_like_user_info_id_fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flaw_eva_like`
--

LOCK TABLES `flaw_eva_like` WRITE;
/*!40000 ALTER TABLE `flaw_eva_like` DISABLE KEYS */;
INSERT INTO `flaw_eva_like` VALUES (1,5,1,0),(1,7,1,0),(1,8,0,1),(3,5,1,0),(72,3,0,1);
/*!40000 ALTER TABLE `flaw_eva_like` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=353 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flaw_evaluation`
--

LOCK TABLES `flaw_evaluation` WRITE;
/*!40000 ALTER TABLE `flaw_evaluation` DISABLE KEYS */;
INSERT INTO `flaw_evaluation` VALUES (1,8,9,'思路挺好，但内容可以更加多一些'),(2,10,8,'这个缺陷和这个应用无关吧？'),(3,9,7,'喜欢！'),(4,10,7,'同意楼上'),(72,1,3,'lll');
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
INSERT INTO `flaw_pic` VALUES (1,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(2,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/FJKnfGnnFn.jpg'),(3,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/G3ewSPJKch.jpg'),(4,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/AEyS6rAYTz.jpg'),(5,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/yZP4tZiNc2.jpg'),(6,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/DTtWF6bnd6.jpg'),(7,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/JDTsTzyNsb.png'),(8,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(9,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/FJKnfGnnFn.jpg'),(10,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/bCZard65es.png'),(11,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(12,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/cNKhx2adcx.jpg'),(13,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(14,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/FJKnfGnnFn.jpg'),(15,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/reKEzfCwkK.jpg'),(16,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/cNKhx2adcx.jpg'),(209,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/kKMBhKJKiJ.png'),(541,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/jR4c3ss5wC.png'),(542,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/jR4c3ss5wC.png');
/*!40000 ALTER TABLE `flaw_pic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multi_objective_recommend_factor`
--

DROP TABLE IF EXISTS `multi_objective_recommend_factor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multi_objective_recommend_factor` (
  `taskId` int(11) NOT NULL,
  `worker_ability` double DEFAULT NULL,
  `activeness` double DEFAULT NULL,
  `worker_diversity` double DEFAULT NULL,
  `task_relevance` double DEFAULT NULL,
  `worker_cost` double DEFAULT NULL,
  PRIMARY KEY (`taskId`),
  CONSTRAINT `multi_objective_recommend_factor_task_info_id_fk` FOREIGN KEY (`taskId`) REFERENCES `task_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multi_objective_recommend_factor`
--

LOCK TABLES `multi_objective_recommend_factor` WRITE;
/*!40000 ALTER TABLE `multi_objective_recommend_factor` DISABLE KEYS */;
INSERT INTO `multi_objective_recommend_factor` VALUES (4,0.1,0.1,0.1,0.5,0.2),(7,0.2,0.3,0,0.1,0.4),(8,0.4,0.1,0.2,0.2,0.1),(9,0.4,0.1,0.2,0.2,0.1),(10,0.4,0.1,0.2,0.2,0.1);
/*!40000 ALTER TABLE `multi_objective_recommend_factor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multi_objective_recommend_result`
--

DROP TABLE IF EXISTS `multi_objective_recommend_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multi_objective_recommend_result` (
  `userId` int(11) NOT NULL,
  `taskId` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`taskId`),
  KEY `table_name_task_info_id_fk` (`taskId`),
  CONSTRAINT `table_name_task_info_id_fk` FOREIGN KEY (`taskId`) REFERENCES `task_info` (`id`) ON DELETE CASCADE,
  CONSTRAINT `table_name_user_info_id_fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multi_objective_recommend_result`
--

LOCK TABLES `multi_objective_recommend_result` WRITE;
/*!40000 ALTER TABLE `multi_objective_recommend_result` DISABLE KEYS */;
INSERT INTO `multi_objective_recommend_result` VALUES (3,7),(5,7),(7,7),(8,7),(9,7),(81,7),(3,8),(5,8),(7,8),(8,8),(9,8),(13,8),(81,8),(3,9),(5,9),(7,9),(8,9),(9,9),(13,9),(81,9),(3,10),(5,10),(7,10),(8,10),(9,10),(13,10),(81,10);
/*!40000 ALTER TABLE `multi_objective_recommend_result` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=524 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_info`
--

LOCK TABLES `report_info` WRITE;
/*!40000 ALTER TABLE `report_info` DISABLE KEYS */;
INSERT INTO `report_info` VALUES (1,8,5,'workerTest1的报告','2022-04-02 09:01:40'),(2,9,5,'workerTest1的报告','2022-04-02 09:02:26'),(3,9,3,'worker的报告','2022-04-02 09:04:36'),(4,8,3,'worker的报告','2022-04-02 09:05:43'),(5,8,7,'workerTest2的报告','2022-04-02 09:07:14'),(6,8,8,'workerTest3的报告','2022-04-02 09:11:43'),(7,8,9,'workerTest4的报告','2022-04-02 09:13:18'),(107,8,81,'f','2022-05-21 07:41:36'),(272,10,3,'worker的报告','2022-05-26 08:05:34'),(273,10,5,'worerkTest1的报告','2022-05-26 08:07:05');
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
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8mb4;
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
INSERT INTO `similarity` VALUES (1,2,0.83),(3,4,0.83),(5,3,0.79),(5,4,0.44),(5,6,0.49),(5,7,0.35),(6,3,0.45),(6,4,0.86),(6,7,0.32),(7,3,0.59),(7,4,0.6),(8,1,0.94),(8,2,0.8),(8,10,0.57),(9,1,0.81),(9,2,0.93),(9,8,0.78),(9,10,0.58),(10,1,0.59),(10,2,0.6),(11,1,0.97),(11,2,0.85),(11,8,0.94),(11,9,0.84),(11,10,0.62),(11,12,0.69),(12,1,0.7),(12,2,0.72),(12,8,0.72),(12,9,0.69),(12,10,0.52),(13,1,0.96),(13,2,0.85),(13,8,0.94),(13,9,0.85),(13,10,0.61),(13,11,0.99),(13,12,0.7),(14,1,0.83),(14,2,0.97),(14,8,0.8),(14,9,0.96),(14,10,0.59),(14,11,0.85),(14,12,0.71),(14,13,0.86),(15,1,0.98),(15,2,0.86),(15,8,0.94),(15,9,0.83),(15,10,0.62),(15,11,0.97),(15,12,0.72),(15,13,0.97),(15,14,0.86),(16,1,0.74),(16,2,0.77),(16,8,0.76),(16,9,0.73),(16,10,0.54),(16,11,0.74),(16,12,0.99),(16,13,0.74),(16,14,0.75),(16,15,0.76),(209,1,0.5),(209,2,0.5),(209,8,0.5),(209,9,0.5),(209,10,0.5),(209,11,0.5),(209,12,0.5),(209,13,0.5),(209,14,0.5),(209,15,0.5),(209,16,0.5),(542,541,0.98);
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
  `recruitStop` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `task_info_user_info__fk` (`userId`),
  CONSTRAINT `task_info_user_info__fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=294 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_info`
--

LOCK TABLES `task_info` WRITE;
/*!40000 ALTER TABLE `task_info` DISABLE KEYS */;
INSERT INTO `task_info` VALUES (4,2,0,'苹果购物APP的测试','这是一款关于购物的app，购买时支付速度、快速下拉购物车、购买流程体验等，这些是我们最想看到的点','2022-04-01 16:00:00','2022-07-01 16:00:00',10,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/4dEpnbGfSN.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/B5FpWriiDk.pdf','2022-04-02 07:28:13',1),(7,2,0,'关于一款养身APP的测试','这是一款关于教用户如何养生的APP，希望用户着重于养生方法推荐、养生秘诀、养生知识等板块进行测试。','2022-04-01 16:00:00','2022-07-01 16:00:00',10,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/BHyJx82sZ7.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/b8wT8DPZBT.png','2022-04-02 07:34:20',0),(8,2,0,'关于购物APP“得物”的测试，要求苹果手机','这是一款关于购物的app，请工人测试时格外关注：购买时支付速度、快速下拉购物车、购买流程体验','2022-04-01 16:00:00','2022-07-01 16:00:00',10,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/JJe64TyYaK.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/zKazFyzXRX.pdf','2022-04-02 07:35:45',0),(9,2,1,'关于单机电脑游戏的测试','希望玩家认真玩完全部流程，并且多在游戏中四处走动，与物体交互','2022-04-01 16:00:00','2022-07-01 16:00:00',10,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/3kaz5TNGtj.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/4Pk68Wh22S.pdf','2022-04-02 07:37:26',0),(10,2,1,'关于独立游戏“篝火”的测试，要求电脑','希望玩家多用连招，与物体交互，记得在所有游戏流程都如此干     ','2022-04-01 16:00:00','2022-07-01 16:00:00',10,'https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/ezWwxfAhFs.zip','https://seiiicollect.oss-cn-beijing.aliyuncs.com/test/ZdXxfeb6Gh.pdf','2022-04-02 07:38:09',1);
/*!40000 ALTER TABLE `task_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_recruit_stop_recommend_factor`
--

DROP TABLE IF EXISTS `task_recruit_stop_recommend_factor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_recruit_stop_recommend_factor` (
  `taskId` int(11) NOT NULL,
  `abilityActual` double DEFAULT NULL,
  `abilityExpected` double DEFAULT NULL,
  `activenessActual` double DEFAULT NULL,
  `activenessExpected` double DEFAULT NULL,
  `relevanceActual` double DEFAULT NULL,
  `relevanceExpected` double DEFAULT NULL,
  `diversityActual` double DEFAULT NULL,
  `diversityExpected` double DEFAULT NULL,
  `avgTargetActual` double DEFAULT NULL,
  `avgTargetExpected` double DEFAULT NULL,
  PRIMARY KEY (`taskId`),
  CONSTRAINT `task_recruit_stop_recommend_factor_task_info_id_fk` FOREIGN KEY (`taskId`) REFERENCES `task_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_recruit_stop_recommend_factor`
--

LOCK TABLES `task_recruit_stop_recommend_factor` WRITE;
/*!40000 ALTER TABLE `task_recruit_stop_recommend_factor` DISABLE KEYS */;
INSERT INTO `task_recruit_stop_recommend_factor` VALUES (7,0,-0.562465268982821,0,-0.4499999999999999,0,-0.009961908529684045,0,0,0,-0.13591400693107242),(8,-1.124930537965642,-1.241730537965642,-0.15,-0.15,-0.13743876366633456,-0.11780465457114392,-0.16666666666666666,-0.15714285714285714,-0.2986643365168715,-0.3133356099359286),(9,-0.5279916108452951,-1.241730537965642,-0.13499999999999998,-0.15,-0.09611111111111109,-0.0828045367203609,-0.2696969696969697,-0.15714285714285714,-0.1971885097592466,-0.306335586365772),(10,-0.5279916108452951,-1.241730537965642,-0.13499999999999998,-0.15,-0.09544444444444444,-0.08165395206987669,-0.2696969696969697,-0.15714285714285714,-0.19705517642591328,-0.3061054694356751);
/*!40000 ALTER TABLE `task_recruit_stop_recommend_factor` ENABLE KEYS */;
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
INSERT INTO `task_tag` VALUES (4,0),(4,1),(7,2),(8,0),(8,1),(9,0),(9,2),(10,1),(10,2);
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
INSERT INTO `task_user` VALUES (8,3),(9,3),(10,3),(8,5),(9,5),(10,5),(8,7),(8,8),(4,9),(8,9),(8,81),(9,81),(10,81);
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
) ENGINE=InnoDB AUTO_INCREMENT=359 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('15161768556',2,1,'admin123','admin','591354379@qq.com'),('17790538663',0,2,'00000000','employer','employer@163.com'),('15028390642',1,3,'00000000','worker','worker@163.com'),('17551158079',1,5,'00000000','workerTest1','test1@worker.com'),('17551158095',1,7,'00000000','workerTest2','test2@worker.com'),('17551158093',1,8,'00000000','workerTest3','test3@worker.com'),('17551158764',1,9,'00000000','workerTest4','test4@worker.com'),('66665555666',1,13,'00000000','workerTest5','5555@2222.3333'),('00000000000',1,81,'00000000','worker1','00000000@11.11');
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
INSERT INTO `user_tag` VALUES (3,1),(3,2),(5,1),(5,2),(7,1),(7,2),(8,1),(8,2),(9,1),(9,2),(81,4);
/*!40000 ALTER TABLE `user_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worker_ability`
--

DROP TABLE IF EXISTS `worker_ability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `worker_ability` (
  `userId` int(11) NOT NULL,
  `collabVal` double DEFAULT NULL,
  `reviewVal` double DEFAULT NULL,
  `creatVal` double DEFAULT NULL,
  `detVal` double DEFAULT NULL,
  `expVal` double DEFAULT NULL,
  `comprehensiveVal` double DEFAULT NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT `worker_ability_user_info_id_fk` FOREIGN KEY (`userId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worker_ability`
--

LOCK TABLES `worker_ability` WRITE;
/*!40000 ALTER TABLE `worker_ability` DISABLE KEYS */;
INSERT INTO `worker_ability` VALUES (3,0.295,0.12,0.5202063397129186,0.7933333333333334,0.5,0.4457079346092504),(5,0.265,0.3,0.5080221291866028,0.7233333333333334,0.5,0.45927109250398723),(7,0.37,0.8400000000000001,0.3956758373205742,0.5266666666666667,0.5,0.5264685007974482),(8,0.5449999999999999,0.54,0.18068181818181817,0.5266666666666667,0.5,0.45846969696969697),(9,0.5449999999999999,0.6734849941080032,0.27522727272727276,0.5433333333333334,0.5,0.5074091200337218),(13,0.3,0.3,0.24,0.12,0.5,0.292),(81,0.195,0.3,0.675,0.405,0.5,0.41500000000000004);
/*!40000 ALTER TABLE `worker_ability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worker_context`
--

DROP TABLE IF EXISTS `worker_context`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `worker_context` (
  `workerId` int(11) NOT NULL,
  `deviceType` varchar(255) DEFAULT NULL,
  `osInfo` varchar(255) DEFAULT NULL,
  `ramSize` varchar(255) DEFAULT NULL,
  `netEnv` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`workerId`),
  CONSTRAINT `worker_context_user_info_id_fk` FOREIGN KEY (`workerId`) REFERENCES `user_info` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worker_context`
--

LOCK TABLES `worker_context` WRITE;
/*!40000 ALTER TABLE `worker_context` DISABLE KEYS */;
INSERT INTO `worker_context` VALUES (3,'电脑','HarmonyOS','16G','Cellular network'),(5,'电脑','ios','16G','WLAN'),(7,'电脑','Windows','8G','WLAN'),(8,'电脑','Linux','8G','LAN'),(9,'手机','ios','4G','LAN'),(13,'平板','ipadOS','8G','Cellular network'),(81,'手机','Android','8G','LAN');
/*!40000 ALTER TABLE `worker_context` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-28 16:15:52