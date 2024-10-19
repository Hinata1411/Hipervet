package com.HipervetCRUDSQL.Hipervet.Entidades;

import java.util.Date;

public class Cita {

        private int codigoSucursal;
        private int numeroCita;
        private Date fechaCita;
        private String horaInicio;
        private String horaFin;
        private String observaciones;
        private int codigoEmpleado;
        private String tipoCita; // M para médica, G para grooming
        private int codigoCliente;
        private String planGrooming; // Plan de grooming
        private String diagnostico;
        private String procedimiento;
        private String medicinaRecetada;

        public Cita() {}

        // Getters y Setters

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

        public String getHoraInicio() {
            return horaInicio;
        }

        public void setHoraInicio(String horaInicio) {
            this.horaInicio = horaInicio;
        }

        public String getHoraFin() {
            return horaFin;
        }

        public void setHoraFin(String horaFin) {
            this.horaFin = horaFin;
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

        public String getPlanGrooming() {
            return planGrooming;
        }

        public void setPlanGrooming(String planGrooming) {
            this.planGrooming = planGrooming;
        }

        public String getDiagnostico() {
            return diagnostico;
        }

        public void setDiagnostico(String diagnostico) {
            this.diagnostico = diagnostico;
        }

        public String getProcedimiento() {
            return procedimiento;
        }

        public void setProcedimiento(String procedimiento) {
            this.procedimiento = procedimiento;
        }

        public String getMedicinaRecetada() {
            return medicinaRecetada;
        }

        public void setMedicinaRecetada(String medicinaRecetada) {
            this.medicinaRecetada = medicinaRecetada;
        }
    }

