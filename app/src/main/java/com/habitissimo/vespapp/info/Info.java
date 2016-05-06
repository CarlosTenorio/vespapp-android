package com.habitissimo.vespapp.info;

/**
 * Created by joan on 4/05/16.
 */
public class Info {

    private String title;
    private String body;

    public Info() {
    }

    public Info(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
