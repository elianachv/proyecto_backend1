package com.elianachv.backend1.proyecto.service;


import com.elianachv.backend1.proyecto.dto.OdontologoDto;
import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.exception.ApiExceptionHandler;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.exception.enums.Operaciones;
import com.elianachv.backend1.proyecto.exception.enums.Usuarios;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import com.elianachv.backend1.proyecto.repository.OdontologoRepository;
import com.elianachv.backend1.proyecto.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    public static Logger logger = LogManager.getLogger(ApiExceptionHandler.class);

    @Autowired
    OdontologoRepository odontologoBd;

    @Autowired
    ObjectMapper objectMapper;

    public GenericResponse crearOdontologo(OdontologoDto odontologoDto) throws DuplicadoException {
        if (odontologoBd.existsByMatricula(odontologoDto.getMatricula())) {
            throw new DuplicadoException("El odontologo con matricula " + odontologoDto.getMatricula() + " ya está registrado en el sistema");
        }
        Odontologo odontologo = objectMapper.convertValue(odontologoDto, Odontologo.class);
        logger.info("[crearOdontologo] " + odontologo.toString());
        Odontologo result = odontologoBd.save(odontologo);
        return new GenericResponse("201", Util.generarMensaje(Operaciones.CREAR, Usuarios.ODONTOLOGO), true, result);
    }

    public GenericResponse modificarOdontologo(OdontologoDto odontologoDto) throws NoEncontradoException {
        if (odontologoBd.existsByMatricula(odontologoDto.getMatricula())) {
            Odontologo odontologo = odontologoBd.findByMatricula(odontologoDto.getMatricula());
            odontologo.setNombre(odontologoDto.getNombre());
            odontologo.setApellido(odontologoDto.getApellido());
            logger.info("[modificarOdontologo] " + odontologo);
            odontologoBd.save(odontologo);
            return new GenericResponse("201", Util.generarMensaje(Operaciones.MODIFICAR, Usuarios.ODONTOLOGO), true, odontologo.getMatricula());
        }
        throw new NoEncontradoException("El odontologo con matricula " + odontologoDto.getMatricula() + " no registra en el sistema");
    }

    public GenericResponse buscarOdontologoPorMatricula(long matricula) throws NoEncontradoException {
        Odontologo odontologo = odontologoBd.findByMatricula(matricula);
        if (odontologo != null) {
            logger.info("[buscarOdontologoPorMatricula] Odontologo encontrado" + odontologo);
            OdontologoDto odontologoDto = objectMapper.convertValue(odontologo, OdontologoDto.class);
            return new GenericResponse("200", Util.generarMensaje(Operaciones.BUSCAR, Usuarios.ODONTOLOGO), true, odontologoDto);
        }
        throw new NoEncontradoException("Odontologo con matricula " + matricula + " no registra en el sistema");
    }

    public GenericResponse buscarOdontologoPorId(long id) throws NoEncontradoException {
        Optional<Odontologo> odontologo = odontologoBd.findById(id);
        if (odontologo.isPresent()) {
            logger.info("[buscarOdontologoPorId] Odontologo encontrado" + odontologo);
            OdontologoDto odontologoDto = objectMapper.convertValue(odontologo.get(), OdontologoDto.class);
            return new GenericResponse("200", Util.generarMensaje(Operaciones.BUSCAR, Usuarios.ODONTOLOGO), true, odontologoDto);
        }
        throw new NoEncontradoException("Odontologo con id " + id + " no registra en el sistema");
    }

    public GenericResponse listarOdontologos() {
        List<Odontologo> odontologos = odontologoBd.findAll();
        String mensaje = odontologos.size() + " registros recuperados";
        return new GenericResponse("200", mensaje, true, Util.convertirListaOdontologoADto(odontologos));
    }

    public GenericResponse eliminarOdontologo(long matricula) throws NoEncontradoException {
        if (odontologoBd.existsByMatricula(matricula)) {
            odontologoBd.deleteByMatricula(matricula);
            return new GenericResponse("200", Util.generarMensaje(Operaciones.ELIMINAR, Usuarios.ODONTOLOGO), true, matricula);
        }
        throw new NoEncontradoException("Odontologo con matricula " + matricula + " no registra en el sistema");
    }

}
