package com.elianachv.backend1.proyecto.service;

import com.elianachv.backend1.proyecto.dao.IDao;
import com.elianachv.backend1.proyecto.dao.h2.PacienteDaoH2;
import com.elianachv.backend1.proyecto.dao.util.ConfiguracionBd;
import com.elianachv.backend1.proyecto.entity.Paciente;
import com.elianachv.backend1.proyecto.exception.DuplicadoException;

import java.sql.SQLException;
import java.util.List;

public class PacienteService {

    private final IDao<Paciente> pacienteBd;

    public PacienteService(ConfiguracionBd bd) {
        this.pacienteBd = new PacienteDaoH2(bd);
    }

    public void crearPaciente(Paciente paciente) throws DuplicadoException {
        pacienteBd.crear(paciente);
    }

    public void modificarPaciente(int dni, Paciente paciente) {
        pacienteBd.modificar(dni, paciente);
    }

    public Paciente buscarPaciente(int dni) {
        return pacienteBd.obtener(dni);
    }

    public List<Paciente> listarPacientes() {
        return pacienteBd.listarTodos();
    }

    public void eliminarPaciente(int dni) {
        pacienteBd.eliminar(dni);
    }
}
