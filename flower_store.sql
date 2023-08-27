-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: flower_store
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cart_id` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `reset_password_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKax3kbn2ljb7h0c7g3s2r1wug0` (`cart_id`),
  KEY `FKn6x8pdp50os8bq5rbb792upse` (`customer_id`),
  CONSTRAINT `FKax3kbn2ljb7h0c7g3s2r1wug0` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKn6x8pdp50os8bq5rbb792upse` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (6,'2023-08-26 23:48:03.757598','minhkhoa@gmail.com','Võ Minh Khoa','$2a$10$62pp7oULYtT21mQgk21dCe7S0FQ.azaevzQXPJ9CUj.XyLifv.p6G',NULL,'minhkhoa',5,5,NULL),(7,'2023-08-26 23:48:20.841246','toiquangle@gmail.com','quangtoi','$2a$10$1ZLel0zY1J/M/p6fV7MSjOKND8cSwGZfQGqlZ5EPCwX2Vz73.zQ5m',NULL,'quangtoi',6,6,'5YWwhWyUmcNebqbAAzaNtKR3K58Kku6DwGipV2ou75sKC');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accounts_roles`
--

DROP TABLE IF EXISTS `accounts_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts_roles` (
  `account_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`account_id`,`role_id`),
  KEY `FKpwest19ib22ux5gk54esw9qve` (`role_id`),
  CONSTRAINT `FKpwest19ib22ux5gk54esw9qve` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKt44duw96d6v8xrapfo4ff2up6` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts_roles`
--

LOCK TABLES `accounts_roles` WRITE;
/*!40000 ALTER TABLE `accounts_roles` DISABLE KEYS */;
INSERT INTO `accounts_roles` VALUES (6,1),(7,1);
/*!40000 ALTER TABLE `accounts_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `total_price` double DEFAULT NULL,
  `cart_id` bigint DEFAULT NULL,
  `flower_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpcttvuq4mxppo8sxggjtn5i2c` (`cart_id`),
  KEY `FKs7t296bs4k6xnaoto1kooy71` (`flower_id`),
  CONSTRAINT `FKpcttvuq4mxppo8sxggjtn5i2c` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`),
  CONSTRAINT `FKs7t296bs4k6xnaoto1kooy71` FOREIGN KEY (`flower_id`) REFERENCES `flowers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (17,1,400000,6,15);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total_amount` int DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (5,0,0),(6,1,400000);
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (3,'Hoa khô'),(4,'Hoa trang trí tiệc'),(5,'Hoa chúc mừng'),(6,'Hoa chúc mừng tốt nghiệp'),(7,'Hoa sinh nhật'),(8,'Hoa tình yêu');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors` DISABLE KEYS */;
/*!40000 ALTER TABLE `colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'ABC, Nhà bè','2023-12-23 00:00:00.000000','toiquangle@gmail.com','Tomcruise2','Allain','0987868763'),(2,NULL,NULL,NULL,NULL,NULL,NULL),(3,NULL,NULL,NULL,NULL,NULL,NULL),(4,NULL,NULL,NULL,NULL,NULL,NULL),(5,NULL,NULL,NULL,NULL,NULL,NULL),(6,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowers`
--

DROP TABLE IF EXISTS `flowers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flowers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stock_quantity` int DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `url_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKet4ju9bpvd0jdlripp0i7600q` (`category_id`),
  CONSTRAINT `FKet4ju9bpvd0jdlripp0i7600q` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowers`
--

LOCK TABLES `flowers` WRITE;
/*!40000 ALTER TABLE `flowers` DISABLE KEYS */;
INSERT INTO `flowers` VALUES (14,'2023-08-22 17:17:57.567230','Hoa được nhập trực tiếp từ Đà Lạt','Hoa Hồng Phát',99,300000,'2023-08-22 17:17:57.567230',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699477/ec0niy5gkq3oxlenhtxz.jpg'),(15,'2023-08-22 17:19:49.557462','','Great Achievement',100,400000,'2023-08-22 17:19:49.557462',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699589/usnmw3s5evmbyiu0m3b1.jpg'),(16,'2023-08-22 17:20:06.698377','','Hưng Phát',98,400000,'2023-08-22 17:20:06.698377',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699606/kqaw1unl9jvnji0pxrvi.jpg'),(17,'2023-08-22 17:20:29.655404','','Kệ hoa chúc mừng',98,500000,'2023-08-22 17:20:29.655404',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699629/pk1vizrilttlpjpsdtun.jpg'),(18,'2023-08-22 17:20:52.822084','','Perfection',100,500000,'2023-08-22 17:20:52.822084',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699653/raaq335fy6xbsc7puwvo.jpg'),(19,'2023-08-22 17:21:10.565965','','Perfection Red',98,500000,'2023-08-22 17:21:10.565965',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699670/n69wsp4lmmxeain01wey.jpg'),(20,'2023-08-22 17:21:28.196749','','Valse mùa xuân',100,500000,'2023-08-22 17:21:28.196749',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699688/llr2h4b1qbeqi85ojneg.jpg'),(21,'2023-08-22 17:22:10.883447','','Bó hoa Congrat',100,500000,'2023-08-22 17:22:10.883447',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699731/dsylzdslvzduyyasdhk1.jpg'),(22,'2023-08-22 17:22:32.999231','','Bó hoa Tango',100,500000,'2023-08-22 17:22:32.999231',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699753/bq7avfzwo8129lif6dnl.jpg'),(23,'2023-08-22 17:22:46.727877','','Chuyện của nàng',99,500000,'2023-08-22 17:22:46.727877',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699766/jdpkgrjtcupjcvms9ndj.jpg'),(24,'2023-08-22 17:23:25.590276','','Prenium vase',100,500000,'2023-08-22 17:23:25.590276',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699805/yh0lewnft1xlo6sil89a.jpg'),(25,'2023-08-22 17:23:42.171415','','Prenium vase blue',100,500000,'2023-08-22 17:23:42.171415',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699822/gt9ajg55yfalsefk7r6g.jpg'),(26,'2023-08-22 17:23:58.346438','','Luxury vase',100,500000,'2023-08-22 17:23:58.346438',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699838/go05ctsrabs21h3ahrgw.jpg'),(27,'2023-08-22 17:24:26.798309','','Hoa Congratulation',98,500000,'2023-08-22 17:24:26.798309',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699866/rm7fspd9dkp8d47fcfih.jpg'),(28,'2023-08-22 17:24:48.432824','','Congratulation mini size',100,500000,'2023-08-22 17:24:48.432824',5,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699888/fownieso9lvr3fvgwoyb.jpg'),(29,'2023-08-22 17:25:41.652408','','Ánh dương',100,500000,'2023-08-22 17:25:41.652408',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699941/ypx25rehyzlgifdieiwo.jpg'),(30,'2023-08-22 17:25:57.952208','','Hoa chúc mừng tốt nghiệp',100,500000,'2023-08-22 17:25:57.952208',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699958/ihdvzihhhuxt9q18oobg.jpg'),(31,'2023-08-22 17:26:09.701003','','Tỏa nắng',100,500000,'2023-08-22 17:26:09.701003',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699969/tcdvkqnytibgwg09cnuc.jpg'),(32,'2023-08-22 17:26:22.641809','','Niềm vui mới',100,500000,'2023-08-22 17:26:22.641809',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692699982/ayx932ojed3fcrkrffxf.jpg'),(33,'2023-08-22 17:26:44.298964','','Tana mộc mạc',100,500000,'2023-08-22 17:26:44.298964',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700004/v304o444dolzk9gjpsyn.jpg'),(34,'2023-08-22 17:26:57.090905','','Just love',100,500000,'2023-08-22 17:26:57.090905',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700017/lx3afnep0h5vjoe5s4un.jpg'),(35,'2023-08-22 17:27:09.524526','','Hoa may mắn',100,500000,'2023-08-22 17:27:09.524526',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700029/iqqkozommvdv1msupwzz.jpg'),(36,'2023-08-22 17:27:20.501069','','Pink baby',100,500000,'2023-08-22 17:27:20.501069',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700040/fzperqscinmorupxnv12.jpg'),(37,'2023-08-22 17:27:42.708083','','Cẩm chướng hồng',100,500000,'2023-08-22 17:27:42.708083',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700062/skngn7bb9ullacbmo5az.jpg'),(38,'2023-08-22 17:27:57.595749','','Niềm tin',100,500000,'2023-08-22 17:27:57.595749',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700077/hrgzhhuhibdghglhosht.jpg'),(39,'2023-08-22 17:28:16.610701','','Hướng dương',100,500000,'2023-08-22 17:28:16.610701',6,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700096/wgg1dzyvz1u9dnm66g7p.jpg'),(40,'2023-08-22 17:28:46.237927','','Purple love',100,500000,'2023-08-22 17:28:46.237927',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700126/cm55nxsbndu65gi7teit.jpg'),(41,'2023-08-22 17:28:56.672573','','Pink lady',100,500000,'2023-08-22 17:28:56.672573',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700136/xlbco0eryk2xl8vjuiqj.jpg'),(42,'2023-08-22 17:29:14.950145','','Pink box',100,500000,'2023-08-22 17:29:14.950145',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700155/kg7khao2aoxtvqfsvkr6.jpg'),(43,'2023-08-22 17:29:33.907692','','Sound of love',100,500000,'2023-08-22 17:29:33.907692',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700174/egcw8bwginrhh8874hg0.jpg'),(44,'2023-08-22 17:29:45.127931','','Romance',100,500000,'2023-08-22 17:29:45.127931',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700184/zmfcw2oomhtlwpigbjw2.jpg'),(45,'2023-08-22 17:29:57.930889','','Hoa hồng đỏ',100,500000,'2023-08-22 17:29:57.930889',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700198/ipilb9fqaoyytstlqgdd.jpg'),(46,'2023-08-22 17:30:09.240206','','Amour',100,500000,'2023-08-22 17:30:09.240206',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700209/dabcsek1soqadi7981kw.jpg'),(47,'2023-08-22 17:30:28.302526','','Pround of you',100,500000,'2023-08-22 17:30:28.302526',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700228/syqfb3qia5cgb3yhdybq.jpg'),(48,'2023-08-22 17:30:53.260478','','Tình đầu thơ ngây',100,400000,'2023-08-22 17:30:53.260478',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700253/e0evq8tdwj0qfl0dlr0z.jpg'),(49,'2023-08-22 17:31:11.641916','','Người thương',100,400000,'2023-08-22 17:31:11.641916',7,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700271/digjtercmt9ngdup6swh.jpg'),(50,'2023-08-22 17:31:41.501004','','Gặp gỡ',100,400000,'2023-08-22 17:31:41.501004',8,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700301/b5m9blspz6paw1c5nbd8.jpg'),(51,'2023-08-22 17:32:15.911450','','Nắng hồng',150,400000,'2023-08-22 17:32:15.911450',8,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700336/wchnta8xuccku6g4rhbx.jpg'),(52,'2023-08-22 17:32:33.020715','','Bó hoa vải thiều',150,400000,'2023-08-22 17:32:33.020715',8,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700353/ls43b4kuyqnxq5bxmjrh.jpg'),(53,'2023-08-22 17:32:52.950730','','Back to love',150,300000,'2023-08-22 17:32:52.950730',8,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700373/nclzeryxgi9iscmjlxsd.jpg'),(54,'2023-08-22 17:33:12.485719','','Cloud and you',150,400000,'2023-08-22 17:33:12.485719',8,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700392/gfkkfbr1s0pp74rindrg.jpg'),(55,'2023-08-22 17:33:28.556162','','Everyday with you',150,400000,'2023-08-22 17:33:28.556162',8,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700408/jhmog8momdyd9wz01it0.jpg'),(56,'2023-08-22 17:33:57.381742','','Only red rose prenium',150,400000,'2023-08-22 17:33:57.381742',8,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700437/pqy1gmewistkjtze6ero.jpg'),(57,'2023-08-22 17:34:12.950940','','Yêu thương đong đầy',150,400000,'2023-08-22 17:34:12.950940',8,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700453/lutrymsh5o1oqhbegciw.jpg'),(58,'2023-08-22 17:34:32.603978','','Trăm năm tình yêu',150,200000,'2023-08-22 17:34:32.603978',8,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700472/li20v2lytpzacaygh8rn.jpg'),(59,'2023-08-22 17:42:57.368071','','Hoa khô mộc lan',150,200000,'2023-08-22 17:42:57.368071',3,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700977/qjsp4nq895yd9nyrpl2x.jpg'),(60,'2023-08-22 17:43:15.600626','','Bó hoa khô trang trí nhà cửa',150,200000,'2023-08-22 17:43:15.600626',3,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692700995/qaq3yg8cxjivhwoe7y38.jpg'),(61,'2023-08-22 17:43:55.710934','','Set Chậu thuỷ tinh hoa khô decor trang trí phong cách Bắc Âu Vintage',150,200000,'2023-08-22 17:43:55.711934',3,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701035/zyaikbgwt1rxqfwvqzau.jpg'),(62,'2023-08-22 17:44:18.725170','','Set bình hoa baby khô trang trí',150,100000,'2023-08-22 17:44:18.725170',3,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701058/mthbyibao4bn53g0pivt.jpg'),(63,'2023-08-22 17:44:36.941929','','Hoa sao khô',180,100000,'2023-08-22 17:44:36.941929',3,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701077/aj3mn8adkgcxeo28mjye.jpg'),(64,'2023-08-22 17:44:54.873507','','Lọ hoa khô',180,100000,'2023-08-22 17:44:54.873507',3,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701095/pgp42sdfhkwk6wtopdyl.jpg'),(65,'2023-08-22 17:45:12.185155','','Hoa cẩm tú cầu khô nhiều màu',180,100000,'2023-08-22 17:45:12.185155',3,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701112/sssxggdp20qpxtnpznur.jpg'),(66,'2023-08-22 17:45:44.991618','','Bó hoa khô Nigella mini siêu cao cấp',180,100000,'2023-08-22 17:45:44.991618',3,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701145/r0yrd5irw3fjuqc9jm8a.jpg'),(67,'2023-08-22 17:49:40.203046','','Lẵng hoa hồng',180,100000,'2023-08-22 17:49:40.203046',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701380/hsw5kepgjnxjrgremt1n.jpg'),(68,'2023-08-22 17:50:01.483718','','Hoa cẩm tú cầu',360,100000,'2023-08-22 17:50:01.483718',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701401/demlp0nff5aic0dvn3la.jpg'),(69,'2023-08-22 17:50:13.713087','','Hoa sen',180,100000,'2023-08-22 17:50:13.713087',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701413/dtdl2z7kzddgsqo9uhmf.jpg'),(70,'2023-08-22 17:50:26.554755','','Hoa baby trang trí',180,100000,'2023-08-22 17:50:26.554755',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701426/crtortjpx9eimbkz9hwo.jpg'),(71,'2023-08-22 17:50:49.032953','','Hoa tulip',360,200000,'2023-08-22 17:50:49.032953',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701449/dnq65ghwraj54oabek8z.jpg'),(72,'2023-08-22 17:51:05.300615','','Hoa mẫu đơn',180,200000,'2023-08-22 17:51:05.300615',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701465/psg3x5zbr0nf6cwqb07g.jpg'),(73,'2023-08-22 17:51:20.898896','','Hoa cẩm chướng',305,400000,'2023-08-22 17:51:20.898896',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701481/zii43owdpuyasbnmkf4s.jpg'),(74,'2023-08-22 17:51:37.245647','','Lan hồ điệp',125,400000,'2023-08-22 17:51:37.246647',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701497/i6irhvr617g0d7xyqqvq.jpg'),(75,'2023-08-22 17:51:49.865696','','Hoa để bàn',125,400000,'2023-08-22 17:51:49.865696',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701508/j1jfejshsujg6hyiugvt.jpg'),(76,'2023-08-22 17:52:04.211625','','Hoa Marsala',125,400000,'2023-08-22 17:52:04.211625',4,'http://res.cloudinary.com/dbkikuoyy/image/upload/v1692701524/cmbgcx6kzymj8yvfntqy.jpg');
/*!40000 ALTER TABLE `flowers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flowers_colors`
--

DROP TABLE IF EXISTS `flowers_colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flowers_colors` (
  `flower_id` bigint NOT NULL,
  `color_id` bigint NOT NULL,
  PRIMARY KEY (`flower_id`,`color_id`),
  KEY `FKe7lu0p84xitl3vwtvuflnogsk` (`color_id`),
  CONSTRAINT `FK844vxqndat4jvder3liqrvobw` FOREIGN KEY (`flower_id`) REFERENCES `flowers` (`id`),
  CONSTRAINT `FKe7lu0p84xitl3vwtvuflnogsk` FOREIGN KEY (`color_id`) REFERENCES `colors` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flowers_colors`
--

LOCK TABLES `flowers_colors` WRITE;
/*!40000 ALTER TABLE `flowers_colors` DISABLE KEYS */;
/*!40000 ALTER TABLE `flowers_colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `imports`
--

DROP TABLE IF EXISTS `imports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_import` datetime(6) DEFAULT NULL,
  `quantity` int NOT NULL,
  `flower_id` bigint DEFAULT NULL,
  `supplier_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtk5qt1fxwu5f81s7djbf49g4g` (`flower_id`),
  KEY `FKq1ggem7me9u0hjjnfcr7hjq51` (`supplier_id`),
  CONSTRAINT `FKq1ggem7me9u0hjjnfcr7hjq51` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`),
  CONSTRAINT `FKtk5qt1fxwu5f81s7djbf49g4g` FOREIGN KEY (`flower_id`) REFERENCES `flowers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `imports`
--

LOCK TABLES `imports` WRITE;
/*!40000 ALTER TABLE `imports` DISABLE KEYS */;
INSERT INTO `imports` VALUES (3,'2023-08-27 22:21:12.361000',100,14,4),(4,'2023-08-27 22:21:12.361000',100,15,4),(5,'2023-08-27 22:21:12.361000',100,16,4),(6,'2023-08-27 22:21:12.361000',100,17,4),(7,'2023-08-27 22:21:12.361000',100,18,4),(8,'2023-08-27 22:21:12.361000',100,19,4),(9,'2023-08-27 22:21:12.361000',100,20,4),(10,'2023-08-27 22:21:12.361000',100,21,4),(11,'2023-08-27 22:21:12.361000',100,22,5),(12,'2023-08-27 22:21:12.361000',100,23,5),(13,'2023-08-27 22:21:12.361000',100,24,5),(14,'2023-08-27 22:21:12.361000',100,25,5),(15,'2023-08-27 22:21:12.361000',100,26,5),(16,'2023-08-27 22:21:12.361000',100,27,5),(17,'2023-08-27 22:21:12.361000',100,28,5),(18,'2023-08-27 22:21:12.361000',100,29,4),(19,'2023-08-27 22:21:12.361000',100,30,4),(20,'2023-08-27 22:21:12.361000',100,31,4),(21,'2023-08-27 22:21:12.361000',100,32,4),(22,'2023-08-27 22:21:12.361000',100,33,5),(23,'2023-08-27 22:21:12.361000',100,34,5),(24,'2023-08-27 22:21:12.361000',100,35,5),(25,'2023-08-27 22:21:12.361000',100,36,5),(26,'2023-08-27 22:21:12.361000',100,37,5),(27,'2023-08-27 22:21:12.361000',100,38,5),(28,'2023-08-27 22:21:12.361000',100,39,5),(29,'2023-08-27 22:21:12.361000',100,40,5),(30,'2023-08-27 22:21:12.361000',100,41,5),(31,'2023-08-27 22:21:12.361000',100,42,5),(32,'2023-08-27 22:21:12.361000',100,43,5),(33,'2023-08-27 22:21:12.361000',100,44,5),(34,'2023-08-27 22:21:12.361000',100,45,5),(35,'2023-08-27 22:21:12.361000',100,46,5),(36,'2023-08-27 22:21:12.361000',100,47,5),(37,'2023-08-27 22:21:12.361000',100,48,5),(38,'2023-08-27 22:21:12.361000',100,49,5),(39,'2023-08-27 22:21:12.361000',100,50,5),(40,'2023-08-27 22:21:12.361000',150,51,5),(41,'2023-08-27 22:21:12.361000',150,52,5),(42,'2023-08-27 22:21:12.361000',150,53,5),(43,'2023-08-27 22:21:12.361000',150,54,5),(44,'2023-08-27 22:21:12.361000',150,55,5),(45,'2023-08-27 22:21:12.361000',150,56,5),(46,'2023-08-27 22:21:12.361000',150,57,5),(47,'2023-08-27 22:21:12.361000',150,58,5),(48,'2023-08-27 22:21:12.361000',150,59,5),(49,'2023-08-27 22:21:12.361000',150,60,5),(50,'2023-08-27 22:21:12.361000',150,61,5),(51,'2023-08-27 22:21:12.361000',150,62,5),(52,'2023-08-27 22:21:12.361000',180,63,5),(53,'2023-08-27 22:21:12.361000',180,64,5),(54,'2023-08-27 22:21:12.361000',180,66,5),(55,'2023-08-27 22:21:12.361000',180,65,5),(56,'2023-08-27 22:21:12.361000',180,67,5),(57,'2023-08-27 22:21:12.361000',180,68,5),(58,'2023-08-27 22:21:12.361000',180,68,5),(59,'2023-08-27 22:21:12.361000',180,69,5),(60,'2023-08-27 22:21:12.361000',180,70,5),(61,'2023-08-27 22:21:12.361000',180,71,5),(62,'2023-08-27 22:21:12.361000',180,71,5),(63,'2023-08-27 22:21:12.361000',180,72,5),(64,'2023-08-27 22:21:12.361000',180,73,5),(65,'2023-08-27 22:21:12.361000',125,73,5),(66,'2023-08-27 22:21:12.361000',125,74,5),(67,'2023-08-27 22:21:12.361000',125,75,5),(68,'2023-08-27 22:21:12.361000',125,76,5);
/*!40000 ALTER TABLE `imports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `sub_total` double DEFAULT NULL,
  `flower_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkaf0oqaegidunvvltoy939mip` (`flower_id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKkaf0oqaegidunvvltoy939mip` FOREIGN KEY (`flower_id`) REFERENCES `flowers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (5,1,300000,14,4),(6,2,800000,16,5),(7,2,1000000,17,5),(8,2,1000000,27,6),(9,1,500000,23,6),(10,2,1000000,19,7);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `delivery_date` datetime(6) DEFAULT NULL,
  `order_date` datetime(6) DEFAULT NULL,
  `order_status` smallint DEFAULT NULL,
  `total_amount` int DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpxtb8awmi0dk6smoh2vp1litg` (`customer_id`),
  CONSTRAINT `FKpxtb8awmi0dk6smoh2vp1litg` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (4,'2023-08-27 09:44:03.437746','2023-08-29 09:44:03.437000','2023-08-27 09:44:03.437000',0,1,300000,6),(5,'2023-08-27 09:46:04.816389','2023-08-29 09:46:04.816000','2023-08-27 09:46:04.816000',0,4,1800000,6),(6,'2023-08-27 14:29:55.715647','2023-08-29 14:29:55.715000','2023-08-27 14:29:55.714000',0,3,1500000,6),(7,'2023-08-27 14:41:02.360449','2023-08-29 14:41:02.360000','2023-08-27 14:41:02.360000',0,2,1000000,6);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `previews`
--

DROP TABLE IF EXISTS `previews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `previews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `date_preview` datetime(6) DEFAULT NULL,
  `preview` double NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `account_id` bigint DEFAULT NULL,
  `flower_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_reviews_account_flower` (`account_id`,`flower_id`),
  KEY `fk_constraint_name` (`flower_id`),
  CONSTRAINT `FK8dcq28etiahve0ci6dfoebcju` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`),
  CONSTRAINT `fk_constraint_name` FOREIGN KEY (`flower_id`) REFERENCES `flowers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `previews`
--

LOCK TABLES `previews` WRITE;
/*!40000 ALTER TABLE `previews` DISABLE KEYS */;
INSERT INTO `previews` VALUES (44,'2023-08-27 14:29:34.806220','2023-08-27 14:29:34.806220',4,'2023-08-27 14:29:34.806220',7,23),(45,'2023-08-27 14:40:23.070006','2023-08-27 14:40:23.070006',5,'2023-08-27 14:40:23.070006',7,19);
/*!40000 ALTER TABLE `previews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contact_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (4,'Cau Dat, Đà Lạt','Nguyễn Văn Hưng','hoatuoidalat@gmail.com','Hoa Tươi Đà Lạt ','0988698234'),(5,'12A, Nguyễn Văn Cừ, Huyện Bình Chánh','Nguyễn Thị Nhung','hoakhogiasi@gmail.com','Hoa khô giá sỉ Hồng Nhung','0988692234');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-27 21:16:21
