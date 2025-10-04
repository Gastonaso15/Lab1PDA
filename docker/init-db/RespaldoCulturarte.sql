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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Teatro',NULL),(2,'Teatro Dramático',1),(3,'Teatro Musical',1),(4,'Comedia',1),(5,'Stand-up',4),(6,'Literatura',NULL),(7,'Música',NULL),(8,'Festival',7),(9,'Concierto',7),(10,'Cine',NULL),(11,'Cine al Aire Libre',10),(12,'Cine a Pedal',10),(13,'Danza',NULL),(14,'Ballet',13),(15,'Flamenco',13),(16,'Carnaval',NULL),(17,'Murga',16),(18,'Humoristas',16),(19,'Parodistas',16),(20,'Lubolos',16),(21,'Revista',16);
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaboraciones`
--

DROP TABLE IF EXISTS `colaboraciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colaboraciones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fechaHora` datetime(6) DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `tipoRetorno` enum('ENTRADAS_GRATIS','PORCENTAJE_GANANCIAS') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `colaborador_id` bigint DEFAULT NULL,
  `propuesta_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK15ttm68todwj4f5byy5jb1rv6` (`colaborador_id`),
  KEY `FK86t6j8njj8j8dk78ebjxyrah` (`propuesta_id`),
  CONSTRAINT `FK15ttm68todwj4f5byy5jb1rv6` FOREIGN KEY (`colaborador_id`) REFERENCES `colaboradores` (`id`),
  CONSTRAINT `FK86t6j8njj8j8dk78ebjxyrah` FOREIGN KEY (`propuesta_id`) REFERENCES `propuestas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaboraciones`
--

LOCK TABLES `colaboraciones` WRITE;
/*!40000 ALTER TABLE `colaboraciones` DISABLE KEYS */;
INSERT INTO `colaboraciones` VALUES (1,'2017-05-20 14:30:00.000000',50000,'PORCENTAJE_GANANCIAS',12,1),(2,'2017-05-24 17:25:00.000000',50000,'PORCENTAJE_GANANCIAS',10,1),(3,'2017-05-30 18:30:00.000000',50000,'PORCENTAJE_GANANCIAS',16,1),(4,'2017-06-30 14:25:00.000000',200000,'PORCENTAJE_GANANCIAS',11,2),(5,'2017-07-01 18:05:00.000000',500,'ENTRADAS_GRATIS',20,2),(6,'2017-07-07 17:45:00.000000',600,'ENTRADAS_GRATIS',18,2),(7,'2017-07-10 14:35:00.000000',50000,'PORCENTAJE_GANANCIAS',12,2),(8,'2017-07-15 09:45:00.000000',50000,'PORCENTAJE_GANANCIAS',13,2),(9,'2017-08-01 07:40:00.000000',200000,'PORCENTAJE_GANANCIAS',11,3),(10,'2017-08-03 09:25:00.000000',80000,'PORCENTAJE_GANANCIAS',13,3),(11,'2017-08-05 16:50:00.000000',50000,'ENTRADAS_GRATIS',14,4),(12,'2017-08-10 15:50:00.000000',120000,'PORCENTAJE_GANANCIAS',12,4),(13,'2017-08-15 19:30:00.000000',120000,'ENTRADAS_GRATIS',15,4),(14,'2017-08-13 04:58:00.000000',100000,'PORCENTAJE_GANANCIAS',13,5),(15,'2017-08-14 11:25:00.000000',200000,'PORCENTAJE_GANANCIAS',11,5),(16,'2017-08-15 04:48:00.000000',30000,'ENTRADAS_GRATIS',15,6),(17,'2017-08-17 15:30:00.000000',150000,'PORCENTAJE_GANANCIAS',11,6);
/*!40000 ALTER TABLE `colaboraciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaboradores`
--

DROP TABLE IF EXISTS `colaboradores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colaboradores` (
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK87fqsiu87up9li4t798j0d41i` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaboradores`
--

LOCK TABLES `colaboradores` WRITE;
/*!40000 ALTER TABLE `colaboradores` DISABLE KEYS */;
INSERT INTO `colaboradores` VALUES (10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20);
/*!40000 ALTER TABLE `colaboradores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proponentes`
--

DROP TABLE IF EXISTS `proponentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proponentes` (
  `bio` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sitioWeb` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKnnskygcfx4pnhrw762b0it9hy` FOREIGN KEY (`id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proponentes`
--

LOCK TABLES `proponentes` WRITE;
/*!40000 ALTER TABLE `proponentes` DISABLE KEYS */;
INSERT INTO `proponentes` VALUES ('Horacio Rubino Torres nace el 25 de febrero de 1962, es conductor, actor y libretista. Debuta en 1982 en carnaval en Los \"Klaper\'s\", donde estuvo cuatro años, actuando y libretando. Luego para \"Gaby\'s\" (6 años), escribió en categoría revistas y humoristas y desde el comienzo y hasta el presente en su propio conjunto Momosapiens.','18 de Julio 1234','https://twitter.com/horacionubino',1),('Martín Buscaglia (Montevideo, 1972) es un artista, músico, compositor y productor uruguayo. Tanto con su banda (\"Los Bochamakers\") como en su formato \"Hombre orquesta\", o solo con su guitarra, ha recorrido el mundo tocando entre otros países en España, Estados Unidos, Inglaterra, Francia, Australia, Brasil, Colombia, Argentina, Chile, Paraguay, México y Uruguay.','Colonia 4321','http://www.martinbuscaglia.com/',2),('En 1972 ingresó a la Escuela de Arte Dramático del teatro El Galpón. Participó en más de treinta obras teatrales y varios largomarcajes. Integró el elenco estable de Radioteatro del Sodre, y en 2006 fue asesor de su Consejo Directivo. Como actor recibió un múltiplos reconocimientos: cuatro premios Florencio, premio al mejor actor extranjero del Festival de Miami y premio Melpr Actor de Cine 2008.','Graí. Flores 5645',NULL,3),('Tabaré Cardozo (Montevideo, 24 de julio de 1971) es un cantante, compositor y murguista uruguayo; conocido por su participación en la murga Agarrate Catalina, conjunto que fundó junto a su hermano Yamando y Carlos Tanco en el año 2001.','Santiago Rivas 1212','https://www.facebook.com/Tabar%C3%A9-Cardozo-55179094281/?ref=br_rs',4),('Nace en el año 1947 en el convenillo \"Medio Mundo\" ubicado en pleno Barrio Sur. Es heredero parcialmente- junto al resto de sus hermanos- de la Comparsa \"Morenada\" (inactiva desde el fallecimiento de Juan Ángel Silva), en 1999 forma su propia Comparsa de negros y lubolos \"Cuareim 1080\". Director responsable, compositor y cantante de la misma.','Br. Artigas 4567','https://www.facebook.com/C1080?ref=br_rs',5),(NULL,'Benito Blanco 4321',NULL,6),(NULL,'Emilio Frugoni 1138 Ap. 02','http://www.electocine.com',7),(NULL,'Paraguay 1423',NULL,8),('Queremos ser vistos y reconocidos como una organización: referente en divulgación científica con un fuerte espíritu didáctico y divertido, a través de acciones coordinadas con otros divulgadores científicos, que permitan establecer puentes de comunicación. Impulsora en la generación de espacios de democratización y apropiación social del conocimiento científico.','8 de Octubre 1429','https://bardocientifico.com/',9);
/*!40000 ALTER TABLE `proponentes` ENABLE KEYS */;
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
INSERT INTO `propuesta_tiposretorno` VALUES (1,'PORCENTAJE_GANANCIAS'),(2,'ENTRADAS_GRATIS'),(2,'PORCENTAJE_GANANCIAS'),(3,'PORCENTAJE_GANANCIAS'),(4,'ENTRADAS_GRATIS'),(4,'PORCENTAJE_GANANCIAS'),(5,'PORCENTAJE_GANANCIAS'),(6,'ENTRADAS_GRATIS'),(6,'PORCENTAJE_GANANCIAS'),(7,'ENTRADAS_GRATIS'),(8,'ENTRADAS_GRATIS');
/*!40000 ALTER TABLE `propuesta_tiposretorno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propuestas`
--

DROP TABLE IF EXISTS `propuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propuestas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `estadoActual` enum('INGRESADA','PUBLICADA','EN_FINANCIACION','FINANCIADA','NO_FINANCIADA','CANCELADA') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fechaPrevista` date DEFAULT NULL,
  `fechaPublicacion` date DEFAULT NULL,
  `imagen` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lugar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `montoNecesario` double DEFAULT NULL,
  `precioEntrada` double DEFAULT NULL,
  `titulo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `categoria_id` bigint DEFAULT NULL,
  `proponente_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e1rcyx6bt2uedriyoa782j9w` (`titulo`),
  KEY `FK1f3wa9igj38ja2f97sydimtoe` (`categoria_id`),
  KEY `FKe2xv8bhc6yjkloshpgok38qjo` (`proponente_id`),
  CONSTRAINT `FK1f3wa9igj38ja2f97sydimtoe` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `FKe2xv8bhc6yjkloshpgok38qjo` FOREIGN KEY (`proponente_id`) REFERENCES `proponentes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propuestas`
--

LOCK TABLES `propuestas` WRITE;
/*!40000 ALTER TABLE `propuestas` DISABLE KEYS */;
INSERT INTO `propuestas` VALUES (1,'El 16 de Diciembre a la hora 20 se proyectará la película \"Clever\", en el Jardín Botánico (Av. 19 de Abril 1181) en el marco de las actividades realizadas por el ciclo Cultural al Aire Libre. El largometraje uruguayo de ficción Clever es dirigido por Federico Borgia y Guillermo Madeiro. Es apto para mayores de 15 años.','CANCELADA','2017-09-16','2017-05-17',NULL,'Jardín Botánico',150000,200,'Cine en el Botánico',11,7),(2,'MOMOSAPIENS presenta \"Religiosamente\". Mediante dos parodias y un hilo conductor que aborda la temática de la religión Momosapiens, mediante el humor y la reflexión, hilvana una historia que muestra al hombre inmerso en el tema religioso. El libreto está escrito utilizando diferentes lenguajes de humor, dando una visión satírica y reflexiva desde distintos puntos de vista, logrando mediante situaciones patódicas narrar una propuesta pública de arte carnavalero.','FINANCIADA','2017-10-07','2017-06-20','uploads/propuestas/Imagen1.jpg','Teatro de Verano',300000,300,'Religiosamente',19,1),(3,'El Pimiento Indomable, formación compuesta por KiNo Veneno y el uruguayo Martín Buscaglia, presentará este 19 de Octubre, su primer trabajo. Bajo un título homónimo al del grupo, es un disco que según los propios protagonistas \"no se parece al de ninguno de los dos por separado. Entre los títulos que se podrán escuchar se encuentran \"Nadador salvador\", \"América es más grande\", \"Pescátic Enroscado\" o \"La reina del placer\".','EN_FINANCIACION','2017-10-19','2017-07-31','uploads/propuestas/Imagen2.jpg','Teatro Solís',400000,400,'El Pimiento Indomable',9,2),(4,'La edición 2017 del Pilsen Rock se celebrará el 21 de Octubre en la Rural del Prado y contará con la participación de más de 15 bandas nacionales. Quienes no puedan trasladarse al lugar, tendrán la posibilidad de disfrutar los shows a través de Internet, así como entrevistas en vivo a los músicos una de finalizados los conciertos.','EN_FINANCIACION','2017-10-21','2017-08-01','uploads/propuestas/Imagen3.jpg','Rural de Prado',900000,1000,'Pilsen Rock',8,8),(5,'Romeo y Julieta de Kenneth MacMillan, uno de los ballets favoritos del director artístico Julio Bocca, se presentará nuevamente el 5 de Noviembre en el Auditorio Nacional del Sodre. Basada en la obra homónima de William Shakespeare, Romeo y Julieta es considerada la coreografía maestra del MacMillan. La producción de vestuario y escenografía se realizó en los Talleres del Auditorio Adela Reta, sobre los diseños originales.','EN_FINANCIACION','2017-11-05','2017-08-10','uploads/propuestas/Imagen4.jpg','Auditorio Nacional del Sodre',750000,800,'Romeo y Julieta',14,6),(6,'La Cataluña presenta el espectáculo \"Un Dia de Julio\" en Landia. Un hombre misterioso y solitario vive encerrado entre las cuatro paredes de su casa. Interna, con sus cortes extravagantes, cambiar el mundo exterior que le resulta inhabitable. Un día de Julio sucederá algo que cambiará su vida y la de su entorno para siempre.','EN_FINANCIACION','2017-11-16','2017-08-12','uploads/propuestas/Imagen5.jpg','Landia',300000,650,'Un día de Julio',17,4),(7,'Vuelve unas de las producciones de El Galpón más aclamadas de los últimos tiempos. Esta obra se ha presentado en Miami, Nueva York, Washington, México, Guadalajara, Río de Janeiro y La Habana. En nuestro país, El Lazarillo de Tormes fue nominado en los rubros mejor espectáculo y mejor dirección a los Premios Florencio 1995, obteniendo su protagonista Héctor Guido el Florencio a Mejor actor de ese año.','PUBLICADA','2017-12-03','2017-08-20',NULL,'Teatro el Galpón',175000,350,'El Lazarillo de Tormes',2,3),(8,'El 10 de Diciembre se presentará Bardo Científico en la FING. El humor puede ser usado como una herramienta importante para el aprendizaje y la democratización de la ciencia, los monólogos científicos son una forma didáctica de apropiación del conocimiento científico y contribuyen a que el público aprenda ciencia de forma amena. Los invitamos a pasar un rato divertido, en un espacio en el cual aprenderán cosas de la ciencia que los sorprenderán. ¡Los esperamos!','INGRESADA','2017-12-10',NULL,NULL,'Anfiteatro Edificio \"José Luis Massera\"',100000,200,'Bardo en la FING',5,9);
/*!40000 ALTER TABLE `propuestas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `propuestas_estados`
--

DROP TABLE IF EXISTS `propuestas_estados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `propuestas_estados` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `estado` enum('INGRESADA','PUBLICADA','EN_FINANCIACION','FINANCIADA','NO_FINANCIADA','CANCELADA') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fechaCambio` date DEFAULT NULL,
  `propuesta_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8cvkjv9ola40nsqd0qe6txh1y` (`propuesta_id`),
  CONSTRAINT `FK8cvkjv9ola40nsqd0qe6txh1y` FOREIGN KEY (`propuesta_id`) REFERENCES `propuestas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `propuestas_estados`
--

LOCK TABLES `propuestas_estados` WRITE;
/*!40000 ALTER TABLE `propuestas_estados` DISABLE KEYS */;
INSERT INTO `propuestas_estados` VALUES (1,'INGRESADA','2017-05-15',1),(2,'PUBLICADA','2017-05-17',1),(3,'EN_FINANCIACION','2017-05-20',1),(4,'FINANCIADA','2017-05-30',1),(5,'CANCELADA','2017-06-15',1),(6,'INGRESADA','2017-06-18',2),(7,'PUBLICADA','2017-06-20',2),(8,'EN_FINANCIACION','2017-06-30',2),(9,'FINANCIADA','2017-07-15',2),(10,'INGRESADA','2017-07-26',3),(11,'PUBLICADA','2017-07-31',3),(12,'EN_FINANCIACION','2017-08-01',3),(13,'INGRESADA','2017-07-30',4),(14,'PUBLICADA','2017-08-01',4),(15,'EN_FINANCIACION','2017-08-05',4),(16,'INGRESADA','2017-08-04',5),(17,'PUBLICADA','2017-08-10',5),(18,'EN_FINANCIACION','2017-08-13',5),(19,'INGRESADA','2017-08-06',6),(20,'PUBLICADA','2017-08-12',6),(21,'EN_FINANCIACION','2017-08-15',6),(22,'INGRESADA','2017-08-18',7),(23,'PUBLICADA','2017-08-20',7),(24,'INGRESADA','2017-08-23',8);
/*!40000 ALTER TABLE `propuestas_estados` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seguimientos`
--

LOCK TABLES `seguimientos` WRITE;
/*!40000 ALTER TABLE `seguimientos` DISABLE KEYS */;
INSERT INTO `seguimientos` VALUES (183,3,1),(184,7,1),(185,9,1),(186,4,2),(187,5,2),(188,8,2),(189,2,3),(190,6,3),(191,1,4),(192,5,4),(193,1,5),(194,2,6),(195,7,6),(196,3,7),(197,9,7),(198,13,8),(199,1,9),(200,16,9),(201,3,10),(202,6,10),(203,7,10),(204,5,11),(205,6,11),(206,8,11),(207,1,12),(208,4,12),(209,5,12),(210,2,13),(211,6,13),(212,7,13),(213,15,14),(214,14,15),(215,7,16),(216,9,16),(217,4,17),(218,5,17),(219,8,17),(220,3,18),(221,14,18),(222,7,19),(223,15,19),(224,6,20),(225,8,20),(226,13,20);
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
  `imagen` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nickname` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cdmw5hxlfj78uf4997i3qyyw5` (`correo`),
  UNIQUE KEY `UK_rhly81kdno827noroj2qabux` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES ('Proponente',1,'Rubino','horacio.rubino@guambia.com.uy','1962-02-25','uploads/usuarios/ImagenUP1.jpg','hrubino','Horacio',NULL),('Proponente',2,'Buscaglia','Martin.bus@agadu.org.uy','1972-06-14','uploads/usuarios/ImagenUP2.jpg','mbusca','Martín',NULL),('Proponente',3,'Guido','hector.gui@elgalpon.org.uy','1954-01-07','uploads/usuarios/ImagenUP5.jpg','hectorg','Héctor',NULL),('Proponente',4,'Cardozo','tabare.car@agadu.org.uy','1971-07-24','uploads/usuarios/ImagenUP3.jpg','tabarec','Tabaré',NULL),('Proponente',5,'Silva','Cachila.sil@c1080.org.uy','1947-01-01','uploads/usuarios/ImagenUP4.jpg','cachilas','Waldemar \"Cachila\"',NULL),('Proponente',6,'Bocca','juliolocca@sodre.com.uy','1967-03-16',NULL,'juliolo','Julio',NULL),('Proponente',7,'Parodi','diego@efectocine.com','1975-01-01',NULL,'diegop','Diego',NULL),('Proponente',8,'Herrera','kairoher@pisemock.com.uy','1840-04-25','uploads/usuarios/ImagenUP6.jpg','kairolo','Kairo',NULL),('Proponente',9,'Bardo','losbardo@bardocientifico.com','1980-10-31','uploads/usuarios/ImagenUP7.jpg','losBardo','Los',NULL),('Colaborador',10,'Henderson','Robin.h@tinglesa.com.uy','1940-08-03',NULL,'robin','Robin',NULL),('Colaborador',11,'Tinelli','mareclot@ideasdelsur.com.ar','1960-04-01','uploads/usuarios/ImagenUC1.jpg','mareclot','Marcelo',NULL),('Colaborador',12,'Novick','edgardo@novick.com.uy','1952-07-17','uploads/usuarios/ImagenUC2.jpg','novick','Edgardo',NULL),('Colaborador',13,'Puglia','puglia@alpanpan.com.uy','1950-01-28','uploads/usuarios/ImagenUC3.jpg','sergiop','Sergio',NULL),('Colaborador',14,'Recoba','chino@trico.org.uy','1976-03-17',NULL,'chino','Alvaro',NULL),('Colaborador',15,'Pacheco','eltony@manya.org.uy','1955-02-14','uploads/usuarios/ImagenUC4.jpg','tonyp','Antonio',NULL),('Colaborador',16,'Jodal','jodal@artech.com.uy','1960-08-09','uploads/usuarios/ImagenUC5.jpg','nicoj','Nicolás',NULL),('Colaborador',17,'Perez','juanp@elpueblo.com','1970-01-01',NULL,'juanP','Juan',NULL),('Colaborador',18,'Gómez','menganog@elpueblo.com','1982-02-02',NULL,'Mengano','Mengano',NULL),('Colaborador',19,'López','pere@elpueblo.com','1985-03-03',NULL,'Perengano','Perengano',NULL),('Colaborador',20,'Jacinta','jacinta@elpueblo.com','1990-04-04',NULL,'Tiajaci','Tía',NULL);
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

-- Dump completed on 2025-09-10  7:40:40
