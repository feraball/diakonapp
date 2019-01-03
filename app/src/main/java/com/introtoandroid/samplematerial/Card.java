package com.introtoandroid.samplematerial;


public class Card {
    private long id;
    private String name;
    private String asis;

    private String cantidadPersonasAtendidas;
    private String poblacionAtendida;
    private String sector;
    private String zona;
    private String direccion;
    private String telefono;
    private String correo;
    private String servicioBrindado;
    private int color_resource;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public String getAsis(){return  asis;}

    public void setAsis(String asis) {
        this.asis = asis;
    }

    public String getCantidadPersonasAtendidas(){return cantidadPersonasAtendidas;}
    public void setCantidadPersonasAtendidas(String cantidadPersonasAtendidas){this.cantidadPersonasAtendidas = cantidadPersonasAtendidas;}

    public String getPoblacionAtendida() {return  poblacionAtendida;}
    public void setPoblacionAtendida(String poblacionAtendida){this.poblacionAtendida = poblacionAtendida;}

    public String getSector() {return  sector;}
    public void setSector(String sector){this.sector=sector;}

    public String getZona() {return  zona;}
    public void setZona(String zona) {this.zona = zona;}

    public String getDireccion() {return  direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}

    public String getTelefono(){return telefono;}
    public void setTelefono(String telefono){this.telefono=telefono;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo){this.correo = correo;}

    public String getServicioBrindado() {return servicioBrindado;}
    public void setServicioBrindado(String servicioBrindado){this.servicioBrindado = servicioBrindado;}




    public int getColorResource() {
        return color_resource;
    }
    public void setColorResource(int color_resource) {
        this.color_resource = color_resource;
    }
}
