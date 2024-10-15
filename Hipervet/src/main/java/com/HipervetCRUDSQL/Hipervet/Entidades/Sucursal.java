package com.HipervetCRUDSQL.Hipervet.Entidades;

public class Sucursal {
    private int codigoSucursal;
    private String nombreSucursal;
    private String direccion;

    public Sucursal() {}

    public int getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(int codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "codigoSucursal=" + codigoSucursal +
                ", nombreSucursal='" + nombreSucursal + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
