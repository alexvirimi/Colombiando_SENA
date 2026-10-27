-- MySQL dump 10.13  Distrib 26.7.0, for macos26.6 (arm64)
--
-- Host: localhost    Database: colombiando
-- ------------------------------------------------------
-- Server version	26.7.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` binary(16) NOT NULL,
  `address_text` text NOT NULL,
  `country_code` char(2) DEFAULT NULL,
  `latitude` decimal(9,6) NOT NULL,
  `longitude` decimal(9,6) NOT NULL,
  `municipality` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (_binary '\0½y\'ºGDš\ã\ìºy>','Cueva del Jaguar Dormido, vereda La Chaquira, San AgustÃ­n, Huila','CO',1.887100,-76.269400,'Andina'),(_binary '.ûÛ½[M¯C~m0\Ä\Ç\Ô','Laguna de los Delfines Rosados, caÃ±o Tarapoto, Puerto NariÃ±o, Amazonas','CO',-3.771200,-70.381500,'AmazonÃ­a'),(_binary 'A«ø\ñ\ÛL‚c—13l\0 ','Cascada El Nido de Agua, sendero de El Almejal, BahÃ­a Solano, ChocÃ³','CO',6.224300,-77.410200,'PacÃ­fica'),(_binary '¢H(\ÓrG¼8m’q\Õ','Cascada Los Tres Suspiros, camino a la finca cafetera El EdÃ©n, Minca, Magdalena','CO',11.144900,-74.113200,'Caribe'),(_binary '¤\ô)n\åB¯»kDo¿k','Estero de las Mangleras Antiguas, vereda Soledad, Guapi, Cauca','CO',2.570200,-77.886600,'PacÃ­fica'),(_binary '­£ËªVLcUk\ÒV‰Á\ì','Ensenada Coral Secreto, costa oriental de Isla BarÃº, Cartagena, BolÃ­var','CO',10.231200,-75.602100,'Caribe'),(_binary '°=7 “D¿\å\Ê\n\çe\ñi','Sendero Las Piedras Dormidas, vereda Guane, Barichara, Santander','CO',6.635200,-73.223100,'Andina'),(_binary '\ÉSv`³\êL-º\í`DZ¤£','CaÃ±Ã³n del ArcoÃ­ris Escondido, sendero Los Mogotes, La Macarena, Meta','CO',2.181500,-73.789300,'OrinoquÃ­a'),(_binary '\Ì)\õ\\jG–ª\Ç(—\Ê7\È_','Brazo del RÃ­o Magdalena, embarcadero de La CiÃ©naga Escondida, Mompox, BolÃ­var','CO',9.244100,-74.428700,'Caribe'),(_binary '\Îk\ê‘øCT¸‘§\ô^uû%','Sabana de los ChigÃ¼iros Mansos, hato La Aurora, Tame, Arauca','CO',6.462100,-71.734700,'OrinoquÃ­a'),(_binary '\×u€V^9C¨¦1\Ó>`\æB','Pozo de las Aguas Quietas, vereda SÃ¡chica Alta, Villa de Leyva, BoyacÃ¡','CO',5.635900,-73.527100,'Andina'),(_binary '\ŞK\Òk={HÍ¶-K\Ò\Û\È}+','Caleta del Pirata Olvidado, sector BahÃ­a Manzanillo, Providencia, San AndrÃ©s y Providencia','CO',13.352400,-81.371800,'Insular'),(_binary '\ã\Î\"#_‚N\÷˜7?Y\Ï2\ä','Salto de la Niebla Eterna, vereda Fin del Mundo, Mocoa, Putumayo','CO',1.151200,-76.649800,'AmazonÃ­a'),(_binary '\ë?¤:L6¬\Ú\ç\î72£','BahÃ­a de las Ballenas Calladas, playa Termales Norte, NuquÃ­, ChocÃ³','CO',5.712900,-77.266800,'PacÃ­fica'),(_binary '\îq›\ã\çLc\àÛ¯§\Æh','Mirador El Silencio, camino real de la vereda La Cascada, JardÃ­n, Antioquia','CO',5.599400,-75.820300,'Andina');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_escorts`
--

DROP TABLE IF EXISTS `booking_escorts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_escorts` (
  `id` binary(16) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `document_type` enum('CC','CE','PASSPORT','TI') NOT NULL,
  `id_number` varchar(45) NOT NULL,
  `last_name` varchar(150) NOT NULL,
  `name` varchar(150) NOT NULL,
  `booking_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_booking_escorts_booking` (`booking_id`),
  CONSTRAINT `fk_booking_escorts_booking` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_escorts`
--

LOCK TABLES `booking_escorts` WRITE;
/*!40000 ALTER TABLE `booking_escorts` DISABLE KEYS */;
INSERT INTO `booking_escorts` VALUES (_binary ':˜û\ÏM¹J\ç‰\â\â”\çÁ\ÖN','1005-04-13','CC','1056743828','Medina','Juan',_binary '&Å¤R\ãF’n¶>C½O');
/*!40000 ALTER TABLE `booking_escorts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` binary(16) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `num_people` int NOT NULL,
  `status` enum('CANCELLED','COMPLETED','CONFIRMED','PENDING') DEFAULT 'PENDING',
  `total_price` decimal(12,2) DEFAULT NULL,
  `instance_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_schedule_instances` (`instance_id`),
  KEY `fk_booking_user` (`user_id`),
  CONSTRAINT `fk_booking_instance` FOREIGN KEY (`instance_id`) REFERENCES `schedule_instances` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (_binary '&Å¤R\ãF’n¶>C½O','2026-10-18 03:41:15.736003',2,'CANCELLED',260000.00,_binary '\Ô\Z°6\îN¿·(\æ\n\õ\ê«\è',_binary '\õjaF°H@”7.\Z¶K‚');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guide_languages`
--

DROP TABLE IF EXISTS `guide_languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guide_languages` (
  `level` enum('A1','A2','B1','B2','C1','C2','NATIVE') NOT NULL DEFAULT 'NATIVE',
  `guide_id` binary(16) NOT NULL,
  `language_id` binary(16) NOT NULL,
  PRIMARY KEY (`guide_id`,`language_id`),
  KEY `fk_guidelanguages_language` (`language_id`),
  CONSTRAINT `fk_guidelanguages_guide` FOREIGN KEY (`guide_id`) REFERENCES `guides` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_guidelanguages_language` FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guide_languages`
--

LOCK TABLES `guide_languages` WRITE;
/*!40000 ALTER TABLE `guide_languages` DISABLE KEYS */;
INSERT INTO `guide_languages` VALUES ('NATIVE',_binary '\É5\É\ÎGw…t(}\ÄTº1',_binary '1\Ìte@Ëƒ¼\İ—'),('C2',_binary '\É5\É\ÎGw…t(}\ÄTº1',_binary 'W[\\úiOKª\Ø\ów9\óûw'),('NATIVE',_binary '4Õ§g\ÂACëŒ”¶q6¾?\æ',_binary '1\Ìte@Ëƒ¼\İ—'),('C1',_binary '4Õ§g\ÂACëŒ”¶q6¾?\æ',_binary 'W[\\úiOKª\Ø\ów9\óûw'),('C2',_binary '4Õ§g\ÂACëŒ”¶q6¾?\æ',_binary 's)Æ²•HLC¸q\ÙÀÀ,‚¯'),('A2',_binary '4Õ§g\ÂACëŒ”¶q6¾?\æ',_binary '¸û°«,\\K´™|§%\ô\ä\İ'),('A2',_binary '6’ü\áu1O\n’­\óüO`',_binary 'œ$ eIf¹ˆ60=\İ\î'),('NATIVE',_binary '6’ü\áu1O\n’­\óüO`',_binary '1\Ìte@Ëƒ¼\İ—'),('C1',_binary '6’ü\áu1O\n’­\óüO`',_binary 's)Æ²•HLC¸q\ÙÀÀ,‚¯'),('C2',_binary '6’ü\áu1O\n’­\óüO`',_binary ' «\ğIw§Kª•sw\Ş[„¡'),('C1',_binary 'kl›\å„El¢X\\}Y?¶ª',_binary '‡\Õ5º\rN}F„3BW'),('B2',_binary 'kl›\å„El¢X\\}Y?¶ª',_binary '­Ç˜HcF\0¸%#\\“‘\æ'),('NATIVE',_binary 'kl›\å„El¢X\\}Y?¶ª',_binary '1\Ìte@Ëƒ¼\İ—'),('C1',_binary 'kl›\å„El¢X\\}Y?¶ª',_binary ' «\ğIw§Kª•sw\Ş[„¡'),('NATIVE',_binary '¯\á`\êKN„\r)/\Ë\ç',_binary '1\Ìte@Ëƒ¼\İ—'),('C2',_binary '¯\á`\êKN„\r)/\Ë\ç',_binary 's)Æ²•HLC¸q\ÙÀÀ,‚¯'),('B2',_binary '¯\á`\êKN„\r)/\Ë\ç',_binary '¸û°«,\\K´™|§%\ô\ä\İ'),('NATIVE',_binary '’EÜ…jL„¨µˆ¹H\Ö\ê™',_binary '1\Ìte@Ëƒ¼\İ—'),('B2',_binary '’EÜ…jL„¨µˆ¹H\Ö\ê™',_binary 'W[\\úiOKª\Ø\ów9\óûw'),('NATIVE',_binary '§m;’—EÔ­‚&p>O\ØC',_binary '1\Ìte@Ëƒ¼\İ—'),('B2',_binary '§m;’—EÔ­‚&p>O\ØC',_binary 's)Æ²•HLC¸q\ÙÀÀ,‚¯'),('C2',_binary '§m;’—EÔ­‚&p>O\ØC',_binary ' «\ğIw§Kª•sw\Ş[„¡'),('NATIVE',_binary '¨³,[\Ş:@‘†\0\ßf²§w',_binary '1\Ìte@Ëƒ¼\İ—'),('B2',_binary '¨³,[\Ş:@‘†\0\ßf²§w',_binary 's)Æ²•HLC¸q\ÙÀÀ,‚¯'),('B2',_binary '¾F?Š}JÏ…\ïA=\r¼',_binary 'œ$ eIf¹ˆ60=\İ\î'),('NATIVE',_binary '¾F?Š}JÏ…\ïA=\r¼',_binary '1\Ìte@Ëƒ¼\İ—'),('C2',_binary '¾F?Š}JÏ…\ïA=\r¼',_binary 'W[\\úiOKª\Ø\ów9\óûw'),('NATIVE',_binary 'Ğ·™©¢/Jÿ€yª\é °',_binary '1\Ìte@Ëƒ¼\İ—'),('A2',_binary 'Ğ·™©¢/Jÿ€yª\é °',_binary 'W[\\úiOKª\Ø\ów9\óûw'),('C2',_binary 'Ğ·™©¢/Jÿ€yª\é °',_binary 's)Æ²•HLC¸q\ÙÀÀ,‚¯'),('C2',_binary '\×şu„i+BË-\É3ú¦',_binary 'œ$ eIf¹ˆ60=\İ\î'),('NATIVE',_binary '\×şu„i+BË-\É3ú¦',_binary '1\Ìte@Ëƒ¼\İ—'),('A2',_binary '\×şu„i+BË-\É3ú¦',_binary 's)Æ²•HLC¸q\ÙÀÀ,‚¯'),('C1',_binary '\×şu„i+BË-\É3ú¦',_binary '¸û°«,\\K´™|§%\ô\ä\İ'),('C1',_binary 'ø…\ÂÍ®C©¾tŸùxMOa',_binary 'œ$ eIf¹ˆ60=\İ\î'),('NATIVE',_binary 'ø…\ÂÍ®C©¾tŸùxMOa',_binary '1\Ìte@Ëƒ¼\İ—'),('A2',_binary 'ø…\ÂÍ®C©¾tŸùxMOa',_binary 'W[\\úiOKª\Ø\ów9\óûw'),('C1',_binary 'ø…\ÂÍ®C©¾tŸùxMOa',_binary 's)Æ²•HLC¸q\ÙÀÀ,‚¯');
/*!40000 ALTER TABLE `guide_languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guides`
--

DROP TABLE IF EXISTS `guides`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guides` (
  `id` binary(16) NOT NULL,
  `bio` text,
  `created_at` datetime(6) NOT NULL,
  `last_name` varchar(150) NOT NULL,
  `name` varchar(150) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `profile_image` text,
  `status` enum('ACTIVE','INACTIVE','SUSPENDED') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guides`
--

LOCK TABLES `guides` WRITE;
/*!40000 ALTER TABLE `guides` DISABLE KEYS */;
INSERT INTO `guides` VALUES (_binary '\É5\É\ÎGw…t(}\ÄTº1','Experto en senderismo de selva y cascadas en el PacÃ­fico y el Caribe. Primeros auxilios certificados y mÃ¡s de 500 expediciones guiadas.','2026-10-18 02:40:34.740172','Cuesta','Jhon Fredy','+573101000011','https://randomuser.me/api/portraits/men/75.jpg','ACTIVE'),(_binary '4Õ§g\ÂACëŒ”¶q6¾?\æ','GuÃ­a indÃ­gena de la Sierra Nevada de Santa Marta. Comparte la cosmovisiÃ³n de su pueblo en caminatas de naturaleza y cascadas ocultas.','2026-10-18 02:39:52.814592','Arhuaco','Wilmer','+573101000005','https://randomuser.me/api/portraits/men/52.jpg','ACTIVE'),(_binary '6’ü\áu1O\n’­\óüO`','GuÃ­a de espeleologÃ­a y arqueologÃ­a en el Macizo Colombiano. Conoce cuevas, tumbas y estatuas ocultas de San AgustÃ­n e InzÃ¡.','2026-10-18 02:40:19.914812','Mosquera','Esteban','+573101000009','https://randomuser.me/api/portraits/men/18.jpg','ACTIVE'),(_binary 'kl›\å„El¢X\\}Y?¶ª','Historiadora y guÃ­a en Mompox y la depresiÃ³n momposina. Ofrece recorridos fluviales al amanecer y relatos de la cultura anfibia del rÃ­o Magdalena.','2026-10-18 02:39:46.189398','BolaÃ±o','Natalia','+573101000004','https://randomuser.me/api/portraits/women/65.jpg','ACTIVE'),(_binary '¯\á`\êKN„\r)/\Ë\ç','GuÃ­a raizal de Providencia, bilingÃ¼e espaÃ±ol-inglÃ©s-creole. Experta en snorkel, historia pirata y gastronomÃ­a isleÃ±a.','2026-10-18 02:40:12.410084','Hooker','Katherine','+573101000008','https://randomuser.me/api/portraits/women/21.jpg','ACTIVE'),(_binary '’EÜ…jL„¨µˆ¹H\Ö\ê™','GuÃ­a nativo de la regiÃ³n Andina con 12 aÃ±os recorriendo caminos reales y senderos de Santander. Apasionado por la historia colonial y la fotografÃ­a de paisaje.','2026-10-18 02:39:25.519299','Barrera','JuliÃ¡n','+573101000001','https://randomuser.me/api/portraits/men/32.jpg','ACTIVE'),(_binary '§m;’—EÔ­‚&p>O\ØC','GuÃ­a en Cartagena y las islas del Rosario. Organiza recorridos en lancha por ensenadas y playas poco visitadas de BarÃº.','2026-10-18 02:40:41.053282','Villadiego','Mariana','+573101000012','https://randomuser.me/api/portraits/women/12.jpg','ACTIVE'),(_binary '¨³,[\Ş:@‘†\0\ßf²§w','GuÃ­a afrodescendiente del PacÃ­fico chocoano. Experto en avistamiento de ballenas, manglares y gastronomÃ­a tradicional de NuquÃ­ y BahÃ­a Solano.','2026-10-18 02:39:39.642671','Palacios','Yeison','+573101000003','https://randomuser.me/api/portraits/men/45.jpg','ACTIVE'),(_binary '¾F?Š}JÏ…\ïA=\r¼','Caficultora y guÃ­a en el Eje Cafetero. Cuenta la historia del cafÃ© desde la semilla hasta la taza y conoce los rincones mÃ¡s tranquilos de JardÃ­n y Salento.','2026-10-18 02:39:32.873050','Cardona','Luz Marina','+573101000002','https://randomuser.me/api/portraits/women/44.jpg','ACTIVE'),(_binary 'Ğ·™©¢/Jÿ€yª\é °','GuÃ­a de turismo cultural en BoyacÃ¡ y Cundinamarca. Combina astronomÃ­a, paleontologÃ­a y rutas gastronÃ³micas por Villa de Leyva.','2026-10-18 02:40:27.320071','Ricaurte','Paola','+573101000010','https://randomuser.me/api/portraits/women/33.jpg','ACTIVE'),(_binary '\×şu„i+BË-\É3ú¦','Llanero de Arauca y guÃ­a de turismo de aventura. Lleva a los visitantes a ver chigÃ¼iros, atardeceres de sabana y a conocer la mÃºsica llanera.','2026-10-18 02:40:05.639429','Sarmiento','Orlando','+573101000007','https://randomuser.me/api/portraits/men/61.jpg','ACTIVE'),(_binary 'ø…\ÂÍ®C©¾tŸùxMOa','BiÃ³loga y guÃ­a en la AmazonÃ­a. Especialista en observaciÃ³n de aves, delfines rosados y plantas medicinales en Puerto NariÃ±o y Mocoa.','2026-10-18 02:39:59.222474','Quintero','Diana','+573101000006','https://randomuser.me/api/portraits/women/68.jpg','ACTIVE');
/*!40000 ALTER TABLE `guides` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `languages` (
  `id` binary(16) NOT NULL,
  `code` char(2) NOT NULL,
  `name` varchar(100) NOT NULL,
  `native_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmyc139vxcejowe4q8qm3ca5jn` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` VALUES (_binary '‡\Õ5º\rN}F„3BW','it','Italiano','Italiano'),(_binary '­Ç˜HcF\0¸%#\\“‘\æ','ja','JaponÃ©s','æ—¥æœ¬èª'),(_binary 'œ$ eIf¹ˆ60=\İ\î','fr','FrancÃ©s','FranÃ§ais'),(_binary '1\Ìte@Ëƒ¼\İ—','es','EspaÃ±ol','EspaÃ±ol'),(_binary 'W[\\úiOKª\Ø\ów9\óûw','pt','PortuguÃ©s','PortuguÃªs'),(_binary 's)Æ²•HLC¸q\ÙÀÀ,‚¯','en','InglÃ©s','English'),(_binary ' «\ğIw§Kª•sw\Ş[„¡','zh','Chino mandarÃ­n','ä¸­æ–‡ (æ™®é€šè¯)'),(_binary '¸û°«,\\K´™|§%\ô\ä\İ','de','AlemÃ¡n','Deutsch');
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` binary(16) NOT NULL,
  `amount_to_pay` decimal(12,2) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `payment_method` enum('CARD','CASH','PSE','TRANSFER') NOT NULL,
  `status` enum('CANCELLED','COMPLETED','PENDING','REFUNDED') NOT NULL DEFAULT 'PENDING',
  `booking_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnuscjm6x127hkb15kcb8n56wo` (`booking_id`),
  KEY `idx_payment_booking` (`booking_id`),
  CONSTRAINT `fk_payment_booking` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (_binary 'u¡\åpIz»‘¿¿\Øı¸\Ì',260000.00,'2026-10-18 03:42:25.851201','COP','CASH','COMPLETED',_binary '&Å¤R\ãF’n¶>C½O');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `places`
--

DROP TABLE IF EXISTS `places`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `places` (
  `id` binary(16) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` text,
  `header_img` text,
  `name` varchar(150) NOT NULL,
  `address_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKqobs9f75q3txha9i5b43c66e9` (`address_id`),
  CONSTRAINT `fk_places_address` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `places`
--

LOCK TABLES `places` WRITE;
/*!40000 ALTER TABLE `places` DISABLE KEYS */;
INSERT INTO `places` VALUES (_binary 'Ojt‚™E+°CR¡ˆ\Û	˜','2026-10-18 02:49:49.095382','Pozo natural de agua cristalina rodeado de paisaje semidesÃ©rtico y fÃ³siles marinos de millones de aÃ±os. Es un sitio tranquilo para meditar, tomar fotografÃ­as y observar el cielo estrellado en noches despejadas.','https://picsum.photos/seed/pozo-aguas-quietas/1200/600','Pozo de las Aguas Quietas',_binary '\×u€V^9C¨¦1\Ó>`\æB'),(_binary 'M_ık6HÊ°€Ÿ\Ë|\í¦','2026-10-18 02:49:49.029326','Sendero de unos 5 km que atraviesa formaciones de roca arenisca y antiguos caminos de piedra del pueblo de Guane. Ofrece miradores sobre el caÃ±Ã³n del rÃ­o SuÃ¡rez y es especialmente bello durante el atardecer.','https://picsum.photos/seed/sendero-piedras-dormidas/1200/600','Sendero Las Piedras Dormidas',_binary '°=7 “D¿\å\Ê\n\çe\ñi'),(_binary 'j§QÀ%NBŒDøI\Â4Š','2026-10-18 02:49:49.093970','PequeÃ±a ensenada de aguas turquesas en la costa oriental de BarÃº, de acceso solo en lancha. Su arrecife de coral poco profundo es perfecto para hacer snorkel entre peces de colores, lejos de las playas mÃ¡s concurridas.','https://picsum.photos/seed/ensenada-coral-secreto/1200/600','Ensenada Coral Secreto',_binary '­£ËªVLcUk\ÒV‰Á\ì'),(_binary 'v¤\ÇJùHr˜€E=­É','2026-10-18 02:49:49.028600','PequeÃ±a caleta de arena blanca y agua tibia que, segÃºn la leyenda isleÃ±a, sirviÃ³ de refugio a piratas del Caribe. Tiene excelentes puntos para snorkel y un sendero corto entre palmeras hasta un mirador sobre el mar de los siete colores.','https://picsum.photos/seed/caleta-pirata-olvidado/1200/600','Caleta del Pirata Olvidado',_binary '\ŞK\Òk={HÍ¶-K\Ò\Û\È}+'),(_binary 'w8|\\û7LÜ¢:d¾Ï±','2026-10-18 02:49:49.025983','Red de ciÃ©nagas y brazos del rÃ­o Magdalena que se recorre en canoa entre Ã¡rboles inundados y garzas. Es un lugar para vivir la cultura anfibia de la regiÃ³n, con pescadores locales y atardeceres sobre el agua.','https://picsum.photos/seed/cienaga-escondida/1200/600','La CiÃ©naga Escondida',_binary '\Ì)\õ\\jG–ª\Ç(—\Ê7\È_'),(_binary '~kj¿\ÄNGÊœ…\ã²hYš)','2026-10-18 02:49:49.095436','Cascada de mÃ¡s de 40 metros en el piedemonte amazÃ³nico, envuelta casi siempre en una neblina fresca. El sendero cruza quebradas y bosque hÃºmedo, y el final es un mirador natural con vista al valle.','https://picsum.photos/seed/salto-niebla-eterna/1200/600','Salto de la Niebla Eterna',_binary '\ã\Î\"#_‚N\÷˜7?Y\Ï2\ä'),(_binary '„mtZEDš†)b†¸','2026-10-18 02:49:49.020366','Laguna de aguas oscuras conectada al caÃ±o Tarapoto, donde habitan delfines rosados y decenas de especies de aves. Se visita en canoa al amanecer, cuando el agua estÃ¡ en calma y los animales se acercan a la superficie.','https://picsum.photos/seed/laguna-delfines-rosados/1200/600','Laguna de los Delfines Rosados',_binary '.ûÛ½[M¯C~m0\Ä\Ç\Ô'),(_binary 'ˆ°@±G‘D “Sf«¶6…','2026-10-18 02:49:49.025958','Laberinto de canales rodeado de manglares centenarios en la costa del PacÃ­fico caucano. Se navega en lancha o canoa con guÃ­as de la comunidad, que enseÃ±an la pesca artesanal y la recolecciÃ³n sostenible de piangua.','https://picsum.photos/seed/estero-mangleras-antiguas/1200/600','Estero de las Mangleras Antiguas',_binary '¤\ô)n\åB¯»kDo¿k'),(_binary 'ˆÌ7}J™Œ\ñ+\ów6\ò','2026-10-18 02:49:49.030933','Mirador natural al final de un camino real entre cafetales y montaÃ±as del suroeste antioqueÃ±o. Ofrece vistas de valles verdes y neblina matutina, con un silencio ideal para fotografÃ­a, observaciÃ³n de aves y desconexiÃ³n.','https://picsum.photos/seed/mirador-el-silencio/1200/600','Mirador El Silencio',_binary '\îq›\ã\çLc\àÛ¯§\Æh'),(_binary 'ê™˜M\é”\Ø[+>T‘','2026-10-18 02:49:49.093820','Cascada de unos 25 metros que cae sobre una poza natural rodeada de selva hÃºmeda del PacÃ­fico. Se llega tras una caminata de 40 minutos por un sendero con puentes de madera, ideal para observar ranas, mariposas y aves tropicales.','https://picsum.photos/seed/cascada-nido-de-agua/1200/600','Cascada El Nido de Agua',_binary 'A«ø\ñ\ÛL‚c—13l\0 '),(_binary 'œ’\Õ\Ó.1B!”Ø§¥3‘\æ','2026-10-18 02:49:49.027487','Extensa sabana inundable del piedemonte llanero con grandes grupos de chigÃ¼iros, garzas y caimanes. Se explora a caballo o en camioneta con guÃ­as llaneros, y la jornada termina con mÃºsica de arpa y cuatro alrededor de la fogata.','https://picsum.photos/seed/sabana-chigueiros-mansos/1200/600','Sabana de los ChigÃ¼iros Mansos',_binary '\Îk\ê‘øCT¸‘§\ô^uû%'),(_binary '±&ŸbN‚\\\ã.[\à.','2026-10-18 02:49:49.093105','CaÃ±Ã³n de roca antigua donde el rÃ­o corre sobre lechos de colores rojo, amarillo y verde gracias a plantas acuÃ¡ticas Ãºnicas. Se recorre a pie por los mogotes de la SerranÃ­a de La Macarena, con paradas para nadar en pozos naturales.','https://picsum.photos/seed/canon-arcoiris-escondido/1200/600','CaÃ±Ã³n del ArcoÃ­ris Escondido',_binary '\ÉSv`³\êL-º\í`DZ¤£'),(_binary 'Â„f•Bÿ¬„47¨l','2026-10-18 02:49:49.030942','Cueva de origen kÃ¡rstico en las montaÃ±as del Macizo Colombiano, con formaciones de roca que recuerdan a un jaguar echado. Se recorre con linterna frontal y guÃ­a especializado, y conserva petroglifos que segÃºn la tradiciÃ³n local tienen mÃ¡s de mil aÃ±os.','https://picsum.photos/seed/cueva-jaguar-dormido/1200/600','Cueva del Jaguar Dormido',_binary '\0½y\'ºGDš\ã\ìºy>'),(_binary '\Ë\ö4I¯2F¤jªX¯ NÁ','2026-10-18 02:49:49.030354','BahÃ­a protegida del PacÃ­fico donde las ballenas jorobadas llegan a parir entre julio y octubre. Se observa desde lancha con operadores certificados o desde la playa, cerca de aguas termales y selva tropical.','https://picsum.photos/seed/bahia-ballenas-calladas/1200/600','BahÃ­a de las Ballenas Calladas',_binary '\ë?¤:L6¬\Ú\ç\î72£'),(_binary '\Ò†£\ê\ÑN3©0- E—ú\á','2026-10-18 02:49:49.030466','Tres caÃ­das de agua escalonadas en la Sierra Nevada, escondidas entre cafetales y bosque nublado. Cada caÃ­da tiene su propia poza para refrescarse, y el camino pasa por una finca cafetera donde se puede probar cafÃ© de origen.','https://picsum.photos/seed/cascada-tres-suspiros/1200/600','Cascada Los Tres Suspiros',_binary '¢H(\ÓrG¼8m’q\Õ');
/*!40000 ALTER TABLE `places` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` binary(16) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `rating` smallint NOT NULL,
  `review` text NOT NULL,
  `booking_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_review_booking` (`booking_id`),
  KEY `fk_review_user` (`user_id`),
  CONSTRAINT `fk_review_booking` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_review_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule_instances`
--

DROP TABLE IF EXISTS `schedule_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule_instances` (
  `id` binary(16) NOT NULL,
  `available_capacity` int NOT NULL,
  `date` date NOT NULL,
  `max_capacity` int NOT NULL,
  `state` enum('ACTIVE','CANCELLED') NOT NULL DEFAULT 'ACTIVE',
  `schedule_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_instance_schedule_date` (`schedule_id`,`date`),
  UNIQUE KEY `UKrebcbonolp66m7mpaomsss7v9` (`schedule_id`),
  KEY `idx_instace_date` (`date`),
  CONSTRAINT `fk_schedule` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_instances`
--

LOCK TABLES `schedule_instances` WRITE;
/*!40000 ALTER TABLE `schedule_instances` DISABLE KEYS */;
INSERT INTO `schedule_instances` VALUES (_binary '\Ô\Z°6\îN¿·(\æ\n\õ\ê«\è',8,'2026-12-27',8,'ACTIVE',_binary '×±U`,,K£>\Ãt1<\Ò'),(_binary '\É\ÅEQHˆ“Ü›\ß|£\â',14,'2027-01-31',14,'ACTIVE',_binary '° øDÿ‡¶e&c×¬4'),(_binary '$\çJ\æ\ìŸIœ™\ë\ô¼\ä²n\n',8,'2027-03-13',8,'ACTIVE',_binary 'E,s\õ\ğ¼BØƒ…ŒÒµ\\'),(_binary '/\ÍU“2“DÓ“¼‰$^½',10,'2027-03-14',10,'ACTIVE',_binary '‚)“ı‘rB]µ©ª\Úø¬a'),(_binary '0\0vˆ\ä¥MÇ¸·\ç9È‡¥S',10,'2027-03-20',10,'ACTIVE',_binary 'vMwX@9°I-¥µhÒ€'),(_binary '3µ\"¨TIıÉŒkPHa\Å',14,'2026-11-28',14,'ACTIVE',_binary '02\'¢\r\÷KC¿\'™\Ğ\æ‹s'),(_binary '5!\çGfM½Š\é\Õ\r‡ş`',10,'2026-11-28',10,'ACTIVE',_binary '…S§»1TEÓ´\ñ!-@C'),(_binary '?8\\gI@T†K~\ÑA¨J\Û',8,'2027-03-14',8,'ACTIVE',_binary 'l\Üù\'@î³³\õaˆº­'),(_binary 'C/Ø¯Y€Gs¬\Ö=Æ²',10,'2027-01-03',10,'ACTIVE',_binary '°J\×\æ¶\ëA„¸‡…µ\òu\ë'),(_binary 'H$ù¡\çŒA$œAO\â¹İ¦q',12,'2027-01-31',12,'ACTIVE',_binary 'j\Ë\æ{j£D»‹Å©·gO\Ô'),(_binary 'O¥EŒ|G0\\‘0\Ó',12,'2027-01-02',12,'ACTIVE',_binary '\ô\ê\Åg\'K›—JAq¦) '),(_binary 'm¡\ŞbCÈ„8\ÏZK\í\"',16,'2027-08-21',16,'ACTIVE',_binary ')\0_£GG·\ŞL+U¤o'),(_binary '†9¯\n¨\éEV-”D',8,'2027-02-06',8,'ACTIVE',_binary '…Xg<²H#ºOGsÊ˜	'),(_binary '‰tSÌsIs &‹¦Gû',8,'2027-03-20',8,'ACTIVE',_binary '>\âc¿–M`›¦¾JRj\Ù'),(_binary '‘C£\ßü@°£ \ô·\Éi\Ì\'',10,'2026-11-21',10,'ACTIVE',_binary 'ÿqˆP<\'G ¿k\É€—“;'),(_binary '¬…—ÑµE–¶o×…\÷}¯',8,'2026-12-27',8,'ACTIVE',_binary 'vP‹Ñ€ªNšE¥’v\İv€'),(_binary '®h\Ó\Ô@\ÍOÉœ\âJAUX=-',8,'2027-03-14',8,'ACTIVE',_binary 'Mb\ó8\ZEh•e‚µi¯‡'),(_binary '²üv—º\rCËŒ\íÀ›Aµ\Ù@',16,'2026-10-25',16,'ACTIVE',_binary '#X ¤J‰›\óq2\åÎ´'),(_binary '»\Û]\á2D\ğ™\ã\Ó\à€beÁ',10,'2027-02-07',10,'ACTIVE',_binary '´!º\Û9xLnŠ©ø_™¢5¬'),(_binary '¼_?7ªnJŠ\Õ3x\æ¡\ñ\é',15,'2027-03-21',15,'ACTIVE',_binary 'ıˆ¨=cD\÷»~za\Z;-'),(_binary '\ÑRµie\õG\ö­\Ô@Ì¯G•',8,'2027-03-13',8,'ACTIVE',_binary '«¦‡—@r¹ûˆ$n6¼'),(_binary 'ß¦\Ôa¸@Øœhq—8\ãª',10,'2026-12-27',10,'ACTIVE',_binary '1G¢\öûB‰®‹p61F£P'),(_binary '\å\È&\Ù>O#´¢	1\ÜZ\ÉM',12,'2027-01-03',12,'ACTIVE',_binary 'N\ÇÄ‚–@’)\Í/¯Lk'),(_binary '\ì\ö[\èrBÌ­®y­]š',12,'2027-03-14',12,'ACTIVE',_binary '±ƒ\Òû/—@É®³.s>'),(_binary 'ûù\ë\ŞF#§#$\ÍÏºy',8,'2026-11-22',8,'ACTIVE',_binary '3ı“\ÕJ‚Ÿ•nsAº\Í');
/*!40000 ALTER TABLE `schedule_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedules` (
  `id` binary(16) NOT NULL,
  `end_date` date NOT NULL,
  `end_time` time NOT NULL,
  `max_capacity` int NOT NULL,
  `price_per_person` decimal(10,2) NOT NULL,
  `start_date` date NOT NULL,
  `start_time` time NOT NULL,
  `status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `guide_id` binary(16) NOT NULL,
  `place_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_schedules_guide_dates` (`guide_id`,`start_date`),
  KEY `idx_schedules_places_dates` (`place_id`,`start_date`),
  CONSTRAINT `fk_schedule_guide` FOREIGN KEY (`guide_id`) REFERENCES `guides` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_schedule_place` FOREIGN KEY (`place_id`) REFERENCES `places` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedules`
--

LOCK TABLES `schedules` WRITE;
/*!40000 ALTER TABLE `schedules` DISABLE KEYS */;
INSERT INTO `schedules` VALUES (_binary '#X ¤J‰›\óq2\åÎ´','2026-10-31','11:30:00',16,180000.00,'2026-10-20','06:30:00','ACTIVE',_binary '¨³,[\Ş:@‘†\0\ßf²§w',_binary '\Ë\ö4I¯2F¤jªX¯ NÁ'),(_binary ')\0_£GG·\ŞL+U¤o','2027-10-15','11:30:00',16,185000.00,'2027-07-15','06:30:00','ACTIVE',_binary '¨³,[\Ş:@‘†\0\ßf²§w',_binary '\Ë\ö4I¯2F¤jªX¯ NÁ'),(_binary '02\'¢\r\÷KC¿\'™\Ğ\æ‹s','2027-03-31','14:00:00',14,160000.00,'2026-11-01','08:00:00','ACTIVE',_binary '§m;’—EÔ­‚&p>O\ØC',_binary 'j§QÀ%NBŒDøI\Â4Š'),(_binary '1G¢\öûB‰®‹p61F£P','2027-03-31','10:30:00',10,90000.00,'2026-11-01','05:30:00','ACTIVE',_binary 'kl›\å„El¢X\\}Y?¶ª',_binary 'w8|\\û7LÜ¢:d¾Ï±'),(_binary '3ı“\ÕJ‚Ÿ•nsAº\Í','2027-03-31','13:00:00',8,95000.00,'2026-11-01','08:00:00','ACTIVE',_binary '6’ü\áu1O\n’­\óüO`',_binary 'Â„f•Bÿ¬„47¨l'),(_binary '>\âc¿–M`›¦¾JRj\Ù','2027-03-31','15:30:00',8,190000.00,'2026-11-01','06:30:00','ACTIVE',_binary '6’ü\áu1O\n’­\óüO`',_binary '±&ŸbN‚\\\ã.[\à.'),(_binary 'E,s\õ\ğ¼BØƒ…ŒÒµ\\','2027-03-31','18:00:00',8,100000.00,'2026-11-01','14:00:00','ACTIVE',_binary '’EÜ…jL„¨µˆ¹H\Ö\ê™',_binary 'Â„f•Bÿ¬„47¨l'),(_binary 'Mb\ó8\ZEh•e‚µi¯‡','2027-03-31','19:00:00',8,95000.00,'2026-11-01','15:00:00','ACTIVE',_binary '§m;’—EÔ­‚&p>O\ØC',_binary 'w8|\\û7LÜ¢:d¾Ï±'),(_binary 'N\ÇÄ‚–@’)\Í/¯Lk','2027-03-31','10:00:00',12,55000.00,'2026-11-01','05:30:00','ACTIVE',_binary '¾F?Š}JÏ…\ïA=\r¼',_binary 'ˆÌ7}J™Œ\ñ+\ów6\ò'),(_binary 'j\Ë\æ{j£D»‹Å©·gO\Ô','2027-03-31','10:00:00',12,65000.00,'2026-11-01','06:00:00','ACTIVE',_binary 'Ğ·™©¢/Jÿ€yª\é °',_binary 'M_ık6HÊ°€Ÿ\Ë|\í¦'),(_binary 'l\Üù\'@î³³\õaˆº­','2027-03-31','18:00:00',8,120000.00,'2026-11-01','13:00:00','ACTIVE',_binary '\É5\É\ÎGw…t(}\ÄTº1',_binary 'ˆ°@±G‘D “Sf«¶6…'),(_binary 'vMwX@9°I-¥µhÒ€','2027-03-31','15:00:00',10,170000.00,'2026-11-01','09:00:00','ACTIVE',_binary 'kl›\å„El¢X\\}Y?¶ª',_binary 'j§QÀ%NBŒDøI\Â4Š'),(_binary 'vP‹Ñ€ªNšE¥’v\İv€','2027-03-31','14:30:00',8,105000.00,'2026-11-01','08:30:00','ACTIVE',_binary '\É5\É\ÎGw…t(}\ÄTº1',_binary '\Ò†£\ê\ÑN3©0- E—ú\á'),(_binary '‚)“ı‘rB]µ©ª\Úø¬a','2027-03-31','16:00:00',10,180000.00,'2026-11-01','07:00:00','ACTIVE',_binary '\×şu„i+BË-\É3ú¦',_binary '±&ŸbN‚\\\ã.[\à.'),(_binary '…S§»1TEÓ´\ñ!-@C','2027-03-31','12:00:00',10,115000.00,'2026-11-01','07:00:00','ACTIVE',_binary '¨³,[\Ş:@‘†\0\ßf²§w',_binary 'ˆ°@±G‘D “Sf«¶6…'),(_binary '«¦‡—@r¹ûˆ$n6¼','2027-03-31','13:00:00',8,110000.00,'2026-11-01','07:00:00','ACTIVE',_binary 'ø…\ÂÍ®C©¾tŸùxMOa',_binary '~kj¿\ÄNGÊœ…\ã²hYš)'),(_binary '…Xg<²H#ºOGsÊ˜	','2027-03-31','10:00:00',8,120000.00,'2026-11-01','05:30:00','ACTIVE',_binary 'ø…\ÂÍ®C©¾tŸùxMOa',_binary '„mtZEDš†)b†¸'),(_binary '°J\×\æ¶\ëA„¸‡…µ\òu\ë','2027-03-31','13:00:00',10,85000.00,'2026-11-01','07:30:00','ACTIVE',_binary '\É5\É\ÎGw…t(}\ÄTº1',_binary 'ê™˜M\é”\Ø[+>T‘'),(_binary '° øDÿ‡¶e&c×¬4','2027-03-31','20:00:00',14,140000.00,'2026-11-01','14:00:00','ACTIVE',_binary '\×şu„i+BË-\É3ú¦',_binary 'œ’\Õ\Ó.1B!”Ø§¥3‘\æ'),(_binary '±ƒ\Òû/—@É®³.s>','2027-03-31','20:00:00',12,70000.00,'2026-11-01','16:00:00','ACTIVE',_binary 'Ğ·™©¢/Jÿ€yª\é °',_binary 'Ojt‚™E+°CR¡ˆ\Û	˜'),(_binary '´!º\Û9xLnŠ©ø_™¢5¬','2027-03-31','21:00:00',10,75000.00,'2026-11-01','17:00:00','ACTIVE',_binary '’EÜ…jL„¨µˆ¹H\Ö\ê™',_binary 'Ojt‚™E+°CR¡ˆ\Û	˜'),(_binary '×±U`,,K£>\Ãt1<\Ò','2027-03-31','15:00:00',8,130000.00,'2026-11-01','09:00:00','ACTIVE',_binary '¯\á`\êKN„\r)/\Ë\ç',_binary 'v¤\ÇJùHr˜€E=­É'),(_binary '\ô\ê\Åg\'K›—JAq¦) ','2027-03-31','14:00:00',12,80000.00,'2026-11-01','08:00:00','ACTIVE',_binary '¨³,[\Ş:@‘†\0\ßf²§w',_binary 'ê™˜M\é”\Ø[+>T‘'),(_binary 'ıˆ¨=cD\÷»~za\Z;-','2027-03-31','19:00:00',15,60000.00,'2026-11-01','15:00:00','ACTIVE',_binary '’EÜ…jL„¨µˆ¹H\Ö\ê™',_binary 'M_ık6HÊ°€Ÿ\Ë|\í¦'),(_binary 'ÿqˆP<\'G ¿k\É€—“;','2027-03-31','13:00:00',10,100000.00,'2026-11-01','07:00:00','ACTIVE',_binary '4Õ§g\ÂACëŒ”¶q6¾?\æ',_binary '\Ò†£\ê\ÑN3©0- E—ú\á');
/*!40000 ALTER TABLE `schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `email` varchar(150) NOT NULL,
  `last_name` varchar(150) DEFAULT NULL,
  `name` varchar(150) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `status` enum('ACTIVE','DELETED','INACTIVE','SUSPENDED') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (_binary '½[–\Ş1Iœ-Ft’_„','2026-10-18 02:29:26.572331','camila.torres@example.com','Torres','Camila','$2a$10$ZnZG0sU7rPovwzj.5gjzhOf3tYbTM.FjLH39n9us6BFRxGhG6MK3.','+573001234508','ACTIVE'),(_binary '0»\Ò*DÜ\öo†/\Û\Ü','2026-10-18 02:30:43.683726','felipe.moreno@example.com','Moreno','Felipe','$2a$10$oF7tm5chCyRj.5FWAT3RIexmdEOPDBg6ZYmt44zsD9HRmtQ8CfZnS','+573001234515','ACTIVE'),(_binary '1¾DV@‹«>¸` \ÄB','2026-10-18 02:30:12.457630','isabella.ortiz@example.com','Ortiz','Isabella','$2a$10$Vnd30QuKUkR.H8ZVx8QjwO/ISdL5fZxlLmBanDnncJtuNWfWrwecG','+573001234512','ACTIVE'),(_binary '4O=‹\ÉGCß\Ü@\ÉgÆŒ','2026-10-18 02:28:43.487974','laura.martinez@example.com','MartÃ­nez','Laura','$2a$10$ebt61d/4TycLm3Gz2jS5Iurb81wcveXhGCWvSRT0INPw3sh/zFqN.','+573001234504','ACTIVE'),(_binary 'NLOÒ²N{·¤ÿd\ê\÷','2026-10-18 02:30:20.941390','mateo.mendoza@example.com','Mendoza','Mateo','$2a$10$8dNi.ZlsxCE0gOLnr/NgF.DWcR./7rpQMVVFejCWAzEJGuDOfSc5y','+573001234513','ACTIVE'),(_binary 'Rµ\ö¤Š`E\î¦USr\É4e`','2026-10-18 02:30:35.116226','daniela.jimenez@example.com','JimÃ©nez','Daniela','$2a$10$lChmQ2yuaTTFhqMWCftJauBOfzgaQsLVYqmtA0Msaul1tsXksMX0O','+573001234514','ACTIVE'),(_binary 'Y\İ;v™úI4Ÿ$\Ö2\Í\Ü&','2026-10-18 02:29:16.544492','santiago.garcia@example.com','GarcÃ­a','Santiago','$2a$10$XM9FE77dkOeUKyyMq6Sr8ed11UO7BGV6AEeA642f.vSa8eZzlACa2','+573001234507','ACTIVE'),(_binary 'u6ü\Z¹G\î§\'\Z\ë`\÷\r','2026-10-18 02:28:35.800398','andres.rodriguez@example.com','RodrÃ­guez','AndrÃ©s','$2a$10$WBbtLJs9.Y.fS0dZPaHGTecx6O9oLHntCM6jY.Cxj3gJwejfKOxGK','+573001234503','ACTIVE'),(_binary 'ƒšJ\ãz‡Hn¶‰QiRy','2026-10-18 02:28:58.001149','juan.hernandez@example.com','HernÃ¡ndez','Juan','$2a$10$NzTghF/pfA4JD.00MB39ZOHQBEnB6UqlWwMHX7SIVICmNvZa/2NG6','+573001234505','ACTIVE'),(_binary '–Ğ¯¡\Û}EÌ‡ú·\Ğo\'j','2026-10-18 02:29:35.774677','daniel.ramirez@example.com','RamÃ­rez','Daniel','$2a$10$dwl/ygFgLePykTvMNw.NauN5VuB43/MIzvgWwIK35K8DSulgCJX0m','+573001234509','ACTIVE'),(_binary 'µq\Åh‚N$ˆ7\Ò\æDXc','2026-10-18 02:28:23.742959','maria.gomez@example.com','GÃ³mez','MarÃ­a','$2a$10$rfNB07aoB2zkD53nny4Emuq6z/LmVCWlJlSr9FD59wB9.PDdqtsXC','+573001234502','ACTIVE'),(_binary '¹±3j\àIı˜¡\÷e‹!n','2026-10-18 02:29:52.522389','sofia.vargas@example.com','Vargas','SofÃ­a','$2a$10$IjMy/M69V4q5mtqf86i.3eZM2aX2UKX0obtPedEoRvt8f/w7Cwg2.','+573001234510','ACTIVE'),(_binary '\ó\ëÜ£bLÄ›ÿ\ÉÙ¯DÑ˜','2026-10-18 02:30:02.402276','sebastian.castro@example.com','Castro','SebastiÃ¡n','$2a$10$q.ZV/j4iamPAQZBr6ir4WeDEWzPW6MElhXLi/etF17I.aMmO/pwIy','+573001234511','ACTIVE'),(_binary '\õpgn@ë€’ªPH','2026-10-18 02:29:07.293301','valentina.lopez@example.com','LÃ³pez','Valentina','$2a$10$rXHOetEzdAwFOA0o3GDRDOLSwBTFkhhNklw5DzK2nRJ6fipUFAgWi','+573001234506','ACTIVE'),(_binary '\õjaF°H@”7.\Z¶K‚','2026-10-18 02:28:10.632323','carlos.perez@example.com','PÃ©rez','Carlos','$2a$10$KRId.Uza8ielBTgkdqKjNOot51AWMZa88wHK2W1id/0ALxfY1yOh2','+573001234501','ACTIVE');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

-- ============================================================
-- Datos adicionales de prueba
--   correo:     resenas@example.com
--   contraseÃ±a: Colombiando123
-- Incluye: reserva COMPLETED (para reseÃ±ar) y reserva CONFIRMED
-- con 2 acompaÃ±antes (para probar el pop up de tours activos).
-- ============================================================

SET @user_id     = UUID_TO_BIN(UUID());
SET @schedule_id = UUID_TO_BIN(UUID());
SET @instance_id = UUID_TO_BIN(UUID());
SET @booking_ok  = UUID_TO_BIN(UUID());
SET @booking_act = UUID_TO_BIN(UUID());

SET @place_id = (SELECT id FROM places WHERE name = 'Ensenada Coral Secreto' LIMIT 1);
SET @guide_id = (SELECT id FROM guides WHERE name = 'Natalia' LIMIT 1);

INSERT INTO users (id, created_at, email, last_name, name, password, phone, status)
VALUES (
  @user_id, NOW(6), 'resenas@example.com', 'Prueba', 'Viajero',
  '$2a$10$sma4hfqkjCyJO/IvCryGc.WpTtbwAU5AIkfx9VnymeaJzRVipkzd6',
  '3001234599', 'ACTIVE'
);

-- Horario e instancia pasados (schedule_instances tiene UNIQUE(schedule_id))
INSERT INTO schedules
  (id, end_date, end_time, max_capacity, price_per_person, start_date, start_time, status, guide_id, place_id)
VALUES
  (@schedule_id, '2026-09-03', '14:00:00', 10, 120000.00, '2026-09-01', '08:00:00', 'ACTIVE', @guide_id, @place_id);

-- CANCELLED para que no salga en el catalogo publico (solo lista ACTIVE)
INSERT INTO schedule_instances (id, available_capacity, `date`, max_capacity, state, schedule_id)
VALUES (@instance_id, 8, '2026-09-02', 10, 'CANCELLED', @schedule_id);

-- Reserva COMPLETED + pago (el usuario puede calificarla)
INSERT INTO bookings (id, created_at, num_people, status, total_price, instance_id, user_id)
VALUES (@booking_ok, '2026-08-20 10:00:00.000000', 2, 'COMPLETED', 240000.00, @instance_id, @user_id);

INSERT INTO payments (id, amount_to_pay, created_at, currency, payment_method, status, booking_id)
VALUES (UUID_TO_BIN(UUID()), 240000.00, '2026-08-20 10:05:00.000000', 'COP', 'CARD', 'COMPLETED', @booking_ok);

-- Reserva CONFIRMED (3 personas) en una instancia futura del mismo lugar
SET @future_instance = (
  SELECT si.id
  FROM schedule_instances si
  JOIN schedules s ON s.id = si.schedule_id
  WHERE s.place_id = @place_id
    AND si.state = 'ACTIVE'
    AND si.`date` > CURDATE()
    AND si.available_capacity >= 3
  ORDER BY si.`date`
  LIMIT 1
);

INSERT INTO bookings (id, created_at, num_people, status, total_price, instance_id, user_id)
SELECT @booking_act, NOW(6), 3, 'CONFIRMED', s.price_per_person * 3, si.id, @user_id
FROM schedule_instances si
JOIN schedules s ON s.id = si.schedule_id
WHERE si.id = @future_instance;

UPDATE schedule_instances
SET available_capacity = available_capacity - 3
WHERE id = @future_instance;

INSERT INTO payments (id, amount_to_pay, created_at, currency, payment_method, status, booking_id)
SELECT UUID_TO_BIN(UUID()), total_price, NOW(6), 'COP', 'TRANSFER', 'COMPLETED', id
FROM bookings WHERE id = @booking_act;

INSERT INTO booking_escorts (id, birth_date, document_type, id_number, last_name, name, booking_id)
VALUES
  (UUID_TO_BIN(UUID()), '1992-04-13', 'CC', '1056743001', 'Prueba', 'Marcela', @booking_act),
  (UUID_TO_BIN(UUID()), '2012-09-30', 'TI', '1100200300', 'Prueba', 'Samuel',  @booking_act);

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-20 21:18:25
