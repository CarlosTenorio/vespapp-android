package com.habitissimo.vespapp.sighting;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Sighting  implements Serializable {

    private static final int STATUS_PENDING = 0;
    private static final int STATUS_PROCESSING = 1;
    private static final int STATUS_PROCESSED = 2;
    //private static final int STATUS_UNSENT = -1;

    public static final int TYPE_WASP = 1;
    public static final int TYPE_NEST = 2;


    private int id;
    private int status;
    private String free_text= "";
    private int type;
    private String contact;
    private String source = "app";
    private String created_at;

    @SerializedName("public")
    private boolean _public;
    private Boolean is_valid;

    private Location location;
    private float lat;
    private float lng;

    private List<Picture> pictures = null;

    public Sighting () {

    }

    public Sighting(int id, int status, String free_text, int type,
                    String contact, String source, boolean _public, boolean _valid,
                    Location location, float lat, float lng) {
        this.id = id;
        this.status = status;
        this.free_text = free_text;
        this.type = type;
        this.contact = contact;
        this.source = source;
        this._public = _public;
        this.is_valid = _valid;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getFree_text() {
        return free_text;
    }

    public int getType() {
        return type;
    }

    public String getContact() {
        return contact;
    }

    public String getSource() {
        return source;
    }

    public boolean is_public() {
        return _public;
    }

    public Location getLocation() {
        return location;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFree_text(String free_text) {
        this.free_text = free_text;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void set_public(boolean _public) {
        this._public = _public;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public Boolean is_valid() {
        return is_valid;
    }

    public void set_valid(Boolean _valid) {
        this.is_valid = _valid;
    }
}
