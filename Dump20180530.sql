-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: proiect_ratb
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.31-MariaDB

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
-- Table structure for table `abonamente`
--

DROP TABLE IF EXISTS `abonamente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `abonamente` (
  `ID_card` int(11) NOT NULL,
  `Tip` int(11) NOT NULL,
  `data_inceput` date DEFAULT NULL,
  `linia` int(11) DEFAULT NULL,
  KEY `ID_card` (`ID_card`),
  KEY `linia` (`linia`),
  CONSTRAINT `abonamente_ibfk_1` FOREIGN KEY (`ID_card`) REFERENCES `carduri` (`ID`),
  CONSTRAINT `abonamente_ibfk_2` FOREIGN KEY (`linia`) REFERENCES `linii` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abonamente`
--

LOCK TABLES `abonamente` WRITE;
/*!40000 ALTER TABLE `abonamente` DISABLE KEYS */;
INSERT INTO `abonamente` VALUES (1,0,'2018-05-16',NULL),(2,1,'2018-05-16',601),(3,1,'2018-05-16',NULL),(1,1,'2018-05-16',1),(1,0,'2018-05-16',NULL),(1,0,'2018-05-26',NULL),(8,1,'2018-05-26',NULL),(8,1,'2018-05-26',NULL),(10,1,'2018-05-27',11),(13,1,'2018-05-30',601);
/*!40000 ALTER TABLE `abonamente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carduri`
--

DROP TABLE IF EXISTS `carduri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carduri` (
  `ID` int(11) NOT NULL,
  `Nume` varchar(20) NOT NULL,
  `Prenume` varchar(20) NOT NULL,
  `Bani` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carduri`
--

LOCK TABLES `carduri` WRITE;
/*!40000 ALTER TABLE `carduri` DISABLE KEYS */;
INSERT INTO `carduri` VALUES (1,'Popescu','Ion',27),(2,'Ceban','Ana',0),(3,'Hitler','Adolf',0),(4,'Putin','Vladimir',0),(5,'Trump','Donald',0),(6,'Gutu','Dan',0),(8,'Al','Capone',100),(10,'Green','John',0),(11,'Blue','John',0),(13,'Popescu','Ion',0),(123,'B','B',0),(12345,'A','B',0);
/*!40000 ALTER TABLE `carduri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `linii`
--

DROP TABLE IF EXISTS `linii`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `linii` (
  `ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `linii`
--

LOCK TABLES `linii` WRITE;
/*!40000 ALTER TABLE `linii` DISABLE KEYS */;
INSERT INTO `linii` VALUES (1),(11),(336),(601);
/*!40000 ALTER TABLE `linii` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `validari`
--

DROP TABLE IF EXISTS `validari`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `validari` (
  `ID_card` int(11) NOT NULL,
  `linia` int(11) NOT NULL,
  `data` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `validari`
--

LOCK TABLES `validari` WRITE;
/*!40000 ALTER TABLE `validari` DISABLE KEYS */;
INSERT INTO `validari` VALUES (1,601,'2018-05-26'),(12345,1,'2018-05-30'),(123,601,'2018-05-30');
/*!40000 ALTER TABLE `validari` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-30 10:05:34
