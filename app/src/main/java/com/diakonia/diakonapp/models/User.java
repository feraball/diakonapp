package com.diakonia.diakonapp.models;

import java.util.ArrayList;

public class User {
    private String correo;
    private int donaciones;
    private String nombre;
    private int puntos;
    private String telefono;
    private String uId;
    //private ArrayList<String> favoritos;




    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public int getDonaciones() {
        return donaciones;
    }

    public void setDonaciones(int donaciones) {
        this.donaciones = donaciones;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public User(int donaciones) {
        this.donaciones = donaciones;
    }

    public User(String nombre, String correo, String uId, String telefono, int donaciones, int puntos) {
        this.nombre     = nombre;
        this.correo     = correo;
        this.uId        = uId;
        this.telefono   = telefono;
        this.donaciones = donaciones;
        this.puntos     = puntos;
    }

    public User(String nombre, String correo, String uId, String telefono) {
        this.nombre     = nombre;
        this.correo     = correo;
        this.uId        = uId;
        this.telefono   = telefono;
        this.puntos     = 0;
        this.donaciones = 0;
    }


    public User() {
    }
}

