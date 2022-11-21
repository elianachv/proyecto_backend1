package com.elianachv.backend1.proyecto.service;

import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OdontologoServiceTest {

    private final OdontologoService odontologoService;

    public OdontologoServiceTest() {
        ConfiguracionBd configuracionBd = new ConfiguracionBd(
                "org.h2.Driver",
                "jdbc:h2:mem:~/test;INIT=RUNSCRIPT FROM 'classpath:sql/init.sql'",
                "sa",
                ""
        );
        odontologoService = new OdontologoService(configuracionBd);
    }

    @Test
    public void debeCrearOdontologoExitosamente() throws DuplicadoException, NoEncontradoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Tatiana", "Vargas");

        //CUANDO
        odontologoService.crearOdontologo(o);

        //ENTONCES
        Assertions.assertNotNull(odontologoService.buscarOdontologo(1234).getData());
    }

    @Test(expected = DuplicadoException.class)
    public void noDebeCrearOdontologoDuplicado() throws DuplicadoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Tatiana", "Vargas");

        //CUANDO
        odontologoService.crearOdontologo(o);
        odontologoService.crearOdontologo(o);

        //ENTONCES
        List<Odontologo> odontologos = (List<Odontologo>) odontologoService.listarOdontologos().getData();
        Assertions.assertEquals(1, odontologos.size());
    }

    @Test
    public void debeModificarOdontologoExitosamente() throws DuplicadoException, NoEncontradoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Tatiana", "Vargas");
        odontologoService.crearOdontologo(o);
        o.setNombre("Lina");

        //CUANDO
        odontologoService.modificarOdontologo(1234, o);

        //ENTONCES
        Odontologo odontologoEsperado = (Odontologo) odontologoService.buscarOdontologo(1234).getData();
        Assertions.assertEquals("Lina", odontologoEsperado.getNombre());
    }

    @Test(expected = NoEncontradoException.class)
    public void debeEliminarOdontologoExitosamente() throws NoEncontradoException, DuplicadoException {
        //DADO
        Odontologo o = new Odontologo(1234, "Lina", "Vargas");
        odontologoService.crearOdontologo(o);

        //CUANDO
        odontologoService.eliminarOdontologo(1234);

        //ENTONCES
        Assertions.assertFalse(odontologoService.buscarOdontologo(1234).isOk());
    }

    @Test
    public void debeListarOdontologosCorrectamente() throws DuplicadoException {
        //DADO
        Odontologo o1 = new Odontologo(1234, "Tatiana", "Vargas");
        Odontologo o2 = new Odontologo(4321, "Pedro", "Flores");
        odontologoService.crearOdontologo(o1);
        odontologoService.crearOdontologo(o2);

        //CUANDO
        List<Odontologo> odontologos = (List<Odontologo>) odontologoService.listarOdontologos().getData();

        //ENTONCES
        Assertions.assertEquals(2, odontologos.size());
    }

}