package com.diakonia.diakonapp.models;

import java.util.ArrayList;

public class Usuario {
    private String correo;
    private ArrayList<String> donaciones;
    private String nombre;
    private String puntos;
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

    public ArrayList<String> getDonaciones() {
        return donaciones;
    }

    public void setDonaciones(ArrayList<String> donaciones) {
        this.donaciones = donaciones;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public Usuario(ArrayList<String> donaciones) {
        this.donaciones = donaciones;
    }

    public Usuario(String nombre, String correo, String uId, String telefono, ArrayList<String> donaciones, String puntos) {
        this.nombre = nombre;
        this.correo = correo;
        this.uId = uId;
        this.telefono = telefono;
        this.donaciones = donaciones;
        this.puntos = puntos;
    }

    public Usuario(String nombre, String correo, String uId, String telefono, String puntos) {
        this.nombre = nombre;
        this.correo = correo;
        this.uId = uId;
        this.telefono = telefono;
        this.puntos = puntos;
    }


    public Usuario(String nombre, String correo, String uId, String telefono,   String puntos ,ArrayList<String> donaciones ){
        this.nombre = nombre;
        this.correo = correo;
        this.uId = uId;
        this.telefono = telefono;
        ;
        this.donaciones = donaciones;
        this.puntos= puntos;
    }



    public Usuario() {
    }
}

