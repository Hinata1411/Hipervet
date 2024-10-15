package com.HipervetCRUDSQL.Hipervet.Entidades;

public class Especie {
    private int codigoEspecie;
    private String descripcion;

    public Especie() {}

    public int getCodigoEspecie() {
        return codigoEspecie;
    }

    public void setCodigoEspecie(int codigoEspecie) {
        this.codigoEspecie = codigoEspecie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Especie{" +
                "codigoEspecie=" + codigoEspecie +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
