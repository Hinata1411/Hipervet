package com.HipervetCRUDSQL.Hipervet.Entidades;

public class Diagnostico {
    private int codigoDiagnostico;
    private String descripcion;

    public Diagnostico() {}

    public int getCodigoDiagnostico() {
        return codigoDiagnostico;
    }

    public void setCodigoDiagnostico(int codigoDiagnostico) {
        this.codigoDiagnostico = codigoDiagnostico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
                "codigoDiagnostico=" + codigoDiagnostico +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
