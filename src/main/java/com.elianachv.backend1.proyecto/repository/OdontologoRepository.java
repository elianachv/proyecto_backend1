package com.elianachv.backend1.proyecto.repository;

import com.elianachv.backend1.proyecto.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo,Long> {

    Odontologo findByMatricula(long matricula);
    void deleteByMatricula(long matricula);
    boolean existsByMatricula(long matricula);
}
