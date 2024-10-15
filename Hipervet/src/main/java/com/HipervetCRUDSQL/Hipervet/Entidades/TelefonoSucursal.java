package com.HipervetCRUDSQL.Hipervet.Entidades;

public class TelefonoSucursal {
    private int correlativoTelefono;
    private int codigoSucursal;

    public TelefonoSucursal() {}

    public int getCorrelativoTelefono() {
        return correlativoTelefono;
    }

    public void setCorrelativoTelefono(int correlativoTelefono) {
        this.correlativoTelefono = correlativoTelefono;
    }

    public int getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(int codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    @Override
    public String toString() {
        return "TelefonoSucursal{" +
                "correlativoTelefono=" + correlativoTelefono +
                ", codigoSucursal=" + codigoSucursal +
                '}';
    }
}
