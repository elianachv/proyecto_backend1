package com.elianachv.backend1.proyecto.service;

import com.elianachv.backend1.proyecto.dto.PacienteDto;
import com.elianachv.backend1.proyecto.entity.Paciente;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.exception.PeticionIncorrectaException;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PacienteServiceTest {

    @Autowired
    private PacienteService pacienteService;


    @Test
    public void debeCrearPacienteExitosamente() throws DuplicadoException, PeticionIncorrectaException, NoEncontradoException {
        //DADO
        PacienteDto p = new PacienteDto(90123, "Gladys", "Moreno", "Calle 45 Localidad1 Provincia1", null);

        //CUANDO
        pacienteService.crearPaciente(p);

        //ENTONCES
        Assertions.assertNotNull(pacienteService.buscarPacientePorDni(90123));
    }

    @Test(expected = DuplicadoException.class)
    public void noDebeCrearPacienteDuplicado() throws PeticionIncorrectaException, DuplicadoException {
        //DADO
        PacienteDto p = new PacienteDto(90123, "Gladys", "Moreno", "Calle 45 Localidad1 Provincia1", null);

        //CUANDO
        pacienteService.crearPaciente(p);
        pacienteService.crearPaciente(p);

        //ENTONCES

    }

    @Test
    public void debeModificarPacienteExitosamente() throws DuplicadoException, PeticionIncorrectaException, NoEncontradoException {
        //DADO
        PacienteDto p = new PacienteDto(8888, "Gloria", "Montalvo", "Calle 45 Localidad1 Provincia1", null);
        pacienteService.crearPaciente(p);
        p.setNombre("Lina");

        //CUANDO
        pacienteService.modificarPaciente(p);

        //ENTONCES
        Assertions.assertEquals("Lina", ((PacienteDto) pacienteService.buscarPacientePorDni(8888).getData()).getNombre());
    }

    @Test(expected = NoEncontradoException.class)
    public void debeEliminarPacienteExitosamente() throws DuplicadoException, PeticionIncorrectaException, NoEncontradoException {
        //DADO
        PacienteDto p = new PacienteDto(2222, "Ramona", "Perez", "Calle 45 Localidad1 Provincia5", null);
        pacienteService.crearPaciente(p);

        //CUANDO
        pacienteService.eliminarPaciente(2222);

        //ENTONCES
        Assertions.assertFalse(pacienteService.buscarPacientePorDni(2222).isOk());
    }

    @Test
    public void debeListarPacientesCorrectamente() throws DuplicadoException, PeticionIncorrectaException {
        //DADO
        PacienteDto p1 = new PacienteDto(7766, "Jairo", "Riapira", "Calle 45 Localidad4 Provincia8", null);
        PacienteDto p2 = new PacienteDto(4455, "Jeronimo", "Saldarriaga", "Calle 90 Localidad4 Provincia8", null);
        pacienteService.crearPaciente(p1);
        pacienteService.crearPaciente(p2);

        //CUANDO
        List<PacienteDto> pacientes = (List<PacienteDto>) pacienteService.listarPacientes().getData();

        //ENTONCES
        Assertions.assertEquals(7, pacientes.size());
    }

    @Test
    public void debeBuscarPacientePorDniExitosamente() throws NoEncontradoException, PeticionIncorrectaException, DuplicadoException {
        //DADO
        PacienteDto p1 = new PacienteDto(3490, "Manuela", "Beltrán", "Calle 45 Localidad4 Provincia8", null);
        pacienteService.crearPaciente(p1);

        //CUANDO
        GenericResponse response = pacienteService.buscarPacientePorDni(3490);
        //ENTONCES
        Assertions.assertTrue(response.isOk());
        Assertions.assertEquals(p1.getNombre(), ((PacienteDto) response.getData()).getNombre());

    }

    @Test
    public void debeBuscarPacientePorIdExitosamente() throws NoEncontradoException, PeticionIncorrectaException, DuplicadoException {
        //DADO
        PacienteDto p1 = new PacienteDto(9203, "Jorge", "Borges", "Calle 45 Localidad4 Provincia8", null);
        GenericResponse response1 = pacienteService.crearPaciente(p1);
        long idCreado = ((Paciente) response1.getData()).getId();

        //CUANDO
        GenericResponse response = pacienteService.buscarPacientePorId(idCreado);

        //ENTONCES
        Assertions.assertTrue(response.isOk());
        Assertions.assertEquals(p1.getNombre(), ((PacienteDto) response.getData()).getNombre());

    }


    @Test(expected = NoEncontradoException.class)
    @Ignore
    public void debeGenerarExcepcionAlBuscarPacientePorId() throws NoEncontradoException {
        //DADO

        //CUANDO
        GenericResponse response = pacienteService.buscarPacientePorId(1);

        //ENTONCES
        Assertions.assertFalse(response.isOk());

    }

    @Test(expected = NoEncontradoException.class)
    public void debeGenerarExcepcionAlBuscarPacientePorDni() throws NoEncontradoException {
        //DADO

        //CUANDO
        GenericResponse response = pacienteService.buscarPacientePorDni(9305);

        //ENTONCES
        Assertions.assertFalse(response.isOk());

    }

    @Test(expected = PeticionIncorrectaException.class)
    public void debeGenerarExcepcionPorMalFormatoDeDireccionAlCrearPaciente() throws NoEncontradoException, PeticionIncorrectaException, DuplicadoException {
        //DADO
        // Formato correcto: Calle NumeroNombreCalle Localidad Provincia
        String direccionErronea = "Calle 50";
        PacienteDto p1 = new PacienteDto(8740, "Paula", "Ibañez", direccionErronea, null);

        //CUANDO
        pacienteService.crearPaciente(p1);

        //ENTONCES

    }

    @Test(expected = PeticionIncorrectaException.class)
    public void debeGenerarExcepcionPorMalFormatoDeDireccionAlModificarPaciente() throws NoEncontradoException, PeticionIncorrectaException, DuplicadoException {
        //DADO
        String direccionErronea = "Calle 30";
        PacienteDto p = new PacienteDto(9319, "Teresa", "Reyes", "Calle 45 Localidad1 Provincia1", null);
        pacienteService.crearPaciente(p);
        p.setDomicilioCompleto(direccionErronea);

        //CUANDO
        pacienteService.modificarPaciente(p);

    }


}
