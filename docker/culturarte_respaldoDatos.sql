-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: culturarte
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `categoriaPadre_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKliaexxwvcnq95gwhl053bdie4` (`categoriaPadre_id`),
  CONSTRAINT `FKliaexxwvcnq95gwhl053bdie4` FOREIGN KEY (`categoriaPadre_id`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Musica',NULL),(2,'Teatro',NULL),(3,'Rock',1),(4,'Jazz',1);
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaboracion`
--

DROP TABLE IF EXISTS `colaboracion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colaboracion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fechaHora` datetime(6) DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `tipoRetorno` enum('ENTRADAS_GRATIS','PORCENTAJE_GANANCIAS') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `colaborador_id` bigint DEFAULT NULL,
  `propuesta_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa6hm3jrc9p7yhs5s2mnxhu0xp` (`colaborador_id`),
  KEY `FKi4x1d0g6alg6ye71j80tdi556` (`propuesta_id`),
  CONSTRAINT `FKa6hm3jrc9p7yhs5s2mnxhu0xp` FOREIGN KEY (`colaborador_id`) REFERENCES `colaborador` (`id`),
  CONSTRAINT `FKi4x1d0g6alg6ye71j80tdi556` FOREIGN KEY (`propuesta_id`) REFERENCES `propuestas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaboracion`
--

LOCK TABLES `colaboracion` WRITE;
/*!40000 ALTER TABLE `colaboracion` DISABLE KEYS */;
INSERT INTO `colaboracion` VALUES (1,'2025-08-30 18:00:00.000000',1000,'ENTRADAS_GRATIS',1,1),(2,'2025-08-31 12:00:00.000000',500,'PORCENTAJE_GANANCIAS',2,1),(3,'2025-08-31 14:30:00.000000',300,'ENTRADAS_GRATIS',1,2);
/*!40000 ALTER TABLE `colaboracion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaborador`
--

DROP TABLE IF EXISTS `colaborador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colaborador` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKr3pgi6hfv64f3xa6lsxlnlo0t` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaborador`
--

LOCK TABLES `colaborador` WRITE;
/*!40000 ALTER TABLE `colaborador` DISABLE KEYS */;
INSERT INTO `colaborador` VALUES (1),(2);
/*!40000 ALTER TABLE `colaborador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proponente`
--

DROP TABLE IF EXISTS `proponente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proponente` (
  `bio` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sitioWeb` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKdvydvoe2hkxcjvw3w9riwj4xo` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proponente`
--

LOCK TABLES `proponente` WRITE;
/*!40000 ALTER TABLE `proponente` DISABLE KEYS */;
INSERT INTO `proponente` VALUES ('Amante del arte y la cultura','Calle Falsa 123','www.carlosarte.com',3),('Organizadora de eventos culturales','Av. Siempre Viva 456','www.lauraeventos.com',4);
/*!40000 ALTER TABLE `proponente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propuesta_tiposretorno`
--

DROP TABLE IF EXISTS `propuesta_tiposretorno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propuesta_tiposretorno` (
  `Propuesta_id` bigint NOT NULL,
  `tiposRetorno` enum('ENTRADAS_GRATIS','PORCENTAJE_GANANCIAS') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  KEY `FKdpm0yfawyg4wfcqql9ui0qsgf` (`Propuesta_id`),
  CONSTRAINT `FKdpm0yfawyg4wfcqql9ui0qsgf` FOREIGN KEY (`Propuesta_id`) REFERENCES `propuestas` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propuesta_tiposretorno`
--

LOCK TABLES `propuesta_tiposretorno` WRITE;
/*!40000 ALTER TABLE `propuesta_tiposretorno` DISABLE KEYS */;
INSERT INTO `propuesta_tiposretorno` VALUES (1,'ENTRADAS_GRATIS'),(1,'PORCENTAJE_GANANCIAS'),(2,'ENTRADAS_GRATIS');
/*!40000 ALTER TABLE `propuesta_tiposretorno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propuestaestado`
--

DROP TABLE IF EXISTS `propuestaestado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propuestaestado` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` enum('INGRESADA','PUBLICADA','EN_FINANCIACION','FINANCIADA','NO_FINANCIADA','CANCELADA') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fechaCambio` date DEFAULT NULL,
  `propuesta_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlec3dnm880enuwthnsc72fxj7` (`propuesta_id`),
  CONSTRAINT `FKlec3dnm880enuwthnsc72fxj7` FOREIGN KEY (`propuesta_id`) REFERENCES `propuestas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propuestaestado`
--

LOCK TABLES `propuestaestado` WRITE;
/*!40000 ALTER TABLE `propuestaestado` DISABLE KEYS */;
INSERT INTO `propuestaestado` VALUES (1,'EN_FINANCIACION','2025-08-30',1),(2,'PUBLICADA','2025-08-28',2);
/*!40000 ALTER TABLE `propuestaestado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propuestas`
--

DROP TABLE IF EXISTS `propuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propuestas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estadoActual` enum('INGRESADA','PUBLICADA','EN_FINANCIACION','FINANCIADA','NO_FINANCIADA','CANCELADA') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fechaPrevista` date DEFAULT NULL,
  `fechaPublicacion` date DEFAULT NULL,
  `imagen` longblob,
  `lugar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `montoNecesario` double DEFAULT NULL,
  `precioEntrada` double DEFAULT NULL,
  `titulo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `categoria_id` bigint DEFAULT NULL,
  `proponente_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e1rcyx6bt2uedriyoa782j9w` (`titulo`),
  KEY `FK1f3wa9igj38ja2f97sydimtoe` (`categoria_id`),
  KEY `FK1466kxt2u3p1prtqyovlkdwmw` (`proponente_id`),
  CONSTRAINT `FK1466kxt2u3p1prtqyovlkdwmw` FOREIGN KEY (`proponente_id`) REFERENCES `proponente` (`id`),
  CONSTRAINT `FK1f3wa9igj38ja2f97sydimtoe` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propuestas`
--

LOCK TABLES `propuestas` WRITE;
/*!40000 ALTER TABLE `propuestas` DISABLE KEYS */;
INSERT INTO `propuestas` VALUES (1,'Concierto de Rock con bandas locales','EN_FINANCIACION','2025-09-15',NULL,NULL,'Auditorio Central',5000,50,'Concierto Rock',3,3),(2,'Obra de teatro infantil','PUBLICADA','2025-10-10',NULL,NULL,'Teatro Municipal',3000,30,'Obra de Teatro',2,4);
/*!40000 ALTER TABLE `propuestas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seguimientos`
--

DROP TABLE IF EXISTS `seguimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seguimientos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `seguido_id` bigint DEFAULT NULL,
  `seguidor_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb5qfn7w373khd24c4799k9d4j` (`seguido_id`),
  KEY `FKe3hco1dfe8nspo78oxk25kug1` (`seguidor_id`),
  CONSTRAINT `FKb5qfn7w373khd24c4799k9d4j` FOREIGN KEY (`seguido_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKe3hco1dfe8nspo78oxk25kug1` FOREIGN KEY (`seguidor_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seguimientos`
--

LOCK TABLES `seguimientos` WRITE;
/*!40000 ALTER TABLE `seguimientos` DISABLE KEYS */;
/*!40000 ALTER TABLE `seguimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `tipo_usuario` varchar(31) COLLATE utf8mb4_unicode_ci NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `correo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `imagen` longblob,
  `nickname` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cdmw5hxlfj78uf4997i3qyyw5` (`correo`),
  UNIQUE KEY `UK_rhly81kdno827noroj2qabux` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES ('COLABORADOR',1,'Gomez','juan.gomez@email.com','1995-03-12',NULL,'juanG','Juan','1234'),('COLABORADOR',2,'Perez','ana.perez@email.com','1990-07-22',NULL,'anaP','Ana','1234'),('PROPONENTE',3,'Lopez','carlos.lopez@email.com','1988-11-05',NULL,'carlosL','Carlos','1234'),('PROPONENTE',4,'Martinez','laura.martinez@email.com','1992-02-17',NULL,'lauraM','Laura','1234');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_propuestas`
--

DROP TABLE IF EXISTS `usuarios_propuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_propuestas` (
  `Usuario_id` bigint NOT NULL,
  `propuestasFavoritas_id` bigint NOT NULL,
  KEY `FKk89w21ivxilg6k32jsawcvxt8` (`propuestasFavoritas_id`),
  KEY `FKqm7trqlcvvftxwb1s3d6xl79k` (`Usuario_id`),
  CONSTRAINT `FKk89w21ivxilg6k32jsawcvxt8` FOREIGN KEY (`propuestasFavoritas_id`) REFERENCES `propuestas` (`id`),
  CONSTRAINT `FKqm7trqlcvvftxwb1s3d6xl79k` FOREIGN KEY (`Usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_propuestas`
--

LOCK TABLES `usuarios_propuestas` WRITE;
/*!40000 ALTER TABLE `usuarios_propuestas` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuarios_propuestas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-31  2:27:55
