package com.habitissimo.vespapp.questions;

import java.io.Serializable;

/**
 * Created by archi on 11/03/16.
 */
public class Answer implements Serializable {

    private int id;
    private String value;

    public Answer(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
