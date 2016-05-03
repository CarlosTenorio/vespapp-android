package com.habitissimo.vespapp.questions;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archi on 11/03/16.
 */
public class Sighting {
    public static final int STATUS_PENDING = 0;
    public static final int STATUS_PROCESSING = 1;
    public static final int STATUS_PROCESSED = 2;
    public static final int STATUS_UNSENT = -1;

    public static final int TYPE_WASP = 0;
    public static final int TYPE_NEST = 1;

    public String id;
    public Location location;
    public float lat;
    public float lng;
    public int status = STATUS_UNSENT;
    public String free_text;
    public int type;
    public List<Question> available_questions = null;
    public List<String> answers = null;
    @SerializedName("public")
    public boolean _public;
    public List<Picture> pictures = null;
    public String contact = "Anónimo";

    public String source = "app";

    public Sighting() {
    }

    public void addAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        this.answers.add(answer.id);
    }

    public void deleteAnswer(Answer answer) {
        if (answers == null) {
            answers = new ArrayList<>();
        }

        this.answers.remove(answer.id);
    }
}
