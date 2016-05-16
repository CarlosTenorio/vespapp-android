package com.habitissimo.vespapp.sighting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.Marker;
import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;
import com.habitissimo.vespapp.info.Info;
import com.habitissimo.vespapp.info.InfoDescription;
import com.habitissimo.vespapp.map.Map;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationsListActivity extends AppCompatActivity {

    public static final String TAG = "LocationsListActivity";

    private Sighting sighting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighting_map);

        initToolbar();

        Intent i = getIntent();
        sighting = (Sighting) i.getSerializableExtra("sightingObject");
    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sigthing_map);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitulo));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.confirm_cap_titulo);
    }

    private void initList(){
        final VespappApi api = Vespapp.get(this).getApi();

        final Callback<List<Info>> callback = new Callback<List<Info>>() {
            @Override
            public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                final List<Info> infoList = response.body();
                for (final Info info : infoList) {
                    Button btn = new Button(getApplicationContext());
                    btn.setTextAppearance(getApplicationContext(), R.style.camera_button);
                    btn.setText(info.getTitle());
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(), InfoDescription.class);
                            i.putExtra("infoObject", info);
                            startActivity(i);
                        }
                    });
                    ll.addView(btn);
                }
            }

            @Override
            public void onFailure(Call<List<Info>> call, Throwable t) {
                System.out.println("onFailure " + t);
            }
        };
        Task.doInBackground(new TaskCallback<List<Info>>() {
            @Override
            public List<Info> executeInBackground() {
                Call<List<Info>> call = api.getInfo();
                call.enqueue(callback);
                return null;
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(List<Info> infos) {
                callback.onResponse(null, Response.success((List<Info>) null));

            }
        });
    }

}
