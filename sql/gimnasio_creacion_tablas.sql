-- Creación de la base de datos

CREATE DATABASE gimnasio_ragnarok;

USE gimnasio_ragnarok; -- <- Sirve para que MySQL sepa que tiene que crear las tablas aquí


-- Creación de las tablas independientes

CREATE TABLE servicios (
    id_servicio INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(50)  NOT NULL,
    precio      DECIMAL(6,2) NOT NULL CHECK (precio > 0),
    fecha_alta  DATE         NOT NULL,
    fecha_baja  DATE
);

CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre     VARCHAR(20)  NOT NULL,
    apellidos  VARCHAR(50)  NOT NULL,
    dni        VARCHAR(9)   UNIQUE NOT NULL,
    email      VARCHAR(60)  UNIQUE NOT NULL,
    telefono   VARCHAR(15)  NOT NULL,
    fecha_alta DATE         NOT NULL,
    fecha_baja DATE
);

CREATE TABLE entrenadores (
    id_entrenador INT AUTO_INCREMENT PRIMARY KEY,
    nombre              VARCHAR(20)  NOT NULL,
    apellidos           VARCHAR(50)  NOT NULL,
    dni                 VARCHAR(9)   UNIQUE NOT NULL,
    email               VARCHAR(60)  UNIQUE NOT NULL,
    telefono            VARCHAR(15)  NOT NULL,
    especialidad        VARCHAR(30)  NOT NULL,
    fecha_contratacion  DATE NOT NULL,
    fecha_baja          DATE
);

CREATE TABLE salas (
    id_sala INT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(20) NOT NULL,
    tipo      VARCHAR(20) NOT NULL,
    capacidad INT         NOT NULL
);

-- Creación de las tablas con claves foráneas

CREATE TABLE pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_servicio INT NOT NULL,
    id_cliente  INT NOT NULL,
    metodo_pago VARCHAR(20)  NOT NULL,
    fecha_pago  DATE         NOT NULL,
    importe     DECIMAL(6,2) NOT NULL CHECK (importe > 0),
    estado      VARCHAR(20)  NOT NULL,

    -- Clave foránea hacia la tabla servicios
    CONSTRAINT      fk_pagos_servicios
        FOREIGN KEY (id_servicio)
        REFERENCES  servicios(id_servicio),

    -- Clave foránea hacia la tabla clientes
    CONSTRAINT      fk_pagos_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES  clientes(id_cliente)
);

CREATE TABLE clases (
    id_clase INT AUTO_INCREMENT PRIMARY KEY,
    id_entrenador    INT NOT NULL,
    id_sala          INT NOT NULL,
    nombre           VARCHAR(20) NOT NULL,
    nivel            VARCHAR(20) NOT NULL,
    duracion_minutos INT NOT NULL CHECK(duracion_minutos > 0),

    -- Clave foránea hacia la tabla entrenadores
    CONSTRAINT      fk_clases_entrenadores
        FOREIGN KEY (id_entrenador)
        REFERENCES  entrenadores(id_entrenador),

    -- Clave foránea hacia la tabla salas
    CONSTRAINT      fk_clases_salas
        FOREIGN KEY (id_sala)
        REFERENCES  salas(id_sala)
);

CREATE TABLE horarios (
    id_horario INT AUTO_INCREMENT PRIMARY KEY,
    id_clase    INT NOT NULL,
    dia_semana  VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin    TIME NOT NULL,

    -- Clave foránea hacia la tabla clases
    CONSTRAINT      fk_horarios_clases
        FOREIGN KEY (id_clase)
        REFERENCES  clases(id_clase)
);

CREATE TABLE reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente    INT  NOT NULL,
    id_horario    INT  NOT NULL,
    fecha_reserva DATE NOT NULL,
    estado        VARCHAR(20) NOT NULL,

    -- Clave foránea hacia la tabla clientes
    CONSTRAINT      fk_reservas_clientes
        FOREIGN KEY (id_cliente)
        REFERENCES  clientes(id_cliente),

    -- Clave foránea hacia la tabla horarios
    CONSTRAINT      fk_reservas_horarios
        FOREIGN KEY (id_horario)
        REFERENCES  horarios(id_horario)
);