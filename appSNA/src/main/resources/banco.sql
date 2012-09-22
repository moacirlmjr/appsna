create database appsna;

use appsna;

create table elemento(
id bigint not null auto_increment,
nome varchar(30) not null,
screen_name varchar(30) not null,
biografia text not null,
localizacao varchar(50),
totalFollowing int not null,
totalTweets int not null, 
URL text, 
timeZone varchar(30),
linguagem char(2),
dataDeCricao timestamp,
URLImagem text,
constraint primary key(id)
);


create table relacionamento(
id_source bigint not null,
id_target bigint not null,
primary key(id_source, id_target),
constraint foreign key(id_source) references elemento(id),
constraint foreign key(id_target) references elemento(id)
);


create table status(
id bigint not null auto_increment,
id_elemento bigint not null,
dataDeCriacao timestamp,
texto text not null,
latitude bigint,
longitude bigint,
totalRetweet int,
isRetweeted bit,
primary key(id, id_elemento),
constraint foreign key(id_elemento) references elemento(id)
);

create table usermention(
id bigint not null auto_increment,
id_status bigint not null,
id_elemento bigint not null,
mention text,
primary key(id, id_status, id_elemento),
foreign key(id_status) references tweet(id),
foreign key(id_elemento) references tweet(id_elemento)
);

create table urlmention(
id bigint not null auto_increment,
id_status bigint not null,
id_elemento bigint not null,
url text,
primary key(id, id_status, id_elemento),
foreign key(id_status) references tweet(id),
foreign key(id_elemento) references tweet(id_elemento)
);


create table hashtagmention(
id bigint not null auto_increment,
id_status bigint not null,
id_elemento bigint not null,
hashtag text,
primary key(id, id_status, id_elemento),
foreign key(id_status) references tweet(id),
foreign key(id_elemento) references tweet(id_elemento)
);

--dump 

-- MySQL dump 10.13  Distrib 5.1.53, for Win32 (ia32)
--
-- Host: localhost    Database: appsna
-- ------------------------------------------------------
-- Server version	5.1.53-community

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
-- Table structure for table `elemento`
--

DROP TABLE IF EXISTS `elemento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `elemento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `screen_name` varchar(30) NOT NULL,
  `biografia` text NOT NULL,
  `localizacao` varchar(50) DEFAULT NULL,
  `totalFollowing` int(11) NOT NULL,
  `totalFollowers` int(11) NOT NULL,
  `totalTweets` int(11) NOT NULL,
  `URL` text,
  `timeZone` varchar(30) DEFAULT NULL,
  `linguagem` char(2) DEFAULT NULL,
  `dataDeCricao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `URLImagem` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elemento`
--

LOCK TABLES `elemento` WRITE;
/*!40000 ALTER TABLE `elemento` DISABLE KEYS */;
INSERT INTO `elemento` VALUES (1,'Danyllo Wagner','danyllo_wagner','sou apenas mais um rapaz latino americano','joao pessoa',300,287,670,'www.globo.com','brasilia','pt','2012-08-11 03:00:00','http://kjksdkdjksjddskj'),(2,'Moacir Lopes','moacirlmjr','o cara da compucao....','joao pessoa',345,543,899,'www.moaca.com','santiago','en','2011-01-01 03:00:00','http://hgdsdjhsghdgshjdg');
/*!40000 ALTER TABLE `elemento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtagmention`
--

DROP TABLE IF EXISTS `hashtagmention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hashtagmention` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_status` bigint(20) NOT NULL,
  `id_elemento` bigint(20) NOT NULL,
  `hashtag` text,
  PRIMARY KEY (`id`,`id_status`,`id_elemento`),
  KEY `id_status` (`id_status`),
  KEY `id_elemento` (`id_elemento`),
  CONSTRAINT `hashtagmention_ibfk_1` FOREIGN KEY (`id_status`) REFERENCES `status` (`id`),
  CONSTRAINT `hashtagmention_ibfk_2` FOREIGN KEY (`id_elemento`) REFERENCES `status` (`id_elemento`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtagmention`
--

LOCK TABLES `hashtagmention` WRITE;
/*!40000 ALTER TABLE `hashtagmention` DISABLE KEYS */;
INSERT INTO `hashtagmention` VALUES (1,1,1,'#GTSNA'),(2,2,2,'#GTSNA');
/*!40000 ALTER TABLE `hashtagmention` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relacionamento`
--

DROP TABLE IF EXISTS `relacionamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relacionamento` (
  `id_source` bigint(20) NOT NULL,
  `id_target` bigint(20) NOT NULL,
  PRIMARY KEY (`id_source`,`id_target`),
  KEY `id_target` (`id_target`),
  CONSTRAINT `relacionamento_ibfk_1` FOREIGN KEY (`id_source`) REFERENCES `elemento` (`id`),
  CONSTRAINT `relacionamento_ibfk_2` FOREIGN KEY (`id_target`) REFERENCES `elemento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relacionamento`
--

LOCK TABLES `relacionamento` WRITE;
/*!40000 ALTER TABLE `relacionamento` DISABLE KEYS */;
INSERT INTO `relacionamento` VALUES (2,1),(1,2);
/*!40000 ALTER TABLE `relacionamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_elemento` bigint(20) NOT NULL,
  `dataDeCriacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `texto` text NOT NULL,
  `latitude` bigint(20) DEFAULT NULL,
  `longitude` bigint(20) DEFAULT NULL,
  `totalRetweet` int(11) DEFAULT NULL,
  `isRetweeted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`,`id_elemento`),
  KEY `id_elemento` (`id_elemento`),
  CONSTRAINT `status_ibfk_1` FOREIGN KEY (`id_elemento`) REFERENCES `elemento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,1,'2012-09-23 03:00:00','@moacirlmjr da uma olhada em http://migre.me/aOPow  #GTSNA',NULL,NULL,0,'\0'),
(2,2,'2012-09-13 03:00:00','@Danyllo_wagner olhei  http://migre.me/aOPow  #GTSNA',NULL,NULL,0,'\0');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `urlmention`
--

DROP TABLE IF EXISTS `urlmention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urlmention` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_status` bigint(20) NOT NULL,
  `id_elemento` bigint(20) NOT NULL,
  `url` text,
  PRIMARY KEY (`id`,`id_status`,`id_elemento`),
  KEY `id_status` (`id_status`),
  KEY `id_elemento` (`id_elemento`),
  CONSTRAINT `urlmention_ibfk_1` FOREIGN KEY (`id_status`) REFERENCES `status` (`id`),
  CONSTRAINT `urlmention_ibfk_2` FOREIGN KEY (`id_elemento`) REFERENCES `status` (`id_elemento`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `urlmention`
--

LOCK TABLES `urlmention` WRITE;
/*!40000 ALTER TABLE `urlmention` DISABLE KEYS */;
INSERT INTO `urlmention` VALUES (1,1,1,'http://migre.me/aOPow'),(2,2,2,'http://migre.me/aOPow');
/*!40000 ALTER TABLE `urlmention` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usermention`
--

DROP TABLE IF EXISTS `usermention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usermention` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_status` bigint(20) NOT NULL,
  `id_elemento` bigint(20) NOT NULL,
  `mention` text,
  PRIMARY KEY (`id`,`id_status`,`id_elemento`),
  KEY `id_status` (`id_status`),
  KEY `id_elemento` (`id_elemento`),
  CONSTRAINT `usermention_ibfk_1` FOREIGN KEY (`id_status`) REFERENCES `status` (`id`),
  CONSTRAINT `usermention_ibfk_2` FOREIGN KEY (`id_elemento`) REFERENCES `status` (`id_elemento`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usermention`
--

LOCK TABLES `usermention` WRITE;
/*!40000 ALTER TABLE `usermention` DISABLE KEYS */;
INSERT INTO `usermention` VALUES (1,1,1,'@moacirlmjr'),(2,2,2,'@Danyllo_Wagner');
/*!40000 ALTER TABLE `usermention` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-09-22 18:20:12
