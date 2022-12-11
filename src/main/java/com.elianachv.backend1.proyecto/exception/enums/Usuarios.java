package com.elianachv.backend1.proyecto.exception.enums;

public enum Usuarios {

    ODONTOLOGO("Odontologo","O"),
    PACIENTE("Paciente","P"),

    TURNO("Turno","T");

    private final String rol;
    private final String codigo;

    Usuarios(String rol, String codigo) {
        this.rol = rol;
        this.codigo = codigo;
    }

    public String getRol() {
        return rol;
    }


    public String getCodigo() {
        return codigo;
    }


}
