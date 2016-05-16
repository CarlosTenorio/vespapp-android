package com.habitissimo.vespapp.sighting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;

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
        setContentView(R.layout.activity_locations_list);

        initToolbar();

        Intent i = getIntent();
        sighting = (Sighting) i.getSerializableExtra("sightingObject");

        initList();
    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_locations_list);
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

        final Callback<List<Location>> callback = new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                LinearLayout ll = (LinearLayout) findViewById(R.id.layout_locations_list);
                final List<Location> locationList = response.body();
                for (final Location location : locationList) {
                    addItemList(location);
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                System.out.println("onFailure " + t);
            }
        };
        Task.doInBackground(new TaskCallback<List<Location>>() {
            @Override
            public List<Location> executeInBackground() {
                Call<List<Location>> call = api.getLocations();
                call.enqueue(callback);
                return null;
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(List<Location> locations) {
                callback.onResponse(null, Response.success((List<Location>) null));

            }
        });
    }

    private void addItemList(final Location location) {
        LinearLayout locationsList = (LinearLayout) findViewById(R.id.layout_locations_list);
        LinearLayout itemList = (LinearLayout) View.inflate(this, R.layout.location_item_list, null);
        TextView itemText = (TextView) itemList.findViewById(R.id.text_item_list);
        itemText.setText(location.getName());
        locationsList.addView(itemList);

        itemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(location);
            }
        });
    }


    private  void changeActivity(Location location) {
        sighting.setLocation(location.getId());
        sighting.setLat(location.getLat());
        sighting.setLng(location.getLng());

        Intent i = new Intent(this, MapSightingActivity.class);
        i.putExtra("sightingObject", sighting);
        startActivity(i);
    }
}