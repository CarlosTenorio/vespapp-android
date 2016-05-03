package com.habitissimo.vespapp.questions;

/**
 * Created by archi on 11/03/16.
 */
public class Picture {
    public String file;
    public String id;
    public String sighting;

    public Picture() {
    }

    public Picture(String file, String id, String sighting) {
        this.file = file;
        this.id = id;
        this.sighting = sighting;
    }
}
