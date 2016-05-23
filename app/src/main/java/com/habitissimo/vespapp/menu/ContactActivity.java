package com.habitissimo.vespapp.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.habitissimo.vespapp.R;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_contact);

        initToolbar();

        ((Button)findViewById(R.id.sendButton_contact)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditText name   = (EditText)findViewById(R.id.name_contact);
                EditText subject   = (EditText)findViewById(R.id.subject_contact);
                EditText body   = (EditText)findViewById(R.id.body_contact);
                EditText phone   = (EditText)findViewById(R.id.phone_contact);

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"vespapp.uib@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,  subject.getText().toString());
                i.putExtra(Intent.EXTRA_TEXT   , "Enviado por: "+name.getText().toString()+ " con el número de teléfono: "+ phone.getText().toString() +" y el mensaje: "+body.getText().toString());
                try {
                    startActivity(Intent.createChooser(i, "Escoje la que quieras..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    System.out.println("There are no email clients installed");
                }
            }
        });

    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_menu_contact);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitle));
        toolbar.setBackgroundColor(getResources().getColor(R.color.brandPrimary));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_menu_contact);
    }





}