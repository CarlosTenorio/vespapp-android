package com.habitissimo.vespapp.sighting;

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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSighting() {
        return sighting;
    }

    public void setSighting(String sighting) {
        this.sighting = sighting;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
