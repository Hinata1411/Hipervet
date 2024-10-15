package com.HipervetCRUDSQL.Hipervet.Entidades;

public class Puesto {
    private int codigoPuesto;
    private String descripcion;

    public Puesto() {}

    public int getCodigoPuesto() {
        return codigoPuesto;
    }

    public void setCodigoPuesto(int codigoPuesto) {
        this.codigoPuesto = codigoPuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Puesto{" +
                "codigoPuesto=" + codigoPuesto +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}