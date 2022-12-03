package com.example.lendti.Entity;

public class Cliente {
    private String nombre;
    private String apellido;
    private String rol;
    private String dni;
    private String codigo;
    private String password;
    private String password1;
    private String correo;

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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Cliente(String nombre, String apellido, String rol, String codigo, String correo, String password, String password1) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.codigo = codigo;
        this.password = password;
        this.password1 = password1;
        this.correo = correo;
    }
}
