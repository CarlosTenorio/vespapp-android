package com.habitissimo.vespapp.questions;

/**
 * Created by archi on 11/03/16.
 */
public class ExpertComment {
    public String body;
    public boolean is_valid;

    public ExpertComment() {
    }

    public ExpertComment(String body, boolean is_valid) {
        this.body = body;
        this.is_valid = is_valid;
    }
}
