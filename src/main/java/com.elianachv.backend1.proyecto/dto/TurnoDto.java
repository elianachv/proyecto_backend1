package com.elianachv.backend1.proyecto.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoDto {

    private long matriculaOdontologo;

    private long dniPaciente;

    private LocalDate fecha;

    public TurnoDto() {
    }

    public TurnoDto(long matriculaOdontologo, long dniPaciente, LocalDate fecha) {
        this.matriculaOdontologo = matriculaOdontologo;
        this.dniPaciente = dniPaciente;
        this.fecha = fecha;
    }

    public long getMatriculaOdontologo() {
        return matriculaOdontologo;
    }

    public void setMatriculaOdontologo(long matriculaOdontologo) {
        this.matriculaOdontologo = matriculaOdontologo;
    }

    public long getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(long dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
