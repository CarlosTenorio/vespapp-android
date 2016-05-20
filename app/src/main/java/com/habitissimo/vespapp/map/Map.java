package com.habitissimo.vespapp.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.habitissimo.vespapp.sighting.Sighting;
import com.habitissimo.vespapp.sighting.SightingViewActivity;

import java.util.HashMap;

public class Map {

    private GoogleMap Gmap;

    public Map(GoogleMap Gmap){
        this.Gmap=Gmap;
    }


   /* public void getCurrentPosition() {
        // Search my position
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                //Toast.makeText(getApplicationContext(), "Gps enabled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                //Toast.makeText(getApplicationContext(), "Gps disabled.", Toast.LENGTH_SHORT).show();
            }
        };
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        locManager.requestLocationUpdates(locationProvider, 0, 0, locListener);

        // Display my position in the map
        Location currentLocation = locManager.getLastKnownLocation(locationProvider);
        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        CameraPosition camPos = new CameraPosition.Builder().target(myLocation).zoom(14).build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

        Gmap.animateCamera(camUpd3);
        Gmap.addMarker(new MarkerOptions().position(myLocation));
    }*/


    public void moveCamera(double lat, double lng, int zoom) {
        //First Vision, center on teh Balearic Islands
        LatLng cameraFocus = new LatLng(lat,lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(cameraFocus, zoom);
        Gmap.animateCamera(cameraUpdate);
    }


    public void removeMap() {
        Gmap.clear();
    }
}