package com.elianachv.backend1.proyecto.service;

import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.entity.Paciente;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import java.util.List;

public class PacienteServiceTest {

    private final PacienteService pacienteService;

    public PacienteServiceTest() {
        ConfiguracionBd configuracionBd = new ConfiguracionBd(
                "org.h2.Driver",
                "jdbc:h2:mem:~/test;INIT=RUNSCRIPT FROM 'classpath:sql/init.sql'",
                "sa",
                ""
        );
        pacienteService = new PacienteService(configuracionBd);
    }

    @Test
    public void debeCrearPacienteExitosamente() throws DuplicadoException {
        //DADO
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);

        //CUANDO
        pacienteService.crearPaciente(p);

        //ENTONCES
        Assertions.assertNotNull(pacienteService.buscarPaciente(90123));
    }

    @Test(expected = DuplicadoException.class)
    public void noDebeCrearPacienteDuplicado() throws DuplicadoException {
        //DADO
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);

        //CUANDO
        pacienteService.crearPaciente(p);
        pacienteService.crearPaciente(p);

        //ENTONCES
        Assertions.assertEquals(1, pacienteService.listarPacientes().size());
    }

    @Test
    public void debeModificarPacienteExitosamente() throws DuplicadoException {
        //DADO
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);
        pacienteService.crearPaciente(p);
        p.setNombre("Lina");

        //CUANDO
        pacienteService.modificarPaciente(90123, p);

        //ENTONCES
        Assertions.assertEquals("Lina", pacienteService.buscarPaciente(90123).getNombre());
    }

    @Test(expected = NoEncontradoException.class)
    public void debeEliminarPacienteExitosamente() throws DuplicadoException {
        //DADO
        Paciente p = new Paciente(90123, "Gladys", "Moreno","Calle 45",null);
        pacienteService.crearPaciente(p);

        //CUANDO
        pacienteService.eliminarPaciente(90123);

        //ENTONCES
        Assertions.assertNull(pacienteService.buscarPaciente(90123));
    }

    @Test
    public void debeListarPacientesCorrectamente() throws DuplicadoException {
        //DADO
        Paciente p1= new Paciente(90123, "Gladys", "Moreno","Calle 45",null);
        Paciente p2 = new Paciente(901235, "Mercedes", "Saldarriaga","Calle 90",null);
        pacienteService.crearPaciente(p1);
        pacienteService.crearPaciente(p2);

        //CUANDO
        List<Paciente> pacientes = pacienteService.listarPacientes();

        //ENTONCES
        Assertions.assertEquals(2, pacientes.size());
    }

}
