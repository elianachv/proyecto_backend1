package com.elianachv.backend1.proyecto.service;


import com.elianachv.backend1.proyecto.dto.TurnoDto;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.entity.Paciente;
import com.elianachv.backend1.proyecto.entity.Turno;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.exception.PeticionIncorrectaException;
import com.elianachv.backend1.proyecto.exception.enums.Operaciones;
import com.elianachv.backend1.proyecto.exception.enums.Usuarios;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import com.elianachv.backend1.proyecto.repository.OdontologoRepository;
import com.elianachv.backend1.proyecto.repository.PacienteRepository;
import com.elianachv.backend1.proyecto.repository.TurnoRepository;
import com.elianachv.backend1.proyecto.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TurnoService {

    public static Logger logger = LogManager.getLogger(TurnoService.class);

    @Autowired
    TurnoRepository turnoBd;

    @Autowired
    OdontologoRepository odontologoBd;

    @Autowired
    PacienteRepository pacienteBd;

    public GenericResponse crearTurno(TurnoDto turnoDto) throws PeticionIncorrectaException {
        //TODO validar fecha turno
        Turno turno = Util.getMapper().convertValue(turnoDto, Turno.class);

        if (!pacienteBd.existsByDni(turnoDto.getDniPaciente())) {
            throw new PeticionIncorrectaException("No es posible agendar turno a paciente NO registrado en el sistema");
        } else if (!odontologoBd.existsByMatricula(turnoDto.getMatriculaOdontologo())) {
            throw new PeticionIncorrectaException("No es posible agendar turno a odontologo NO registrado en el sistema");
        }

        Odontologo odontologo = odontologoBd.findByMatricula(turnoDto.getMatriculaOdontologo());
        Paciente paciente = pacienteBd.findByDni(turnoDto.getDniPaciente());

        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        logger.info("[crearTurno] " + turno);
        Turno result = turnoBd.save(turno);
        return new GenericResponse("201", Util.generarMensaje(Operaciones.CREAR, Usuarios.TURNO), true, result);
    }

    public GenericResponse modificarTurno(TurnoDto turnoDto) throws NoEncontradoException {
        Turno turno = turnoBd.findByOdontologoAndPaciente(turnoDto.getMatriculaOdontologo(), turnoDto.getDniPaciente());
        if (turno != null) {
            turno.setFecha(turnoDto.getFecha());
            logger.info("[modificarTurno] " + turno);
            turnoBd.save(turno);
            return new GenericResponse("201", Util.generarMensaje(Operaciones.MODIFICAR, Usuarios.TURNO), true, null);
        }
        throw new NoEncontradoException("No existe el turno del paciente " + turnoDto.getDniPaciente() + " y el odontologo " + turnoDto.getMatriculaOdontologo());

    }

    public GenericResponse buscarTurno(long id) throws NoEncontradoException {
        Turno turno = turnoBd.findById(id).orElse(null);
        if (turno != null) {
            TurnoDto turnoDto = Util.getMapper().convertValue(turno, TurnoDto.class);
            turnoDto.setMatriculaOdontologo(turno.getOdontologo().getMatricula());
            turnoDto.setDniPaciente(turno.getPaciente().getDni());
            return new GenericResponse("200", Util.generarMensaje(Operaciones.BUSCAR, Usuarios.TURNO), true, turnoDto);

        }

        throw new NoEncontradoException("Turno no encontrado");
    }

    public GenericResponse buscarTurnosPorRol(String rol, String id) throws NoEncontradoException, PeticionIncorrectaException {
        try {
            List<Turno> turnos;
            long identificacion = Long.parseLong(id);
            String mensaje = "";

            switch (rol) {
                case "ODONTOLOGO":
                    if (odontologoBd.existsByMatricula(identificacion)) {
                        turnos = turnoBd.findByOdontologo(identificacion);
                        mensaje = "El odontologo " + identificacion + " tiene " + turnos.size() + " turnos agendados";
                    } else {
                        throw new NoEncontradoException("Odontologo no registra en el sistema");
                    }
                    break;
                case "PACIENTE":
                    if (pacienteBd.existsByDni(identificacion)) {
                        turnos = turnoBd.findByPaciente(identificacion);
                        mensaje = "El paciente " + identificacion + " tiene " + turnos.size() + " turnos agendados";
                    } else {
                        throw new NoEncontradoException("Paciente no registra en el sistema");
                    }
                    break;
                default:
                    throw new PeticionIncorrectaException("Rol inválido");
            }
            return new GenericResponse("200", mensaje, true, Util.convertirListaTurnoADto(turnos));
        } catch (NumberFormatException e) {
            logger.error(e);
            throw new PeticionIncorrectaException("Identificacion invalida");
        }
    }

    public GenericResponse buscarTurnosPorFecha(String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        List<Turno> turnos = turnoBd.findByFecha(localDate);
        String mensaje = turnos.size() + " registros recuperados";
        return new GenericResponse("200", mensaje, true, Util.convertirListaTurnoADto(turnos));
    }

    public GenericResponse listarTurnos() {
        List<Turno> turnos = turnoBd.findAll();
        String mensaje = turnos.size() + " registros recuperados";
        return new GenericResponse("200", mensaje, true, Util.convertirListaTurnoADto(turnos));
    }

    public GenericResponse eliminarTurno(long id) throws NoEncontradoException {
        if (turnoBd.existsById(id)) {
            turnoBd.deleteById(id);
            return new GenericResponse("200", Util.generarMensaje(Operaciones.ELIMINAR, Usuarios.TURNO), true, id);
        }
        throw new NoEncontradoException("Turno no encontrado");
    }
}
