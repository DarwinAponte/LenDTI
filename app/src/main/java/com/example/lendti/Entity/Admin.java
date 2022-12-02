package com.example.lendti.Entity;

public class Admin {

    private String estado;
    private String nombre;
    private String direccion;
    private String correo;
    private String codigo;
    private String horario;
    private String urlfoto;



    public Admin(String estado, String nombre, String direccion, String correo, String codigo, String horario) {
        this.estado = estado;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.codigo = codigo;
        this.horario = horario;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
