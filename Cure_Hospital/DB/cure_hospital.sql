-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: cure_hospital
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `admin_id` int NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `designation` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `admin_id_UNIQUE` (`admin_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `password_UNIQUE` (`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (101,'Roman','Islam','Admin','Barishal','01949348018','roman','roman');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diagonosis`
--

DROP TABLE IF EXISTS `diagonosis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagonosis` (
  `diago_ID` int NOT NULL AUTO_INCREMENT,
  `symptomps` varchar(200) DEFAULT NULL,
  `diagonosis` varchar(200) DEFAULT NULL,
  `doctor` varchar(200) DEFAULT NULL,
  `medical_word` varchar(45) DEFAULT NULL,
  `patient_ID` int DEFAULT NULL,
  PRIMARY KEY (`diago_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagonosis`
--

LOCK TABLES `diagonosis` WRITE;
/*!40000 ALTER TABLE `diagonosis` DISABLE KEYS */;
INSERT INTO `diagonosis` VALUES (1,'High Tempareture','CBC','Dr. Abdullah Al Mamun','Single',1001),(2,'Kapuni','CBC','Napa Extra, Tenil','Double',1002),(3,'Dairiya','DC','Dr. Abdullah Al Mamun (Medicine)','General',NULL),(4,'dfssgdfsg','PCV','Dr. Abdullah Al Mamun (Medicine)','Single',NULL),(5,'jfgkj','TC','Dr. Abdullah Al Mamun (Medicine)','General',1019);
/*!40000 ALTER TABLE `diagonosis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fees`
--

DROP TABLE IF EXISTS `fees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fees` (
  `fee_ID` int NOT NULL AUTO_INCREMENT,
  `admit_fee` double DEFAULT NULL,
  `word_fee` double DEFAULT NULL,
  `doctor_fee` double DEFAULT NULL,
  `test_fee` double DEFAULT NULL,
  `medicine_fee` double DEFAULT NULL,
  `transport_fee` double DEFAULT NULL,
  `service_fee` double DEFAULT NULL,
  `total_payable` double DEFAULT NULL,
  `patient_ID` int DEFAULT NULL,
  PRIMARY KEY (`fee_ID`),
  KEY `patient_ID_idx` (`patient_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fees`
--

LOCK TABLES `fees` WRITE;
/*!40000 ALTER TABLE `fees` DISABLE KEYS */;
INSERT INTO `fees` VALUES (101,200,500,700,250,0,250,0,1100,1001),(102,300,600,600,1250,500,500,150,2500,1002),(105,200,700,700,710,265,350,125,NULL,NULL),(106,565,555,454,530,457,454,4544,NULL,1019);
/*!40000 ALTER TABLE `fees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_ID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `age` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `blood_group` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `major_disease` varchar(200) DEFAULT NULL,
  `admit_date` varchar(45) DEFAULT NULL,
  `admit_time` varchar(45) DEFAULT NULL,
  `diago_ID` int DEFAULT NULL,
  `fee_ID` int DEFAULT NULL,
  PRIMARY KEY (`patient_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1024 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1001,'Roman Islam','01949348018','26','Male','A+','Mohammadpur','Fever','2022-09-22','00:04:59',1,101),(1002,'Mustafiz','02154552445','26','Female','B-','Barishal','Backpain','2022-09-22','00:06:04',2,102),(1003,'Tamim Iqbal','03216549872','37','Male','O+','Chittagong','Vain Problem','2022-09-22','00:08:04',NULL,NULL),(1004,'Nasir Hossain','07894521632','26','Male','AB-','Satkhira','Hormonal Disease','2022-09-22','00:09:21',NULL,NULL),(1005,'Taskin','06542318793','28','Male','A+','Dhaka','Masculear Pain','2022-09-22','00:10:43',NULL,NULL),(1006,'Jamal Bhuiyan','05482364712','28','Male','A+','Finland','Ganglion in left leg','2022-09-22','00:12:15',NULL,NULL),(1007,'Jahanara','06321578498','25','Female','A-','Nowakhali','Hair problem','2022-09-22','00:13:29',NULL,NULL),(1008,'Mark Zukarbarg','54821546325','35','Male','A+','Gorgeia','Fever','2022-09-22','02:39:26',NULL,NULL),(1012,'Asraful','01255454554','38','Others','O+','Dhaka','Musqular problem','27/00/2022','12:08:55 AM',NULL,NULL),(1013,'Imrul Kayes','01545852456','37','Male','O+','sdasdd','asdasd','27/09/2022','12:10:38 AM',NULL,NULL),(1014,'Jahidul Shikder','019465552526','25','Male','N/A','Gazipur													','Fever','30/09/2022','10:19:07 AM',NULL,NULL),(1015,'Roman Islam','01949348018','25','Male','A+','Barishal	','Fever','30/09/2022','11:54:35 PM',NULL,NULL),(1016,'Salauddin','01554545','26','Male','A+','     Bhola','dfsgh','01/10/2022','08:06:17 PM',NULL,NULL),(1017,'Sohel Rana','01628446667','26','Male','O+','     Mohammadpur','Feverr','03/10/2022','10:49:20 PM',NULL,NULL),(1018,'Muntasir','01656254855','35','Male','AB+','Dhaka','Decentry','04/10/2022','12:37:39 AM',NULL,NULL),(1019,'fdsgafdgfsd','465476874','7578','Male','O+','     ','','04/10/2022','01:25:32 AM',NULL,NULL),(1020,'hghg','554654','45','Male','AB+','     hhgug','ghug','04/10/2022','01:50:59 AM',NULL,NULL),(1021,'asdfasdfd','59494654','25','Male','A+','     asdfasdfasfd','asdfsaddfsaddf','04/10/2022','01:56:48 AM',NULL,NULL),(1022,'rttdyhdgf','555','4575','Female','B+','     dfdsf','sddfdsf','04/10/2022','02:00:34 AM',NULL,NULL),(1023,'sdfgf','4565','56','Male','B+','     sdffds','sdfdsf','04/10/2022','02:02:59 AM',NULL,NULL);
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `test_ID` int NOT NULL AUTO_INCREMENT,
  `test_name` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`test_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=506 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (501,'HB%',180),(502,'ESR',150),(503,'PCV',180),(504,'TC',350),(505,'DC',350);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-04  2:23:18
