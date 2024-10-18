package com.HipervetCRUDSQL.Hipervet.Entidades;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Empleado {

    @Getter
        @Setter
        private int codigoEmpleado;
        private int codigoPersona;
        private String primerNombre;
        private String segundoNombre;
        private String tercerNombre;
        private String primerApellido;
        private String segundoApellido;
        private String tercerApellido;
        private LocalDate fechaNacimiento;
        private int codigoPuesto;
        private String descripcionPuesto;

    public Empleado() {
        this.codigoEmpleado = codigoEmpleado;
    }

    // Getters y Setters para los nuevos campos
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

        public LocalDate getFechaNacimiento() {
            return fechaNacimiento;
        }
        public void setFechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public int getCodigoPuesto() {
            return codigoPuesto;
        }

        public void setCodigoPuesto(int codigoPuesto) {
            this.codigoPuesto = codigoPuesto;
        }

        public String getDescripcionPuesto() {
            return descripcionPuesto;
        }

        public void setDescripcionPuesto(String descripcionPuesto) {
            this.descripcionPuesto = descripcionPuesto;
        }

}

