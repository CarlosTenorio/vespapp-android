package com.habitissimo.vespapp.questions;

import java.util.List;

/**
 * Created by archi on 11/03/16.
 */
public class Question {

    public static final int TYPE_RADIO = 1;
    public static final int TYPE_CHECKBOX = 2;

    public String id;
    public List<Answer> answers;
    public String title;
    public int question_type;

    public Question(String id, List<Answer> answers, String title, int question_type) {
        this.id = id;
        this.answers = answers;
        this.title = title;
        this.question_type = question_type;
    }

    public boolean isCheckBox() {
        return question_type == 2;
    }
}
