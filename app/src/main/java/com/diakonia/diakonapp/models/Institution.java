package com.diakonia.diakonapp.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Institution implements Parcelable {

    private long id;
    private String nombre;
    private String asistencia;

    private String cantidadPersonasAtendidas;
    private String poblacionAtendida;
    private String sector;
    private String zona;
    private String direccion;
    private String telefono;
    private String correo;
    private String servicioBrindado;
    private String longitud;
    private String latitud;
    private String diasAtencion;
    private String horarioAtencion;
    private String urlFoto;
    private int color_resource;

    public Institution(){

    }


    public Institution(String nombre, String asistencia, String telefono, String longitud, String latitud, String cantidad, String direccion, String horarioDeAtencion, String correo, String urlFoto) {
        this.nombre =nombre;
        this.asistencia =asistencia;
        this.telefono=telefono;
        this.longitud=longitud;
        this.latitud=latitud;
        this.cantidadPersonasAtendidas=cantidad;
        this.direccion=direccion;
        this.horarioAtencion=horarioDeAtencion;
        this.correo=correo;
        this.urlFoto=urlFoto;
    }

    public Institution(String nombre) {
        this.nombre = nombre;
        this.asistencia ="";
        this.telefono="";
        this.longitud="";
        this.latitud="";
        this.cantidadPersonasAtendidas="";
        this.direccion="";
        this.horarioAtencion="";
        this.correo="";
        this.urlFoto="";
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsistencia(){return asistencia;}
    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
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


    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getDiasAtencion() {
        return diasAtencion;
    }

    public void setDiasAtencion(String diasAtencion) {
        this.diasAtencion = diasAtencion;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public int getColorResource() {
        return color_resource;
    }
    public void setColorResource(int color_resource) {
        this.color_resource = color_resource;
    }



    public Institution(Parcel in) {
        this.nombre = in.readString();
        this.asistencia = in.readString();
        this.telefono                   = in.readString();
        this.longitud                   = in.readString();
        this.latitud                    = in.readString();
        this.cantidadPersonasAtendidas  = in.readString();
        this.direccion                  = in.readString();
        this.horarioAtencion            = in.readString();
        this.correo                     = in.readString();
        this.urlFoto                    = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(asistencia);
        dest.writeString(telefono);
        dest.writeString(longitud);
        dest.writeString(latitud);
        dest.writeString(cantidadPersonasAtendidas);
        dest.writeString(direccion);
        dest.writeString(horarioAtencion);
        dest.writeString(correo);
        dest.writeString(urlFoto);
    }

    public static final Parcelable.Creator<Institution> CREATOR = new Parcelable.Creator<Institution>(){
        public Institution createFromParcel(Parcel in) {
            return new Institution(in);
        }

        public Institution[] newArray(int size) {
            return new Institution[size];
        }
    };
}



