package com.elianachv.backend1.proyecto.dao.h2;

import com.elianachv.backend1.proyecto.dao.IDao;
import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.dao.util.Sentencia;
import com.elianachv.backend1.proyecto.entity.Domicilio;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DomiciliosDaoH2 implements IDao<Domicilio> {

    public static Logger logger = LogManager.getLogger(OdontologoDaoH2.class);
    private final ConfiguracionBd bd;

    public DomiciliosDaoH2(ConfiguracionBd bd) {
        this.bd = bd;
    }

    public void crear(Domicilio domicilio) throws DuplicadoException {
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.CREAR_DOMICILIO);
            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());
            ps.setString(3, domicilio.getLocalidad());
            ps.setString(4, domicilio.getProvincia());
            ps.executeUpdate();
            logger.info("[crear] Domicilio " + domicilio.getDomicilioCompleto() + " creado con exito");
        } catch (SQLException e) {
            logger.error(e);
        }
    }


    public void modificar(int id, Domicilio domicilio) {
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.MODIFICAR_DOMICILIO);
            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());
            ps.setString(3, domicilio.getLocalidad());
            ps.setString(4, domicilio.getProvincia());
            ps.setInt(5, domicilio.getId());
            ps.executeUpdate();
            logger.info("[modificar] Domicilio " + domicilio.getDomicilioCompleto() + " modificado con exito");
        } catch (SQLException e) {
            logger.error(e);
        }
    }


    public Domicilio obtener(int id) {
        Domicilio domicilio = null;
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.BUSCAR_DOMICILIO_POR_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                domicilio = new Domicilio(
                        rs.getInt("id"),
                        rs.getString("calle"),
                        rs.getInt("numero"),
                        rs.getString("localidad"),
                        rs.getString("provincia"));
                logger.info("[obtener] Domicilio " + id + " encontrado: " + domicilio);
            } else {
                logger.warn("[obtener] Domicilio " + id + " NO encontrado");
                throw new NoEncontradoException("Domicilio no registrado en la base de datos");
            }

        } catch (SQLException | NoEncontradoException e) {
            logger.error(e);
        }

        return domicilio;
    }


    public List<Domicilio> listarTodos() {
        List<Domicilio> domicilios = new ArrayList<>();

        try {
            ResultSet rs = bd.getConexion().prepareStatement(Sentencia.BUSCAR_DOMICILIOS).executeQuery();

            while (rs.next()) {
                Domicilio domicilio = new Domicilio(
                        rs.getInt("id"),
                        rs.getString("calle"),
                        rs.getInt("numero"),
                        rs.getString("localidad"),
                        rs.getString("provincia"));
                domicilios.add(domicilio);
            }
            logger.info("[listarTodos] " + domicilios.size() + " domicilios encontrados");
        } catch (SQLException e) {
            logger.error(e);
        }
        return domicilios;
    }


    public void eliminar(int id) {
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.ELIMINAR_DOMICILIO);
            ps.setInt(1, id);
            ps.executeUpdate();
            logger.info("[eliminar] Domicilio " + id + " eliminado correctamente");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public int contarRegistros() {
        int total = 0;
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.CONTAR_REGISTROS_DOMICILIOS);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
                logger.info("[contarRegistros] " + total);
            } else {
                logger.warn("[contarRegistros] Sin registros");
            }

        } catch (SQLException e) {
            logger.error(e);
        }

        return total;
    }
}
