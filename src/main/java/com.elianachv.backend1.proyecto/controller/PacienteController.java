package com.elianachv.backend1.proyecto.controller;

import com.elianachv.backend1.proyecto.dto.PacienteDto;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.exception.PeticionIncorrectaException;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import com.elianachv.backend1.proyecto.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pacientes")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<GenericResponse> listarPacientes() {
        return new ResponseEntity<>(pacienteService.listarPacientes(), HttpStatus.OK);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<GenericResponse> buscarPacientePorDni(@PathVariable long dni) throws NoEncontradoException {
        return new ResponseEntity<>(pacienteService.buscarPacientePorDni(dni), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GenericResponse> buscarPacientePorId(@PathVariable long id) throws NoEncontradoException {
        return new ResponseEntity<>(pacienteService.buscarPacientePorId(id), HttpStatus.OK);
    }


    @GetMapping("/nombre")
    public ResponseEntity<GenericResponse> buscarPacientePorId(@RequestParam String nombre, @RequestParam String apellido) throws NoEncontradoException {
        return new ResponseEntity<>(pacienteService.buscarPacientePorNombreYApellido(nombre, apellido), HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<GenericResponse> crearPaciente(@RequestBody PacienteDto paciente) throws DuplicadoException, PeticionIncorrectaException {
        return new ResponseEntity<>(pacienteService.crearPaciente(paciente), HttpStatus.CREATED);
    }

    @PutMapping("/modificar")
    public ResponseEntity<GenericResponse> modificarPaciente(@RequestBody PacienteDto paciente) throws NoEncontradoException, PeticionIncorrectaException {
        return new ResponseEntity<>(pacienteService.modificarPaciente(paciente), HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/dni/{dni}")
    public ResponseEntity<GenericResponse> eliminarPaciente(@PathVariable long dni) throws NoEncontradoException {
        return new ResponseEntity<>(pacienteService.eliminarPaciente(dni), HttpStatus.OK);
    }

}