package com.habitissimo.vespapp.sighting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.dialog.LoadingDialog;
import com.habitissimo.vespapp.map.Map;

public class NewSightingMapActivity extends AppCompatActivity implements OnMarkerDragListener {

    public static final String TAG = "NewSightingMapActivity";

    private Map map;
    private Marker marker;
    private Sighting sighting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sighting_map);

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
        new AddNewSighting(this).sendSighting(sighting);
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
                marker = moveMarker(Gmap, marker, position);
            }
        });


        Gmap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng myPosition = getCurrentPosition();
                if (myPosition != null) {
                    marker = moveMarker(Gmap, marker, myPosition);

                    CameraPosition camPos = new CameraPosition.Builder().target(myPosition).zoom(14).build();
                    CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

                    Gmap.animateCamera(camUpd3);
                    return true;
                }
                return false;
            }
        });
    }


    private Marker moveMarker(GoogleMap Gmap, Marker marker, LatLng position) {
        if (marker != null) {
            marker.remove();
        }
        marker = Gmap.addMarker(new MarkerOptions()
                .position(position)
                .title("Mantenme pulsado y arrástrame")
                .draggable(true)
                .visible(true));

        return marker;
    }


    public LatLng getCurrentPosition() {
        // Search my position
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Display my position in the map
        Criteria criteria = new Criteria();
        String locProvider = locManager.getBestProvider(criteria, false);
        Location currentLocation = locManager.getLastKnownLocation(locProvider);

        // getting GPS status
        boolean isGPSEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        boolean isNWEnabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNWEnabled) {
            showGPSDialog();
            return null;
        } else {
            // First get location from Network Provider
            if (isNWEnabled) {
                if (locManager != null) {
                    System.out.println("internet");
                    currentLocation = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }

            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                if (currentLocation == null) {
                    if (locManager != null) {
                        System.out.println("gps");
                        currentLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }
            if (currentLocation == null) {
                showToastConnectingGPS();
                return null;
            }
            LatLng myPosition = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            return myPosition;
        }
    }


    private void showGPSDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS está desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void showToastConnectingGPS() {
        Toast.makeText(this, "Conectando con GPS. Inténtalo de nuevo en unos segundos", Toast.LENGTH_SHORT).show();
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
