create database calificaciones;
use calificaciones;

create table usuarios (
    correo VARCHAR(50) NOT NULL PRIMARY KEY,
    contrasenia VARCHAR(255) NOT NULL,
    cedula VARCHAR(10) NOT NULL,
    tipo_rol ENUM('Administrador', 'Estudiante') NOT NULL
);

-- Insertar un Administrador
INSERT INTO usuarios (correo, contrasenia, cedula, tipo_rol) 
VALUES ('admin@epn.ec.com', 'admin123', '1234567890', 'Administrador');

-- Insertar un Estudiante
INSERT INTO usuarios (correo, contrasenia, cedula, tipo_rol) 
VALUES ('richard@epn.com', 'richard123', '1753078839', 'Estudiante');


CREATE TABLE calificaciones (
    cedula_estudiante VARCHAR(10) NOT NULL,
    materia INT,
    calificacion DECIMAL(5, 2) NOT NULL
);


select * from usuarios;
select * from calificaciones;

