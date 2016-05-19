package com.habitissimo.vespapp.sighting;

import java.io.Serializable;

public class Location implements Serializable {

    private int id;
    private String name;
    private float lat;
    private float lng;
    private String created_at;
    private String updated_at;
    private int province;

    public Location(int id, String name, float lat, float lng,
                    String created_at, String updated_at, int province) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.province = province;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }
}
