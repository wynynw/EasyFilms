-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: androiddb
-- ------------------------------------------------------
-- Server version	5.6.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `ID` varchar(45) NOT NULL,
  `Date` varchar(45) NOT NULL,
  `Price` varchar(45) NOT NULL,
  `Account` varchar(45) NOT NULL,
  `MovieTime` varchar(45) NOT NULL,
  `MName` varchar(45) NOT NULL,
  `Seat` varchar(45) NOT NULL,
  `RoomX` int(11) NOT NULL,
  `RoomY` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Account_idx` (`Account`),
  CONSTRAINT `Account` FOREIGN KEY (`Account`) REFERENCES `account` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('1124069','20220501','200.0','test','09301210','The Northman','5',6,10),('1762643','20220501','100.0','test','09301210','The Qutfit','2',9,7),('2159958','20220502','200.0','zz','09301210','The Northman','5',9,8),('264879','20220501','100.0','zz','09301210','The BAD GUYs','3',9,9),('2856467','20220501','200.0','test','09301210','The Northman','5',6,9),('3309675','20220501','100.0','zz','09301210','The Qutfit','2',7,11),('3436282','20220501','100.0','zz','09301230','Infinite Storm','4',6,2),('4039116','20220501','200.0','test','09301200','X','1',8,6),('4680973','20220501','100.0','test','09301510','The BAD GUYs','3',7,9),('5783073','20220501','100.0','zz','09301210','The Qutfit','2',7,9),('5964476','20220501','100.0','test','09301230','Infinite Storm','4',7,6),('6256653','20220501','100.0','test','09301230','Infinite Storm','4',8,7),('627321','20220502','200.0','zz','09301210','The Northman','5',9,7),('7139102','20220501','100.0','test','09301210','The Northman','5',6,11),('7873413','20220502','200.0','zz','09301210','The Northman','5',9,8),('7970438','20220502','200.0','zz','09301210','The Northman','5',9,8),('92298','20220501','200.0','test','09301200','X','1',8,7);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-01 14:37:25
