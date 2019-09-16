-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-09-2019 a las 16:10:20
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
  `aloCodigo` varchar(11) NOT NULL,
  `aloCapacidad` int(11) NOT NULL,
  `aloPrecio` varchar(45) NOT NULL,
  `aloDescripcion` text NOT NULL,
  `fkLugar` int(11) NOT NULL,
  `fkTipoAlojamiento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calificacion`
--

CREATE TABLE `calificacion` (
  `calId` int(11) NOT NULL,
  `calValoracion` enum('1','2','3','4','5') NOT NULL,
  `calComentario` text,
  `fkUsuario` int(11) NOT NULL,
  `fkAlojamiento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamento`
--

CREATE TABLE `departamento` (
  `depId` int(11) NOT NULL,
  `depNombre` varchar(45) NOT NULL,
  `fkPais` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `foto`
--

CREATE TABLE `foto` (
  `fotId` int(11) NOT NULL,
  `fotRuta` varchar(45) NOT NULL,
  `fotLabel` varchar(45) NOT NULL,
  `fotDescripcion` text,
  `fkLugar` int(11) DEFAULT NULL,
  `fkAlojamiento` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar`
--

CREATE TABLE `lugar` (
  `lugId` int(11) NOT NULL,
  `lugNombre` varchar(45) NOT NULL,
  `lugDireccion` varchar(45) NOT NULL,
  `lugTelefono` varchar(45) NOT NULL,
  `lugCorreo` varchar(45) NOT NULL,
  `lugLatitud` varchar(45) NOT NULL,
  `lugLongitud` varchar(45) NOT NULL,
  `lugDescripcion` text NOT NULL,
  `fkTipoLugar` int(11) NOT NULL,
  `fkMunicipio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `municipio`
--

CREATE TABLE `municipio` (
  `munId` int(11) NOT NULL,
  `munNombre` varchar(45) NOT NULL,
  `fkDepartamento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `paiId` int(11) NOT NULL,
  `paiNombre` varchar(45) NOT NULL,
  `paiCodigo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `resId` int(11) NOT NULL,
  `resFechaRegistro` date NOT NULL,
  `resFechaLlegada` date NOT NULL,
  `resFechaSalida` date NOT NULL,
  `resFechaChecking` varchar(45) DEFAULT NULL,
  `resFechaCheckout` varchar(45) DEFAULT NULL,
  `resEstado` enum('0','1','2','3') NOT NULL COMMENT '0 -> Reservado: Es cuando realiza la reserva\n1 -> Confirmado: Es cuando llega al lugar e inicia el checking\n2 -> Terminado: Cuando se realiza el checkout\n3 -> Cancelado: No llega',
  `resPago` double DEFAULT NULL,
  `fkCliente` int(11) NOT NULL,
  `fkAlojamiento` int(11) NOT NULL,
  `fkUsuarioChecking` int(11) DEFAULT NULL,
  `fkUsuarioCheckout` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva_cliente`
--

CREATE TABLE `reserva_cliente` (
  `ruId` int(11) NOT NULL,
  `fkCliente` int(11) NOT NULL,
  `fkReserva` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `rolId` int(11) NOT NULL,
  `rolNombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol_usuario`
--

CREATE TABLE `rol_usuario` (
  `ruId` int(11) NOT NULL,
  `rolEstado` enum('0','1','2') NOT NULL DEFAULT '0' COMMENT '0 -> Solicitado1 -> Aceptado2 -> Cancelado',
  `fkUsuario` int(11) NOT NULL,
  `fkRol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoalojamiento`
--

CREATE TABLE `tipoalojamiento` (
  `talId` int(11) NOT NULL,
  `talNombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoidentificacion`
--

CREATE TABLE `tipoidentificacion` (
  `tidId` int(11) NOT NULL,
  `tipSigla` varchar(45) NOT NULL,
  `tipNombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipolugar`
--

CREATE TABLE `tipolugar` (
  `tluId` int(11) NOT NULL,
  `tluNombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `usuId` int(11) NOT NULL,
  `usuIdentificacion` varchar(45) NOT NULL,
  `usuNombres` varchar(45) NOT NULL,
  `usuGenero` enum('M','F','O') NOT NULL,
  `usuCorreo` varchar(45) DEFAULT NULL,
  `usuTelefono` varchar(45) DEFAULT NULL,
  `usuAvatar` varchar(45) DEFAULT NULL,
  `fkTipoIdentificacion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  ADD KEY `fkDepartamento` (`fkDepartamento`);

--
-- Indices de la tabla `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`paiId`),
  ADD UNIQUE KEY `paiCodigo` (`paiCodigo`);

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
  MODIFY `depId` int(11) NOT NULL AUTO_INCREMENT;

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
  MODIFY `munId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `paiId` int(11) NOT NULL AUTO_INCREMENT;

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
