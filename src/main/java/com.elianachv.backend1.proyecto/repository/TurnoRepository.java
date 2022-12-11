package com.elianachv.backend1.proyecto.repository;

import com.elianachv.backend1.proyecto.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    Turno findByOdontologoAndPaciente(long matricula, long dni);
    @Query(value = "SELECT * FROM turnos WHERE matricula_odontologo = ?1", nativeQuery = true)
    List<Turno> findByOdontologo(long matricula);
    @Query(value = "SELECT * FROM turnos WHERE dni_paciente = ?1", nativeQuery = true)
    List<Turno> findByPaciente(long dni);

    @Query(value = "SELECT * FROM turnos WHERE fecha = ?1", nativeQuery = true)
    List<Turno> findByFecha(LocalDate fecha);

    void deleteByOdontologo(long matricula);

    void deleteByPaciente(long dni);

}