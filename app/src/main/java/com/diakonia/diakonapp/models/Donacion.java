package com.diakonia.diakonapp.models;


public class Donacion {
    private String id;
    private String beneficiario;
    private  String cantidad;


    private  String email;
    private String fechaDonacion;
    private String pesoPorUnidad;
    private String producto;
    private String puntos;
    private String uId;
    private String unidad;
    private String foto;

    public Donacion(){

    }

    //TextView producto,beneficiario, fecha, puntos;
    //        ImageView fotoDonacion;


    public Donacion(String producto, String beneficiario, String fechaDonacion, String puntos, String foto) {

        this.producto = producto;
        this.beneficiario = beneficiario;
        this.fechaDonacion = fechaDonacion;
        this.puntos = puntos;
        this.foto = foto;
    }

    public Donacion(String beneficiario, String cantidad, String email, String fechaDonacion, String pesoPorUnidad, String producto, String puntos, String uId, String unidad, String foto) {

        this.beneficiario = beneficiario;
        this.cantidad = cantidad;

        this.email = email;
        this.fechaDonacion = fechaDonacion;
        this.pesoPorUnidad = pesoPorUnidad;
        this.producto = producto;
        this.puntos = puntos;
        this.uId = uId;
        this.unidad = unidad;
        this.foto = foto;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(String fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public String getPesoPorUnidad() {
        return pesoPorUnidad;
    }

    public void setPesoPorUnidad(String pesoPorUnidad) {
        this.pesoPorUnidad = pesoPorUnidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}