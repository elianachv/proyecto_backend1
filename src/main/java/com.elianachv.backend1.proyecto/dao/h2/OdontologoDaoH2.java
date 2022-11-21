package com.elianachv.backend1.proyecto.dao.h2;

import com.elianachv.backend1.proyecto.dao.IDao;
import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.dao.util.Sentencia;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    public static Logger logger = LogManager.getLogger(OdontologoDaoH2.class);
    private final ConfiguracionBd bd;

    public OdontologoDaoH2(ConfiguracionBd bd) {
        this.bd = bd;
    }

    public void crear(Odontologo odontologo) throws DuplicadoException {
        try {
            if (verificarMatriculaRegistrada(odontologo.getMatricula())) {
                throw new DuplicadoException("El odontologo ya se encuentra registrado. Intenta eliminarlo y crearlo nuevamente o modificarlo");
            }
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.CREAR_ODONTOLOGO);
            ps.setInt(1, odontologo.getMatricula());
            ps.setString(2, odontologo.getNombre());
            ps.setString(3, odontologo.getApellido());
            ps.executeUpdate();
            logger.info("[crear] Odontologo " + odontologo.getMatricula() + " creado con exito");
        } catch (DuplicadoException e) {
            logger.error(e);
            throw e;
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public void modificar(int matricula, Odontologo odontologo) {
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.MODIFICAR_ODONTOLOGO);
            ps.setInt(1, odontologo.getMatricula());
            ps.setString(2, odontologo.getNombre());
            ps.setString(3, odontologo.getApellido());
            ps.setInt(4, matricula);
            ps.executeUpdate();
            logger.info("[modificar] Odontologo " + odontologo.getMatricula() + " modificado correctamente");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public Odontologo obtener(int matricula) {
        Odontologo odontologo = null;
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.BUSCAR_ODONTOLOGO_POR_MATRICULA);
            ps.setInt(1, matricula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                odontologo = new Odontologo(
                        rs.getInt("matricula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"));
                logger.info("[obtener] Odontologo " + matricula + " encontrado: " + odontologo);
            } else {
                logger.warn("[obtener] Odontologo " + matricula + " NO encontrado");
                throw new NoEncontradoException("Odontologo no registrado en la base de datos");
            }

        } catch (SQLException | NoEncontradoException e) {
            logger.error(e);
        }

        return odontologo;
    }

    public List<Odontologo> listarTodos() {
        List<Odontologo> odontologos = new ArrayList<>();

        try {
            ResultSet rs = bd.getConexion().prepareStatement(Sentencia.BUSCAR_ODONTOLOGOS).executeQuery();

            while (rs.next()) {
                Odontologo odontologo = new Odontologo(
                        rs.getInt("matricula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"));
                odontologos.add(odontologo);
            }
            logger.info("[listarTodos] " + odontologos.size() + " odontologos encontrados");
        } catch (SQLException e) {
            logger.error(e);
        }
        return odontologos;
    }

    public void eliminar(int matricula) {
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.ELIMINAR_ODONTOLOGO);
            ps.setInt(1, matricula);
            ps.executeUpdate();
            logger.info("[eliminar] Odontologo " + matricula + " eliminado correctamente");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    private boolean verificarMatriculaRegistrada(int matricula) {
        return this.obtener(matricula) != null;
    }


}
