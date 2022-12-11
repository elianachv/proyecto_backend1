package com.elianachv.backend1.proyecto.service;

import com.elianachv.backend1.proyecto.dto.OdontologoDto;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
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
public class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;


    @Test
    public void debeCrearOdontologoExitosamente() throws DuplicadoException, NoEncontradoException {
        //DADO
        OdontologoDto o = new OdontologoDto(9999, "Milena", "Uribe");

        //CUANDO
        odontologoService.crearOdontologo(o);

        //ENTONCES
        Assertions.assertNotNull(odontologoService.buscarOdontologoPorMatricula(9999).getData());
    }

    @Test(expected = DuplicadoException.class)
    public void noDebeCrearOdontologoDuplicado() throws DuplicadoException {
        //DADO
        OdontologoDto o = new OdontologoDto(1234, "Tatiana", "Vargas");

        //CUANDO
        odontologoService.crearOdontologo(o);
        odontologoService.crearOdontologo(o);

        //ENTONCES
        List<OdontologoDto> odontologos = (List<OdontologoDto>) odontologoService.listarOdontologos().getData();
        Assertions.assertEquals(1, odontologos.size());
    }

    @Test
    public void debeModificarOdontologoExitosamente() throws DuplicadoException, NoEncontradoException {
        //DADO
        OdontologoDto o = new OdontologoDto(5555, "Roberto", "Duran");
        odontologoService.crearOdontologo(o);
        o.setNombre("Mario");

        //CUANDO
        odontologoService.modificarOdontologo(o);

        //ENTONCES
        OdontologoDto odontologoEsperado = (OdontologoDto) odontologoService.buscarOdontologoPorMatricula(5555).getData();
        Assertions.assertEquals("Mario", odontologoEsperado.getNombre());
    }

    @Test
    @Ignore
    public void debeEliminarOdontologoExitosamente() throws NoEncontradoException, DuplicadoException {
        //DADO
        OdontologoDto o = new OdontologoDto(4567, "Lina", "Palacios");
        odontologoService.crearOdontologo(o);

        //CUANDO
        odontologoService.eliminarOdontologo(4567);

        //ENTONCES
        Assertions.assertFalse(odontologoService.buscarOdontologoPorMatricula(4567).isOk());
    }

    @Test(expected = NoEncontradoException.class)
    public void noDebeEliminarOdontologoNoRegistrado() throws NoEncontradoException {
        //DADO

        //CUANDO
        GenericResponse genericResponse = odontologoService.eliminarOdontologo(1111);

        //ENTONCES
        Assertions.assertFalse(genericResponse.isOk());
    }

    @Test
    public void debeListarOdontologosCorrectamente() throws DuplicadoException {
        //DADO
        OdontologoDto o1 = new OdontologoDto(7777, "Yenny", "Mora");
        OdontologoDto o2 = new OdontologoDto(8888, "Julian", "Forero");
        odontologoService.crearOdontologo(o1);
        odontologoService.crearOdontologo(o2);

        //CUANDO
        List<OdontologoDto> odontologos = (List<OdontologoDto>) odontologoService.listarOdontologos().getData();

        //ENTONCES
        Assertions.assertEquals(2, odontologos.size());
    }

    @Test
    public void debeBuscarOdontologoPorIdExitosamente() throws DuplicadoException, NoEncontradoException {
        //DADO
        OdontologoDto o1 = new OdontologoDto(2020, "Pedro", "Roman");
        GenericResponse response1 = odontologoService.crearOdontologo(o1);

        long idCreado = ((Odontologo) response1.getData()).getId();

        //CUANDO
        GenericResponse response = odontologoService.buscarOdontologoPorId(idCreado);

        //ENTONCES
        Assertions.assertTrue(response.isOk());
        Assertions.assertEquals(o1.getNombre(), ((OdontologoDto) response.getData()).getNombre());
    }

    @Test
    public void debeBuscarOdontologoPorMatriculaExitosamente() throws DuplicadoException, NoEncontradoException {
        //DADO
        OdontologoDto o1 = new OdontologoDto(9823, "Lorena", "Paez");
        odontologoService.crearOdontologo(o1);

        //CUANDO
        GenericResponse response = odontologoService.buscarOdontologoPorMatricula(9823);

        //ENTONCES
        Assertions.assertTrue(response.isOk());
        Assertions.assertEquals(o1.getNombre(), ((OdontologoDto) response.getData()).getNombre());
    }

    @Test(expected = NoEncontradoException.class)
    public void debeGenerarExcepcionCuandoBuscaOdontologoPorMatricula() throws NoEncontradoException {
        //DADO

        //CUANDO
        GenericResponse response = odontologoService.buscarOdontologoPorMatricula(9292);

        //ENTONCES
        Assertions.assertFalse(response.isOk());
    }

    @Test(expected = NoEncontradoException.class)
    public void debeGenerarExcepcionCuandoBuscaOdontologoPorId() throws NoEncontradoException {
        //DADO

        //CUANDO
        GenericResponse response = odontologoService.buscarOdontologoPorId(9);

        //ENTONCES
        Assertions.assertFalse(response.isOk());
    }

}