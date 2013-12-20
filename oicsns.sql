CREATE DATABASE  IF NOT EXISTS `oicsns` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `oicsns`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: oicsns
-- ------------------------------------------------------
-- Server version	5.5.32-0ubuntu0.12.10.1-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `abuselog`
--

DROP TABLE IF EXISTS `abuselog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `abuselog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentnumber` char(5) NOT NULL,
  `username` char(24) NOT NULL,
  `time` datetime NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abuselog`
--

LOCK TABLES `abuselog` WRITE;
/*!40000 ALTER TABLE `abuselog` DISABLE KEYS */;
/*!40000 ALTER TABLE `abuselog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avatar`
--

DROP TABLE IF EXISTS `avatar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avatar` (
  `avatarid` int(11) NOT NULL AUTO_INCREMENT,
  `imagepath` varchar(50) NOT NULL,
  PRIMARY KEY (`avatarid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avatar`
--

LOCK TABLES `avatar` WRITE;
/*!40000 ALTER TABLE `avatar` DISABLE KEYS */;
INSERT INTO `avatar` VALUES (1,'/man1.png');
/*!40000 ALTER TABLE `avatar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buglog`
--

DROP TABLE IF EXISTS `buglog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buglog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stunum` char(5) NOT NULL,
  `username` varchar(24) NOT NULL,
  `time` datetime NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buglog`
--

LOCK TABLES `buglog` WRITE;
/*!40000 ALTER TABLE `buglog` DISABLE KEYS */;
/*!40000 ALTER TABLE `buglog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatlog`
--

DROP TABLE IF EXISTS `chatlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatlog` (
  `userid` int(11) NOT NULL,
  `content` varchar(60) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`userid`),
  KEY `fk_chatlog_user1_idx` (`userid`),
  CONSTRAINT `fk_chatlog_user1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatlog`
--

LOCK TABLES `chatlog` WRITE;
/*!40000 ALTER TABLE `chatlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `chatlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inquirylog`
--

DROP TABLE IF EXISTS `inquirylog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inquirylog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentnumber` char(5) NOT NULL,
  `username` varchar(24) NOT NULL,
  `time` datetime NOT NULL,
  `category` enum('request','question','other') NOT NULL,
  `email` varchar(50) NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inquirylog`
--

LOCK TABLES `inquirylog` WRITE;
/*!40000 ALTER TABLE `inquirylog` DISABLE KEYS */;
/*!40000 ALTER TABLE `inquirylog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `setting` (
  `userid` int(11) NOT NULL,
  `privategrade` enum('private','public') NOT NULL,
  `privatesex` enum('private','public') NOT NULL,
  `privatebirth` enum('private','public') NOT NULL,
  PRIMARY KEY (`userid`),
  KEY `fk_setting_user1_idx` (`userid`),
  CONSTRAINT `fk_setting_user1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `accesstoken` varchar(100) NOT NULL,
  `accesstokensecret` varchar(100) NOT NULL,
  `studentnumber` char(5) NOT NULL,
  `avatarid` int(11) NOT NULL,
  `name` char(24) NOT NULL,
  `grade` enum('1','2','3','4') DEFAULT NULL,
  `sex` enum('man','woman','otokonoko','other') DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `comment` varchar(280) DEFAULT NULL,
  PRIMARY KEY (`userid`,`avatarid`),
  KEY `fk_user_avatar1_idx` (`avatarid`),
  CONSTRAINT `fk_user_avatar1` FOREIGN KEY (`avatarid`) REFERENCES `avatar` (`avatarid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (4,'tera09','tera09','b2020',1,'まにゅ','2','man','1933-09-11','ろりこん'),(5,'tera099','tera099','b2021',1,'まぬｎ','2','otokonoko','1993-09-11','おにいぽん'),(8,'test','test','z9999',1,'testtt',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-12-17 19:44:19
