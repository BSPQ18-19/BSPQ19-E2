-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: hotelmanagementdb
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `administrator` (
  `USERID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`USERID`),
  CONSTRAINT `ADMINISTRATOR_FK1` FOREIGN KEY (`USERID`) REFERENCES `user` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES ('DEFAULT');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator_createdhotels`
--

DROP TABLE IF EXISTS `administrator_createdhotels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `administrator_createdhotels` (
  `USERID_OID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `HOTELID_EID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `IDX` int(11) NOT NULL,
  PRIMARY KEY (`USERID_OID`,`IDX`),
  KEY `ADMINISTRATOR_CREATEDHOTELS_N49` (`USERID_OID`),
  KEY `ADMINISTRATOR_CREATEDHOTELS_N50` (`HOTELID_EID`),
  CONSTRAINT `ADMINISTRATOR_CREATEDHOTELS_FK1` FOREIGN KEY (`USERID_OID`) REFERENCES `administrator` (`USERID`),
  CONSTRAINT `ADMINISTRATOR_CREATEDHOTELS_FK2` FOREIGN KEY (`HOTELID_EID`) REFERENCES `hotel` (`HOTELID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator_createdhotels`
--

LOCK TABLES `administrator_createdhotels` WRITE;
/*!40000 ALTER TABLE `administrator_createdhotels` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrator_createdhotels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guest`
--

DROP TABLE IF EXISTS `guest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `guest` (
  `USERID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `ADDRESS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `PHONE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`USERID`),
  CONSTRAINT `GUEST_FK1` FOREIGN KEY (`USERID`) REFERENCES `user` (`USERID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guest`
--

LOCK TABLES `guest` WRITE;
/*!40000 ALTER TABLE `guest` DISABLE KEYS */;
INSERT INTO `guest` VALUES ('1486983201','Barakaldo','659845'),('1914358047','Bilbao','12345'),('802894300','example','5896'),('u1','a','a');
/*!40000 ALTER TABLE `guest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hotel` (
  `HOTELID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `LOCATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `SEASONENDING` timestamp NULL DEFAULT NULL,
  `SEASONSTART` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`HOTELID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES ('H01','Bilbao','Hotel1','2019-12-30 23:00:00','2019-03-31 22:00:00'),('H02','Barcelona','Hotel2','2019-09-29 22:00:00','2019-05-31 22:00:00'),('H03','Madrid','Hotel3','2019-06-19 22:00:00','2019-04-14 22:00:00'),('H04','Sevilla','Hotel4','2019-09-30 22:00:00','2019-04-30 22:00:00'),('H05','Zaragoza','Hotel5','2019-11-29 23:00:00','2019-05-13 22:00:00'),('H06','Gijon','Hotel6','2019-11-29 23:00:00','2019-04-19 22:00:00');
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reservation` (
  `RESERVATIONID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `DATE` timestamp NULL DEFAULT NULL,
  `FIRSTDAY` timestamp NULL DEFAULT NULL,
  `GUESTID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `LASTDAY` timestamp NULL DEFAULT NULL,
  `ROOMID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`RESERVATIONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `review` (
  `REVIEWID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `HOTEL_HOTELID_OID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `OPINION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `PUBLISHDATE` timestamp NULL DEFAULT NULL,
  `SCORE` int(11) NOT NULL,
  `USER_USERID_OID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`REVIEWID`),
  KEY `REVIEW_N49` (`HOTEL_HOTELID_OID`),
  KEY `REVIEW_N50` (`USER_USERID_OID`),
  CONSTRAINT `REVIEW_FK1` FOREIGN KEY (`HOTEL_HOTELID_OID`) REFERENCES `hotel` (`HOTELID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `REVIEW_FK2` FOREIGN KEY (`USER_USERID_OID`) REFERENCES `user` (`USERID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES ('1486983201-H01','H01','It was a very good hotel.',NULL,8,'1486983201'),('1486983201-H02','H02','The hotel is really bad shape.',NULL,3,'1486983201'),('1914358047-H01','H01','The hotel was great, but room where not very clean.',NULL,7,'1914358047'),('802894300-H01','H01','One of the best hotel i have ever seen',NULL,9,'802894300'),('802894300-H02','H02','Not recomended to go with children.',NULL,5,'802894300');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `room` (
  `ROOMID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `HOTEL_HOTELID_OID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `ISOCCUPIED` bit(1) NOT NULL,
  `PRICE` float NOT NULL,
  `SIZE` float NOT NULL,
  `TYPE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `LISTROOMS_INTEGER_IDX` int(11) DEFAULT NULL,
  PRIMARY KEY (`ROOMID`),
  KEY `ROOM_N49` (`HOTEL_HOTELID_OID`),
  CONSTRAINT `ROOM_FK1` FOREIGN KEY (`HOTEL_HOTELID_OID`) REFERENCES `hotel` (`HOTELID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES ('R01','H01',_binary '\0',150,250,'DOUBLE',0),('R02','H01',_binary '\0',400,450,'SUITE',1),('R03','H01',_binary '\0',400,350,'TRIPLE',2),('R05','H02',_binary '\0',150,250,'DOUBLE',0),('R06','H02',_binary '\0',150,250,'DOUBLE',1),('R07','H02',_binary '\0',150,250,'DOUBLE',2),('R08','H02',_binary '\0',100,200,'SINGLE',3);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `USERID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `EMAIL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `PASSWORD` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`USERID`),
  UNIQUE KEY `USER_U1` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1486983201','egoitz@gmail.com','egoitz','egoitz'),('1914358047','dipina@gmail.com','Diego','diego'),('802894300','example@user.com','example','example'),('DEFAULT','admin','admin','admin'),('u1','a','a','a');
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

-- Dump completed on 2019-05-22 17:00:07
