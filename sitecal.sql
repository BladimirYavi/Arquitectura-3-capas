-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-04-2019 a las 12:05:53
-- Versión del servidor: 10.1.26-MariaDB
-- Versión de PHP: 7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sitecal`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `area`
--

CREATE TABLE `area` (
  `idArea` varchar(4) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `area`
--

INSERT INTO `area` (`idArea`, `nombre`, `telefono`) VALUES
('A001', 'Gabinete', '3498536'),
('A002', 'Laboratorio', '3568947'),
('A003', 'Maquinaria', '3425860'),
('A004', 'Campo', '3498525');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `ciCliente` varchar(12) NOT NULL,
  `nombre` varchar(80) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`ciCliente`, `nombre`, `direccion`, `email`, `telefono`) VALUES
('1025689', 'pedro salazar rivera', 'Av. santa cruz, 1er anillo', 'pedrosr@gmail.com', '71826986'),
('2365896', 'Ariel Saavedra Sensano', 'Av. Cumavi 6to anilo', 'arielss@gmail.com', '75689524'),
('2689548', 'David yavi vicente', 'Av. 1ero de mayo', 'davidyv@gmail.com', '71026589'),
('9681913', 'Wilfredo yaure alvarez', 'Km9 doble via la guardia', 'w6996@gmail.com', '60950982');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuota`
--

CREATE TABLE `cuota` (
  `idCuota` int(11) NOT NULL,
  `monto` double DEFAULT NULL,
  `fechaCuota` varchar(10) DEFAULT NULL,
  `idPago` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cuota`
--

INSERT INTO `cuota` (`idCuota`, `monto`, `fechaCuota`, `idPago`) VALUES
(5, 2000, '23-04-2019', 1),
(6, 1800, '25-04-2019', 1),
(7, 4800, '23-04-2019', 6),
(8, 3500, '23-04-2019', 2),
(9, 4500, '23-04-2019', 3),
(10, 3500, '23-04-2019', 5);

--
-- Disparadores `cuota`
--
DELIMITER $$
CREATE TRIGGER `upsaldoPago` AFTER INSERT ON `cuota` FOR EACH ROW BEGIN
		declare	var_monto double;
		set var_monto = NEW.monto;
		/*select var_monto = monto from cuota where idCuota = New.idCuota;*/
		UPDATE pago SET saldo = (saldo - var_monto) WHERE idPago=new.idPago;
	END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleservicio`
--

CREATE TABLE `detalleservicio` (
  `nro` int(11) NOT NULL,
  `idSolicitud` int(11) NOT NULL,
  `costo` double DEFAULT NULL,
  `idServicio` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detalleservicio`
--

INSERT INTO `detalleservicio` (`nro`, `idSolicitud`, `costo`, `idServicio`) VALUES
(60, 1, 800, 1),
(61, 1, 3000, 4),
(62, 1, 800, 1),
(63, 1, 2000, 7),
(64, 1, 7000, 9),
(65, 2, 800, 1),
(66, 2, 2000, 7),
(67, 2, 7000, 9),
(68, 3, 900, 2),
(69, 3, 6000, 6),
(70, 4, 6000, 6),
(71, 4, 800, 1),
(72, 5, 4000, 5),
(73, 5, 2000, 7),
(74, 5, 800, 1),
(75, 6, 800, 1),
(76, 6, 8000, 8),
(77, 6, 6000, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `idPago` int(11) NOT NULL,
  `fecha` varchar(10) NOT NULL,
  `idSolicitud` int(11) DEFAULT NULL,
  `ciCliente` varchar(12) DEFAULT NULL,
  `saldo` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`idPago`, `fecha`, `idSolicitud`, `ciCliente`, `saldo`) VALUES
(1, '22-04-2019', 1, '1025689', 0),
(2, '23-04-2019', 1, '1025689', 6300),
(3, '22-04-2019', 2, '1025689', 5300),
(4, '23-04-2019', 3, '2365896', 6900),
(5, '23-04-2019', 4, '2689548', 3300),
(6, '23-04-2019', 6, '2689548', 10000),
(7, '23-04-2019', 5, '9681913', 6800);

--
-- Disparadores `pago`
--
DELIMITER $$
CREATE TRIGGER `upestadoPago` AFTER UPDATE ON `pago` FOR EACH ROW BEGIN
		DECLARE	var_saldo DOUBLE;
		SET var_saldo = NEW.saldo;
		IF var_saldo = 0 THEN
		UPDATE solicitud SET estadoPago="Cancelado" WHERE idSolicitud=new.idSolicitud;
		END IF;
	END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recepcionmaterial`
--

CREATE TABLE `recepcionmaterial` (
  `idRecepcion` int(11) NOT NULL,
  `material` varchar(80) DEFAULT NULL,
  `nro_muestra` int(11) DEFAULT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `fechallegada` varchar(10) DEFAULT NULL,
  `fechaSalidad` varchar(10) DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `idSolicitud` int(11) DEFAULT NULL,
  `ciCliente` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `recepcionmaterial`
--

INSERT INTO `recepcionmaterial` (`idRecepcion`, `material`, `nro_muestra`, `descripcion`, `fechallegada`, `fechaSalidad`, `idUsuario`, `idSolicitud`, `ciCliente`) VALUES
(10, 'Arcilla', 2, 'Los material que ingresaron son los ensayos de humedad natural y corte directo', '22-04-2019', '30-04-2019', 2, 3, '2365896'),
(11, 'Concreto', 3, 'Ingresaron material en bolsas para el ensayo de compresion  confinada, corte directo', '23-04-2019', '30-04-2019', 3, 5, '9681913'),
(12, 'Arcilla', 5, 'Materiales en bolsas para los ensayos solicitados', '22-04-2019', '01-05-2019', 2, 1, '1025689'),
(13, 'Piedra', 3, 'Entrego los materiales en 2 bolsas de yute', '24-04-2019', '28-04-2019', 3, 3, '2365896'),
(14, 'Piedra', 6, 'Materiales Entregados en Unidades', '22-04-2019', '25-04-2019', 2, 2, '1025689'),
(15, 'Arcilla', 4, 'Se recepciono los material en bolsa negra para su posterior analisis ', '23-04-2019', '02-05-2019', 3, 4, '2689548'),
(16, 'Concreto', 10, 'Bolsas de materiales para Sr. david yavi realizar urgente', '23-04-2019', '30-04-2019', 2, 6, '2689548');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE `servicio` (
  `idServicio` int(11) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `costoServ` double NOT NULL,
  `idArea` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `servicio`
--

INSERT INTO `servicio` (`idServicio`, `nombre`, `costoServ`, `idArea`) VALUES
(1, 'Granulometria', 800, 'A002'),
(2, 'Humedad Natural', 900, 'A002'),
(4, 'Limites de plasticidad', 3000, 'A002'),
(5, 'Compresion confinada', 4000, 'A002'),
(6, 'Corte directo', 6000, 'A002'),
(7, 'Peso unitario', 2000, 'A002'),
(8, 'Consolidacion', 8000, 'A002'),
(9, 'Compactacion', 7000, 'A002');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitud`
--

CREATE TABLE `solicitud` (
  `idSolicitud` int(11) NOT NULL,
  `descripcion` varchar(400) DEFAULT NULL,
  `fecha` varchar(10) DEFAULT NULL,
  `montoTotal` double DEFAULT NULL,
  `ciCliente` varchar(12) DEFAULT NULL,
  `estadoEntrega` varchar(10) NOT NULL,
  `estadoPago` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `solicitud`
--

INSERT INTO `solicitud` (`idSolicitud`, `descripcion`, `fecha`, `montoTotal`, `ciCliente`, `estadoEntrega`, `estadoPago`) VALUES
(1, 'Entregar urgente para el 23 de abril', '22-04-2019', 9800, '1025689', 'Finalizado', 'Cancelado'),
(2, 'Terminar urgente, hasta el dia miercoles 23 de abril', '22-04-2019', 9800, '1025689', 'En proceso', 'debe'),
(3, 'Terminar Urgente los ensayos del laboratorio', '24-04-2019', 6900, '2365896', 'En proceso', 'debe'),
(4, 'Terminar Urgente los ensayos', '23-04-2019', 6800, '2689548', 'En proceso', 'debe'),
(5, 'Terminar los ensayos hasta el 1ero de mayo', '23-04-2019', 6800, '9681913', 'En proceso', 'debe'),
(6, 'Realizar todos los ensayos con cuidado y terminar hasta el 30 de abril', '23-04-2019', 14800, '2689548', 'Finalizado', 'debe');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `tipoUsuario` varchar(50) DEFAULT NULL,
  `idArea` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `email`, `telefono`, `tipoUsuario`, `idArea`) VALUES
(1, 'bladimir yavi quiroz', 'blyq24@gmail.com', '65036181', 'gerente', 'A001'),
(2, 'martin espinoza cruz', 'martinec@hotmail.com', '65005860', 'Encargado', 'A003'),
(3, 'wilson cabrera lopez', 'wilsoncl@yahoo.com', '7049668', 'ayudante', 'A002'),
(4, 'Oscar Quinteros Flores', 'OscarQF@gmail.m', '75689654', 'Ayudante', 'A004');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `area`
--
ALTER TABLE `area`
  ADD PRIMARY KEY (`idArea`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`ciCliente`);

--
-- Indices de la tabla `cuota`
--
ALTER TABLE `cuota`
  ADD PRIMARY KEY (`idCuota`),
  ADD KEY `idPago` (`idPago`);

--
-- Indices de la tabla `detalleservicio`
--
ALTER TABLE `detalleservicio`
  ADD PRIMARY KEY (`nro`,`idSolicitud`),
  ADD KEY `idSolicitud` (`idSolicitud`),
  ADD KEY `idServicio` (`idServicio`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`idPago`),
  ADD KEY `idSolicitud` (`idSolicitud`),
  ADD KEY `ciCliente` (`ciCliente`);

--
-- Indices de la tabla `recepcionmaterial`
--
ALTER TABLE `recepcionmaterial`
  ADD PRIMARY KEY (`idRecepcion`),
  ADD KEY `idUsuario` (`idUsuario`),
  ADD KEY `idSolicitud` (`idSolicitud`),
  ADD KEY `ciCliente` (`ciCliente`);

--
-- Indices de la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD PRIMARY KEY (`idServicio`),
  ADD KEY `idArea` (`idArea`);

--
-- Indices de la tabla `solicitud`
--
ALTER TABLE `solicitud`
  ADD PRIMARY KEY (`idSolicitud`),
  ADD KEY `ciCliente` (`ciCliente`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD KEY `idArea` (`idArea`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cuota`
--
ALTER TABLE `cuota`
  MODIFY `idCuota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT de la tabla `detalleservicio`
--
ALTER TABLE `detalleservicio`
  MODIFY `nro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;
--
-- AUTO_INCREMENT de la tabla `recepcionmaterial`
--
ALTER TABLE `recepcionmaterial`
  MODIFY `idRecepcion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT de la tabla `servicio`
--
ALTER TABLE `servicio`
  MODIFY `idServicio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cuota`
--
ALTER TABLE `cuota`
  ADD CONSTRAINT `cuota_ibfk_1` FOREIGN KEY (`idPago`) REFERENCES `pago` (`idPago`);

--
-- Filtros para la tabla `detalleservicio`
--
ALTER TABLE `detalleservicio`
  ADD CONSTRAINT `detalleservicio_ibfk_1` FOREIGN KEY (`idSolicitud`) REFERENCES `solicitud` (`idSolicitud`),
  ADD CONSTRAINT `detalleservicio_ibfk_2` FOREIGN KEY (`idServicio`) REFERENCES `servicio` (`idServicio`);

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`idSolicitud`) REFERENCES `solicitud` (`idSolicitud`),
  ADD CONSTRAINT `pago_ibfk_2` FOREIGN KEY (`ciCliente`) REFERENCES `cliente` (`ciCliente`);

--
-- Filtros para la tabla `recepcionmaterial`
--
ALTER TABLE `recepcionmaterial`
  ADD CONSTRAINT `recepcionmaterial_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`),
  ADD CONSTRAINT `recepcionmaterial_ibfk_2` FOREIGN KEY (`idSolicitud`) REFERENCES `solicitud` (`idSolicitud`),
  ADD CONSTRAINT `recepcionmaterial_ibfk_3` FOREIGN KEY (`ciCliente`) REFERENCES `cliente` (`ciCliente`);

--
-- Filtros para la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD CONSTRAINT `servicio_ibfk_1` FOREIGN KEY (`idArea`) REFERENCES `area` (`idArea`);

--
-- Filtros para la tabla `solicitud`
--
ALTER TABLE `solicitud`
  ADD CONSTRAINT `solicitud_ibfk_1` FOREIGN KEY (`ciCliente`) REFERENCES `cliente` (`ciCliente`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idArea`) REFERENCES `area` (`idArea`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
