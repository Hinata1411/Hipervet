package com.HipervetCRUDSQL.Hipervet.Entidades;

public class Raza {
    private int codigoRaza;
    private String descripcion;

    public Raza() {}

    public int getCodigoRaza() {
        return codigoRaza;
    }

    public void setCodigoRaza(int codigoRaza) {
        this.codigoRaza = codigoRaza;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Raza{" +
                "codigoRaza=" + codigoRaza +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
