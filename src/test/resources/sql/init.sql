
DROP TABLE IF EXISTS odontologos;
CREATE TABLE odontologos(id INT AUTO_INCREMENT UNIQUE, matricula INT UNIQUE NOT NULL, nombre VARCHAR(40) , apellido VARCHAR(40), PRIMARY KEY(id));

DROP TABLE IF EXISTS pacientes;
CREATE TABLE pacientes(id INT AUTO_INCREMENT UNIQUE, dni INT UNIQUE NOT NULL, nombre VARCHAR(40) , apellido VARCHAR(40), domicilio INT , fechaAlta DATETIME, PRIMARY KEY(id));

DROP TABLE IF EXISTS domicilios;
CREATE TABLE domicilios(id INT AUTO_INCREMENT, calle VARCHAR(40) , numero INT, localidad VARCHAR(40) , provincia VARCHAR(40), PRIMARY KEY(id));

DROP TABLE IF EXISTS turnos;
CREATE TABLE turnos(id INT AUTO_INCREMENT, fecha DATETIME, paciente INT, odontologo INT, PRIMARY KEY(id));
