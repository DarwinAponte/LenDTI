package com.example.lendti.Entity;

public class Cliente {
    private String nombre;
    private String apellido;
    private String rol;
    private String codigo;
    private String password;
    private String correo;
    private String urlFoto;

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Cliente(String nombre, String apellido, String rol, String codigo,String correo, String password,  String urlFoto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.codigo = codigo;
        this.password = password;
        this.correo = correo;
        this.urlFoto = urlFoto;
    }
}
