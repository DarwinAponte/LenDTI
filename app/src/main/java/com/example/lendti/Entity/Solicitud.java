package com.example.lendti.Entity;

public class Solicitud {

    private String uidCliente;
    private String uidEquipo;
    private String tipo;
    private String marca;
    private String motivo;
    private String curso;
    private String time;
    private String programas;
    private String urlFotoDNI;
    private String otros;
    private String estado;
    private String cantidad;


    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
