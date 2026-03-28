-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: hotelnhom11
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `chi_tiet_dat_phong`
--

DROP TABLE IF EXISTS `chi_tiet_dat_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chi_tiet_dat_phong` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gia` decimal(15,2) NOT NULL,
  `dat_phong_id` bigint NOT NULL,
  `phong_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK106jhvu0om8tde24dpmctivqt` (`dat_phong_id`),
  KEY `FKi9ik3krma24myp8c1u3qmt01d` (`phong_id`),
  CONSTRAINT `FK106jhvu0om8tde24dpmctivqt` FOREIGN KEY (`dat_phong_id`) REFERENCES `dat_phong` (`id`),
  CONSTRAINT `FKi9ik3krma24myp8c1u3qmt01d` FOREIGN KEY (`phong_id`) REFERENCES `phong` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chi_tiet_dat_phong`
--

LOCK TABLES `chi_tiet_dat_phong` WRITE;
/*!40000 ALTER TABLE `chi_tiet_dat_phong` DISABLE KEYS */;
INSERT INTO `chi_tiet_dat_phong` VALUES (1,200000.00,1,1),(2,1000000.00,2,2),(3,200000.00,3,1),(4,1000000.00,3,2);
/*!40000 ALTER TABLE `chi_tiet_dat_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dat_phong`
--

DROP TABLE IF EXISTS `dat_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dat_phong` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ngay_dat` datetime(6) NOT NULL,
  `ngay_nhan` date DEFAULT NULL,
  `ngay_tra` date DEFAULT NULL,
  `trang_thai` enum('CANCELLED','CHECKED_IN','CHECKED_OUT','CONFIRMED','PENDING') NOT NULL,
  `khach_hang_id` bigint NOT NULL,
  `nhan_vien_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn1i57k678mn5ysnrgeki728j7` (`khach_hang_id`),
  KEY `FK2wxg8ahyi6c4bot411b33glq3` (`nhan_vien_id`),
  CONSTRAINT `FK2wxg8ahyi6c4bot411b33glq3` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`),
  CONSTRAINT `FKn1i57k678mn5ysnrgeki728j7` FOREIGN KEY (`khach_hang_id`) REFERENCES `khach_hang` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dat_phong`
--

LOCK TABLES `dat_phong` WRITE;
/*!40000 ALTER TABLE `dat_phong` DISABLE KEYS */;
INSERT INTO `dat_phong` VALUES (1,'2026-03-28 19:23:36.182380','2026-03-28','2026-03-29','CHECKED_OUT',1,2),(2,'2026-03-28 19:42:14.493702','2026-03-28','2026-03-30','CHECKED_OUT',2,2),(3,'2026-03-28 19:49:35.801586','2026-04-01','2026-04-04','CONFIRMED',2,2);
/*!40000 ALTER TABLE `dat_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dich_vu`
--

DROP TABLE IF EXISTS `dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dich_vu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gia` decimal(15,2) NOT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `ten` varchar(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dich_vu`
--

LOCK TABLES `dich_vu` WRITE;
/*!40000 ALTER TABLE `dich_vu` DISABLE KEYS */;
INSERT INTO `dich_vu` VALUES (1,20000.00,'https://res.cloudinary.com/dv8kytctn/image/upload/v1774700465/hotel/services/cfz4ucqaexmzxspkrn7y.jpg','Sting');
/*!40000 ALTER TABLE `dich_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don`
--

DROP TABLE IF EXISTS `hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ngay_tao` datetime(6) NOT NULL,
  `tong_tien` decimal(15,2) NOT NULL,
  `dat_phong_id` bigint NOT NULL,
  `nhan_vien_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqdvtwe0vcro8wvmfeeiu975ry` (`dat_phong_id`),
  KEY `FKf3pkyuwrjwl5ru53n1r6fieih` (`nhan_vien_id`),
  CONSTRAINT `FKag38lf78ig70mjh21omkhxleq` FOREIGN KEY (`dat_phong_id`) REFERENCES `dat_phong` (`id`),
  CONSTRAINT `FKf3pkyuwrjwl5ru53n1r6fieih` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don`
--

LOCK TABLES `hoa_don` WRITE;
/*!40000 ALTER TABLE `hoa_don` DISABLE KEYS */;
INSERT INTO `hoa_don` VALUES (1,'2026-03-28 19:24:07.429334',100000.00,1,2),(2,'2026-03-28 19:42:45.745011',1470000.00,2,2);
/*!40000 ALTER TABLE `hoa_don` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoa_don_khuyen_mai`
--

DROP TABLE IF EXISTS `hoa_don_khuyen_mai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoa_don_khuyen_mai` (
  `hoa_don_id` bigint NOT NULL,
  `khuyen_mai_id` bigint NOT NULL,
  PRIMARY KEY (`hoa_don_id`,`khuyen_mai_id`),
  KEY `FK9wkuxnjo0mo95pmlvdkpf49my` (`khuyen_mai_id`),
  CONSTRAINT `FK9wkuxnjo0mo95pmlvdkpf49my` FOREIGN KEY (`khuyen_mai_id`) REFERENCES `khuyen_mai` (`id`),
  CONSTRAINT `FKetc71me62lel7550aq8mjgqq1` FOREIGN KEY (`hoa_don_id`) REFERENCES `hoa_don` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoa_don_khuyen_mai`
--

LOCK TABLES `hoa_don_khuyen_mai` WRITE;
/*!40000 ALTER TABLE `hoa_don_khuyen_mai` DISABLE KEYS */;
INSERT INTO `hoa_don_khuyen_mai` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `hoa_don_khuyen_mai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khach_hang`
--

DROP TABLE IF EXISTS `khach_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khach_hang` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(120) DEFAULT NULL,
  `sdt` varchar(20) NOT NULL,
  `ten` varchar(120) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6gn74xxiy11yxkbb2xmldnlld` (`sdt`),
  UNIQUE KEY `UKj3lhg8opnqln2wcb41cp14xn9` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khach_hang`
--

LOCK TABLES `khach_hang` WRITE;
/*!40000 ALTER TABLE `khach_hang` DISABLE KEYS */;
INSERT INTO `khach_hang` VALUES (1,'nguyenhuynh.663459@gmail.com','0862663459','HDN'),(2,'23110270@student.hcmute.edu.vn','0862663452','Huỳnh Duy Nguyễn');
/*!40000 ALTER TABLE `khach_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khuyen_mai`
--

DROP TABLE IF EXISTS `khuyen_mai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khuyen_mai` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gia_tri` decimal(15,2) NOT NULL,
  `loai_giam` enum('AMOUNT','PERCENT') NOT NULL,
  `ten` varchar(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyen_mai`
--

LOCK TABLES `khuyen_mai` WRITE;
/*!40000 ALTER TABLE `khuyen_mai` DISABLE KEYS */;
INSERT INTO `khuyen_mai` VALUES (1,100000.00,'AMOUNT','Khai Trương'),(2,30.00,'PERCENT','Khách Hàng Vip Lần đầu');
/*!40000 ALTER TABLE `khuyen_mai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai_phong`
--

DROP TABLE IF EXISTS `loai_phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_phong` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gia_co_ban` decimal(15,2) NOT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `mo_ta` varchar(1000) DEFAULT NULL,
  `ten_loai` varchar(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai_phong`
--

LOCK TABLES `loai_phong` WRITE;
/*!40000 ALTER TABLE `loai_phong` DISABLE KEYS */;
INSERT INTO `loai_phong` VALUES (2,200000.00,'https://res.cloudinary.com/dv8kytctn/image/upload/v1774700337/hotel/room-types/yng4ol4l7f3fcmd8vxad.jpg','Phòng thường chỉ có giường nhà vệ sinh','Phòng thường'),(3,1000000.00,'https://res.cloudinary.com/dv8kytctn/image/upload/v1774701487/hotel/room-types/qhmxsdefczumiejilrpc.jpg','Giành cho khách hàng có nhu cầu nhiều','Phòng Vip');
/*!40000 ALTER TABLE `loai_phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhan_vien`
--

DROP TABLE IF EXISTS `nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhan_vien` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role` enum('ADMIN','MANAGER','RECEPTIONIST') NOT NULL,
  `ten` varchar(120) NOT NULL,
  `tai_khoan_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKq7taur03uqn1dnggpmw1say0m` (`tai_khoan_id`),
  CONSTRAINT `FKqlh4dmq36aycnyxysoqst8kcj` FOREIGN KEY (`tai_khoan_id`) REFERENCES `tai_khoan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhan_vien`
--

LOCK TABLES `nhan_vien` WRITE;
/*!40000 ALTER TABLE `nhan_vien` DISABLE KEYS */;
INSERT INTO `nhan_vien` VALUES (1,'ADMIN','Admin System',1),(2,'RECEPTIONIST','Le Tan Mac Dinh',2),(3,'MANAGER','Quan Ly Mac Dinh',3);
/*!40000 ALTER TABLE `nhan_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phong`
--

DROP TABLE IF EXISTS `phong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phong` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(500) DEFAULT NULL,
  `so_phong` varchar(30) NOT NULL,
  `trang_thai` enum('AVAILABLE','CLEANING','MAINTENANCE','OCCUPIED') NOT NULL,
  `loai_phong_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9q3rwjmcotm2u54nh10iqi6yk` (`so_phong`),
  KEY `FKif7j57iq0f8j81krqbgan5ifd` (`loai_phong_id`),
  CONSTRAINT `FKif7j57iq0f8j81krqbgan5ifd` FOREIGN KEY (`loai_phong_id`) REFERENCES `loai_phong` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phong`
--

LOCK TABLES `phong` WRITE;
/*!40000 ALTER TABLE `phong` DISABLE KEYS */;
INSERT INTO `phong` VALUES (1,'https://res.cloudinary.com/dv8kytctn/image/upload/v1774700385/hotel/rooms/vcaw6pgfpvwtulzlhi7u.jpg','101','AVAILABLE',2),(2,'https://res.cloudinary.com/dv8kytctn/image/upload/v1774701544/hotel/rooms/bf7x8ffhcggojmayegbg.jpg','201','AVAILABLE',3);
/*!40000 ALTER TABLE `phong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `su_dung_dich_vu`
--

DROP TABLE IF EXISTS `su_dung_dich_vu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `su_dung_dich_vu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `so_luong` int NOT NULL,
  `thoi_diem` datetime(6) NOT NULL,
  `dat_phong_id` bigint NOT NULL,
  `dich_vu_id` bigint NOT NULL,
  `phong_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqb35tfi2hwcde6nebfvloh8g8` (`dat_phong_id`),
  KEY `FK9ykdw1mohe4h9rvxx6255pg5k` (`dich_vu_id`),
  KEY `FKgk6rmc9tmkjmvdgi2nr5ol7is` (`phong_id`),
  CONSTRAINT `FK9ykdw1mohe4h9rvxx6255pg5k` FOREIGN KEY (`dich_vu_id`) REFERENCES `dich_vu` (`id`),
  CONSTRAINT `FKgk6rmc9tmkjmvdgi2nr5ol7is` FOREIGN KEY (`phong_id`) REFERENCES `phong` (`id`),
  CONSTRAINT `FKqb35tfi2hwcde6nebfvloh8g8` FOREIGN KEY (`dat_phong_id`) REFERENCES `dat_phong` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `su_dung_dich_vu`
--

LOCK TABLES `su_dung_dich_vu` WRITE;
/*!40000 ALTER TABLE `su_dung_dich_vu` DISABLE KEYS */;
INSERT INTO `su_dung_dich_vu` VALUES (1,4,'2026-03-28 19:42:53.367780',2,1,2),(2,1,'2026-03-28 19:43:09.693832',2,1,2);
/*!40000 ALTER TABLE `su_dung_dich_vu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tai_khoan`
--

DROP TABLE IF EXISTS `tai_khoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tai_khoan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(120) NOT NULL,
  `password` varchar(255) NOT NULL,
  `trang_thai` enum('ACTIVE','INACTIVE','LOCKED') NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKd0golrlr34gkql6so1i4gbuw5` (`email`),
  UNIQUE KEY `UKbbnyfsdsmk05phi1q5tus8mff` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tai_khoan`
--

LOCK TABLES `tai_khoan` WRITE;
/*!40000 ALTER TABLE `tai_khoan` DISABLE KEYS */;
INSERT INTO `tai_khoan` VALUES (1,'admin@hotel.local','123456','ACTIVE','admin'),(2,'receptionist@hotel.local','123456','ACTIVE','receptionist'),(3,'manager@hotel.local','123456','ACTIVE','manager');
/*!40000 ALTER TABLE `tai_khoan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thanh_toan`
--

DROP TABLE IF EXISTS `thanh_toan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thanh_toan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ngay_thanh_toan` datetime(6) NOT NULL,
  `phuong_thuc` enum('CARD','CASH','E_WALLET','TRANSFER') NOT NULL,
  `so_tien` decimal(15,2) NOT NULL,
  `hoa_don_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbyudoen2dblfb1j2vgcq2cxxd` (`hoa_don_id`),
  CONSTRAINT `FKbyudoen2dblfb1j2vgcq2cxxd` FOREIGN KEY (`hoa_don_id`) REFERENCES `hoa_don` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thanh_toan`
--

LOCK TABLES `thanh_toan` WRITE;
/*!40000 ALTER TABLE `thanh_toan` DISABLE KEYS */;
INSERT INTO `thanh_toan` VALUES (1,'2026-03-28 19:25:13.620985','CASH',100000.00,1),(2,'2026-03-28 19:43:38.494990','CASH',1470000.00,2);
/*!40000 ALTER TABLE `thanh_toan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-28 19:57:27
