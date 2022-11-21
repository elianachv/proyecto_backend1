package com.elianachv.backend1.proyecto.exception;

import com.elianachv.backend1.proyecto.dao.h2.OdontologoDaoH2;
import com.elianachv.backend1.proyecto.model.GenericResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ApiExceptionHandler {

    public static Logger logger = LogManager.getLogger(OdontologoDaoH2.class);


    @ExceptionHandler({DuplicadoException.class})
    public ResponseEntity<GenericResponse> handleDuplicadoException(DuplicadoException ex) {
        logger.error(ex);
        return new ResponseEntity<>(new GenericResponse("400", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoEncontradoException.class})
    public ResponseEntity<GenericResponse> handleNoEncontradoException(NoEncontradoException ex) {
        logger.error(ex);
        return new ResponseEntity<>(new GenericResponse("404", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NoDisponibleException.class})
    public ResponseEntity<GenericResponse> handleNoDisponibleException(NoDisponibleException ex) {
        logger.error(ex);
        return new ResponseEntity<>(new GenericResponse("400", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<GenericResponse> handleSQLException(SQLException ex) {
        logger.error(ex);
        return new ResponseEntity<>(new GenericResponse("500", "Error comunicación con la base de datos"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<GenericResponse> handleException(Exception ex) {
        logger.error(ex);
        return new ResponseEntity<>(new GenericResponse("500", "Error inesperado"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
