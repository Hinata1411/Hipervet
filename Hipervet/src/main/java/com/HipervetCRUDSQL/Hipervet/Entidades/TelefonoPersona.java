package com.HipervetCRUDSQL.Hipervet.Entidades;

public class TelefonoPersona {
    private int correlativoTelefono;
    private int codigoPersona;

    public TelefonoPersona() {}

    public int getCorrelativoTelefono() {
        return correlativoTelefono;
    }

    public void setCorrelativoTelefono(int correlativoTelefono) {
        this.correlativoTelefono = correlativoTelefono;
    }

    public int getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(int codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    @Override
    public String toString() {
        return "TelefonoPersona{" +
                "correlativoTelefono=" + correlativoTelefono +
                ", codigoPersona=" + codigoPersona +
                '}';
    }
}

