package com.HipervetCRUDSQL.Hipervet.Entidades;

import java.util.Date;

public class Persona {
    private int codigoPersona;
    private String primerNombre;
    private String segundoNombre;
    private String tercerNombre;
    private String primerApellido;
    private String segundoApellido;
    private String tercerApellido;
    private String tipoPersona;
    private String razonSocial;
    private Date fechaNacimiento;
    private String genero;

    public Persona() {
        this.codigoPersona = codigoPersona;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.tercerNombre = tercerNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.tercerApellido = tercerApellido;
        this.tipoPersona = tipoPersona;
        this.razonSocial = razonSocial;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
    }

    public int getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(int codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getTercerNombre() {
        return tercerNombre;
    }

    public void setTercerNombre(String tercerNombre) {
        this.tercerNombre = tercerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTercerApellido() {
        return tercerApellido;
    }

    public void setTercerApellido(String tercerApellido) {
        this.tercerApellido = tercerApellido;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return  "| Codigo= " + codigoPersona +
                "| Primer Nombre= " + primerNombre + '\'' +
                "| Segundo Nombre= " + segundoNombre + '\'' +
                "| Tercer Nombre= " + tercerNombre + '\'' +
                "| Primer Apellido= " + primerApellido + '\'' +
                "| Segundo Apellido= " + segundoApellido + '\'' +
                "| Tercer Apellido= " + tercerApellido + '\'' +
                "| Tipo Persona= " + tipoPersona + '\'' +
                "| Razon Social= " + razonSocial + '\'' +
                "| Fecha Nacimiento= " + fechaNacimiento +
                "| Genero= " + genero + '\'';
    }
}