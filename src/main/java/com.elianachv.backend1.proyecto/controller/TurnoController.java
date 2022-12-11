package com.elianachv.backend1.proyecto.controller;


import com.elianachv.backend1.proyecto.dto.TurnoDto;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;
import com.elianachv.backend1.proyecto.exception.PeticionIncorrectaException;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import com.elianachv.backend1.proyecto.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/turnos")
public class TurnoController {
    @Autowired
    TurnoService turnoService;

    @GetMapping
    public ResponseEntity<GenericResponse> listarTurnos() {
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }

    @GetMapping("/rol")
    public ResponseEntity<GenericResponse> buscarTurnosPorRol(@RequestParam String rol, @RequestParam String identificacion) throws NoEncontradoException, PeticionIncorrectaException {
        return new ResponseEntity<>(turnoService.buscarTurnosPorRol(rol, identificacion), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GenericResponse> buscarTurno(@PathVariable long id) throws NoEncontradoException {
        return new ResponseEntity<>(turnoService.buscarTurno(id), HttpStatus.OK);
    }

    @GetMapping("/fecha")
    public ResponseEntity<GenericResponse> buscarTurnosPorFecha(@RequestParam String fecha) throws NoEncontradoException {
        return new ResponseEntity<>(turnoService.buscarTurnosPorFecha(fecha), HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<GenericResponse> agendarTurno(@RequestBody TurnoDto turno) throws PeticionIncorrectaException {
        return new ResponseEntity<>(turnoService.crearTurno(turno), HttpStatus.CREATED);
    }

    @PutMapping("/modificar")
    public ResponseEntity<GenericResponse> modificarTurno(@RequestBody TurnoDto turno) throws NoEncontradoException {
        return new ResponseEntity<>(turnoService.modificarTurno(turno), HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/id/{id}")
    public ResponseEntity<GenericResponse> eliminarTurno(@PathVariable long id) throws NoEncontradoException {
        return new ResponseEntity<>(turnoService.eliminarTurno(id), HttpStatus.OK);
    }

}


