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
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `Name` varchar(45) NOT NULL,
  `Poster` varchar(500) DEFAULT NULL,
  `TimeOn` varchar(45) DEFAULT NULL,
  `Director` varchar(45) DEFAULT NULL,
  `Theater` varchar(50) DEFAULT NULL,
  `Simple` varchar(45) DEFAULT NULL,
  `Detail` varchar(1024) DEFAULT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `location` varchar(20) NOT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES ('Ambulance ','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/kuxjMVuc3VTD7p42TZpJNsSrM1V.jpg','20220408','Michael Bay','Worcester','It was supposed to be a simple heist.','Decorated veteran Will Sharp, desperate for money to cover his wife\'s medical bills, asks for help from his adoptive brother Danny. A charismatic career criminal, Danny instead offers him a score: the biggest bank heist in Los Angeles history: $32 million.','Action','42.23,-71.81'),('Infinite Storm','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/sX51ugLCPxs9lCNrKokgYv4d7UG.jpg','20220325','Małgorzata Szumowska','Chinatown',' Footprints i n the Snow Lead to an Emotional','Tells the story of Pam Beers, a mother, nurse and hiking enthusiast, who, while trekking alone up Mount Washington and stranded by a snowstorm, meets strange hikers and launches a daring rescue for them.','thriller','42.34,-71.06'),('Memory','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/zGPLpljwrlK2y7AWXVpGx0ceIyH.jpg','20220429','Martin Campbell','Portland','His mind is fading. His conscience is clear.','Alex, an assassin-for-hire, finds that he\'s become a target after he refuses to complete a job for a dangerous criminal organization. With the crime syndicate and FBI in hot pursuit, Alex has the skills to stay ahead, except for one thing: he is struggling with severe memory loss, affecting his every move. Alex must question his every action and whom he can ultimately trust.','Mystery','43.00,-70.23'),('Morbius','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/6nhwr1LCozBiIN47b8oBEomOADm.jpg','20220401','Daniel Espinosa','Providence','A new Marvel legend arrives.','Dangerously ill with a rare blood disorder, and determined to save others suffering his same fate, Dr. Michael Morbius attempts a desperate gamble. What at first appears to be a radical success soon reveals itself to be a remedy potentially worse than the disease.','Action','41.80,-71.40'),('The BAD GUYs','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1s0em1CVrM1e6fsafiNePXqh6Hv.jpg','20220422','Pierre Perifel','Newton','Good is no fun at all.','Created by Aaron Blabey in black and white, the comic revolves around four \"bad guys\": Mr. Wolf, Mr. Shark, Mr. Snake, and Mr. Squid, who are tired of hearing people scream and run away from them and decide to be \"good,\" even though Their nature is \"bad\". The first comic book in the series was published in 2016 and sold millions of copies worldwide.','comedy','42.33,-71.20'),('The Contractor','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/rJPGPZ5soaG27MK90oKpioSiJE2.jpg','20220401','Tarik Saleh','Worcester','The mission is not what it seems.','After being involuntarily discharged from the U.S. Special Forces, James Harper decides to support his family by joining a private contracting organization alongside his best friend and under the command of a fellow veteran. Overseas on a covert mission, Harper must evade those trying to kill him while making his way back home.','Action','42.23,-71.81'),('The Lost City ','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/neMZH82Stu91d3iqvLdNQfqPPyl.jpg','20220325','Adam Nee','Manchester','The adventure is real. The heroes are not.','Follows a reclusive romance novelist who was sure nothing could be worse than getting stuck on a book tour with her cover model, until a kidnapping attempt sweeps them both into a cutthroat jungle adventure, proving life can be so much stranger, and more romantic, than any of her paperback fictions.','Action','43.00,-71.43'),('The Northman','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/zhLKlUaF1SEpO58ppHIAyENkwgw.jpg','20220422','Robert Eggers','Malden','Conquer your fate.','In 10th century Iceland, a prince avenges the death of his father.','adventure','42.42,-71.06'),('The Qutfit','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mBUoNT1nJ2dK53PXRSUOyoPez8S.jpg','20220318','Graham Moore','Chinatown','Everyone has something up their sleeve.','The story follows British tailor Leonard (Mark Rylance), who once made suits in world-famous London\'s Savile Row, but after a personal tragedy, he moves to Chicago to run a small tailor store in a modest part of town, making beautiful clothes for the only people around who can afford them: an evil mob family.','thriller','42.34,-71.06'),('X','https://www.themoviedb.org/t/p/w600_and_h900_bestv2/woTQx9Q4b8aO13jR9dsj8C9JESy.jpg','20220318','Ti West','Quincy','Dying to show you a good time.','In 1979, a group of young filmmakers set out to make an adult film in rural Texas, but encountered a bizarre elderly couple ......','horror','42.24,-70.99');
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-01 14:37:42
