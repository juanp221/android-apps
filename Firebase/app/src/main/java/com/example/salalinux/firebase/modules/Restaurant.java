package com.example.salalinux.firebase.modules;



import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by mario on 22/03/2017.
 */
@IgnoreExtraProperties
public class Restaurant {
    /*// este codigo fue un ejemplo para a primera prueba
    private String tipo,nombre,imagen;
    private int distancia;

    public Restaurant(String tipo, String nombre, String imagen,int distancia) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.distancia=distancia;
    }

    public String getTipo() {
        return tipo;
    }

    public int getDistancia() {
        return distancia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public String getImagen() {
        return imagen;
    }*/


    private String formatted_address,icon,name,place_id,website;
    private ArrayList<String> types;
    private int rating;

    public Restaurant(String formatted_address, String icon, String name, String place_id, String website, ArrayList<String> types, int rating) {
        this.formatted_address = formatted_address;
        this.icon = icon;
        this.name = name;
        this.place_id = place_id;
        this.website = website;
        this.types = types;
        this.rating = rating;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getWebsite() {
        return website;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public int getRating() {
        return rating;
    }

    public Restaurant(){

    }
}
