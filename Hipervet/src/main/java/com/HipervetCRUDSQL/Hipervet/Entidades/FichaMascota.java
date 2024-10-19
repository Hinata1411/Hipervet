package com.HipervetCRUDSQL.Hipervet.Entidades;
import java.time.LocalDate;


public class FichaMascota {

        private int numeroFicha;
        private String codigoEspecie;
        private String codigoRaza;
        private String nombre;
        private LocalDate fechaNacimiento;
        private String talla;
        private String genero;
        private int codigoCliente;
        private String nombreDueño; // Nuevo campo para mostrar el nombre del dueño

        public int getNumeroFicha() {
            return numeroFicha;
        }

        public void setNumeroFicha(int numeroFicha) {
            this.numeroFicha = numeroFicha;
        }

        public String getCodigoEspecie() {
            return codigoEspecie;
        }

        public void setCodigoEspecie(String codigoEspecie) {
            this.codigoEspecie = codigoEspecie;
        }

        public String getCodigoRaza() {
            return codigoRaza;
        }

        public void setCodigoRaza(String codigoRaza) {
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

        public int getCodigoCliente() {
            return codigoCliente;
        }

        public void setCodigoCliente(int codigoCliente) {
            this.codigoCliente = codigoCliente;
        }

        public String getNombreDuenio() {
            return nombreDueño;
        }

        public void setNombreDuenio(String nombreDueño) {
            this.nombreDueño = nombreDueño;
        }
    }
