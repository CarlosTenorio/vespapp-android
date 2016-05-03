package com.habitissimo.vespapp.questions;

/**
 * Created by archi on 11/03/16.
 */
public class Location {

    private String name;
    private float lat;
    private float lng;

    public Location() {
    }

    public Location(String name, float lat, float lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}
