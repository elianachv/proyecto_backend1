package com.elianachv.backend1.proyecto.util;

import com.elianachv.backend1.proyecto.exception.enums.Operaciones;
import com.elianachv.backend1.proyecto.exception.enums.Usuarios;

public class Util {

    public static String generarMensaje(Operaciones o, Usuarios u, boolean exitoso) {
        if (exitoso) {
            return u.getRol() + " " + o.getVerboConjugado() + " con éxito";
        } else {
            return "Error al " + o.getVerboInfinitivo() + u.getRol();
        }

    }

    public static String generarMensaje(Operaciones o, Usuarios u) {
        return generarMensaje(o, u, true);
    }
}