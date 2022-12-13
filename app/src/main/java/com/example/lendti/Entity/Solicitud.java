package com.example.lendti.Entity;

import com.google.firebase.Timestamp;

public class Solicitud {

    public String getUidCliente() {
        return uidCliente;
    }

    public void setUidCliente(String uidCliente) {
        this.uidCliente = uidCliente;
    }

    public String getUidEquipo() {
        return uidEquipo;
    }

    public void setUidEquipo(String uidEquipo) {
        this.uidEquipo = uidEquipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Timestamp getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(Timestamp tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public String getProgramas() {
        return programas;
    }

    public void setProgramas(String programas) {
        this.programas = programas;
    }

    public String getUrlFotoDNI() {
        return urlFotoDNI;
    }

    public void setUrlFotoDNI(String urlFotoDNI) {
        this.urlFotoDNI = urlFotoDNI;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTiempoPrestamo() {
        return tiempoPrestamo;
    }

    public void setTiempoPrestamo(String tiempoPrestamo) {
        this.tiempoPrestamo = tiempoPrestamo;
    }

    public Timestamp getTiempoFin() {
        return tiempoFin;
    }

    public void setTiempoFin(Timestamp tiempoFin) {
        this.tiempoFin = tiempoFin;
    }

    private String uidCliente;
    private String uidEquipo;
    private String tipo;
    private String marca;
    private String motivo;
    private String curso;
    private Timestamp tiempoInicio;
    private String programas;
    private String urlFotoDNI;
    private String otros;
    private String estado;
    private String tiempoPrestamo;
    private Timestamp tiempoFin;


}
