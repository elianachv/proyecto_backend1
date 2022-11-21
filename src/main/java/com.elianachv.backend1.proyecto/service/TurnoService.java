package com.elianachv.backend1.proyecto.service;

import com.elianachv.backend1.proyecto.dao.IDao;
import com.elianachv.backend1.proyecto.dao.h2.TurnoDaoH2;
import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.entity.Turno;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;

import java.sql.SQLException;
import java.util.List;

public class TurnoService {
    private final IDao<Turno> turnoBd;

    public TurnoService(ConfiguracionBd bd) {
        this.turnoBd = new TurnoDaoH2(bd);
    }

    public void crearTurno(Turno turno) throws DuplicadoException {
        turnoBd.crear(turno);
    }

    public void modificarTurno(int id, Turno turno) {
        turnoBd.modificar(id, turno);
    }

    public Turno buscarTurno(int id) {
        return turnoBd.obtener(id);
    }

    public List<Turno> listarTurnos() {
        return turnoBd.listarTodos();
    }

    public void eliminarTurno(int id) {
        turnoBd.eliminar(id);
    }
}
