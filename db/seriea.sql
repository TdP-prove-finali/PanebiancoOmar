# ************************************************************
# Sequel Pro SQL dump
# Version 5438
#
# https://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.5-10.1.39-MariaDB)
# Database: seriea
# Generation Time: 2020-06-17 13:08:17 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table partite
# ------------------------------------------------------------

DROP TABLE IF EXISTS `partite`;

CREATE TABLE `partite` (
  `id_partita` int(11) NOT NULL,
  `giornata` int(11) NOT NULL,
  `id_squadra_casa` int(11) NOT NULL,
  `id_squadra_trasferta` int(11) NOT NULL,
  `gol_squadra_casa` int(11) DEFAULT '-1',
  `gol_squadra_trasferta` int(11) DEFAULT '-1',
  PRIMARY KEY (`id_partita`),
  KEY `id_squadra_casa` (`id_squadra_casa`),
  KEY `id_squadra_trasferta` (`id_squadra_trasferta`),
  CONSTRAINT `partite_ibfk_1` FOREIGN KEY (`id_squadra_casa`) REFERENCES `squadre` (`id_squadra`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `partite_ibfk_2` FOREIGN KEY (`id_squadra_trasferta`) REFERENCES `squadre` (`id_squadra`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `partite` WRITE;
/*!40000 ALTER TABLE `partite` DISABLE KEYS */;

INSERT INTO `partite` (`id_partita`, `giornata`, `id_squadra_casa`, `id_squadra_trasferta`, `gol_squadra_casa`, `gol_squadra_trasferta`)
VALUES
	(1,25,18,13,-1,-1),
	(2,25,20,4,-1,-1),
	(3,25,1,16,-1,-1),
	(4,25,7,15,-1,-1),
	(5,27,5,3,-1,-1),
	(6,27,10,11,-1,-1),
	(7,27,2,8,-1,-1),
	(8,27,17,4,-1,-1),
	(9,27,20,12,-1,-1),
	(10,27,6,13,-1,-1),
	(11,27,18,19,-1,-1),
	(12,27,7,16,-1,-1),
	(13,27,1,9,-1,-1),
	(14,27,14,15,-1,-1),
	(15,28,8,10,-1,-1),
	(16,28,3,6,-1,-1),
	(17,28,4,18,-1,-1),
	(18,28,9,5,-1,-1),
	(19,28,11,14,-1,-1),
	(20,28,19,1,-1,-1),
	(21,28,15,2,-1,-1),
	(22,28,16,20,-1,-1),
	(23,28,12,17,-1,-1),
	(24,28,13,7,-1,-1),
	(25,29,18,9,-1,-1),
	(26,29,6,8,-1,-1),
	(27,29,7,3,-1,-1),
	(28,29,2,4,-1,-1),
	(29,29,17,11,-1,-1),
	(30,29,20,13,-1,-1),
	(31,29,10,15,-1,-1),
	(32,29,5,16,-1,-1),
	(33,29,1,12,-1,-1),
	(34,29,14,19,-1,-1),
	(35,30,8,18,-1,-1),
	(36,30,16,10,-1,-1),
	(37,30,9,11,-1,-1),
	(38,30,7,2,-1,-1),
	(39,30,4,1,-1,-1),
	(40,30,13,5,-1,-1),
	(41,30,19,6,-1,-1),
	(42,30,3,20,-1,-1),
	(43,30,15,17,-1,-1),
	(44,30,12,14,-1,-1),
	(45,31,10,9,-1,-1),
	(46,31,11,8,-1,-1),
	(47,31,5,4,-1,-1),
	(48,31,6,12,-1,-1),
	(49,31,18,3,-1,-1),
	(50,31,14,13,-1,-1),
	(51,31,1,15,-1,-1),
	(52,31,2,16,-1,-1),
	(53,31,17,19,-1,-1),
	(54,31,20,7,-1,-1),
	(55,32,9,16,-1,-1),
	(56,32,3,14,-1,-1),
	(57,32,8,1,-1,-1),
	(58,32,6,17,-1,-1),
	(59,32,13,2,-1,-1),
	(60,32,5,20,-1,-1),
	(61,32,4,10,-1,-1),
	(62,32,19,15,-1,-1),
	(63,32,12,11,-1,-1),
	(64,32,7,18,-1,-1),
	(65,33,1,3,-1,-1),
	(66,33,15,4,-1,-1),
	(67,33,2,12,-1,-1),
	(68,33,11,13,-1,-1),
	(69,33,10,5,-1,-1),
	(70,33,14,20,-1,-1),
	(71,33,16,8,-1,-1),
	(72,33,19,9,-1,-1),
	(73,33,18,6,-1,-1),
	(74,33,17,7,-1,-1),
	(75,34,20,1,-1,-1),
	(76,34,4,16,-1,-1),
	(77,34,11,2,-1,-1),
	(78,34,13,15,-1,-1),
	(79,34,6,10,-1,-1),
	(80,34,3,17,-1,-1),
	(81,34,5,18,-1,-1),
	(82,34,12,19,-1,-1),
	(83,34,14,7,-1,-1),
	(84,34,8,9,-1,-1),
	(85,35,1,2,-1,-1),
	(86,35,16,11,-1,-1),
	(87,35,13,12,-1,-1),
	(88,35,10,3,-1,-1),
	(89,35,7,5,-1,-1),
	(90,35,15,6,-1,-1),
	(91,35,18,20,-1,-1),
	(92,35,17,14,-1,-1),
	(93,35,19,8,-1,-1),
	(94,35,9,4,-1,-1),
	(95,36,11,1,-1,-1),
	(96,36,14,5,-1,-1),
	(97,36,6,7,-1,-1),
	(98,36,20,9,-1,-1),
	(99,36,2,10,-1,-1),
	(100,36,3,13,-1,-1),
	(101,36,8,15,-1,-1),
	(102,36,12,16,-1,-1),
	(103,36,17,18,-1,-1),
	(104,36,4,19,-1,-1),
	(105,37,13,1,-1,-1),
	(106,37,5,2,-1,-1),
	(107,37,9,3,-1,-1),
	(108,37,16,6,-1,-1),
	(109,37,4,8,-1,-1),
	(110,37,19,10,-1,-1),
	(111,37,15,11,-1,-1),
	(112,37,7,12,-1,-1),
	(113,37,18,14,-1,-1),
	(114,37,20,17,-1,-1),
	(115,38,11,4,-1,-1),
	(116,38,17,5,-1,-1),
	(117,38,6,20,-1,-1),
	(118,38,1,7,-1,-1),
	(119,38,12,9,-1,-1),
	(120,38,10,13,-1,-1),
	(121,38,8,14,-1,-1),
	(122,38,3,15,-1,-1),
	(123,38,2,18,-1,-1),
	(124,38,16,19,-1,-1);

/*!40000 ALTER TABLE `partite` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table squadre
# ------------------------------------------------------------

DROP TABLE IF EXISTS `squadre`;

CREATE TABLE `squadre` (
  `id_squadra` int(11) NOT NULL,
  `nome_squadra` varchar(255) NOT NULL,
  `pg_casa` int(11) NOT NULL,
  `v_casa` int(11) NOT NULL,
  `n_casa` int(11) NOT NULL,
  `p_casa` int(11) NOT NULL,
  `gf_casa` int(11) NOT NULL,
  `gs_casa` int(11) NOT NULL,
  `pg_trasferta` int(11) NOT NULL,
  `v_trasferta` int(11) NOT NULL,
  `n_trasferta` int(11) NOT NULL,
  `p_trasferta` int(11) NOT NULL,
  `gf_trasferta` int(11) NOT NULL,
  `gs_trasferta` int(11) NOT NULL,
  PRIMARY KEY (`id_squadra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `squadre` WRITE;
/*!40000 ALTER TABLE `squadre` DISABLE KEYS */;

INSERT INTO `squadre` (`id_squadra`, `nome_squadra`, `pg_casa`, `v_casa`, `n_casa`, `p_casa`, `gf_casa`, `gs_casa`, `pg_trasferta`, `v_trasferta`, `n_trasferta`, `p_trasferta`, `gf_trasferta`, `gs_trasferta`)
VALUES
	(1,'atalanta',12,6,2,4,33,19,13,8,4,1,37,15),
	(2,'bologna',13,4,5,4,18,20,13,5,2,6,20,22),
	(3,'brescia',13,1,3,9,13,25,13,3,1,9,9,24),
	(4,'cagliari',13,5,2,6,25,23,12,3,6,3,16,17),
	(5,'fiorentina',13,3,5,5,13,17,13,4,4,5,19,19),
	(6,'genoa',12,4,1,7,14,18,14,2,6,6,17,29),
	(7,'inter',12,7,4,1,23,10,13,9,2,2,26,14),
	(8,'juventus',13,12,1,0,31,10,13,8,2,3,19,14),
	(9,'lazio',14,11,3,0,39,10,12,8,2,2,21,13),
	(10,'lecce',13,2,5,6,19,26,13,4,2,7,15,30),
	(11,'milan',13,4,5,4,13,15,13,6,1,6,15,19),
	(12,'napoli',13,5,2,6,17,18,13,6,4,3,24,18),
	(13,'parma',13,6,1,6,17,12,12,4,4,4,15,19),
	(14,'roma',13,6,3,4,26,19,13,7,3,3,25,16),
	(15,'sampdoria',14,4,4,6,16,22,11,3,1,7,12,22),
	(16,'sassuolo',13,6,1,6,28,22,12,3,4,5,13,17),
	(17,'spal',12,2,2,8,11,20,14,3,1,10,9,24),
	(18,'torino',12,4,2,6,11,22,13,4,1,8,17,23),
	(19,'udinese',13,5,4,4,10,12,13,2,3,8,11,25),
	(20,'verona',12,6,3,3,17,12,13,3,5,5,12,14);

/*!40000 ALTER TABLE `squadre` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table statistiche_casa
# ------------------------------------------------------------

DROP TABLE IF EXISTS `statistiche_casa`;

CREATE TABLE `statistiche_casa` (
  `id_squadra` int(11) NOT NULL,
  `tiri_totali_fatti` double NOT NULL,
  `tiri_azione_fatti` double NOT NULL,
  `tiri_piazzato_fatti` double NOT NULL,
  `tiri_rigore_fatti` double NOT NULL,
  `gol_azione_realizzati` double NOT NULL,
  `gol_piazzato_realizzati` double NOT NULL,
  `gol_rigore_realizzati` double NOT NULL,
  `tiri_totali_concessi` double NOT NULL,
  `tiri_azione_concessi` double NOT NULL,
  `tiri_piazzato_concessi` double NOT NULL,
  `tiri_rigore_concessi` double NOT NULL,
  `gol_azione_subiti` double NOT NULL,
  `gol_piazzato_subiti` double NOT NULL,
  `gol_rigore_subiti` double NOT NULL,
  PRIMARY KEY (`id_squadra`),
  CONSTRAINT `statistiche_casa_ibfk_1` FOREIGN KEY (`id_squadra`) REFERENCES `squadre` (`id_squadra`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `statistiche_casa` WRITE;
/*!40000 ALTER TABLE `statistiche_casa` DISABLE KEYS */;

INSERT INTO `statistiche_casa` (`id_squadra`, `tiri_totali_fatti`, `tiri_azione_fatti`, `tiri_piazzato_fatti`, `tiri_rigore_fatti`, `gol_azione_realizzati`, `gol_piazzato_realizzati`, `gol_rigore_realizzati`, `tiri_totali_concessi`, `tiri_azione_concessi`, `tiri_piazzato_concessi`, `tiri_rigore_concessi`, `gol_azione_subiti`, `gol_piazzato_subiti`, `gol_rigore_subiti`)
VALUES
	(1,21.8,16.3,5.2,0.3,2.1,0.4,0.3,9.7,6.6,3,0.1,1,0.3,0.1),
	(2,14.2,11.1,2.9,0.2,0.7,0.5,0.2,11.8,8.9,2.5,0.4,0.9,0.4,0.3),
	(3,11.7,7.8,3.9,0,0.5,0.5,0,17.9,13.8,3.7,0.5,1.2,0.2,0.4),
	(4,12.9,10.3,2.4,0.2,1.5,0.3,0,13.1,10.5,2.3,0.4,1.1,0.3,0.3),
	(5,16,11.1,4.7,0.2,0.6,0.3,0.2,9.8,7,2.7,0.1,1.1,0.2,0.1),
	(6,13.7,8.9,4.5,0.3,0.8,0.2,0.3,12.8,9,3.5,0.3,0.8,0.3,0.3),
	(7,19,14.6,4.2,0.2,1.5,0.3,0.2,12.2,8.4,3.7,0.2,0.7,0.1,0.1),
	(8,20.5,16.2,3.8,0.5,1.6,0.3,0.5,9.5,6,3.5,0,0.5,0.2,0),
	(9,17.7,13.1,3.9,0.7,1.7,0.4,0.6,12.6,8.6,3.8,0.1,0.5,0.1,0.1),
	(10,14.3,11,2.9,0.4,0.8,0.2,0.4,17.9,13.7,3.8,0.4,1.3,0.3,0.3),
	(11,17.6,14.3,3.3,0,0.6,0.3,0,11.1,7.8,3.1,0.2,0.9,0.2,0.1),
	(12,22,16.8,5.2,0,1,0.3,0,11.6,9.2,2.4,0,1.3,0.2,0),
	(13,14.9,10.2,4.5,0.2,1.1,0.2,0,14.8,10.6,3.7,0.5,0.6,0.2,0.2),
	(14,21,14.3,6.4,0.3,1.1,0.5,0.2,10.5,7.6,2.6,0.4,0.8,0.3,0.4),
	(15,18.1,13.3,4.4,0.4,0.7,0.1,0.3,10.6,7.8,2.6,0.1,1,0.3,0.1),
	(16,14.8,11.2,3.5,0.1,2,0.1,0,12.6,9.5,2.7,0.4,1,0.2,0.3),
	(17,15.1,10.9,3.9,0.3,0.5,0.2,0.3,15.3,10.7,4.3,0.3,1.3,0,0.3),
	(18,10.5,7,3.4,0.1,0.8,0.1,0.1,16.6,12.6,3.6,0.4,0.9,0.6,0.3),
	(19,14.6,11.3,3.3,0,0.7,0.2,0,13,9.3,3.6,0.2,0.7,0.1,0.2),
	(20,11,7.6,3.1,0.3,0.6,0.4,0.3,13.1,8.7,4.2,0.3,0.7,0.1,0.3);

/*!40000 ALTER TABLE `statistiche_casa` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table statistiche_trasferta
# ------------------------------------------------------------

DROP TABLE IF EXISTS `statistiche_trasferta`;

CREATE TABLE `statistiche_trasferta` (
  `id_squadra` int(11) NOT NULL,
  `tiri_totali_fatti` double NOT NULL,
  `tiri_azione_fatti` double NOT NULL,
  `tiri_piazzato_fatti` double NOT NULL,
  `tiri_rigore_fatti` double NOT NULL,
  `gol_azione_realizzati` double NOT NULL,
  `gol_piazzato_realizzati` double NOT NULL,
  `gol_rigore_realizzati` double NOT NULL,
  `tiri_totali_concessi` double NOT NULL,
  `tiri_azione_concessi` double NOT NULL,
  `tiri_piazzato_concessi` double NOT NULL,
  `tiri_rigore_concessi` double NOT NULL,
  `gol_azione_subiti` double NOT NULL,
  `gol_piazzato_subiti` double NOT NULL,
  `gol_rigore_subiti` double NOT NULL,
  PRIMARY KEY (`id_squadra`),
  CONSTRAINT `statistiche_trasferta_ibfk_1` FOREIGN KEY (`id_squadra`) REFERENCES `squadre` (`id_squadra`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `statistiche_trasferta` WRITE;
/*!40000 ALTER TABLE `statistiche_trasferta` DISABLE KEYS */;

INSERT INTO `statistiche_trasferta` (`id_squadra`, `tiri_totali_fatti`, `tiri_azione_fatti`, `tiri_piazzato_fatti`, `tiri_rigore_fatti`, `gol_azione_realizzati`, `gol_piazzato_realizzati`, `gol_rigore_realizzati`, `tiri_totali_concessi`, `tiri_azione_concessi`, `tiri_piazzato_concessi`, `tiri_rigore_concessi`, `gol_azione_subiti`, `gol_piazzato_subiti`, `gol_rigore_subiti`)
VALUES
	(1,18.7,14.2,4.2,0.3,2.1,0.5,0.2,12.6,8.5,3.8,0.3,0.8,0.1,0.2),
	(2,15,11.6,3.2,0.2,1.1,0.1,0.2,14.8,10.1,4.6,0.1,1.3,0.2,0.1),
	(3,9.7,6.3,3.2,0.2,0.3,0.2,0.2,18.6,13.9,4.6,0.1,1.2,0.5,0.1),
	(4,11.1,8.8,2,0.3,0.9,0.2,0.3,18.5,14.4,3.9,0.2,1.1,0.1,0.2),
	(5,12.9,8.7,3.8,0.4,0.8,0.2,0.3,13.8,9.8,3.8,0.3,0.9,0.4,0.2),
	(6,10.6,7.7,2.5,0.4,0.9,0.1,0.3,19.2,13.3,5.6,0.3,1.1,0.6,0.3),
	(7,13.8,10.1,3.3,0.4,1.2,0.5,0.4,13.5,9.5,3.8,0.2,0.8,0.1,0.1),
	(8,14.5,10.3,4,0.2,0.9,0.3,0.2,14.6,10.8,3.2,0.7,0.6,0.1,0.4),
	(9,13.5,10.7,2.5,0.3,1.4,0.3,0.2,14.5,10,4.4,0.2,0.7,0.3,0.1),
	(10,12.6,10,2.3,0.3,0.7,0.3,0.2,20.2,15.5,4.4,0.3,1.8,0.3,0.2),
	(11,14.2,11.1,2.8,0.3,0.5,0.3,0.3,13.2,9.4,3.4,0.4,0.8,0.4,0.2),
	(12,15,11.3,3.5,0.2,1.3,0.2,0.2,12.2,8.6,3.2,0.4,0.8,0.2,0.3),
	(13,9.1,6.9,2.2,0,1.1,0.2,0,21.5,16,5.5,0,1.3,0.3,0),
	(14,13.6,8.9,4.3,0.4,1.2,0.4,0.3,11.8,9,2.8,0.1,1,0.2,0.1),
	(15,10.8,7.6,2.9,0.3,0.5,0.3,0.3,14.4,10.5,3.7,0.2,1.4,0.4,0.2),
	(16,14.2,10.5,3.6,0.1,0.9,0.1,0.1,15.7,12.7,2.8,0.2,0.8,0.3,0.2),
	(17,11.8,8,3.6,0.2,0.4,0.1,0.1,17.8,13.5,4.2,0.1,1.1,0.4,0.1),
	(18,11.4,7.4,3.7,0.3,0.6,0.3,0.3,17.6,12.7,4.6,0.3,1.2,0.2,0.2),
	(19,11.6,9.1,2.5,0,0.7,0.2,0,17.8,13.5,3.9,0.4,1.2,0.4,0.4),
	(20,12.1,8.6,3.3,0.2,0.7,0.1,0.1,19.2,14,5,0.2,0.5,0.3,0.2);

/*!40000 ALTER TABLE `statistiche_trasferta` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
