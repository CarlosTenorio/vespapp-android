package com.habitissimo.vespapp.questions;

import java.io.Serializable;
import java.util.List;

/**
 * Created by archi on 11/03/16.
 */
public class Question implements Serializable{

    private static final int TYPE_RADIO = 1;
    private static final int TYPE_CHECKBOX = 2;

    private int id;
    private List<Answer> answers;
    private List<Answer> available_answers;
    private String title;

    private int question_type;

    public Question(int id, List<Answer> answers, List<Answer> available_answers, String title, int question_type) {
        this.id = id;
        this.answers = answers;
        this.available_answers = available_answers;
        this.title = title;
        this.question_type = question_type;
    }

    public boolean isCheckBox() {
        return question_type == TYPE_CHECKBOX;
    }

    public int getId() {
        return id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public List<Answer> getAvailable_answers() {
        return available_answers;
    }

    public String getTitle() {
        return title;
    }

    public int getQuestion_type() {
        return question_type;
    }

    public boolean isRadioButton() {
        return question_type == TYPE_RADIO;
    }
}
