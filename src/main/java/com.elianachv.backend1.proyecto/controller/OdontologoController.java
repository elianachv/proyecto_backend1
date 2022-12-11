package com.elianachv.backend1.proyecto.controller;

import com.elianachv.backend1.proyecto.dto.OdontologoDto;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import com.elianachv.backend1.proyecto.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/odontologos")
public class OdontologoController {
    @Autowired
    OdontologoService odontologoService;

    @GetMapping
    public ResponseEntity<GenericResponse> listarOdontologos() {
        return new ResponseEntity<>(odontologoService.listarOdontologos(), HttpStatus.OK);
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<GenericResponse> buscarOdontologoPorMatricula(@PathVariable long matricula) throws NoEncontradoException {
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorMatricula(matricula), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GenericResponse> buscarOdontologoPorId(@PathVariable long id) throws NoEncontradoException {
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorId(id), HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<GenericResponse> crearOdontologo(@RequestBody OdontologoDto odontologo) throws DuplicadoException {
        return new ResponseEntity<>(odontologoService.crearOdontologo(odontologo), HttpStatus.CREATED);
    }

    @PutMapping("/modificar")
    public ResponseEntity<GenericResponse> modificarOdontologo(@RequestBody OdontologoDto odontologo) throws NoEncontradoException {
        return new ResponseEntity<>(odontologoService.modificarOdontologo(odontologo), HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/matricula/{matricula}")
    public ResponseEntity<GenericResponse> eliminarOdontologo(@PathVariable long matricula) throws NoEncontradoException {
        return new ResponseEntity<>(odontologoService.eliminarOdontologo(matricula), HttpStatus.OK);
    }

}


