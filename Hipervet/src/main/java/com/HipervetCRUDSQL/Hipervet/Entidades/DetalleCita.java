package com.HipervetCRUDSQL.Hipervet.Entidades;

public class DetalleCita {
    private int numeroCita;
    private int numeroFicha;
    private int correlativo;
    private int codigoServicio;
    private String inicio;
    private String fin;
    private int codigoEmpleado;
    private int codigoSucursal;

    public DetalleCita() {}

    public int getNumeroCita() {
        return numeroCita;
    }

    public void setNumeroCita(int numeroCita) {
        this.numeroCita = numeroCita;
    }

    public int getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(int numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(int codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    @Override
    public String toString() {
        return "DetalleCita{" +
                "numeroCita=" + numeroCita +
                ", numeroFicha=" + numeroFicha +
                ", correlativo=" + correlativo +
                ", codigoServicio=" + codigoServicio +
                ", inicio='" + inicio + '\'' +
                ", fin='" + fin + '\'' +
                ", codigoEmpleado=" + codigoEmpleado +
                ", codigoSucursal=" + codigoSucursal +
                '}';
    }
}