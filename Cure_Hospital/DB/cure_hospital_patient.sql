-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cure_hospital
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
  PRIMARY KEY (`patient_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1017 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1001,'Md. Roman Islam Ali','01949348018','26','Male','A+','Mohammadpur','Fever','2022-09-22','00:04:59'),(1002,'Mustafiz','02154552445','26','Female','B-','Barishal','Backpain','2022-09-22','00:06:04'),(1003,'Tamim Iqbal','03216549872','37','Male','O+','Chittagong','Vain Problem','2022-09-22','00:08:04'),(1004,'Nasir Hossain','07894521632','26','Male','AB-','Satkhira','Hormonal Disease','2022-09-22','00:09:21'),(1005,'Taskin','06542318793','28','Male','A+','Dhaka','Masculear Pain','2022-09-22','00:10:43'),(1006,'Jamal Bhuiyan','05482364712','28','Male','A+','Finland','Ganglion in left leg','2022-09-22','00:12:15'),(1007,'Jahanara','06321578498','25','Female','A-','Nowakhali','Hair problem','2022-09-22','00:13:29'),(1008,'Mark Zukarbarg','54821546325','35','Male','A+','Gorgeia','Fever','2022-09-22','02:39:26'),(1012,'Asraful','01255454554','38','Others','O+','Dhaka','Musqular problem','27/00/2022','12:08:55 AM'),(1013,'Imrul Kayes','01545852456','37','Male','O+','sdasdd','asdasd','27/09/2022','12:10:38 AM'),(1014,'Jahidul Shikder','019465552526','25','Male','N/A','Gazipur													','Fever','30/09/2022','10:19:07 AM'),(1015,'Roman Islam','01949348018','25','Male','A+','Barishal	','Fever','30/09/2022','11:54:35 PM'),(1016,'Salauddin','01554545','26','Male','A+','     Bhola','dfsgh','01/10/2022','08:06:17 PM');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-04  9:12:32
