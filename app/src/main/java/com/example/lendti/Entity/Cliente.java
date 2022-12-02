package com.example.lendti.Entity;

public class Cliente {

    private String nombre;
    private String apellido;
    private String codigo;
    private String rol;
    private String correo;
    private String password;
    private String urlfoto;

    public Cliente(String nombre, String apellido, String codigo, String rol, String correo, String password, String urlfoto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigo = codigo;
        this.rol = rol;
        this.correo = correo;
        this.password = password;
        this.urlfoto = urlfoto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
