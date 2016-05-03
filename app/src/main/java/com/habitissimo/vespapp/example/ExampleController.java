package com.habitissimo.vespapp.example;

import com.habitissimo.vespapp.base.Controller;
import com.habitissimo.vespapp.database.Database;

public class ExampleController extends Controller<ExampleView, String> {
    private static final String KEY_PRESS_COUNT = "pressed_count";

    private String argument;

    public void onClickMePressed() {
        Database database = Database.get(this);

        String emoticon = getView().title().contains(":D") ? "D:" : ":D";

        //Ejemplo de persistencia de datos usando Database
        int nextInt = database.loadInt(KEY_PRESS_COUNT) + 1;
        showTitle(argument + " " + emoticon + " " + nextInt);
        database.saveInt(KEY_PRESS_COUNT, nextInt);
    }

    private void showTitle(String title) {
        getView().title(title);
    }

    @Override public void onLoad(String argument) {
        this.argument = argument;
        showTitle("");
    }
}
