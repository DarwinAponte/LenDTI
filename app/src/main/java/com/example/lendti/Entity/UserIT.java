package com.example.lendti.Entity;

public class UserIT {

    public UserIT(String nombre, String correo, String codigo, String password, String urlfoto) {
        this.nombre = nombre;
        this.correo = correo;
        this.codigo = codigo;
        this.password = password;
        this.urlfoto = urlfoto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    private String nombre;
    private String correo;
    private String codigo;
    private String password;
    private String urlfoto;



}
