package com.habitissimo.vespapp.locations;

import java.io.Serializable;

public class Location implements Serializable{

    private int id;
    private String name;
    private float lat;
    private float lng;

    public Location() {
    }

    public Location(int id, String name, float lat, float lng) {
        this.id = id;

        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }
}
