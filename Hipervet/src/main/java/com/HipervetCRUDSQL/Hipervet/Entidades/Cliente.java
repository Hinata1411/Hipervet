package com.HipervetCRUDSQL.Hipervet.Entidades;

public class Cliente {
    private int codigoCliente;
    private int codigoPersona;

    public Cliente() {}

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(int codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigoCliente=" + codigoCliente +
                ", codigoPersona=" + codigoPersona +
                '}';
    }
}