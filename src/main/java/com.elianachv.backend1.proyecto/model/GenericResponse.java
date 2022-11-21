package com.elianachv.backend1.proyecto.model;

public class GenericResponse {

    private boolean ok;
    private String estado;
    private String detalle;

    private Object data;

    public GenericResponse(String estado, String detalle) {
        this.estado = estado;
        this.detalle = detalle;
        this.ok = false;
        this.data = null;
    }

    public GenericResponse(String estado, String detalle, boolean ok) {
        this.estado = estado;
        this.detalle = detalle;
        this.ok = ok;
        this.data = null;
    }

    public GenericResponse(String estado, String detalle, boolean ok, Object data) {
        this.estado = estado;
        this.detalle = detalle;
        this.ok = ok;
        this.data = data;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
