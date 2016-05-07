package com.habitissimo.vespapp.info;

import java.io.Serializable;

/**
 * Created by joan on 4/05/16.
 */
public class Info implements Serializable {

    private String title;
    private String body;
    private String image;

    public Info(String title, String body, String image) {
        this.title = title;
        this.body = body;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImage() {
        return image;
    }
}
