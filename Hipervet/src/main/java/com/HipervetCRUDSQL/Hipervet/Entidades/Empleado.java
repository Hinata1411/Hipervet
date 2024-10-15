package com.HipervetCRUDSQL.Hipervet.Entidades;

public class Empleado {
    private int codigoEmpleado;
    private int codigoPersona;
    private int codigoPuesto;

    public Empleado() {}

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(int codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    public int getCodigoPuesto() {
        return codigoPuesto;
    }

    public void setCodigoPuesto(int codigoPuesto) {
        this.codigoPuesto = codigoPuesto;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "codigoEmpleado=" + codigoEmpleado +
                ", codigoPersona=" + codigoPersona +
                ", codigoPuesto=" + codigoPuesto +
                '}';
    }
}
