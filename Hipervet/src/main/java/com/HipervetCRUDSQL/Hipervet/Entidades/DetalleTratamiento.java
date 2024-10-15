package com.HipervetCRUDSQL.Hipervet.Entidades;

public class DetalleTratamiento {
    private int correlativo;
    private int numeroCita;
    private int numeroFicha;
    private int codigoDiagnostico;
    private int codigoTratamiento;
    private int codigoSucursal;

    public DetalleTratamiento() {}

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

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

    public int getCodigoDiagnostico() {
        return codigoDiagnostico;
    }

    public void setCodigoDiagnostico(int codigoDiagnostico) {
        this.codigoDiagnostico = codigoDiagnostico;
    }

    public int getCodigoTratamiento() {
        return codigoTratamiento;
    }

    public void setCodigoTratamiento(int codigoTratamiento) {
        this.codigoTratamiento = codigoTratamiento;
    }

    public int getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(int codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    @Override
    public String toString() {
        return "DetalleDeTratamiento{" +
                "correlativo=" + correlativo +
                ", numeroCita=" + numeroCita +
                ", numeroFicha=" + numeroFicha +
                ", codigoDiagnostico=" + codigoDiagnostico +
                ", codigoTratamiento=" + codigoTratamiento +
                ", codigoSucursal=" + codigoSucursal +
                '}';
    }
}
