CREATE DATABASE  IF NOT EXISTS `asistenciasx` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `asistenciasx`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: asistenciasx
-- ------------------------------------------------------
-- Server version	5.6.22-log

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
-- Table structure for table `carrera`
--

DROP TABLE IF EXISTS `carrera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrera` (
  `idcarrera` varchar(10) NOT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`idcarrera`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='abreviacion es un campo unico y puede ser usado como ID, or.... idcarrera que sea la abreviacion.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curso` (
  `idcurso` int(11) NOT NULL,
  `departamento` varchar(255) DEFAULT NULL,
  `sec` varchar(5) DEFAULT NULL,
  `idprofesor` int(11) DEFAULT NULL,
  `idmateria` varchar(10) DEFAULT NULL,
  `idcarrera` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idcurso`),
  KEY `profesorid_idx` (`idprofesor`),
  KEY `materiaid_idx` (`idmateria`),
  KEY `idcarrera_idx` (`idcarrera`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Id = NRC';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `horario`
--

DROP TABLE IF EXISTS `horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `horario` (
  `idhorario` int(11) NOT NULL AUTO_INCREMENT,
  `horaEntrada` int(11) DEFAULT NULL,
  `horaSalida` int(11) DEFAULT NULL,
  `Lun` bit(1) DEFAULT NULL,
  `Mar` bit(1) DEFAULT NULL,
  `Mie` bit(1) DEFAULT NULL,
  `Jue` bit(1) DEFAULT NULL,
  `Vie` bit(1) DEFAULT NULL,
  `Sab` bit(1) DEFAULT NULL,
  `edif` varchar(5) DEFAULT NULL,
  `aula` varchar(5) DEFAULT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `idcurso` int(11) DEFAULT NULL,
  PRIMARY KEY (`idhorario`),
  KEY `idcurso_idx` (`idcurso`)
) ENGINE=InnoDB AUTO_INCREMENT=776 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `horarios`
--

DROP TABLE IF EXISTS `horarios`;
/*!50001 DROP VIEW IF EXISTS `horarios`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `horarios` (
  `idcurso` tinyint NOT NULL,
  `idprofesor` tinyint NOT NULL,
  `Profesor` tinyint NOT NULL,
  `Materia` tinyint NOT NULL,
  `horaEntrada` tinyint NOT NULL,
  `horaSalida` tinyint NOT NULL,
  `lun` tinyint NOT NULL,
  `mar` tinyint NOT NULL,
  `mie` tinyint NOT NULL,
  `jue` tinyint NOT NULL,
  `vie` tinyint NOT NULL,
  `sab` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `horariosporprofesor`
--

DROP TABLE IF EXISTS `horariosporprofesor`;
/*!50001 DROP VIEW IF EXISTS `horariosporprofesor`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `horariosporprofesor` (
  `idcurso` tinyint NOT NULL,
  `nombre` tinyint NOT NULL,
  `horaEntrada` tinyint NOT NULL,
  `horaSalida` tinyint NOT NULL,
  `lun` tinyint NOT NULL,
  `mar` tinyint NOT NULL,
  `mie` tinyint NOT NULL,
  `jue` tinyint NOT NULL,
  `vie` tinyint NOT NULL,
  `sab` tinyint NOT NULL,
  `edif` tinyint NOT NULL,
  `aula` tinyint NOT NULL,
  `idprofesor` tinyint NOT NULL,
  `idhorario` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `materia`
--

DROP TABLE IF EXISTS `materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `materia` (
  `idmateria` varchar(10) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `area` varchar(10) DEFAULT NULL,
  `creditos` int(11) DEFAULT NULL,
  `teoria` int(11) DEFAULT NULL,
  `practica` int(11) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `nivel` varchar(100) DEFAULT NULL,
  `extraordinario` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`idmateria`),
  KEY `Materia` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profesor`
--

DROP TABLE IF EXISTS `profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profesor` (
  `idprofesor` int(11) NOT NULL,
  `nombres` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `celular` varchar(50) DEFAULT NULL,
  `foto` blob,
  `huella` blob,
  `huellavirdi` varchar(12000) DEFAULT NULL,
  PRIMARY KEY (`idprofesor`),
  UNIQUE KEY `Codigo_UNIQUE` (`idprofesor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profesor_acceso`
--

DROP TABLE IF EXISTS `profesor_acceso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profesor_acceso` (
  `idprofesor_acceso` int(11) NOT NULL AUTO_INCREMENT,
  `idprofesor` int(11) NOT NULL,
  `fechayhora` datetime NOT NULL,
  PRIMARY KEY (`idprofesor_acceso`)
) ENGINE=InnoDB AUTO_INCREMENT=349 DEFAULT CHARSET=utf8 COMMENT='This a security table, in case the profesor does not have horario at the time or what ever, this will still record that he touched the sensor, for reference later.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `registro`
--

DROP TABLE IF EXISTS `registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registro` (
  `idregistro` int(11) NOT NULL AUTO_INCREMENT,
  `idhorario` int(11) DEFAULT '0',
  `fechayhora` datetime DEFAULT NULL,
  PRIMARY KEY (`idregistro`),
  KEY `idhorario_idx` (`idhorario`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `registrodeasistencias`
--

DROP TABLE IF EXISTS `registrodeasistencias`;
/*!50001 DROP VIEW IF EXISTS `registrodeasistencias`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `registrodeasistencias` (
  `nombres` tinyint NOT NULL,
  `apellidos` tinyint NOT NULL,
  `nombre` tinyint NOT NULL,
  `aula` tinyint NOT NULL,
  `edif` tinyint NOT NULL,
  `fechayhora` tinyint NOT NULL,
  `idprofesor` tinyint NOT NULL,
  `idhorario` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `horarios`
--

/*!50001 DROP TABLE IF EXISTS `horarios`*/;
/*!50001 DROP VIEW IF EXISTS `horarios`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `horarios` AS select `c`.`idcurso` AS `idcurso`,`p`.`idprofesor` AS `idprofesor`,concat_ws(' ',`p`.`nombres`,`p`.`apellidos`) AS `Profesor`,`m`.`nombre` AS `Materia`,`h`.`horaEntrada` AS `horaEntrada`,`h`.`horaSalida` AS `horaSalida`,`h`.`Lun` AS `lun`,`h`.`Mar` AS `mar`,`h`.`Mie` AS `mie`,`h`.`Jue` AS `jue`,`h`.`Vie` AS `vie`,`h`.`Sab` AS `sab` from (((`curso` `c` join `horario` `h`) join `profesor` `p`) join `materia` `m`) where ((`c`.`idcurso` = `h`.`idcurso`) and (`p`.`idprofesor` = `c`.`idprofesor`) and (`c`.`idmateria` = `m`.`idmateria`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `horariosporprofesor`
--

/*!50001 DROP TABLE IF EXISTS `horariosporprofesor`*/;
/*!50001 DROP VIEW IF EXISTS `horariosporprofesor`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `horariosporprofesor` AS select `c`.`idcurso` AS `idcurso`,`m`.`nombre` AS `nombre`,`h`.`horaEntrada` AS `horaEntrada`,`h`.`horaSalida` AS `horaSalida`,`h`.`Lun` AS `lun`,`h`.`Mar` AS `mar`,`h`.`Mie` AS `mie`,`h`.`Jue` AS `jue`,`h`.`Vie` AS `vie`,`h`.`Sab` AS `sab`,`h`.`edif` AS `edif`,`h`.`aula` AS `aula`,`c`.`idprofesor` AS `idprofesor`,`h`.`idhorario` AS `idhorario` from ((`curso` `c` join `horario` `h`) join `materia` `m`) where ((`h`.`idcurso` = `c`.`idcurso`) and (`c`.`idmateria` = `m`.`idmateria`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `registrodeasistencias`
--

/*!50001 DROP TABLE IF EXISTS `registrodeasistencias`*/;
/*!50001 DROP VIEW IF EXISTS `registrodeasistencias`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `registrodeasistencias` AS select `p`.`nombres` AS `nombres`,`p`.`apellidos` AS `apellidos`,`m`.`nombre` AS `nombre`,`h`.`aula` AS `aula`,`h`.`edif` AS `edif`,`r`.`fechayhora` AS `fechayhora`,`p`.`idprofesor` AS `idprofesor`,`h`.`idhorario` AS `idhorario` from ((((`registro` `r` join `horario` `h`) join `curso` `c`) join `profesor` `p`) join `materia` `m`) where ((`r`.`idhorario` = `h`.`idhorario`) and (`h`.`idcurso` = `c`.`idcurso`) and (`c`.`idprofesor` = `p`.`idprofesor`) and (`c`.`idmateria` = `m`.`idmateria`)) order by `r`.`idregistro` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-31  5:07:06
