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
-- Table structure for table `showdetail`
--

DROP TABLE IF EXISTS `showdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `showdetail` (
  `MName` varchar(45) NOT NULL,
  `Time` varchar(45) NOT NULL,
  `Price` varchar(45) NOT NULL,
  `Room` varchar(45) NOT NULL,
  PRIMARY KEY (`MName`,`Time`,`Price`,`Room`),
  CONSTRAINT `Name` FOREIGN KEY (`MName`) REFERENCES `movies` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `showdetail`
--

LOCK TABLES `showdetail` WRITE;
/*!40000 ALTER TABLE `showdetail` DISABLE KEYS */;
INSERT INTO `showdetail` VALUES ('Ambulance','2022050109301200','45','6'),('Ambulance','2022050113301530','45','1'),('Ambulance','2022050117301930','45','2'),('Infinite Storm','2022050109301230','100','4'),('Memory','2022050109301200','70','7'),('Memory','2022050113301510','70','5'),('Morbius','2022050113301530','40','4'),('Morbius','2022050117301930','40','3'),('The BAD GUYs','2022050109301210','100','3'),('The BAD GUYs','2022050109301510','100','3'),('The Contractor','2022050109301200','75','3'),('The Contractor','2022050113301510','75','2'),('The Contractor','2022050117301930','75','2'),('The Contractor','2022050120302230','75','1'),('The Lost City','2022050109301200','50','6'),('The Lost City','2022050113301510','50','6'),('The Northman','2022050109301210','100','5'),('The Northman','2022050114301510','100','5'),('The Qutfit','2022050109301210','100','2'),('X','2022050109301200','100','1');
/*!40000 ALTER TABLE `showdetail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-01 14:37:36
