DROP TABLE IF EXISTS odontologos;
CREATE TABLE odontologos(matricula INT UNIQUE NOT NULL, nombre VARCHAR(40) , apellido VARCHAR(40), PRIMARY KEY(matricula));

DROP TABLE IF EXISTS pacientes;
CREATE TABLE pacientes(dni INT UNIQUE NOT NULL, nombre VARCHAR(40) , apellido VARCHAR(40), domicilio INT , fechaAlta DATETIME, PRIMARY KEY(dni));

DROP TABLE IF EXISTS domicilios;
CREATE TABLE domicilios(id INT AUTO_INCREMENT, calle VARCHAR(40) , numero INT, localidad VARCHAR(40) , provincia VARCHAR(40), PRIMARY KEY(id));

DROP TABLE IF EXISTS turnos;
CREATE TABLE turnos(id INT AUTO_INCREMENT, fecha DATETIME, paciente INT, odontologo INT, PRIMARY KEY(id));
