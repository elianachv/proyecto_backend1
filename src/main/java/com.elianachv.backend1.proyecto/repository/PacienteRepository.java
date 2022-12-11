package com.elianachv.backend1.proyecto.repository;

import com.elianachv.backend1.proyecto.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    Paciente findByDni(long dni);

    Paciente findByNombreAndApellido(String nombre, String apellido);

    void deleteByDni(long matricula);

    boolean existsByDni(long matricula);
}

