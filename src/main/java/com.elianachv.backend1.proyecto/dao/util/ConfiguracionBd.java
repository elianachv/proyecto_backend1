package com.elianachv.backend1.proyecto.dao.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConfiguracionBd {

    public static Logger logger = LogManager.getLogger(ConfiguracionBd.class);
    private final String claseDriver;
    private final String url;
    private final String usuario;
    private final String clave;
    private Connection conexion;

    public ConfiguracionBd(
            @Value("${db.driver}") String claseDriver,
            @Value("${db.url}") String url,
            @Value("{db.usuario}") String usuario,
            @Value("${db.clave}") String clave) {
        this.claseDriver = claseDriver;
        this.url = url;
        this.usuario = usuario;
        this.clave = clave;
        registrarControlador();
        crearConexion();
        crearBd();
    }

    public void registrarControlador() {
        try {
            Class.forName(claseDriver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            logger.error("Error: No fue posible cargar la clase del driver");
            System.exit(1);
        }
    }

    public void crearConexion() {
        try {
            conexion = DriverManager.getConnection(url, usuario, clave);
            // conn.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void crearBd() {
        // crearTablaPacientes();
        // crearTablaOdontologos();
        // crearTablaTurnos();
        logger.info("La base de datos se configuró correctamente y está lista para usar");
    }

    private void crearTablaOdontologos() {
        try {
            conexion.prepareStatement(Sentencia.BORRAR_TABLA_ODONTOLOGOS).executeUpdate();
            conexion.prepareStatement(Sentencia.CREAR_TABLA_ODONTOLOGOS).execute();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private void crearTablaPacientes() {
        try {
            conexion.prepareStatement(Sentencia.BORRAR_TABLA_PACIENTES).execute();
            conexion.prepareStatement(Sentencia.CREAR_TABLA_PACIENTES).execute();
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private void crearTablaTurnos() {
        try {
            conexion.prepareStatement(Sentencia.BORRAR_TABLA_TURNOS).execute();
            conexion.prepareStatement(Sentencia.CREAR_TABLA_TURNOS).execute();
        } catch (SQLException e) {
            logger.error(e);
        }
    }


}
