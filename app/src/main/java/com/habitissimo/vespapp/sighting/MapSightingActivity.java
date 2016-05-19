package com.habitissimo.vespapp.sighting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.habitissimo.vespapp.Constants;
import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.database.Database;
import com.habitissimo.vespapp.info.Info;
import com.habitissimo.vespapp.map.Map;

import java.io.File;

import butterknife.ButterKnife;

public class MapSightingActivity extends AppCompatActivity implements OnMarkerDragListener {

    public static final String TAG = "MapSightingActivity";

    private Map map;
    private Marker marker;
    private Sighting sighting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighting_map);

        initToolbar();

        Intent i = getIntent();
        sighting = (Sighting) i.getSerializableExtra("sightingObject");

        initMap();
    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sigthing_map);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.confirm_cap_map);

        Button btn_send = (Button) findViewById(R.id.send_button);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSighting();
            }
        });
    }


    private void sendSighting() {
        sighting.setLat((float) marker.getPosition().latitude);
        sighting.setLng((float) marker.getPosition().longitude);
        new AddSighting(this).sendSighting(sighting);
    }

    private void initMap() {
        final GoogleMap Gmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        Gmap.setMyLocationEnabled(true);
        map = new Map(Gmap);

        LatLng position = new LatLng(sighting.getLat(), sighting.getLng());

        marker = Gmap.addMarker(new MarkerOptions()
                .position(position)
                .title("Mantenme pulsado y arrástrame")
                .draggable(true));

        Gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        Gmap.setOnMarkerDragListener(this);

        Gmap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng position) {
                if (marker != null) {
                    marker.remove();
                }
                marker = Gmap.addMarker(new MarkerOptions()
                        .position(position)
                        .title("Mantenme pulsado y arrástrame")
                        .draggable(true)
                        .visible(true));
            }
        });
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

}
