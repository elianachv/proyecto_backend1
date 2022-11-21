package com.elianachv.backend1.proyecto.exception.enums;

import java.util.Arrays;

public enum Operaciones {
    CREAR("C", "crear","creado"),
    MODIFICAR("M", "modificar","modificado"),
    ELIMINAR("E", "eliminar","eliminado"),
    BUSCAR("B", "encontrar","encontrado");

    private final String codigo;
    private final String verboConjugado;

    private final String verboInfinitivo;

    Operaciones(String codigo,String verboInfinitivo, String verboConjugado) {
        this.codigo = codigo;
        this.verboInfinitivo = verboInfinitivo;
        this.verboConjugado = verboConjugado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getVerboConjugado() {
        return verboConjugado;
    }

    public String getVerboInfinitivo() {
        return verboInfinitivo;
    }

    public static String obtenerVerboPorCodigo(String codigo) {
        Operaciones operacion = Arrays.stream(Operaciones.values()).filter(o -> o.getCodigo().equals(codigo)).findFirst().orElse(null);
        if (operacion != null) {
            return operacion.getVerboConjugado();
        } else {
            return " ";
        }
    }
}
