package com.habitissimo.vespapp.sighting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class SightingViewActivity extends AppCompatActivity {

    public static final String TAG = "SightingViewActivity";

    private Sighting sighting;

    private Map map;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighting_view);

        initToolbar();

        Intent i = getIntent();
        sighting = (Sighting) i.getSerializableExtra("sightingObject");

        initTabs();
    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sigthing_view);
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

    private void initTabs() {
        final TabHost tabs = (TabHost) findViewById(R.id.tabs_sighting_view);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("GuiaTab");
        spec.setContent(R.id.layout_pictures_sighting_tab);
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(),android.R.drawable.ic_dialog_info, null));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("MainTab");
        spec.setContent(R.id.layout_info_sighting_tab);
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_menu_camera, null));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("MapTab");
        spec.setContent(R.id.map);
        spec.setIndicator("Mapa");
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_dialog_map, null));
        tabs.addTab(spec);

        //Add color initial
        tabs.getTabWidget().getChildAt(1).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

        tabs.setCurrentTab(1);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                int i = tabs.getCurrentTab();
                if (i == 0) {
                    if (map != null) {
                        map.removeMap();
                    }
                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.orange));
                    tabs.getTabWidget().getChildAt(1).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(2).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    getInfo();
                } else if (i == 1) {
                    LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                    //ll.removeAllViews();

                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.orange));
                    tabs.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(2).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

                    if (map != null) {
                        map.removeMap();
                    }
                } else if (i == 2) {
                    LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                    //ll.removeAllViews();

                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.orange));
                    tabs.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(1).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

                    initMap();
                }
            }
        });
    }

    private void initMap() {
        final VespappApi api = Vespapp.get(this).getApi();

        final GoogleMap Gmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        Gmap.setMyLocationEnabled(true);
        map = new Map(Gmap);

        LatLng myLocation = new LatLng(sighting.getLat(), sighting.getLng());
        marker= Gmap.addMarker(new MarkerOptions().position(myLocation));
        double lat = 39.56;
        double lng = 2.62;
        map.moveCamera(lat, lng);
    }


    private void getInfo() {

    }



}
