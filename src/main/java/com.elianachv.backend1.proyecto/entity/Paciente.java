package com.elianachv.backend1.proyecto.entity;

import java.util.Date;

public class Paciente {

    private int dni;
    private String nombre;
    private String apellido;
    private Domicilio domicilio;
    private Date fechaAlta;

    public Paciente() {
    }

    public Paciente(int dni, String nombre, String apellido, Domicilio domicilio, Date fechaAlta) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.fechaAlta = fechaAlta;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", domicilio='" + domicilio.getDomicilioCompleto() + '\'' +
                ", fechaAlta=" + fechaAlta +
                '}';
    }
}
