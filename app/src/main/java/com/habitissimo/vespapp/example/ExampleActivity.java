package com.habitissimo.vespapp.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.habitissimo.vespapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExampleActivity extends AppCompatActivity implements ExampleView {
    @Bind(R.id.title_text) TextView titleText;
    private ExampleController controller;

    @OnClick(R.id.title_button) void onTitleButtonPressed() {
        controller.onClickMePressed();
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        ButterKnife.bind(this);
        controller = new ExampleController();
        controller.takeView(this);
        controller.onLoad("Argumento");
    }

    @Override public String title() {
        return titleText.getText().toString();
    }

    @Override public void title(String title) {
        titleText.setText(title);
    }
}
