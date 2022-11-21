package com.elianachv.backend1.proyecto.service;


import com.elianachv.backend1.proyecto.dao.IDao;
import com.elianachv.backend1.proyecto.dao.h2.OdontologoDaoH2;
import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.exception.enums.Operaciones;
import com.elianachv.backend1.proyecto.exception.enums.Usuarios;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import com.elianachv.backend1.proyecto.util.Util;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService {

    private final IDao<Odontologo> odontologoBd;

    public OdontologoService(ConfiguracionBd bd) {
        this.odontologoBd = new OdontologoDaoH2(bd);
    }

    public GenericResponse crearOdontologo(Odontologo odontologo) throws DuplicadoException {
        odontologoBd.crear(odontologo);
        return new GenericResponse("201", Util.generarMensaje(Operaciones.CREAR, Usuarios.ODONTOLOGO), true, odontologo.getMatricula());
    }

    public GenericResponse modificarOdontologo(int matricula, Odontologo odontologo) {
        odontologoBd.modificar(matricula, odontologo);
        return new GenericResponse("201", Util.generarMensaje(Operaciones.MODIFICAR, Usuarios.ODONTOLOGO), true, odontologo.getMatricula());
    }

    public GenericResponse buscarOdontologo(int matricula) throws NoEncontradoException {
        Odontologo o = odontologoBd.obtener(matricula);
        if (o != null) {
            return new GenericResponse("200", Util.generarMensaje(Operaciones.BUSCAR, Usuarios.ODONTOLOGO), true, o);
        }
        throw new NoEncontradoException("Odontologo no registra en el sistema");
    }

    public GenericResponse listarOdontologos() {
        List<Odontologo> odontologos = odontologoBd.listarTodos();
        String mensaje = odontologos.size() + " registros recuperados";
        return new GenericResponse("200", mensaje, true, odontologos);

    }

    public GenericResponse eliminarOdontologo(int matricula) {
        odontologoBd.eliminar(matricula);
        return new GenericResponse("200", Util.generarMensaje(Operaciones.ELIMINAR, Usuarios.ODONTOLOGO), true, matricula);
    }
}
