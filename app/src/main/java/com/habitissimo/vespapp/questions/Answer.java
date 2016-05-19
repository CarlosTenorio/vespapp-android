package com.habitissimo.vespapp.questions;

/**
 * Created by archi on 11/03/16.
 */
public class Answer {

    private int id;
    private String value;

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Answer(int id, String value) {
        this.id = id;
        this.value = value;
    }
}
