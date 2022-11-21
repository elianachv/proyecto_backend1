package com.elianachv.backend1.proyecto.entity;

import java.util.Date;

public class Turno {

    private int id;
    private Date fecha;
    private Odontologo odontologo;
    private Paciente paciente;

    public Turno() {
    }

    public Turno(Date fecha, Odontologo odontologo, Paciente paciente) {
        this.fecha = fecha;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }
    public Turno(int id, Date fecha, Odontologo odontologo, Paciente paciente) {
        this.id = id;
        this.fecha = fecha;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", odontologo=" + odontologo.getMatricula() +
                ", paciente=" + paciente.getDni() +
                '}';
    }
}
