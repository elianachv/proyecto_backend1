package com.elianachv.backend1.proyecto.dao.h2;

import com.elianachv.backend1.proyecto.dao.IDao;
import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.dao.util.Sentencia;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.entity.Paciente;
import com.elianachv.backend1.proyecto.entity.Turno;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurnoDaoH2 implements IDao<Turno> {

    public static Logger logger = LogManager.getLogger(TurnoDaoH2.class);

    private final ConfiguracionBd bd;

    private final IDao<Paciente> pacienteBd;
    private final IDao<Odontologo> odontologoBd;

    public TurnoDaoH2(ConfiguracionBd bd) {
        this.bd = bd;
        pacienteBd = new PacienteDaoH2(bd);
        odontologoBd = new OdontologoDaoH2(bd);
    }

    public void crear(Turno turno) {
        // TODO verificar disponibilidad del odontologo antes de agendar turno, desencadenar NoDisponibleException
        try {
            if(!verificarPacienteYOdontologo(turno)){
                throw new NoEncontradoException("El odontologo y/o el paciente no son válidos");
            }
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.CREAR_TURNO);
            ps.setDate(1, convertirFecha(turno.getFecha()));
            ps.setInt(2, turno.getPaciente().getDni());
            ps.setInt(3, turno.getOdontologo().getMatricula());
            ps.executeUpdate();
            logger.info("[crear] Turno para paciente " + turno.getPaciente().getDni() + " con odontologo "+ turno.getOdontologo().getMatricula() +" creado con exito");
        } catch (SQLException | NoEncontradoException e) {
            logger.error(e);
        }
    }

    public void modificar(int id, Turno turno) {
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.MODIFICAR_TURNO);
            ps.setDate(1, convertirFecha(turno.getFecha()));
            ps.setInt(2, turno.getPaciente().getDni());
            ps.setInt(3, turno.getOdontologo().getMatricula());
            ps.setInt(4, id);
            ps.executeUpdate();
            logger.info("[modificar] Turno " + turno.getId() + " modificado correctamente");
        } catch (SQLException e) {
            logger.error(e);
        }

    }

    public Turno obtener(int id){

        Turno turno = null;

        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.BUSCAR_TURNO_POR_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Paciente paciente = pacienteBd.obtener(rs.getInt("paciente"));
                Odontologo odontologo = odontologoBd.obtener(rs.getInt("odontologo"));
                turno = new Turno(
                        rs.getInt("id"),
                        rs.getDate("fecha"),
                        odontologo,
                        paciente);
                logger.info("[obtener] Turno " + id + " encontrado: " + turno);
            }else {
                logger.warn("[obtener] Turno " + id + " NO encontrado");
                throw new NoEncontradoException("Turno no registrado en la base de datos");
            }
        } catch (SQLException | NoEncontradoException e) {
            if (e instanceof SQLException) {
                logger.error(e);
            }
        }

        return turno;
    }

    public List<Turno> listarTodos() {

        List<Turno> turnos = new ArrayList<>();

        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.BUSCAR_TURNOS);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Paciente paciente = pacienteBd.obtener(rs.getInt("paciente"));
                Odontologo odontologo = odontologoBd.obtener(rs.getInt("odontologo"));
                Turno turno = new Turno(
                        rs.getInt("id"),
                        rs.getDate("fecha"),
                        odontologo,
                        paciente);
                turnos.add(turno);
            }
            logger.info("[listarTodos] "+turnos.size() + " turnos encontrados");
        } catch (SQLException e) {
            logger.error(e);
        }

        return turnos;
    }

    public void eliminar(int id) {
        try {
            PreparedStatement ps = bd.getConexion().prepareStatement(Sentencia.ELIMINAR_TURNO);
            ps.setInt(1, id);
            ps.executeUpdate();
            logger.info("[eliminar] Turno " + id + " eliminado correctamente");

        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public boolean verificarPacienteYOdontologo(Turno turno) throws SQLException {
        return pacienteBd.obtener(turno.getPaciente().getDni()) != null && odontologoBd.obtener(turno.getOdontologo().getMatricula()) != null;
    }

    public Date convertirFecha(java.util.Date fecha){
        return new Date(fecha.getTime());
    }

}
