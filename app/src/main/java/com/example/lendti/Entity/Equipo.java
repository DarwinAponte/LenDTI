package com.example.lendti.Entity;

import com.google.firebase.Timestamp;

import java.util.List;

public class Equipo {

    public Equipo(String tipo, String marca, String caracteristicas, String incluye, String stock, List<String> listaFotos) {
        this.tipo = tipo;
        this.marca = marca;
        this.caracteristicas = caracteristicas;
        this.incluye = incluye;
        this.stock = stock;
        this.listaFotos = listaFotos;
    }
    public Equipo(){

    }

    private String tipo;
    private String marca;
    private String caracteristicas;
    private String incluye;
    private String stock;
    private List<String> listaFotos;
    private transient Timestamp timestamp;

    public Equipo(){

    }

    public Equipo(String tipo, String marca, String caracteristicas, String incluye, String stock, List<String> listaFotos) {
        this.tipo = tipo;
        this.marca = marca;
        this.caracteristicas = caracteristicas;
        this.incluye = incluye;
        this.stock = stock;
        this.listaFotos = listaFotos;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }




    public List<String> getListaFotos() {
        return listaFotos;
    }

    public void setListaFotos(List<String> listaFotos) {
        this.listaFotos = listaFotos;
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

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getIncluye() {
        return incluye;
    }

    public void setIncluye(String incluye) {
        this.incluye = incluye;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
