package com.elianachv.backend1.proyecto.controller;

import com.elianachv.backend1.proyecto.entity.Odontologo;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import com.elianachv.backend1.proyecto.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/odontologo")
public class OdontologoController {
    @Autowired
    OdontologoService odontologoService;

    @GetMapping("/todos")
    public ResponseEntity<GenericResponse> listarOdontologos() {
        return new ResponseEntity<>(odontologoService.listarOdontologos(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GenericResponse> buscarOdontologo(@RequestParam int matricula) throws NoEncontradoException {
        return new ResponseEntity<>(odontologoService.buscarOdontologo(matricula), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<GenericResponse> crearOdontologo(@RequestBody Odontologo odontologo) throws DuplicadoException {
        return new ResponseEntity<>(odontologoService.crearOdontologo(odontologo), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<GenericResponse> modificarOdontologo(@RequestParam int matricula, @RequestBody Odontologo odontologo) throws DuplicadoException {
        return new ResponseEntity<>(odontologoService.modificarOdontologo(matricula, odontologo), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<GenericResponse> eliminarOdontologo(@RequestParam int matricula) throws DuplicadoException {
        return new ResponseEntity<>(odontologoService.eliminarOdontologo(matricula), HttpStatus.OK);
    }

}


