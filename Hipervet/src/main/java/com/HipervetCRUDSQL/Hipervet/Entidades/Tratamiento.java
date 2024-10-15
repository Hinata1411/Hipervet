package com.HipervetCRUDSQL.Hipervet.Entidades;

public class Tratamiento {
    private int codigoTratamiento;
    private String descripcion;

    public Tratamiento() {}

    public int getCodigoTratamiento() {
        return codigoTratamiento;
    }

    public void setCodigoTratamiento(int codigoTratamiento) {
        this.codigoTratamiento = codigoTratamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Tratamiento{" +
                "codigoTratamiento=" + codigoTratamiento +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
