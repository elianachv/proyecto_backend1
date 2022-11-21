package com.elianachv.backend1.proyecto.dao.h2;

import com.elianachv.backend1.proyecto.dao.IDao;
import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.dao.util.Sentencia;
import com.elianachv.backend1.proyecto.entity.Domicilio;
import com.elianachv.backend1.proyecto.entity.Paciente;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDaoH2 implements IDao<Paciente> {
    public static Logger logger = LogManager.getLogger(PacienteDaoH2.class);

    private final ConfiguracionBd bd;
    private final IDao<Domicilio> domicilioBd;

    public PacienteDaoH2(ConfiguracionBd bd) {
        this.bd = bd;
        this.domicilioBd = new DomiciliosDaoH2(bd);
    }

    public void crear(Paciente paciente) {
        try {
            if (verificarDniRegistrado(paciente.getDni())) {
                throw new DuplicadoException("El paciente ya se encuentra registrado. Intenta eliminarlo y crearlo nuevamente o modificarlo");
            }
            // Crear domicilio
            domicilioBd.crear(paciente.getDomicilio());
            int domicilioId = ((DomiciliosDaoH2) domicilioBd).contarRegistros();

            //Crear paciente y asociar domicilio
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.CREAR_PACIENTE);
            ps.setInt(1, paciente.getDni());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getApellido());
            ps.setInt(4, domicilioId);
            ps.setDate(5, (Date) paciente.getFechaAlta());
            ps.executeUpdate();
            logger.info("[crear] Paciente " + paciente.getDni() + " creado con exito");
        } catch (SQLException | DuplicadoException e) {
            logger.error(e);
        }
    }

    public void modificar(int dni, Paciente paciente) {
        try {
            // Modificar domicilio
            domicilioBd.modificar(paciente.getDomicilio().getId(), paciente.getDomicilio());

            //Modificar paciente
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.MODIFICAR_PACIENTE);
            ps.setInt(1, paciente.getDni());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getApellido());
            ps.setInt(4, paciente.getDomicilio().getId());
            ps.setDate(5, (Date) paciente.getFechaAlta());
            ps.setInt(6, dni);
            ps.executeUpdate();
            logger.info("[modificar] Paciente " + paciente.getDni() + " modificado correctamente");
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public Paciente obtener(int dni) {
        Paciente paciente = null;
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.BUSCAR_PACIENTE_POR_DNI);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Domicilio domicilio = domicilioBd.obtener(rs.getInt("domicilio"));
                paciente = new Paciente(
                        rs.getInt("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        domicilio,
                        rs.getDate("fechaAlta"));
                logger.info("[obtener] Paciente " + dni + " encontrado: " + paciente);

            } else {
                logger.warn("[obtener] Paciente " + dni + " NO encontrado");
                throw new NoEncontradoException("Paciente no registrado en la base de datos");
            }
        } catch (SQLException | NoEncontradoException e) {
            if (e instanceof SQLException) {
                logger.error(e);
            }
        }
        return paciente;
    }

    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = new ArrayList<>();

        try {
            ResultSet rs = bd.getConexion().prepareStatement(Sentencia.BUSCAR_PACIENTES).executeQuery();

            while (rs.next()) {
                Domicilio domicilio = domicilioBd.obtener(rs.getInt("domicilio"));
                Paciente paciente = new Paciente(
                        rs.getInt("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        domicilio,
                        rs.getDate("fechaAlta"));
                pacientes.add(paciente);
            }
            logger.info("[listarTodos] " + pacientes.size() + " pacientes encontrados");

        } catch (SQLException e) {
            logger.error(e);
        }
        return pacientes;
    }

    public void eliminar(int dni) {

        try {
            if (verificarDniRegistrado(dni)) {
                throw new NoEncontradoException("El paciente que desea eliminar no se encuentra en el sistema");
            }
            Paciente paciente = obtener(dni);
            domicilioBd.eliminar(paciente.getDomicilio().getId());
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.ELIMINAR_PACIENTE);
            ps.setInt(1, dni);
            ps.executeUpdate();
            logger.info("[eliminar] Paciente " + dni + " eliminado correctamente");
        } catch (SQLException | NoEncontradoException e) {
            logger.error(e);
        }
    }

    private boolean verificarDniRegistrado(int dni) {
        return this.obtener(dni) != null;
    }


}
