package com.elianachv.backend1.proyecto.dao;

import com.elianachv.backend1.proyecto.exception.DuplicadoException;
import com.elianachv.backend1.proyecto.exception.NoEncontradoException;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {

    void crear(T t) throws DuplicadoException;

    void modificar(int id, T t);

    T obtener(int id);

    List<T> listarTodos();

    void eliminar(int id);

}

