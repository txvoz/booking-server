-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-09-2019 a las 01:39:02
-- Versión del servidor: 10.1.37-MariaDB
-- Versión de PHP: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_booking`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alojamiento`
--

CREATE TABLE `alojamiento` (
  `aloId` int(11) NOT NULL,
  `aloCodigo` varchar(11) COLLATE utf8mb4_spanish_ci NOT NULL,
  `aloCapacidad` int(11) NOT NULL,
  `aloPrecio` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `aloDescripcion` mediumtext COLLATE utf8mb4_spanish_ci NOT NULL,
  `fkLugar` int(11) NOT NULL,
  `fkTipoAlojamiento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calificacion`
--

CREATE TABLE `calificacion` (
  `calId` int(11) NOT NULL,
  `calValoracion` enum('1','2','3','4','5') COLLATE utf8mb4_spanish_ci NOT NULL,
  `calComentario` mediumtext COLLATE utf8mb4_spanish_ci,
  `fkUsuario` int(11) NOT NULL,
  `fkAlojamiento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE `departamento` (
  `depId` int(11) NOT NULL,
  `depNombre` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `fkPais` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `departamento`
--

INSERT INTO `departamento` (`depId`, `depNombre`, `fkPais`) VALUES
(5, 'ANTIOQUIA', 48),
(8, 'ATL?NTICO', 48),
(11, 'BOGOT?, D.C.', 48),
(13, 'BOL?VAR', 48),
(15, 'BOYAC?', 48),
(17, 'CALDAS', 48),
(18, 'CAQUET?', 48),
(19, 'CAUCA', 48),
(20, 'CESAR', 48),
(23, 'C?RDOBA', 48),
(25, 'CUNDINAMARCA', 48),
(27, 'CHOC?', 48),
(41, 'HUILA', 48),
(44, 'LA GUAJIRA', 48),
(47, 'MAGDALENA', 48),
(50, 'META', 48),
(52, 'NARI?O', 48),
(54, 'NORTE DE SANTANDER', 48),
(63, 'QUINDIO', 48),
(66, 'RISARALDA', 48),
(68, 'SANTANDER', 48),
(70, 'SUCRE', 48),
(73, 'TOLIMA', 48),
(76, 'VALLE DEL CAUCA', 48),
(81, 'ARAUCA', 48),
(85, 'CASANARE', 48),
(86, 'PUTUMAYO', 48),
(88, 'SAN ANDR?S', 48),
(91, 'AMAZONAS', 48),
(94, 'GUAIN?A', 48),
(95, 'GUAVIARE', 48),
(97, 'VAUP?S', 48),
(99, 'VICHADA', 48),
(9999, '', 48);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foto`
--

CREATE TABLE `foto` (
  `fotId` int(11) NOT NULL,
  `fotRuta` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fotLabel` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fotDescripcion` mediumtext COLLATE utf8mb4_spanish_ci,
  `fkLugar` int(11) DEFAULT NULL,
  `fkAlojamiento` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar`
--

CREATE TABLE `lugar` (
  `lugId` int(11) NOT NULL,
  `lugNombre` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `lugDireccion` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `lugTelefono` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `lugCorreo` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `lugLatitud` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `lugLongitud` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `lugDescripcion` mediumtext COLLATE utf8mb4_spanish_ci NOT NULL,
  `fkTipoLugar` int(11) NOT NULL,
  `fkMunicipio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipio`
--

CREATE TABLE `municipio` (
  `munId` int(11) NOT NULL,
  `munNombre` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `munCodigo` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fkDepartamento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `municipio`
--

INSERT INTO `municipio` (`munId`, `munNombre`, `munCodigo`, `fkDepartamento`) VALUES
(1, 'MEDELLÍN', '05001', 5),
(2, 'ABEJORRAL', '05002', 5),
(3, 'ABRIAQUÍ', '05004', 5),
(4, 'ALEJANDRÍA', '05021', 5),
(5, 'AMAGÁ', '05030', 5),
(6, 'AMALFI', '05031', 5),
(7, 'ANDES', '05034', 5),
(8, 'ANGELÓPOLIS', '05036', 5),
(9, 'ANGOSTURA', '05038', 5),
(10, 'ANORÍ', '05040', 5),
(11, 'SANTAFÉ DE ANTIOQUIA', '05042', 5),
(12, 'ANZA', '05044', 5),
(13, 'APARTADÓ', '05045', 5),
(14, 'ARBOLETES', '05051', 5),
(15, 'ARGELIA', '05055', 5),
(16, 'ARMENIA ANTIOQUIA', '05059', 5),
(17, 'BARBOSA', '05079', 5),
(18, 'BELMIRA', '05086', 5),
(19, 'BELLO', '05088', 5),
(20, 'BETANIA', '05091', 5),
(21, 'BETULIA', '05093', 5),
(22, 'CIUDAD BOLÍVAR', '05101', 5),
(23, 'BRICEÑO', '05107', 5),
(24, 'BURITICÁ', '05113', 5),
(25, 'CÁCERES', '05120', 5),
(26, 'CAICEDO', '05125', 5),
(27, 'CALDAS', '05129', 5),
(28, 'CAMPAMENTO', '05134', 5),
(29, 'CAÑASGORDAS', '05138', 5),
(30, 'CARACOLÍ', '05142', 5),
(31, 'CARAMANTA', '05145', 5),
(32, 'CAREPA', '05147', 5),
(33, 'EL CARMEN DE VIBORAL', '05148', 5),
(34, 'CAROLINA', '05150', 5),
(35, 'CAUCASIA', '05154', 5),
(36, 'CHIGORODÓ', '05172', 5),
(37, 'CISNEROS', '05190', 5),
(38, 'COCORNÁ', '05197', 5),
(39, 'CONCEPCIÓN', '05206', 5),
(40, 'CONCORDIA', '05209', 5),
(41, 'COPACABANA', '05212', 5),
(42, 'DABEIBA', '05234', 5),
(43, 'DON MATÍAS', '05237', 5),
(44, 'EBÉJICO', '05240', 5),
(45, 'EL BAGRE', '05250', 5),
(46, 'ENTRERRIOS', '05264', 5),
(47, 'ENVIGADO', '05266', 5),
(48, 'FREDONIA', '05282', 5),
(49, 'FRONTINO', '05284', 5),
(50, 'GIRALDO', '05306', 5),
(51, 'GIRARDOTA', '05308', 5),
(52, 'GÓMEZ PLATA', '05310', 5),
(53, 'GRANADA', '05313', 5),
(54, 'GUADALUPE', '05315', 5),
(55, 'GUARNE', '05318', 5),
(56, 'GUATAPE', '05321', 5),
(57, 'HELICONIA', '05347', 5),
(58, 'HISPANIA', '05353', 5),
(59, 'ITAGUI', '05360', 5),
(60, 'ITUANGO', '05361', 5),
(61, 'JARDÍN', '05364', 5),
(62, 'JERICÓ', '05368', 5),
(63, 'LA CEJA', '05376', 5),
(64, 'LA ESTRELLA', '05380', 5),
(65, 'LA PINTADA', '05390', 5),
(66, 'LA UNIÓN', '05400', 5),
(67, 'LIBORINA', '05411', 5),
(68, 'MACEO', '05425', 5),
(69, 'MARINILLA', '05440', 5),
(70, 'MONTEBELLO', '05467', 5),
(71, 'MURINDÓ', '05475', 5),
(72, 'MUTATÁ', '05480', 5),
(73, 'NARIÑO', '05483', 5),
(74, 'NECOCLÍ', '05490', 5),
(75, 'NECHÍ', '05495', 5),
(76, 'OLAYA', '05501', 5),
(77, 'PEÑOL', '05541', 5),
(78, 'PEQUE', '05543', 5),
(79, 'PUEBLORRICO', '05576', 5),
(80, 'PUERTO BERRÍO', '05579', 5),
(81, 'PUERTO NARE', '05585', 5),
(82, 'PUERTO TRIUNFO', '05591', 5),
(83, 'REMEDIOS', '05604', 5),
(84, 'RETIRO', '05607', 5),
(85, 'RIONEGRO', '05615', 5),
(86, 'SABANALARGA', '05628', 5),
(87, 'SABANETA', '05631', 5),
(88, 'SALGAR', '05642', 5),
(89, 'SAN ANDRÉS DE CUERQUÍA', '05647', 5),
(90, 'SAN CARLOS', '05649', 5),
(91, 'SAN FRANCISCO', '05652', 5),
(92, 'SAN JERÓNIMO', '05656', 5),
(93, 'SAN JOSÉ DE LA MONTAÑA', '05658', 5),
(94, 'SAN JUAN DE URABÁ', '05659', 5),
(95, 'SAN LUIS', '05660', 5),
(96, 'SAN PEDRO', '05664', 5),
(97, 'SAN PEDRO DE URABA', '05665', 5),
(98, 'SAN RAFAEL', '05667', 5),
(99, 'SAN ROQUE', '05670', 5),
(100, 'SAN VICENTE', '05674', 5),
(101, 'SANTA BÁRBARA', '05679', 5),
(102, 'SANTA ROSA DE OSOS', '05686', 5),
(103, 'SANTO DOMINGO', '05690', 5),
(104, 'EL SANTUARIO', '05697', 5),
(105, 'SEGOVIA', '05736', 5),
(106, 'SONSON', '05756', 5),
(107, 'SOPETRÁN', '05761', 5),
(108, 'TÁMESIS', '05789', 5),
(109, 'TARAZÁ', '05790', 5),
(110, 'TARSO', '05792', 5),
(111, 'TITIRIBÍ', '05809', 5),
(112, 'TOLEDO', '05819', 5),
(113, 'TURBO', '05837', 5),
(114, 'URAMITA', '05842', 5),
(115, 'URRAO', '05847', 5),
(116, 'VALDIVIA', '05854', 5),
(117, 'VALPARAÍSO', '05856', 5),
(118, 'VEGACHÍ', '05858', 5),
(119, 'VENECIA', '05861', 5),
(120, 'VIGÍA DEL FUERTE', '05873', 5),
(121, 'YALÍ', '05885', 5),
(122, 'YARUMAL', '05887', 5),
(123, 'YOLOMBÓ', '05890', 5),
(124, 'YONDÓ', '05893', 5),
(125, 'ZARAGOZA', '05895', 5),
(126, 'BARRANQUILLA', '08001', 8),
(127, 'BARANOA', '08078', 8),
(128, 'CAMPO DE LA CRUZ', '08137', 8),
(129, 'CANDELARIA', '08141', 8),
(130, 'GALAPA', '08296', 8),
(131, 'JUAN DE ACOSTA', '08372', 8),
(132, 'LURUACO', '08421', 8),
(133, 'MALAMBO', '08433', 8),
(134, 'MANATÍ', '08436', 8),
(135, 'PALMAR DE VARELA', '08520', 8),
(136, 'PIOJÓ', '08549', 8),
(137, 'POLONUEVO', '08558', 8),
(138, 'PONEDERA', '08560', 8),
(139, 'PUERTO COLOMBIA', '08573', 8),
(140, 'REPELÓN', '08606', 8),
(141, 'SABANAGRANDE', '08634', 8),
(142, 'SABANALARGA', '08638', 8),
(143, 'SANTA LUCÍA', '08675', 8),
(144, 'SANTO TOMÁS', '08685', 8),
(145, 'SOLEDAD', '08758', 8),
(146, 'SUAN', '08770', 8),
(147, 'TUBARÁ', '08832', 8),
(148, 'USIACURÍ', '08849', 8),
(149, 'BOGOTÁ, D.C.', '11001', 11),
(150, 'CARTAGENA', '13001', 13),
(151, 'ACHÍ', '13006', 13),
(152, 'ALTOS DEL ROSARIO', '13030', 13),
(153, 'ARENAL', '13042', 13),
(154, 'ARJONA', '13052', 13),
(155, 'ARROYOHONDO', '13062', 13),
(156, 'BARRANCO DE LOBA', '13074', 13),
(157, 'CALAMAR', '13140', 13),
(158, 'CANTAGALLO', '13160', 13),
(159, 'CICUCO', '13188', 13),
(160, 'CÓRDOBA', '13212', 13),
(161, 'CLEMENCIA', '13222', 13),
(162, 'EL CARMEN DE BOLÍVAR', '13244', 13),
(163, 'EL GUAMO', '13248', 13),
(164, 'EL PEÑÓN', '13268', 13),
(165, 'HATILLO DE LOBA', '13300', 13),
(166, 'MAGANGUÉ', '13430', 13),
(167, 'MAHATES', '13433', 13),
(168, 'MARGARITA', '13440', 13),
(169, 'MARÍA LA BAJA', '13442', 13),
(170, 'MONTECRISTO', '13458', 13),
(171, 'MOMPÓS', '13468', 13),
(172, 'MORALES', '13473', 13),
(173, 'PINILLOS', '13549', 13),
(174, 'REGIDOR', '13580', 13),
(175, 'RÍO VIEJO', '13600', 13),
(176, 'SAN CRISTÓBAL', '13620', 13),
(177, 'SAN ESTANISLAO', '13647', 13),
(178, 'SAN FERNANDO', '13650', 13),
(179, 'SAN JACINTO', '13654', 13),
(180, 'SAN JACINTO DEL CAUCA', '13655', 13),
(181, 'SAN JUAN NEPOMUCENO', '13657', 13),
(182, 'SAN MARTÍN DE LOBA', '13667', 13),
(183, 'SAN PABLO', '13670', 13),
(184, 'SANTA CATALINA', '13673', 13),
(185, 'SANTA ROSA', '13683', 13),
(186, 'SANTA ROSA DEL SUR', '13688', 13),
(187, 'SIMITÍ', '13744', 13),
(188, 'SOPLAVIENTO', '13760', 13),
(189, 'TALAIGUA NUEVO', '13780', 13),
(190, 'TIQUISIO', '13810', 13),
(191, 'TURBACO', '13836', 13),
(192, 'TURBANÁ', '13838', 13),
(193, 'VILLANUEVA', '13873', 13),
(194, 'ZAMBRANO', '13894', 13),
(195, 'TUNJA', '15001', 15),
(196, 'ALMEIDA', '15022', 15),
(197, 'AQUITANIA', '15047', 15),
(198, 'ARCABUCO', '15051', 15),
(199, 'BELÉN', '15087', 15),
(200, 'BERBEO', '15090', 15),
(201, 'BETÉITIVA', '15092', 15),
(202, 'BOAVITA', '15097', 15),
(203, 'BOYACÁ', '15104', 15),
(204, 'BRICEÑO', '15106', 15),
(205, 'BUENAVISTA', '15109', 15),
(206, 'BUSBANZÁ', '15114', 15),
(207, 'CALDAS', '15131', 15),
(208, 'CAMPOHERMOSO', '15135', 15),
(209, 'CERINZA', '15162', 15),
(210, 'CHINAVITA', '15172', 15),
(211, 'CHIQUINQUIRÁ', '15176', 15),
(212, 'CHISCAS', '15180', 15),
(213, 'CHITA', '15183', 15),
(214, 'CHITARAQUE', '15185', 15),
(215, 'CHIVATÁ', '15187', 15),
(216, 'CIÉNEGA', '15189', 15),
(217, 'CÓMBITA', '15204', 15),
(218, 'COPER', '15212', 15),
(219, 'CORRALES', '15215', 15),
(220, 'COVARACHÍA', '15218', 15),
(221, 'CUBARÁ', '15223', 15),
(222, 'CUCAITA', '15224', 15),
(223, 'CUÍTIVA', '15226', 15),
(224, 'CHÍQUIZA', '15232', 15),
(225, 'CHIVOR', '15236', 15),
(226, 'DUITAMA', '15238', 15),
(227, 'EL COCUY', '15244', 15),
(228, 'EL7aSPINO', '15248', 15),
(229, 'FIRAVITOBA', '15272', 15),
(230, 'FLORESTA', '15276', 15),
(231, 'GACHANTIVÁ', '15293', 15),
(232, 'GAMEZA', '15296', 15),
(233, 'GARAGOA', '15299', 15),
(234, 'GUACAMAYAS', '15317', 15),
(235, 'GUATEQUE', '15322', 15),
(236, 'GUAYATÁ', '15325', 15),
(237, 'GÜICÁN', '15332', 15),
(238, 'IZA', '15362', 15),
(239, 'JENESANO', '15367', 15),
(240, 'JERICÓ', '15368', 15),
(241, 'LABRANZAGRANDE', '15377', 15),
(242, 'LA CAPILLA', '15380', 15),
(243, 'LA VICTORIA', '15401', 15),
(244, 'LA UVITA', '15403', 15),
(245, 'VILLA DE LEYVA', '15407', 15),
(246, 'MACANAL', '15425', 15),
(247, 'MARIPÍ', '15442', 15),
(248, 'MIRAFLORES', '15455', 15),
(249, 'MONGUA', '15464', 15),
(250, 'MONGUÍ', '15466', 15),
(251, 'MONIQUIRÁ', '15469', 15),
(252, 'MOTAVITA', '15476', 15),
(253, 'MUZO', '15480', 15),
(254, 'NOBSA', '15491', 15),
(255, 'NUEVO COLÓN', '15494', 15),
(256, 'OICATÁ', '15500', 15),
(257, 'OTANCHE', '15507', 15),
(258, 'PACHAVITA', '15511', 15),
(259, 'PÁEZ', '15514', 15),
(260, 'PAIPA', '15516', 15),
(261, 'PAJARITO', '15518', 15),
(262, 'PANQUEBA', '15522', 15),
(263, 'PAUNA', '15531', 15),
(264, 'PAYA', '15533', 15),
(265, 'PAZ DE RÍO', '15537', 15),
(266, 'PESCA', '15542', 15),
(267, 'PISBA', '15550', 15),
(268, 'PUERTO BOYACÁ', '15572', 15),
(269, 'QUÍPAMA', '15580', 15),
(270, 'RAMIRIQUÍ', '15599', 15),
(271, 'RÁQUIRA', '15600', 15),
(272, 'RONDÓN', '15621', 15),
(273, 'SABOYÁ', '15632', 15),
(274, 'SÁCHICA', '15638', 15),
(275, 'SAMACÁ', '15646', 15),
(276, 'SAN EDUARDO', '15660', 15),
(277, 'SAN JOSÉ DE PARE', '15664', 15),
(278, 'SAN LUIS DE GACENO', '15667', 15),
(279, 'SAN MATEO', '15673', 15),
(280, 'SAN MIGUEL DE SEMA', '15676', 15),
(281, 'SAN PABLO DE BORBUR', '15681', 15),
(282, 'SANTANA', '15686', 15),
(283, 'SANTA MARÍA', '15690', 15),
(284, 'SANTA ROSA DE VITERBO', '15693', 15),
(285, 'SANTA SOFÍA', '15696', 15),
(286, 'SATIVANORTE', '15720', 15),
(287, 'SATIVASUR', '15723', 15),
(288, 'SIACHOQUE', '15740', 15),
(289, 'SOATÁ', '15753', 15),
(290, 'SOCOTÁ', '15755', 15),
(291, 'SOCHA', '15757', 15),
(292, 'SOGAMOSO', '15759', 15),
(293, 'SOMONDOCO', '15761', 15),
(294, 'SORA', '15762', 15),
(295, 'SOTAQUIRÁ', '15763', 15),
(296, 'SORACÁ', '15764', 15),
(297, 'SUSACÓN', '15774', 15),
(298, 'SUTAMARCHÁN', '15776', 15),
(299, 'SUTATENZA', '15778', 15),
(300, 'TASCO', '15790', 15),
(301, 'TENZA', '15798', 15),
(302, 'TIBANÁ', '15804', 15),
(303, 'TIBASOSA', '15806', 15),
(304, 'TINJACÁ', '15808', 15),
(305, 'TIPACOQUE', '15810', 15),
(306, 'TOCA', '15814', 15),
(307, 'TOGÜÍ', '15816', 15),
(308, 'TÓPAGA', '15820', 15),
(309, 'TOTA', '15822', 15),
(310, 'TUNUNGUÁ', '15832', 15),
(311, 'TURMEQUÉ', '15835', 15),
(312, 'TUTA', '15837', 15),
(313, 'TUTAZÁ', '15839', 15),
(314, 'UMBITA', '15842', 15),
(315, 'VENTAQUEMADA', '15861', 15),
(316, 'VIRACACHÁ', '15879', 15),
(317, 'ZETAQUIRA', '15897', 15),
(318, 'MANIZALES', '17001', 17),
(319, 'AGUADAS', '17013', 17),
(320, 'ANSERMA', '17042', 17),
(321, 'ARANZAZU', '17050', 17),
(322, 'BELALCÁZAR', '17088', 17),
(323, 'CHINCHINÁ', '17174', 17),
(324, 'FILADELFIA', '17272', 17),
(325, 'LA DORADA', '17380', 17),
(326, 'LA MERCED', '17388', 17),
(327, 'MANZANARES', '17433', 17),
(328, 'MARMATO', '17442', 17),
(329, 'MARQUETALIA', '17444', 17),
(330, 'MARULANDA', '17446', 17),
(331, 'NEIRA', '17486', 17),
(332, 'NORCASIA', '17495', 17),
(333, 'PÁCORA', '17513', 17),
(334, 'PALESTINA', '17524', 17),
(335, 'PENSILVANIA', '17541', 17),
(336, 'RIOSUCIO', '17614', 17),
(337, 'RISARALDA', '17616', 17),
(338, 'SALAMINA', '17653', 17),
(339, 'SAMANÁ', '17662', 17),
(340, 'SAN JOSÉ', '17665', 17),
(341, 'SUPÍA', '17777', 17),
(342, 'VICTORIA', '17867', 17),
(343, 'VILLAMARÍA', '17873', 17),
(344, 'VITERBO', '17877', 17),
(345, 'FLORENCIA', '18001', 18),
(346, 'ALBANIA', '18029', 18),
(347, 'BELÉN DE LOS ANDAQUIES', '18094', 18),
(348, 'CARTAGENA DEL CHAIRÁ', '18150', 18),
(349, 'CURILLO', '18205', 18),
(350, 'EL DONCELLO', '18247', 18),
(351, 'EL PAUJIL', '18256', 18),
(352, 'LA MONTAÑITA', '18410', 18),
(353, 'MILÁN', '18460', 18),
(354, 'MORELIA', '18479', 18),
(355, 'PUERTO RICO', '18592', 18),
(356, 'SAN JOSÉ DEL FRAGUA', '18610', 18),
(357, 'SAN VICENTE DEL CAGUÁN', '18753', 18),
(358, 'SOLANO', '18756', 18),
(359, 'SOLITA', '18785', 18),
(360, 'VALPARAÍSO', '18860', 18),
(361, 'POPAYÁN', '19001', 19),
(362, 'ALMAGUER', '19022', 19),
(363, 'ARGELIA', '19050', 19),
(364, 'BALBOA', '19075', 19),
(365, 'BOLÍVAR', '19100', 19),
(366, 'BUENOS AIRES', '19110', 19),
(367, 'CAJIBÍO', '19130', 19),
(368, 'CALDONO', '19137', 19),
(369, 'CALOTO', '19142', 19),
(370, 'CORINTO', '19212', 19),
(371, 'EL TAMBO', '19256', 19),
(372, 'FLORENCIA', '19290', 19),
(373, 'GUACHENÉ', '19300', 19),
(374, 'GUAPI', '19318', 19),
(375, 'INZÁ', '19355', 19),
(376, 'JAMBALÓ', '19364', 19),
(377, 'LA SIERRA', '19392', 19),
(378, 'LA VEGA', '19397', 19),
(379, 'LÓPEZ', '19418', 19),
(380, 'MERCADERES', '19450', 19),
(381, 'MIRANDA', '19455', 19),
(382, 'MORALES', '19473', 19),
(383, 'PADILLA', '19513', 19),
(384, 'PAEZ', '19517', 19),
(385, 'PATÍA', '19532', 19),
(386, 'PIAMONTE', '19533', 19),
(387, 'PIENDAMÓ', '19548', 19),
(388, 'PUERTO TEJADA', '19573', 19),
(389, 'PURACÉ', '19585', 19),
(390, 'ROSAS', '19622', 19),
(391, 'SAN SEBASTIÁN', '19693', 19),
(392, 'SANTANDER DE QUILICHAO', '19698', 19),
(393, 'SANTA ROSA', '19701', 19),
(394, 'SILVIA', '19743', 19),
(395, 'SOTARA', '19760', 19),
(396, 'SUÁREZ', '19780', 19),
(397, 'SUCRE', '19785', 19),
(398, 'TIMBÍO', '19807', 19),
(399, 'TIMBIQUÍ', '19809', 19),
(400, 'TORIBIO', '19821', 19),
(401, 'TOTORÓ', '19824', 19),
(402, 'VILLA RICA', '19845', 19),
(403, 'VALLEDUPAR', '20001', 20),
(404, 'AGUACHICA', '20011', 20),
(405, 'AGUSTÍN CODAZZI', '20013', 20),
(406, 'ASTREA', '20032', 20),
(407, 'BECERRIL', '20045', 20),
(408, 'BOSCONIA', '20060', 20),
(409, 'CHIMICHAGUA', '20175', 20),
(410, 'CHIRIGUANÁ', '20178', 20),
(411, 'CURUMANÍ', '20228', 20),
(412, 'EL COPEY', '20238', 20),
(413, 'EL PASO', '20250', 20),
(414, 'GAMARRA', '20295', 20),
(415, 'GONZÁLEZ', '20310', 20),
(416, 'LA GLORIA', '20383', 20),
(417, 'LA JAGUA DE IBIRICO', '20400', 20),
(418, 'MANAURE', '20443', 20),
(419, 'PAILITAS', '20517', 20),
(420, 'PELAYA', '20550', 20),
(421, 'PUEBLO BELLO', '20570', 20),
(422, 'RÍO DE ORO', '20614', 20),
(423, 'LA PAZ', '20621', 20),
(424, 'SAN ALBERTO', '20710', 20),
(425, 'SAN DIEGO', '20750', 20),
(426, 'SAN MARTÍN', '20770', 20),
(427, 'TAMALAMEQUE', '20787', 20),
(428, 'MONTERÍA', '23001', 23),
(429, 'AYAPEL', '23068', 23),
(430, 'BUENAVISTA', '23079', 23),
(431, 'CANALETE', '23090', 23),
(432, 'CERETÉ', '23162', 23),
(433, 'CHIMÁ', '23168', 23),
(434, 'CHINÚ', '23182', 23),
(435, 'CIÉNAGA DE ORO', '23189', 23),
(436, 'COTORRA', '23300', 23),
(437, 'LA APARTADA', '23350', 23),
(438, 'LORICA', '23417', 23),
(439, 'LOS CÓRDOBAS', '23419', 23),
(440, 'MOMIL', '23464', 23),
(441, 'MONTELÍBANO', '23466', 23),
(442, 'MOÑITOS', '23500', 23),
(443, 'PLANETA RICA', '23555', 23),
(444, 'PUEBLO NUEVO', '23570', 23),
(445, 'PUERTO ESCONDIDO', '23574', 23),
(446, 'PUERTO LIBERTADOR', '23580', 23),
(447, 'PURÍSIMA', '23586', 23),
(448, 'SAHAGÚN', '23660', 23),
(449, 'SAN ANDRÉS SOTAVENTO', '23670', 23),
(450, 'SAN ANTERO', '23672', 23),
(451, 'SAN BERNARDO DEL VIENTO', '23675', 23),
(452, 'SAN CARLOS', '23678', 23),
(453, 'SAN PELAYO', '23686', 23),
(454, 'TIERRALTA', '23807', 23),
(455, 'VALENCIA', '23855', 23),
(456, 'AGUA DE DIOS', '25001', 25),
(457, 'ALBÁN', '25019', 25),
(458, 'ANAPOIMA', '25035', 25),
(459, 'ANOLAIMA', '25040', 25),
(460, 'ARBELÁEZ', '25053', 25),
(461, 'BELTRÁN', '25086', 25),
(462, 'BITUIMA', '25095', 25),
(463, 'BOJACÁ', '25099', 25),
(464, 'CABRERA', '25120', 25),
(465, 'CACHIPAY', '25123', 25),
(466, 'CAJICÁ', '25126', 25),
(467, 'CAPARRAPÍ', '25148', 25),
(468, 'CAQUEZA', '25151', 25),
(469, 'CARMEN DE CARUPA', '25154', 25),
(470, 'CHAGUANÍ', '25168', 25),
(471, 'CHÍA', '25175', 25),
(472, 'CHIPAQUE', '25178', 25),
(473, 'CHOACHÍ', '25181', 25),
(474, 'CHOCONTÁ', '25183', 25),
(475, 'COGUA', '25200', 25),
(476, 'COTA', '25214', 25),
(477, 'CUCUNUBÁ', '25224', 25),
(478, 'EL COLEGIO', '25245', 25),
(479, 'EL PEÑÓN', '25258', 25),
(480, 'EL ROSAL', '25260', 25),
(481, 'FACATATIVÁ', '25269', 25),
(482, 'FOMEQUE', '25279', 25),
(483, 'FOSCA', '25281', 25),
(484, 'FUNZA', '25286', 25),
(485, 'FÚQUENE', '25288', 25),
(486, 'FUSAGASUGÁ', '25290', 25),
(487, 'GACHALA', '25293', 25),
(488, 'GACHANCIPÁ', '25295', 25),
(489, 'GACHETÁ', '25297', 25),
(490, 'GAMA', '25299', 25),
(491, 'GIRARDOT', '25307', 25),
(492, 'GRANADA', '25312', 25),
(493, 'GUACHETÁ', '25317', 25),
(494, 'GUADUAS', '25320', 25),
(495, 'GUASCA', '25322', 25),
(496, 'GUATAQUÍ', '25324', 25),
(497, 'GUATAVITA', '25326', 25),
(498, 'GUAYABAL DE SIQUIMA', '25328', 25),
(499, 'GUAYABETAL', '25335', 25),
(500, 'GUTIÉRREZ', '25339', 25),
(501, 'JERUSALÉN', '25368', 25),
(502, 'JUNÍN', '25372', 25),
(503, 'LA CALERA', '25377', 25),
(504, 'LA MESA', '25386', 25),
(505, 'LA PALMA', '25394', 25),
(506, 'LA PEÑA', '25398', 25),
(507, 'LA VEGA', '25402', 25),
(508, 'LENGUAZAQUE', '25407', 25),
(509, 'MACHETA', '25426', 25),
(510, 'MADRID', '25430', 25),
(511, 'MANTA', '25436', 25),
(512, 'MEDINA', '25438', 25),
(513, 'MOSQUERA', '25473', 25),
(514, 'NARIÑO', '25483', 25),
(515, 'NEMOCÓN', '25486', 25),
(516, 'NILO', '25488', 25),
(517, 'NIMAIMA', '25489', 25),
(518, 'NOCAIMA', '25491', 25),
(519, 'VENECIA', '25506', 25),
(520, 'PACHO', '25513', 25),
(521, 'PAIME', '25518', 25),
(522, 'PANDI', '25524', 25),
(523, 'PARATEBUENO', '25530', 25),
(524, 'PASCA', '25535', 25),
(525, 'PUERTO SALGAR', '25572', 25),
(526, 'PULÍ', '25580', 25),
(527, 'QUEBRADANEGRA', '25592', 25),
(528, 'QUETAME', '25594', 25),
(529, 'QUIPILE', '25596', 25),
(530, 'APULO', '25599', 25),
(531, 'RICAURTE', '25612', 25),
(532, 'SAN ANTONIO DEL TEQUENDAMA', '25645', 25),
(533, 'SAN BERNARDO', '25649', 25),
(534, 'SAN CAYETANO', '25653', 25),
(535, 'SAN FRANCISCO', '25658', 25),
(536, 'SAN JUAN DE RÍO SECO', '25662', 25),
(537, 'SASAIMA', '25718', 25),
(538, 'SESQUILÉ', '25736', 25),
(539, 'SIBATÉ', '25740', 25),
(540, 'SILVANIA', '25743', 25),
(541, 'SIMIJACA', '25745', 25),
(542, 'SOACHA', '25754', 25),
(543, 'SOPÓ', '25758', 25),
(544, 'SUBACHOQUE', '25769', 25),
(545, 'SUESCA', '25772', 25),
(546, 'SUPATÁ', '25777', 25),
(547, 'SUSA', '25779', 25),
(548, 'SUTATAUSA', '25781', 25),
(549, 'TABIO', '25785', 25),
(550, 'TAUSA', '25793', 25),
(551, 'TENA', '25797', 25),
(552, 'TENJO', '25799', 25),
(553, 'TIBACUY', '25805', 25),
(554, 'TIBIRITA', '25807', 25),
(555, 'TOCAIMA', '25815', 25),
(556, 'TOCANCIPÁ', '25817', 25),
(557, 'TOPAIPÍ', '25823', 25),
(558, 'UBALÁ', '25839', 25),
(559, 'UBAQUE', '25841', 25),
(560, 'VILLA DE SAN DIEGO DE UBATE', '25843', 25),
(561, 'UNE', '25845', 25),
(562, 'ÚTICA', '25851', 25),
(563, 'VERGARA', '25862', 25),
(564, 'VIANÍ', '25867', 25),
(565, 'VILLAGÓMEZ', '25871', 25),
(566, 'VILLAPINZÓN', '25873', 25),
(567, 'VILLETA', '25875', 25),
(568, 'VIOTÁ', '25878', 25),
(569, 'YACOPÍ', '25885', 25),
(570, 'ZIPACÓN', '25898', 25),
(571, 'ZIPAQUIRÁ', '25899', 25),
(572, 'QUIBDÓ', '27001', 27),
(573, 'ACANDÍ', '27006', 27),
(574, 'ALTO BAUDO', '27025', 27),
(575, 'ATRATO', '27050', 27),
(576, 'BAGADÓ', '27073', 27),
(577, 'BAHÍA SOLANO', '27075', 27),
(578, 'BAJO BAUDÓ', '27077', 27),
(579, 'BELÉN DE BAJIRÁ', '27086', 27),
(580, 'BOJAYA', '27099', 27),
(581, 'EL CANTÓN DEL SAN PABLO', '27135', 27),
(582, 'CARMEN DEL DARIEN', '27150', 27),
(583, 'CÉRTEGUI', '27160', 27),
(584, 'CONDOTO', '27205', 27),
(585, 'EL CARMEN DE ATRATO', '27245', 27),
(586, 'EL LITORAL DEL SAN JUAN', '27250', 27),
(587, 'ISTMINA', '27361', 27),
(588, 'JURADÓ', '27372', 27),
(589, 'LLORÓ', '27413', 27),
(590, 'MEDIO ATRATO', '27425', 27),
(591, 'MEDIO BAUDÓ', '27430', 27),
(592, 'MEDIO SAN JUAN', '27450', 27),
(593, 'NÓVITA', '27491', 27),
(594, 'NUQUÍ', '27495', 27),
(595, 'RÍO IRO', '27580', 27),
(596, 'RÍO QUITO', '27600', 27),
(597, 'RIOSUCIO', '27615', 27),
(598, 'SAN JOSÉ DEL PALMAR', '27660', 27),
(599, 'SIPÍ', '27745', 27),
(600, 'TADÓ', '27787', 27),
(601, 'UNGUÍA', '27800', 27),
(602, 'UNIÓN PANAMERICANA', '27810', 27),
(603, 'NEIVA', '41001', 41),
(604, 'ACEVEDO', '41006', 41),
(605, 'AGRADO', '41013', 41),
(606, 'AIPE', '41016', 41),
(607, 'ALGECIRAS', '41020', 41),
(608, 'ALTAMIRA', '41026', 41),
(609, 'BARAYA', '41078', 41),
(610, 'CAMPOALEGRE', '41132', 41),
(611, 'COLOMBIA', '41206', 41),
(612, 'ELÍAS', '41244', 41),
(613, 'GARZÓN', '41298', 41),
(614, 'GIGANTE', '41306', 41),
(615, 'GUADALUPE', '41319', 41),
(616, 'HOBO', '41349', 41),
(617, 'IQUIRA', '41357', 41),
(618, 'ISNOS', '41359', 41),
(619, 'LA ARGENTINA', '41378', 41),
(620, 'LA PLATA', '41396', 41),
(621, 'NÁTAGA', '41483', 41),
(622, 'OPORAPA', '41503', 41),
(623, 'PAICOL', '41518', 41),
(624, 'PALERMO', '41524', 41),
(625, 'PALESTINA', '41530', 41),
(626, 'PITAL', '41548', 41),
(627, 'PITALITO', '41551', 41),
(628, 'RIVERA', '41615', 41),
(629, 'SALADOBLANCO', '41660', 41),
(630, 'SAN AGUSTÍN', '41668', 41),
(631, 'SANTA MARÍA', '41676', 41),
(632, 'SUAZA', '41770', 41),
(633, 'TARQUI', '41791', 41),
(634, 'TESALIA', '41797', 41),
(635, 'TELLO', '41799', 41),
(636, 'TERUEL', '41801', 41),
(637, 'TIMANÁ', '41807', 41),
(638, 'VILLAVIEJA', '41872', 41),
(639, 'YAGUARÁ', '41885', 41),
(640, 'RIOHACHA', '44001', 44),
(641, 'ALBANIA', '44035', 44),
(642, 'BARRANCAS', '44078', 44),
(643, 'DIBULLA', '44090', 44),
(644, 'DISTRACCIÓN', '44098', 44),
(645, 'EL MOLINO', '44110', 44),
(646, 'FONSECA', '44279', 44),
(647, 'HATONUEVO', '44378', 44),
(648, 'LA JAGUA DEL PILAR', '44420', 44),
(649, 'MAICAO', '44430', 44),
(650, 'MANAURE', '44560', 44),
(651, 'SAN JUAN DEL CESAR', '44650', 44),
(652, 'URIBIA', '44847', 44),
(653, 'URUMITA', '44855', 44),
(654, 'VILLANUEVA', '44874', 44),
(655, 'SANTA MARTA', '47001', 47),
(656, 'ALGARROBO', '47030', 47),
(657, 'ARACATACA', '47053', 47),
(658, 'ARIGUANÍ', '47058', 47),
(659, 'CERRO SAN ANTONIO', '47161', 47),
(660, 'CHIBOLO', '47170', 47),
(661, 'CIÉNAGA', '47189', 47),
(662, 'CONCORDIA', '47205', 47),
(663, 'EL BANCO', '47245', 47),
(664, 'EL PIÑON', '47258', 47),
(665, 'EL RETÉN', '47268', 47),
(666, 'FUNDACIÓN', '47288', 47),
(667, 'GUAMAL', '47318', 47),
(668, 'NUEVA GRANADA', '47460', 47),
(669, 'PEDRAZA', '47541', 47),
(670, 'PIJIÑO DEL CARMEN', '47545', 47),
(671, 'PIVIJAY', '47551', 47),
(672, 'PLATO', '47555', 47),
(673, 'PUEBLOVIEJO', '47570', 47),
(674, 'REMOLINO', '47605', 47),
(675, 'SABANAS DE SAN ANGEL', '47660', 47),
(676, 'SALAMINA', '47675', 47),
(677, 'SAN SEBASTIÁN DE BUENAVISTA', '47692', 47),
(678, 'SAN ZENÓN', '47703', 47),
(679, 'SANTA ANA', '47707', 47),
(680, 'SANTA BÁRBARA DE PINTO', '47720', 47),
(681, 'SITIONUEVO', '47745', 47),
(682, 'TENERIFE', '47798', 47),
(683, 'ZAPAYÁN', '47960', 47),
(684, 'ZONA BANANERA', '47980', 47),
(685, 'VILLAVICENCIO', '50001', 50),
(686, 'ACACÍAS', '50006', 50),
(687, 'BARRANCA DE UPÍA', '50110', 50),
(688, 'CABUYARO', '50124', 50),
(689, 'CASTILLA LA NUEVA', '50150', 50),
(690, 'CUBARRAL', '50223', 50),
(691, 'CUMARAL', '50226', 50),
(692, 'EL CALVARIO', '50245', 50),
(693, 'EL CASTILLO', '50251', 50),
(694, 'EL DORADO', '50270', 50),
(695, 'FUENTE DE ORO', '50287', 50),
(696, 'GRANADA', '50313', 50),
(697, 'GUAMAL', '50318', 50),
(698, 'MAPIRIPÁN', '50325', 50),
(699, 'MESETAS', '50330', 50),
(700, 'LA MACARENA', '50350', 50),
(701, 'URIBE', '50370', 50),
(702, 'LEJANÍAS', '50400', 50),
(703, 'PUERTO CONCORDIA', '50450', 50),
(704, 'PUERTO GAITÁN', '50568', 50),
(705, 'PUERTO LÓPEZ', '50573', 50),
(706, 'PUERTO LLERAS', '50577', 50),
(707, 'PUERTO RICO', '50590', 50),
(708, 'RESTREPO', '50606', 50),
(709, 'SAN CARLOS DE GUAROA', '50680', 50),
(710, 'SAN JUAN DE ARAMA', '50683', 50),
(711, 'SAN JUANITO', '50686', 50),
(712, 'SAN MARTÍN', '50689', 50),
(713, 'VISTAHERMOSA', '50711', 50),
(714, 'PASTO', '52001', 52),
(715, 'ALBÁN', '52019', 52),
(716, 'ALDANA', '52022', 52),
(717, 'ANCUYÁ', '52036', 52),
(718, 'ARBOLEDA', '52051', 52),
(719, 'BARBACOAS', '52079', 52),
(720, 'BELÉN', '52083', 52),
(721, 'BUESACO', '52110', 52),
(722, 'COLÓN', '52203', 52),
(723, 'CONSACA', '52207', 52),
(724, 'CONTADERO', '52210', 52),
(725, 'CÓRDOBA', '52215', 52),
(726, 'CUASPUD', '52224', 52),
(727, 'CUMBAL', '52227', 52),
(728, 'CUMBITARA', '52233', 52),
(729, 'CHACHAGÜÍ', '52240', 52),
(730, 'EL CHARCO', '52250', 52),
(731, 'EL PEÑOL', '52254', 52),
(732, 'EL ROSARIO', '52256', 52),
(733, 'EL TABLÓN DE GÓMEZ', '52258', 52),
(734, 'EL TAMBO', '52260', 52),
(735, 'FUNES', '52287', 52),
(736, 'GUACHUCAL', '52317', 52),
(737, 'GUAITARILLA', '52320', 52),
(738, 'GUALMATÁN', '52323', 52),
(739, 'ILES', '52352', 52),
(740, 'IMUÉS', '52354', 52),
(741, 'IPIALES', '52356', 52),
(742, 'LA CRUZ', '52378', 52),
(743, 'LA FLORIDA', '52381', 52),
(744, 'LA LLANADA', '52385', 52),
(745, 'LA TOLA', '52390', 52),
(746, 'LA UNIÓN', '52399', 52),
(747, 'LEIVA', '52405', 52),
(748, 'LINARES', '52411', 52),
(749, 'LOS ANDES', '52418', 52),
(750, 'MAGÜI', '52427', 52),
(751, 'MALLAMA', '52435', 52),
(752, 'MOSQUERA', '52473', 52),
(753, 'NARIÑO', '52480', 52),
(754, 'OLAYA HERRERA', '52490', 52),
(755, 'OSPINA', '52506', 52),
(756, 'FRANCISCO PIZARRO', '52520', 52),
(757, 'POLICARPA', '52540', 52),
(758, 'POTOSÍ', '52560', 52),
(759, 'PROVIDENCIA', '52565', 52),
(760, 'PUERRES', '52573', 52),
(761, 'PUPIALES', '52585', 52),
(762, 'RICAURTE', '52612', 52),
(763, 'ROBERTO PAYÁN', '52621', 52),
(764, 'SAMANIEGO', '52678', 52),
(765, 'SANDONÁ', '52683', 52),
(766, 'SAN BERNARDO', '52685', 52),
(767, 'SAN LORENZO', '52687', 52),
(768, 'SAN PABLO', '52693', 52),
(769, 'SAN PEDRO DE CARTAGO', '52694', 52),
(770, 'SANTA BÁRBARA', '52696', 52),
(771, 'SANTACRUZ', '52699', 52),
(772, 'SAPUYES', '52720', 52),
(773, 'TAMINANGO', '52786', 52),
(774, 'TANGUA', '52788', 52),
(775, 'SAN ANDRES DE TUMACO', '52835', 52),
(776, 'TÚQUERRES', '52838', 52),
(777, 'YACUANQUER', '52885', 52),
(778, 'CÚCUTA', '54001', 54),
(779, 'ABREGO', '54003', 54),
(780, 'ARBOLEDAS', '54051', 54),
(781, 'BOCHALEMA', '54099', 54),
(782, 'BUCARASICA', '54109', 54),
(783, 'CÁCOTA', '54125', 54),
(784, 'CACHIRÁ', '54128', 54),
(785, 'CHINÁCOTA', '54172', 54),
(786, 'CHITAGÁ', '54174', 54),
(787, 'CONVENCIÓN', '54206', 54),
(788, 'CUCUTILLA', '54223', 54),
(789, 'DURANIA', '54239', 54),
(790, 'EL CARMEN', '54245', 54),
(791, 'EL TARRA', '54250', 54),
(792, 'EL ZULIA', '54261', 54),
(793, 'GRAMALOTE', '54313', 54),
(794, 'HACARÍ', '54344', 54),
(795, 'HERRÁN', '54347', 54),
(796, 'LABATECA', '54377', 54),
(797, 'LA ESPERANZA', '54385', 54),
(798, 'LA PLAYA', '54398', 54),
(799, 'LOS PATIOS', '54405', 54),
(800, 'LOURDES', '54418', 54),
(801, 'MUTISCUA', '54480', 54),
(802, 'OCAÑA', '54498', 54),
(803, 'PAMPLONA', '54518', 54),
(804, 'PAMPLONITA', '54520', 54),
(805, 'PUERTO SANTANDER', '54553', 54),
(806, 'RAGONVALIA', '54599', 54),
(807, 'SALAZAR', '54660', 54),
(808, 'SAN CALIXTO', '54670', 54),
(809, 'SAN CAYETANO', '54673', 54),
(810, 'SANTIAGO', '54680', 54),
(811, 'SARDINATA', '54720', 54),
(812, 'SILOS', '54743', 54),
(813, 'TEORAMA', '54800', 54),
(814, 'TIBÚ', '54810', 54),
(815, 'TOLEDO', '54820', 54),
(816, 'VILLA CARO', '54871', 54),
(817, 'VILLA DEL ROSARIO', '54874', 54),
(818, 'ARMENIA QUINDIO', '63001', 63),
(819, 'BUENAVISTA', '63111', 63),
(820, 'CALARCA', '63130', 63),
(821, 'CIRCASIA', '63190', 63),
(822, 'CÓRDOBA', '63212', 63),
(823, 'FILANDIA', '63272', 63),
(824, 'GÉNOVA', '63302', 63),
(825, 'LA TEBAIDA', '63401', 63),
(826, 'MONTENEGRO', '63470', 63),
(827, 'PIJAO', '63548', 63),
(828, 'QUIMBAYA', '63594', 63),
(829, 'SALENTO', '63690', 63),
(830, 'PEREIRA', '66001', 66),
(831, 'APÍA', '66045', 66),
(832, 'BALBOA', '66075', 66),
(833, 'BELÉN DE UMBRÍA', '66088', 66),
(834, 'DOSQUEBRADAS', '66170', 66),
(835, 'GUÁTICA', '66318', 66),
(836, 'LA CELIA', '66383', 66),
(837, 'LA VIRGINIA', '66400', 66),
(838, 'MARSELLA', '66440', 66),
(839, 'MISTRATÓ', '66456', 66),
(840, 'PUEBLO RICO', '66572', 66),
(841, 'QUINCHÍA', '66594', 66),
(842, 'SANTA ROSA DE CABAL', '66682', 66),
(843, 'SANTUARIO', '66687', 66),
(844, 'BUCARAMANGA', '68001', 68),
(845, 'AGUADA', '68013', 68),
(846, 'ALBANIA', '68020', 68),
(847, 'ARATOCA', '68051', 68),
(848, 'BARBOSA', '68077', 68),
(849, 'BARICHARA', '68079', 68),
(850, 'BARRANCABERMEJA', '68081', 68),
(851, 'BETULIA', '68092', 68),
(852, 'BOLÍVAR', '68101', 68),
(853, 'CABRERA', '68121', 68),
(854, 'CALIFORNIA', '68132', 68),
(855, 'CAPITANEJO', '68147', 68),
(856, 'CARCASÍ', '68152', 68),
(857, 'CEPITÁ', '68160', 68),
(858, 'CERRITO', '68162', 68),
(859, 'CHARALÁ', '68167', 68),
(860, 'CHARTA', '68169', 68),
(861, 'CHIMA', '68176', 68),
(862, 'CHIPATÁ', '68179', 68),
(863, 'CIMITARRA', '68190', 68),
(864, 'CONCEPCIÓN', '68207', 68),
(865, 'CONFINES', '68209', 68),
(866, 'CONTRATACIÓN', '68211', 68),
(867, 'COROMORO', '68217', 68),
(868, 'CURITÍ', '68229', 68),
(869, 'EL CARMEN DE CHUCURÍ', '68235', 68),
(870, 'EL GUACAMAYO', '68245', 68),
(871, 'EL PEÑÓN', '68250', 68),
(872, 'EL PLAYÓN', '68255', 68),
(873, 'ENCINO', '68264', 68),
(874, 'ENCISO', '68266', 68),
(875, 'FLORIÁN', '68271', 68),
(876, 'FLORIDABLANCA', '68276', 68),
(877, 'GALÁN', '68296', 68),
(878, 'GAMBITA', '68298', 68),
(879, 'GIRÓN', '68307', 68),
(880, 'GUACA', '68318', 68),
(881, 'GUADALUPE', '68320', 68),
(882, 'GUAPOTÁ', '68322', 68),
(883, 'GUAVATÁ', '68324', 68),
(884, 'GÜEPSA', '68327', 68),
(885, 'HATO', '68344', 68),
(886, 'JESÚS MARÍA', '68368', 68),
(887, 'JORDÁN', '68370', 68),
(888, 'LA BELLEZA', '68377', 68),
(889, 'LANDÁZURI', '68385', 68),
(890, 'LA PAZ', '68397', 68),
(891, 'LEBRÍJA', '68406', 68),
(892, 'LOS SANTOS', '68418', 68),
(893, 'MACARAVITA', '68425', 68),
(894, 'MÁLAGA', '68432', 68),
(895, 'MATANZA', '68444', 68),
(896, 'MOGOTES', '68464', 68),
(897, 'MOLAGAVITA', '68468', 68),
(898, 'OCAMONTE', '68498', 68),
(899, 'OIBA', '68500', 68),
(900, 'ONZAGA', '68502', 68),
(901, 'PALMAR', '68522', 68),
(902, 'PALMAS DEL SOCORRO', '68524', 68),
(903, 'PÁRAMO', '68533', 68),
(904, 'PIEDECUESTA', '68547', 68),
(905, 'PINCHOTE', '68549', 68),
(906, 'PUENTE NACIONAL', '68572', 68),
(907, 'PUERTO PARRA', '68573', 68),
(908, 'PUERTO WILCHES', '68575', 68),
(909, 'RIONEGRO', '68615', 68),
(910, 'SABANA DE TORRES', '68655', 68),
(911, 'SAN ANDRÉS', '68669', 68),
(912, 'SAN BENITO', '68673', 68),
(913, 'SAN GIL', '68679', 68),
(914, 'SAN JOAQUÍN', '68682', 68),
(915, 'SAN JOSÉ DE MIRANDA', '68684', 68),
(916, 'SAN MIGUEL', '68686', 68),
(917, 'SAN VICENTE DE CHUCURÍ', '68689', 68),
(918, 'SANTA BÁRBARA', '68705', 68),
(919, 'SANTA HELENA DEL OPÓN', '68720', 68),
(920, 'SIMACOTA', '68745', 68),
(921, 'SOCORRO', '68755', 68),
(922, 'SUAITA', '68770', 68),
(923, 'SUCRE', '68773', 68),
(924, 'SURATÁ', '68780', 68),
(925, 'TONA', '68820', 68),
(926, 'VALLE DE SAN JOSÉ', '68855', 68),
(927, 'VÉLEZ', '68861', 68),
(928, 'VETAS', '68867', 68),
(929, 'VILLANUEVA', '68872', 68),
(930, 'ZAPATOCA', '68895', 68),
(931, 'SINCELEJO', '70001', 70),
(932, 'BUENAVISTA', '70110', 70),
(933, 'CAIMITO', '70124', 70),
(934, 'COLOSO', '70204', 70),
(935, 'COROZAL', '70215', 70),
(936, 'COVEÑAS', '70221', 70),
(937, 'CHALÁN', '70230', 70),
(938, 'EL ROBLE', '70233', 70),
(939, 'GALERAS', '70235', 70),
(940, 'GUARANDA', '70265', 70),
(941, 'LA UNIÓN', '70400', 70),
(942, 'LOS PALMITOS', '70418', 70),
(943, 'MAJAGUAL', '70429', 70),
(944, 'MORROA', '70473', 70),
(945, 'OVEJAS', '70508', 70),
(946, 'PALMITO', '70523', 70),
(947, 'SAMPUÉS', '70670', 70),
(948, 'SAN BENITO ABAD', '70678', 70),
(949, 'SAN JUAN DE BETULIA', '70702', 70),
(950, 'SAN MARCOS', '70708', 70),
(951, 'SAN ONOFRE', '70713', 70),
(952, 'SAN PEDRO', '70717', 70),
(953, 'SAN LUIS DE SINCÉ', '70742', 70),
(954, 'SUCRE', '70771', 70),
(955, 'SANTIAGO DE TOLÚ', '70820', 70),
(956, 'TOLÚ VIEJO', '70823', 70),
(957, 'IBAGUÉ', '73001', 73),
(958, 'ALPUJARRA', '73024', 73),
(959, 'ALVARADO', '73026', 73),
(960, 'AMBALEMA', '73030', 73),
(961, 'ANZOÁTEGUI', '73043', 73),
(962, 'ARMERO', '73055', 73),
(963, 'ATACO', '73067', 73),
(964, 'CAJAMARCA', '73124', 73),
(965, 'CARMEN DE APICALÁ', '73148', 73),
(966, 'CASABIANCA', '73152', 73),
(967, 'CHAPARRAL', '73168', 73),
(968, 'COELLO', '73200', 73),
(969, 'COYAIMA', '73217', 73),
(970, 'CUNDAY', '73226', 73),
(971, 'DOLORES', '73236', 73),
(972, 'ESPINAL', '73268', 73),
(973, 'FALAN', '73270', 73),
(974, 'FLANDES', '73275', 73),
(975, 'FRESNO', '73283', 73),
(976, 'GUAMO', '73319', 73),
(977, 'HERVEO', '73347', 73),
(978, 'HONDA', '73349', 73),
(979, 'ICONONZO', '73352', 73),
(980, 'LÉRIDA', '73408', 73),
(981, 'LÍBANO', '73411', 73),
(982, 'MARIQUITA', '73443', 73),
(983, 'MELGAR', '73449', 73),
(984, 'MURILLO', '73461', 73),
(985, 'NATAGAIMA', '73483', 73),
(986, 'ORTEGA', '73504', 73),
(987, 'PALOCABILDO', '73520', 73),
(988, 'PIEDRAS', '73547', 73),
(989, 'PLANADAS', '73555', 73),
(990, 'PRADO', '73563', 73),
(991, 'PURIFICACIÓN', '73585', 73),
(992, 'RIOBLANCO', '73616', 73),
(993, 'RONCESVALLES', '73622', 73),
(994, 'ROVIRA', '73624', 73),
(995, 'SALDAÑA', '73671', 73),
(996, 'SAN ANTONIO', '73675', 73),
(997, 'SAN LUIS', '73678', 73),
(998, 'SANTA ISABEL', '73686', 73),
(999, 'SUÁREZ', '73770', 73),
(1000, 'VALLE DE SAN JUAN', '73854', 73);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `paiId` int(11) NOT NULL,
  `paiNombre` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `paiCodigo` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `pais`
--

INSERT INTO `pais` (`paiId`, `paiNombre`, `paiCodigo`) VALUES
(1, 'ANDORRA', '37'),
(2, 'EMIRATOS ?RABES UNIDOS', '244'),
(3, 'AFGANIST?N', '13'),
(4, 'ANTIGUA Y BARBUDA', '43'),
(5, 'ANGUILA', '41'),
(6, 'ALBANIA', '17'),
(7, 'ARMENIA', '26'),
(8, 'ANTILLAS HOLANDESAS', '47'),
(9, 'ANGOLA', '40'),
(10, 'ANT?RTIDA', '10'),
(11, 'ARGENTINA', '63'),
(12, 'SAMOA AMERICANA', '690'),
(13, 'AUSTRIA', '72'),
(14, 'AUSTRALIA', '69'),
(15, 'ARUBA', '27'),
(16, 'ISLAS GLAND', '248'),
(17, 'AZERBAIY?N', '74'),
(18, 'BOSNIA Y HERZEGOVINA', '29'),
(19, 'BARBADOS', '83'),
(20, 'BANGLADESH', '81'),
(21, 'B?LGICA', '87'),
(22, 'BURKINA FASO', '31'),
(23, 'BULGARIA', '111'),
(24, 'BAHR?IN', '80'),
(25, 'BURUNDI', '115'),
(26, 'BEN?N', '229'),
(27, 'BERMUDAS', '90'),
(28, 'BRUN?I', '108'),
(29, 'BOLIVIA', '97'),
(30, 'BRASIL', '105'),
(31, 'BAHAMAS', '77'),
(32, 'BHUT?N', '119'),
(33, 'ISLA BOUVET', '74'),
(34, 'BOTSUANA', '101'),
(35, 'BIELORRUSIA', '91'),
(36, 'BELICE', '88'),
(37, 'CANAD?', '149'),
(38, 'ISLAS COCOS', '165'),
(39, 'REP?BLICA DEMOCR?TICA DEL CONGO', '888'),
(40, 'REP?BLICA CENTROAFRICANA', '640'),
(41, 'CONGO', '177'),
(42, 'SUIZA', '767'),
(43, 'COSTA DE MARFIL', '193'),
(44, 'ISLAS COOK', '183'),
(45, 'CHILE', '211'),
(46, 'CAMER?N', '145'),
(47, 'CHINA', '215'),
(48, 'COLOMBIA', '169'),
(49, 'COSTA RICA', '196'),
(50, 'CUBA', '199'),
(51, 'CABO VERDE', '127'),
(52, 'ISLA DE NAVIDAD', '162'),
(53, 'CHIPRE', '221'),
(54, 'REP?BLICA CHECA', '644'),
(55, 'ALEMANIA', '23'),
(56, 'YIBUTI', '885'),
(57, 'DINAMARCA', '232'),
(58, 'DOMINICA', '235'),
(59, 'REP?BLICA DOMINICANA', '647'),
(60, 'ARGELIA', '59'),
(61, 'ECUADOR', '239'),
(62, 'ESTONIA', '251'),
(63, 'EGIPTO', '240'),
(64, 'SAHARA OCCIDENTAL', '685'),
(65, 'ERITREA', '243'),
(66, 'ESPA?A', '245'),
(67, 'ETIOP?A', '253'),
(68, 'FINLANDIA', '271'),
(69, 'FIYI', '870'),
(70, 'ISLAS MALVINAS', '238'),
(71, 'MICRONESIA', '494'),
(72, 'ISLAS FEROE', '259'),
(73, 'FRANCIA', '275'),
(74, 'GAB?N', '281'),
(75, 'REINO UNIDO', '628'),
(76, 'GRANADA', '297'),
(77, 'GEORGIA', '287'),
(78, 'GUAYANA FRANCESA', '325'),
(79, 'GHANA', '289'),
(80, 'GIBRALTAR', '293'),
(81, 'GROENLANDIA', '305'),
(82, 'GAMBIA', '285'),
(83, 'GUINEA', '329'),
(84, 'GUADALUPE', '309'),
(85, 'GUINEA ECUATORIAL', '331'),
(86, 'GRECIA', '301'),
(87, 'ISLAS GEORGIAS DEL SUR Y SANDWICH DEL SUR', '239'),
(88, 'GUATEMALA', '317'),
(89, 'GUAM', '313'),
(90, 'GUINEA-BISSAU', '334'),
(91, 'GUYANA', '337'),
(92, 'HONG KONG', '351'),
(93, 'ISLAS HEARD Y MCDONALD', '334'),
(94, 'HONDURAS', '345'),
(95, 'CROACIA', '198'),
(96, 'HAIT?', '341'),
(97, 'HUNGR?A', '355'),
(98, 'INDONESIA', '365'),
(99, 'IRLANDA', '375'),
(100, 'ISRAEL', '383'),
(101, 'INDIA', '361'),
(102, 'TERRITORIO BRIT?NICO DEL OC?ANO ?NDICO', '787'),
(103, 'IRAQ', '369'),
(104, 'IR?N', '372'),
(105, 'ISLANDIA', '379'),
(106, 'ITALIA', '386'),
(107, 'JAMAICA', '391'),
(108, 'JORDANIA', '403'),
(109, 'JAP?N', '399'),
(110, 'KENIA', '410'),
(111, 'KIRGUIST?N', '412'),
(112, 'CAMBOYA', '141'),
(113, 'KIRIBATI', '411'),
(114, 'COMORAS', '173'),
(115, 'SAN CRIST?BAL Y NIEVES', '695'),
(116, 'COREA DEL NORTE', '187'),
(117, 'COREA DEL SUR', '190'),
(118, 'KUWAIT', '413'),
(119, 'ISLAS CAIM?N', '137'),
(120, 'KAZAJST?N', '406'),
(121, 'LAOS', '420'),
(122, 'L?BANO', '431'),
(123, 'SANTA LUC?A', '715'),
(124, 'LIECHTENSTEIN', '440'),
(125, 'SRI LANKA', '750'),
(126, 'LIBERIA', '434'),
(127, 'LESOTHO', '426'),
(128, 'LITUANIA', '443'),
(129, 'LUXEMBURGO', '445'),
(130, 'LETONIA', '429'),
(131, 'LIBIA', '438'),
(132, 'MARRUECOS', '474'),
(133, 'M?NACO', '498'),
(134, 'MOLDAVIA', '496'),
(135, 'MONTENEGRO', '502'),
(136, 'MADAGASCAR', '450'),
(137, 'ISLAS MARSHALL', '472'),
(138, 'MACEDONIA', '448'),
(139, 'MAL?', '464'),
(140, 'MYANMAR', '93'),
(141, 'MONGOLIA', '497'),
(142, 'MACAO', '447'),
(143, 'ISLAS MARIANAS DEL NORTE', '469'),
(144, 'MARTINICA', '477'),
(145, 'MAURITANIA', '488'),
(146, 'MONTSERRAT', '501'),
(147, 'MALTA', '467'),
(148, 'MAURICIO', '485'),
(149, 'MALDIVAS', '461'),
(150, 'MALAUI', '458'),
(151, 'M?XICO', '493'),
(152, 'MALASIA', '455'),
(153, 'MOZAMBIQUE', '508'),
(154, 'NAMIBIA', '507'),
(155, 'NUEVA CALEDONIA', '542'),
(156, 'N?GER', '525'),
(157, 'ISLA NORFOLK', '574'),
(158, 'NIGERIA', '528'),
(159, 'NICARAGUA', '521'),
(160, 'PA?SES BAJOS', '573'),
(161, 'NORUEGA', '538'),
(162, 'NEPAL', '517'),
(163, 'NAURU', '508'),
(164, 'NIUE', '531'),
(165, 'NUEVA ZELANDA', '548'),
(166, 'OM?N', '556'),
(167, 'PANAM?', '580'),
(168, 'PER?', '589'),
(169, 'POLINESIA FRANCESA', '599'),
(170, 'PAP?A NUEVA GUINEA', '545'),
(171, 'FILIPINAS', '267'),
(172, 'PAKIST?N', '576'),
(173, 'POLONIA', '603'),
(174, 'SAN PEDRO Y MIQUEL?N', '700'),
(175, 'ISLAS PITCAIRN', '593'),
(176, 'PUERTO RICO', '611'),
(177, 'PALESTINA', '579'),
(178, 'PORTUGAL', '607'),
(179, 'PALAOS', '578'),
(180, 'PARAGUAY', '586'),
(181, 'QATAR', '618'),
(182, 'REUNI?N', '660'),
(183, 'RUMANIA', '670'),
(184, 'SERBIA', '32'),
(185, 'RUSIA', '676'),
(186, 'RUANDA', '675'),
(187, 'ARABIA SAUD?', '53'),
(188, 'ISLAS SALOM?N', '677'),
(189, 'SEYCHELLES', '731'),
(190, 'SUD?N', '759'),
(191, 'SUECIA', '764'),
(192, 'SINGAPUR', '741'),
(193, 'SANTA HELENA', '710'),
(194, 'ESLOVENIA', '247'),
(195, 'SVALBARD Y JAN MAYEN', '744'),
(196, 'ESLOVAQUIA', '246'),
(197, 'SIERRA LEONA', '735'),
(198, 'SAN MARINO', '697'),
(199, 'SENEGAL', '728'),
(200, 'SOMALIA', '748'),
(201, 'SURINAM', '770'),
(202, 'SANTO TOM? Y PR?NCIPE', '720'),
(203, 'EL SALVADOR', '242'),
(204, 'SIRIA', '744'),
(205, 'SUAZILANDIA', '773'),
(206, 'ISLAS TURCAS Y CAICOS', '823'),
(207, 'CHAD', '203'),
(208, 'TERRITORIOS AUSTRALES FRANCESES', '260'),
(209, 'TOGO', '800'),
(210, 'TAILANDIA', '776'),
(211, 'TAYIKIST?N', '774'),
(212, 'TOKELAU', '805'),
(213, 'TIMOR ORIENTAL', '788'),
(214, 'TURKMENIST?N', '825'),
(215, 'T?NEZ', '820'),
(216, 'TONGA', '810'),
(217, 'TURQU?A', '827'),
(218, 'TRINIDAD Y TOBAGO', '815'),
(219, 'TUVALU', '828'),
(220, 'TAIW?N', '218'),
(221, 'TANZANIA', '780'),
(222, 'UCRANIA', '830'),
(223, 'UGANDA', '833'),
(224, 'ISLAS ULTRAMARINAS DE ESTADOS UNIDOS', '566'),
(225, 'ESTADOS UNIDOS', '249'),
(226, 'URUGUAY', '845'),
(227, 'UZBEKIST?N', '847'),
(228, 'CIUDAD DEL VATICANO', '159'),
(229, 'SAN VICENTE Y LAS GRANADINAS', '705'),
(230, 'VENEZUELA', '850'),
(231, 'ISLAS V?RGENES BRIT?NICAS', '863'),
(232, 'ISLAS V?RGENES DE LOS ESTADOS UNIDOS', '866'),
(233, 'VIETNAM', '855'),
(234, 'VANUATU', '551'),
(235, 'WALLIS Y FUTUNA', '875'),
(236, 'SAMOA', '687'),
(237, 'YEMEN', '880'),
(238, 'MAYOTTE', '175'),
(239, 'SUD?FRICA', '756'),
(240, 'ZAMBIA', '890'),
(241, 'ZIMBABUE', '665'),
(999, '', '999');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `resId` int(11) NOT NULL,
  `resFechaRegistro` date NOT NULL,
  `resFechaLlegada` date NOT NULL,
  `resFechaSalida` date NOT NULL,
  `resFechaChecking` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `resFechaCheckout` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `resEstado` enum('0','1','2','3') COLLATE utf8mb4_spanish_ci NOT NULL COMMENT '0 -> Reservado: Es cuando realiza la reserva\n1 -> Confirmado: Es cuando llega al lugar e inicia el checking\n2 -> Terminado: Cuando se realiza el checkout\n3 -> Cancelado: No llega',
  `resPago` double DEFAULT NULL,
  `fkCliente` int(11) NOT NULL,
  `fkAlojamiento` int(11) NOT NULL,
  `fkUsuarioChecking` int(11) DEFAULT NULL,
  `fkUsuarioCheckout` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva_cliente`
--

CREATE TABLE `reserva_cliente` (
  `ruId` int(11) NOT NULL,
  `fkCliente` int(11) NOT NULL,
  `fkReserva` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `rolId` int(11) NOT NULL,
  `rolNombre` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol_usuario`
--

CREATE TABLE `rol_usuario` (
  `ruId` int(11) NOT NULL,
  `rolEstado` enum('0','1','2') COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT '0' COMMENT '0 -> Solicitado1 -> Aceptado2 -> Cancelado',
  `fkUsuario` int(11) NOT NULL,
  `fkRol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoalojamiento`
--

CREATE TABLE `tipoalojamiento` (
  `talId` int(11) NOT NULL,
  `talNombre` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoidentificacion`
--

CREATE TABLE `tipoidentificacion` (
  `tidId` int(11) NOT NULL,
  `tipSigla` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `tipNombre` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipolugar`
--

CREATE TABLE `tipolugar` (
  `tluId` int(11) NOT NULL,
  `tluNombre` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `usuId` int(11) NOT NULL,
  `usuIdentificacion` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `usuNombres` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `usuGenero` enum('M','F','O') COLLATE utf8mb4_spanish_ci NOT NULL,
  `usuCorreo` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `usuTelefono` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `usuAvatar` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `fkTipoIdentificacion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alojamiento`
--
ALTER TABLE `alojamiento`
  ADD PRIMARY KEY (`aloId`),
  ADD KEY `fkLugar` (`fkLugar`),
  ADD KEY `fkTipoAlojamiento` (`fkTipoAlojamiento`);

--
-- Indices de la tabla `calificacion`
--
ALTER TABLE `calificacion`
  ADD PRIMARY KEY (`calId`),
  ADD KEY `fkUsuario` (`fkUsuario`),
  ADD KEY `fkAlojamiento` (`fkAlojamiento`);

--
-- Indices de la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`depId`),
  ADD KEY `fkPais` (`fkPais`);

--
-- Indices de la tabla `foto`
--
ALTER TABLE `foto`
  ADD PRIMARY KEY (`fotId`),
  ADD KEY `fkLugar` (`fkLugar`),
  ADD KEY `fkAlojamiento` (`fkAlojamiento`);

--
-- Indices de la tabla `lugar`
--
ALTER TABLE `lugar`
  ADD PRIMARY KEY (`lugId`),
  ADD KEY `fkTipoLugar` (`fkTipoLugar`),
  ADD KEY `fkMunicipio` (`fkMunicipio`);

--
-- Indices de la tabla `municipio`
--
ALTER TABLE `municipio`
  ADD PRIMARY KEY (`munId`),
  ADD UNIQUE KEY `munCodigo` (`munCodigo`),
  ADD KEY `fkDepartamento` (`fkDepartamento`);

--
-- Indices de la tabla `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`paiId`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`resId`),
  ADD KEY `fkCliente` (`fkCliente`),
  ADD KEY `fkAlojamiento` (`fkAlojamiento`),
  ADD KEY `fkUsuarioChecking` (`fkUsuarioChecking`),
  ADD KEY `fkUsuarioCheckout` (`fkUsuarioCheckout`);

--
-- Indices de la tabla `reserva_cliente`
--
ALTER TABLE `reserva_cliente`
  ADD PRIMARY KEY (`ruId`),
  ADD KEY `fkUsuario` (`fkCliente`),
  ADD KEY `fkReserva` (`fkReserva`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`rolId`);

--
-- Indices de la tabla `rol_usuario`
--
ALTER TABLE `rol_usuario`
  ADD PRIMARY KEY (`ruId`),
  ADD KEY `fkUsuario` (`fkUsuario`),
  ADD KEY `fkRol` (`fkRol`);

--
-- Indices de la tabla `tipoalojamiento`
--
ALTER TABLE `tipoalojamiento`
  ADD PRIMARY KEY (`talId`);

--
-- Indices de la tabla `tipoidentificacion`
--
ALTER TABLE `tipoidentificacion`
  ADD PRIMARY KEY (`tidId`);

--
-- Indices de la tabla `tipolugar`
--
ALTER TABLE `tipolugar`
  ADD PRIMARY KEY (`tluId`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`usuId`),
  ADD UNIQUE KEY `usuIdentificacion` (`usuIdentificacion`),
  ADD KEY `fkTipoIdentificacion` (`fkTipoIdentificacion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alojamiento`
--
ALTER TABLE `alojamiento`
  MODIFY `aloId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `calificacion`
--
ALTER TABLE `calificacion`
  MODIFY `calId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `departamento`
--
ALTER TABLE `departamento`
  MODIFY `depId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10000;

--
-- AUTO_INCREMENT de la tabla `foto`
--
ALTER TABLE `foto`
  MODIFY `fotId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `lugar`
--
ALTER TABLE `lugar`
  MODIFY `lugId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `municipio`
--
ALTER TABLE `municipio`
  MODIFY `munId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1001;

--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `paiId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1000;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `resId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reserva_cliente`
--
ALTER TABLE `reserva_cliente`
  MODIFY `ruId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `rolId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `rol_usuario`
--
ALTER TABLE `rol_usuario`
  MODIFY `ruId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipoalojamiento`
--
ALTER TABLE `tipoalojamiento`
  MODIFY `talId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipoidentificacion`
--
ALTER TABLE `tipoidentificacion`
  MODIFY `tidId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipolugar`
--
ALTER TABLE `tipolugar`
  MODIFY `tluId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `usuId` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alojamiento`
--
ALTER TABLE `alojamiento`
  ADD CONSTRAINT `fk_alojamiento_lugar` FOREIGN KEY (`fkLugar`) REFERENCES `lugar` (`lugId`),
  ADD CONSTRAINT `fk_alojamiento_tipoalojamiento` FOREIGN KEY (`fkTipoAlojamiento`) REFERENCES `tipoalojamiento` (`talId`);

--
-- Filtros para la tabla `calificacion`
--
ALTER TABLE `calificacion`
  ADD CONSTRAINT `fk_calificacion_alojamiento` FOREIGN KEY (`fkAlojamiento`) REFERENCES `alojamiento` (`aloId`),
  ADD CONSTRAINT `fk_calificacion_usuario` FOREIGN KEY (`fkUsuario`) REFERENCES `usuario` (`usuId`);

--
-- Filtros para la tabla `departamento`
--
ALTER TABLE `departamento`
  ADD CONSTRAINT `fk_departamento_pais` FOREIGN KEY (`fkPais`) REFERENCES `pais` (`paiId`);

--
-- Filtros para la tabla `foto`
--
ALTER TABLE `foto`
  ADD CONSTRAINT `fk_foto_alojamiento` FOREIGN KEY (`fkAlojamiento`) REFERENCES `alojamiento` (`aloId`),
  ADD CONSTRAINT `fk_foto_lugar` FOREIGN KEY (`fkLugar`) REFERENCES `lugar` (`lugId`);

--
-- Filtros para la tabla `lugar`
--
ALTER TABLE `lugar`
  ADD CONSTRAINT `fk_lugar_municipio` FOREIGN KEY (`fkMunicipio`) REFERENCES `municipio` (`munId`),
  ADD CONSTRAINT `fk_lugar_tipolugar` FOREIGN KEY (`fkTipoLugar`) REFERENCES `tipolugar` (`tluId`);

--
-- Filtros para la tabla `municipio`
--
ALTER TABLE `municipio`
  ADD CONSTRAINT `fk_municipio_departamento` FOREIGN KEY (`fkDepartamento`) REFERENCES `departamento` (`depId`);

--
-- Filtros para la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `fk_reserva_alojamiento` FOREIGN KEY (`fkAlojamiento`) REFERENCES `alojamiento` (`aloId`),
  ADD CONSTRAINT `fk_reserva_cliente` FOREIGN KEY (`fkCliente`) REFERENCES `usuario` (`usuId`),
  ADD CONSTRAINT `fk_reserva_usuariochecking` FOREIGN KEY (`fkUsuarioChecking`) REFERENCES `usuario` (`usuId`),
  ADD CONSTRAINT `fk_reserva_usuariocheckout` FOREIGN KEY (`fkUsuarioCheckout`) REFERENCES `usuario` (`usuId`);

--
-- Filtros para la tabla `reserva_cliente`
--
ALTER TABLE `reserva_cliente`
  ADD CONSTRAINT `fk_reservacliente_reserva` FOREIGN KEY (`fkReserva`) REFERENCES `reserva` (`resId`),
  ADD CONSTRAINT `fk_reservacliente_usuario` FOREIGN KEY (`fkCliente`) REFERENCES `usuario` (`usuId`);

--
-- Filtros para la tabla `rol_usuario`
--
ALTER TABLE `rol_usuario`
  ADD CONSTRAINT `fk_rolusuario_rol` FOREIGN KEY (`fkRol`) REFERENCES `rol` (`rolId`),
  ADD CONSTRAINT `fk_rolusuario_usuario` FOREIGN KEY (`fkUsuario`) REFERENCES `usuario` (`usuId`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_usuario_tipoidentificacion` FOREIGN KEY (`fkTipoIdentificacion`) REFERENCES `tipoidentificacion` (`tidId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
