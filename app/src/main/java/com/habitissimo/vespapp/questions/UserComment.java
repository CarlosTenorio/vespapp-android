package com.habitissimo.vespapp.questions;

import java.util.Date;

/**
 * Created by archi on 11/03/16.
 */
public class UserComment {

    public String body;
    public Boolean moderated;
    public Date created_at;
    public Date updated_at;

    public UserComment() {
    }

    public UserComment(String body, Boolean moderated, Date created_at, Date updated_at) {
        this.body = body;
        this.moderated = moderated;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
