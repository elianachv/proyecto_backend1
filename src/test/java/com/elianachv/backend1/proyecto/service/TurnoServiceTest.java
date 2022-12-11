package com.elianachv.backend1.proyecto.service;

import com.elianachv.backend1.proyecto.dto.OdontologoDto;
import com.elianachv.backend1.proyecto.dto.PacienteDto;
import com.elianachv.backend1.proyecto.dto.TurnoDto;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.PeticionIncorrectaException;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TurnoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private TurnoService turnoService;


    @Test
    @Ignore
    public void debeCrearTurnoExitosamente() throws DuplicadoException, PeticionIncorrectaException {
        //DADO
        OdontologoDto o = new OdontologoDto(8304, "Ignacio", "Rodriguez");
        odontologoService.crearOdontologo(o);

        PacienteDto p = new PacienteDto(5746, "Keila", "Gonzalez", "Calle 45 Localidad1 Provincia1", null);
        pacienteService.crearPaciente(p);

        TurnoDto t = new TurnoDto(8304, 5746, LocalDate.now());

        //CUANDO
        GenericResponse response = turnoService.crearTurno(t);

        //ENTONCES
        Assertions.assertTrue(response.isOk());
    }

    @Test(expected = PeticionIncorrectaException.class)
    public void debeGenerarExcepcionAlCrearTurnoSiElOdontologoNoEstaRegistrado() throws DuplicadoException, PeticionIncorrectaException {
        //DADO
        PacienteDto p = new PacienteDto(7839, "Ramiro", "Velez", "Calle 45 Localidad1 Provincia1", null);
        pacienteService.crearPaciente(p);

        TurnoDto t = new TurnoDto(1111, 7839, LocalDate.now());

        //CUANDO
        GenericResponse response = turnoService.crearTurno(t);

        //ENTONCES
        Assertions.assertFalse(response.isOk());
    }

    @Test(expected = PeticionIncorrectaException.class)
    public void debeGenerarExcepcionAlCrearTurnoSiElPacienteNoEstaRegistrado() throws DuplicadoException, PeticionIncorrectaException {
        //DADO
        OdontologoDto o = new OdontologoDto(4903, "Gabriela", "Perez");
        odontologoService.crearOdontologo(o);


        TurnoDto t = new TurnoDto(4903, 2222, LocalDate.now());

        //CUANDO
        GenericResponse response = turnoService.crearTurno(t);

        //ENTONCES
        Assertions.assertFalse(response.isOk());
    }

}
