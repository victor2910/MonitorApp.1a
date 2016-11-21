-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 21-11-2016 a las 01:49:38
-- Versión del servidor: 5.5.49-0ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `aquatek`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `analyzer`
--

CREATE TABLE IF NOT EXISTS `analyzer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valor` double NOT NULL,
  `tiempo` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Volcado de datos para la tabla `analyzer`
--

INSERT INTO `analyzer` (`id`, `valor`, `tiempo`) VALUES
(1, 2.35, '2016-10-22 12:25:30'),
(2, 2.8, '2016-10-22 14:39:12'),
(3, 3.2, '2016-10-22 20:18:08'),
(4, 2.1, '2016-10-23 05:10:59'),
(15, 3.5, '2016-10-23 05:26:19'),
(16, 4.8, '2016-10-23 10:24:26'),
(17, 5.1, '2016-10-28 15:18:16'),
(18, 3.9, '2016-10-24 03:18:23'),
(19, 3.1, '2016-10-24 06:24:33'),
(20, 4.2, '2016-10-24 17:47:26'),
(21, 5.3, '2016-10-25 07:36:36'),
(22, 5.1, '2016-10-25 17:36:49'),
(23, 4.8, '2016-10-25 10:31:33'),
(24, 4.2, '2016-10-25 19:51:42'),
(25, 3.9, '2016-10-26 05:17:19'),
(26, 4.3, '2016-10-26 08:23:29'),
(27, 4.1, '2016-10-26 13:38:42'),
(28, 4.8, '2016-10-27 07:21:25'),
(29, 3.7, '2016-10-27 18:31:48');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
