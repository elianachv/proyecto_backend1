package com.elianachv.backend1.proyecto.dao.util;

public class Sentencia {

    // ODONTOLOGOS
    public static String CREAR_TABLA_ODONTOLOGOS = "CREATE TABLE odontologos(matricula INT PRIMARY KEY, nombre VARCHAR(40) , apellido VARCHAR(40));";
    public static String BORRAR_TABLA_ODONTOLOGOS = "DROP TABLE IF EXISTS odontologos";
    public static String CREAR_ODONTOLOGO = "INSERT INTO odontologos(matricula,nombre,apellido) VALUES(?,?,?)";
    public static String MODIFICAR_ODONTOLOGO = "UPDATE odontologos SET matricula = ? , nombre = ? , apellido = ? WHERE matricula = ?";
    public static String BUSCAR_ODONTOLOGO_POR_MATRICULA = "SELECT * FROM odontologos WHERE matricula = ?";
    public static String BUSCAR_ODONTOLOGOS = "SELECT * FROM odontologos";
    public static String ELIMINAR_ODONTOLOGO = "DELETE FROM odontologos WHERE matricula = ?";

    // PACIENTES
    public static String BORRAR_TABLA_PACIENTES = "DROP TABLE IF EXISTS pacientes";
    public static String CREAR_TABLA_PACIENTES = "CREATE TABLE pacientes(dni INT PRIMARY KEY, nombre VARCHAR(40) , apellido VARCHAR(40), domicilio VARCHAR(40) , fechaAlta DATETIME)";
    public static String CREAR_PACIENTE = "INSERT INTO pacientes(dni,nombre,apellido,domicilio,fechaAlta) VALUES(?,?,?,?,?)";
    public static String MODIFICAR_PACIENTE = "UPDATE pacientes SET dni = ? , nombre = ? , apellido = ? , domicilio = ? , fechaAlta = ? WHERE dni = ?";
    public static String BUSCAR_PACIENTE_POR_DNI = "SELECT * FROM pacientes WHERE dni = ?";
    public static String BUSCAR_PACIENTES = "SELECT * FROM pacientes";
    public static String ELIMINAR_PACIENTE = "DELETE FROM pacientes WHERE dni = ?";

    // DOMICILIOS
    public static String BORRAR_TABLA_DOMICILIOS = "DROP TABLE IF EXISTS domicilios";
    public static String CREAR_TABLA_DOMICILIOS = "CREATE TABLE domicilios(id INT AUTO_INCREMENT, calle VARCHAR(40) , numero INT, localidad VARCHAR(40) , provincia VARCHAR(40), PRIMARY KEY(id))";
    public static String CREAR_DOMICILIO = "INSERT INTO domicilios(calle,numero,localidd,provincia) VALUES(?,?,?,?)";
    public static String MODIFICAR_DOMICILIO = "UPDATE domicilios SET calle = ? , numero = ? , localidad = ? , provincia = ? WHERE id = ?";
    public static String BUSCAR_DOMICILIO_POR_ID = "SELECT * FROM domicilios WHERE id = ?";
    public static String BUSCAR_DOMICILIOS = "SELECT * FROM domicilios";
    public static String ELIMINAR_DOMICILIO = "DELETE FROM domicilios WHERE id = ?";

    public static final String CONTAR_REGISTROS_DOMICILIOS = "SELECT COUNT(id) AS total FROM domicilios";


    // TURNOS
    public static String BORRAR_TABLA_TURNOS = "DROP TABLE IF EXISTS turnos";
    public static String CREAR_TABLA_TURNOS = "CREATE TABLE turnos(id INT AUTO_INCREMENT PRIMARY KEY, fecha DATETIME, paciente INT, odontologo INT)";
    public static String CREAR_TURNO = "INSERT INTO turnos(fecha, paciente, odontologo) VALUES(?,?,?)";
    public static String MODIFICAR_TURNO = "UPDATE turnos SET fecha = ? , paciente = ? , odontologo = ? WHERE id = ?";
    public static String BUSCAR_TURNO_POR_ID = "SELECT * FROM turnos WHERE id = ?";
    public static String BUSCAR_TURNOS_POR_PACIENTE = "SELECT * FROM turnos WHERE paciente = ?";
    public static String BUSCAR_TURNOS_POR_ODONTOLOGO = "SELECT * FROM turnos WHERE odontologo = ?";
    public static String BUSCAR_TURNOS = "SELECT * FROM turnos";
    public static String ELIMINAR_TURNO = "DELETE FROM turnos WHERE id = ?";

}
