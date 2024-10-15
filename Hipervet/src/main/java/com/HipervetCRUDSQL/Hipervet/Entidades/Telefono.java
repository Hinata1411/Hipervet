package com.HipervetCRUDSQL.Hipervet.Entidades;

public class Telefono {
    private int correlativoTelefono;
    private String numeroTelefono;

    public Telefono() {}

    public int getCorrelativoTelefono() {
        return correlativoTelefono;
    }

    public void setCorrelativoTelefono(int correlativoTelefono) {
        this.correlativoTelefono = correlativoTelefono;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    @Override
    public String toString() {
        return "Telefono{" +
                "correlativoTelefono=" + correlativoTelefono +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                '}';
    }
}