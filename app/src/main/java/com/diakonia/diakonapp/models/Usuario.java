package com.diakonia.diakonapp.models;

import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String correo;
    private String uId;
    private String telefono;
    private String urlFoto;

    private  ArrayList<String> donaciones;



    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public ArrayList<String> getDonaciones() {
        return donaciones;
    }

    public void setDonaciones(ArrayList<String> donaciones) {
        this.donaciones = donaciones;
    }



    public Usuario(ArrayList<String> donaciones) {
        this.donaciones = donaciones;
    }

    public Usuario(String nombre, String correo, String uId, String telefono, ArrayList<String> donaciones) {
        this.nombre = nombre;
        this.correo = correo;
        this.uId = uId;
        this.telefono = telefono;
        this.donaciones = donaciones;
    }

    public Usuario(String nombre, String correo, String uId, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.uId = uId;
        this.telefono = telefono;
    }

    public Usuario(String nombre, String correo, String uId, String telefono, String urlFoto, ArrayList<String> donaciones) {
        this.nombre = nombre;
        this.correo = correo;
        this.uId = uId;
        this.telefono = telefono;
        this.urlFoto = urlFoto;
        this.donaciones = donaciones;
    }

    public Usuario(String nombre, String correo, String uId, String telefono, String urlFoto) {
        this.nombre = nombre;
        this.correo = correo;
        this.uId = uId;
        this.telefono = telefono;
        this.urlFoto = urlFoto;
    }
}

