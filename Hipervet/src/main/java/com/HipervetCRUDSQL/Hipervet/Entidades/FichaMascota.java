package com.HipervetCRUDSQL.Hipervet.Entidades;
import java.time.LocalDate;
public class FichaMascota {
    private int numeroFicha;
    private int codigoEspecie;
    private int codigoRaza;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String talla;
    private String genero;

    public FichaMascota() {}

    public int getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(int numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public int getCodigoEspecie() {
        return codigoEspecie;
    }

    public void setCodigoEspecie(int codigoEspecie) {
        this.codigoEspecie = codigoEspecie;
    }

    public int getCodigoRaza() {
        return codigoRaza;
    }

    public void setCodigoRaza(int codigoRaza) {
        this.codigoRaza = codigoRaza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "FichaMascota{" +
                "numeroFicha=" + numeroFicha +
                ", codigoEspecie=" + codigoEspecie +
                ", codigoRaza=" + codigoRaza +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", talla='" + talla + '\'' +
                ", genero='" + genero + '\'' +
                '}';
    }
}
