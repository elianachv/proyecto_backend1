package com.elianachv.backend1.proyecto.service;


import com.elianachv.backend1.proyecto.dto.PacienteDto;
import com.elianachv.backend1.proyecto.entity.Domicilio;
import com.elianachv.backend1.proyecto.entity.Paciente;
import com.elianachv.backend1.proyecto.exception.ApiExceptionHandler;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.exception.PeticionIncorrectaException;
import com.elianachv.backend1.proyecto.exception.enums.Operaciones;
import com.elianachv.backend1.proyecto.exception.enums.Usuarios;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import com.elianachv.backend1.proyecto.repository.PacienteRepository;
import com.elianachv.backend1.proyecto.util.Util;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    public static Logger logger = LogManager.getLogger(ApiExceptionHandler.class);

    @Autowired
    PacienteRepository pacienteBd;

    ObjectMapper objectMapper;

    public PacienteService() {
        objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public GenericResponse crearPaciente(PacienteDto pacienteDto) throws DuplicadoException, PeticionIncorrectaException {
        if (pacienteBd.existsByDni(pacienteDto.getDni())) {
            throw new DuplicadoException("El paciente con dni " + pacienteDto.getDni() + " ya está registrado en el sistema");
        }
        Paciente paciente = objectMapper.convertValue(pacienteDto, Paciente.class);
        paciente.setDomicilio(convertirDomicilio(pacienteDto.getDomicilioCompleto()));
        logger.info("[crearPaciente] " + paciente);
        Paciente result = pacienteBd.save(paciente);
        return new GenericResponse("201", Util.generarMensaje(Operaciones.CREAR, Usuarios.PACIENTE), true, result);

    }

    public GenericResponse modificarPaciente(PacienteDto pacienteDto) throws PeticionIncorrectaException, NoEncontradoException {

        if (pacienteBd.existsByDni(pacienteDto.getDni())) {
            Paciente paciente = pacienteBd.findByDni(pacienteDto.getDni());
            paciente.setNombre(pacienteDto.getNombre());
            paciente.setApellido(pacienteDto.getApellido());
            paciente.setDomicilio(convertirDomicilio(pacienteDto.getDomicilioCompleto()));
            logger.info("[modificarPaciente] " + paciente);
            pacienteBd.save(paciente);
            return new GenericResponse("201", Util.generarMensaje(Operaciones.MODIFICAR, Usuarios.PACIENTE), true, paciente.getDni());
        }
        throw new NoEncontradoException("El paciente con dni " + pacienteDto.getDni() + " no registra en el sistema");

    }

    public GenericResponse buscarPacientePorDni(long dni) throws NoEncontradoException {
        Paciente paciente = pacienteBd.findByDni(dni);
        if (paciente != null) {
            logger.info("[buscarPacientePorDni]" + paciente);
            PacienteDto pacienteDto = objectMapper.convertValue(paciente, PacienteDto.class);
            pacienteDto.setDomicilioCompleto(paciente.getDomicilio().getDomicilioCompleto());
            return new GenericResponse("200", Util.generarMensaje(Operaciones.BUSCAR, Usuarios.PACIENTE), true, pacienteDto);
        }
        throw new NoEncontradoException("Paciente con dni " + dni + " no registra en el sistema");

    }

    public GenericResponse buscarPacientePorId(long id) throws NoEncontradoException {
        Optional<Paciente> paciente = pacienteBd.findById(id);
        if (paciente.isPresent()) {
            logger.info("[buscarPacientePorId]" + paciente.get());
            PacienteDto pacienteDto = objectMapper.convertValue(paciente.get(), PacienteDto.class);
            pacienteDto.setDomicilioCompleto(paciente.get().getDomicilio().getDomicilioCompleto());
            return new GenericResponse("200", Util.generarMensaje(Operaciones.BUSCAR, Usuarios.PACIENTE), true, pacienteDto);
        }
        throw new NoEncontradoException("Paciente con id " + id + " no registra en el sistema");

    }

    public GenericResponse buscarPacientePorNombreYApellido(String nombre, String apellido) throws NoEncontradoException {
        Paciente paciente = pacienteBd.findByNombreAndApellido(nombre, apellido);
        if (paciente != null) {
            logger.info("[buscarPacientePorNombreYApellido]" + paciente);
            PacienteDto pacienteDto = objectMapper.convertValue(paciente, PacienteDto.class);
            pacienteDto.setDomicilioCompleto(paciente.getDomicilio().getDomicilioCompleto());
            return new GenericResponse("200", Util.generarMensaje(Operaciones.BUSCAR, Usuarios.PACIENTE), true, pacienteDto);
        }
        throw new NoEncontradoException("Paciente  " + nombre + " " + apellido + " no registra en el sistema");

    }

    public GenericResponse listarPacientes() {

        List<Paciente> pacientes = pacienteBd.findAll();
        String mensaje = pacientes.size() + " registros recuperados";
        return new GenericResponse("200", mensaje, true, Util.convertirListaPacienteADto(pacientes));
    }

    public GenericResponse eliminarPaciente(long dni) throws NoEncontradoException {
        if (pacienteBd.existsByDni(dni)) {
            pacienteBd.deleteByDni(dni);
            return new GenericResponse("200", Util.generarMensaje(Operaciones.ELIMINAR, Usuarios.PACIENTE), true, dni);
        }
        throw new NoEncontradoException("Paciente con dni " + dni + " no registra en el sistema");

    }

    private Domicilio convertirDomicilio(String domicilioCompleto) throws PeticionIncorrectaException {
        try {
            String[] domicilioArray = domicilioCompleto.split(" ");
            return new Domicilio(domicilioArray[0], Integer.parseInt(domicilioArray[1]), domicilioArray[2], domicilioArray[3]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new PeticionIncorrectaException("El formato del domicilio no es correcto");
        }
    }
}
