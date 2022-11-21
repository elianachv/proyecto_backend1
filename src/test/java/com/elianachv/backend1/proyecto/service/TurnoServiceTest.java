package com.elianachv.backend1.proyecto.service;

import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.entity.Paciente;
import com.elianachv.backend1.proyecto.entity.Turno;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class TurnoServiceTest {
    private final TurnoService turnoService;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoServiceTest() {
        ConfiguracionBd configuracionBd = new ConfiguracionBd(
                "org.h2.Driver",
                "jdbc:h2:mem:~/test;INIT=RUNSCRIPT FROM 'classpath:sql/init.sql'",
                "sa",
                ""
        );
        turnoService = new TurnoService(configuracionBd);
        pacienteService = new PacienteService(configuracionBd);
        odontologoService = new OdontologoService(configuracionBd);

    }

    @Test
    public void debeCrearTurnoExitosamente() throws DuplicadoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Tatiana", "Vargas");
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);
        Turno t = new Turno(new Date(), o, p);

        pacienteService.crearPaciente(p);
        odontologoService.crearOdontologo(o);

        //CUANDO
        turnoService.crearTurno(t);

        //ENTONCES
        Assertions.assertNotNull(turnoService.buscarTurno(1));
    }

    @Test
    public void noDebeCrearTurnoSiElPacienteNoExiste() throws DuplicadoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Tatiana", "Vargas");
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);
        Turno t = new Turno(new Date(), o, p);

        odontologoService.crearOdontologo(o);

        //CUANDO
        turnoService.crearTurno(t);

        //ENTONCES
        Assertions.assertEquals(0, turnoService.listarTurnos().size());
    }

    @Test
    public void noDebeCrearTurnoSiElOdontologoNoExiste() throws DuplicadoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Tatiana", "Vargas");
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);
        Turno t = new Turno(new Date(), o, p);

        pacienteService.crearPaciente(p);

        //CUANDO
        turnoService.crearTurno(t);

        //ENTONCES
        Assertions.assertEquals(0, turnoService.listarTurnos().size());
    }

    @Test
    public void debeModificarTurnoExitosamente() throws DuplicadoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Tatiana", "Vargas");
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);
        Paciente p2 = new Paciente(90125, "Roberta", "Diaz","Carrera 45",null);
        Turno t = new Turno(new Date(), o, p);

        pacienteService.crearPaciente(p);
        pacienteService.crearPaciente(p2);
        odontologoService.crearOdontologo(o);
        turnoService.crearTurno(t);

        //CUANDO
        t.setPaciente(p2);
        turnoService.modificarTurno(1,t);

        //ENTONCES
        Assertions.assertEquals(90125, turnoService.buscarTurno(1).getPaciente().getDni());
    }

    @Test(expected = NoEncontradoException.class)
    public void debeEliminarTurnoExitosamente() throws DuplicadoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Tatiana", "Vargas");
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);
        Turno t = new Turno(new Date(), o, p);

        pacienteService.crearPaciente(p);
        odontologoService.crearOdontologo(o);
        turnoService.crearTurno(t);

        //CUANDO
        turnoService.eliminarTurno(1);

        //ENTONCES
        Assertions.assertNull(turnoService.buscarTurno(1));
    }

    @Test
    public void debeListarTurnosCorrectamente() throws DuplicadoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Tatiana", "Vargas");
        Odontologo o2 = new Odontologo(4321, "Damian", "Castro");
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);
        Paciente p2 = new Paciente(90125, "Roberta", "Diaz","Carrera 45",null);
        Turno t = new Turno(new Date(), o, p);
        Turno t2 = new Turno(new Date(), o2, p2);

        pacienteService.crearPaciente(p);
        pacienteService.crearPaciente(p2);
        odontologoService.crearOdontologo(o);
        odontologoService.crearOdontologo(o2);
        turnoService.crearTurno(t);
        turnoService.crearTurno(t2);


        //CUANDO
        List<Turno> turnos = turnoService.listarTurnos();

        //ENTONCES
        Assertions.assertEquals(2, turnos.size());
    }
}
