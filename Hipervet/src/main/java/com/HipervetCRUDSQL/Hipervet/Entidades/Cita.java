package com.HipervetCRUDSQL.Hipervet.Entidades;

import java.util.Date;

public class Cita {
    private int codigoSucursal;
    private int numeroCita;
    private Date fechaCita;
    private String observaciones;
    private int codigoEmpleado;
    private String tipoCita;
    private int codigoCliente;

    public Cita() {}

    public int getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(int codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public int getNumeroCita() {
        return numeroCita;
    }

    public void setNumeroCita(int numeroCita) {
        this.numeroCita = numeroCita;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "codigoSucursal=" + codigoSucursal +
                ", numeroCita=" + numeroCita +
                ", fechaCita=" + fechaCita +
                ", observaciones='" + observaciones + '\'' +
                ", codigoEmpleado=" + codigoEmpleado +
                ", tipoCita='" + tipoCita + '\'' +
                ", codigoCliente=" + codigoCliente +
                '}';
    }
}
