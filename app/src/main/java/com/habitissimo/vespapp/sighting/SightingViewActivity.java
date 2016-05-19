package com.habitissimo.vespapp.sighting;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SightingViewActivity extends AppCompatActivity {

    public static final String TAG = "SightingViewActivity";

    private static Sighting sighting;

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

        getInfo();
        getPictures();
        initMap();
    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sigthing_view);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitulo));
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
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_dialog_info, null));
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
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void getInfo() {

        TextView lSource = (TextView) findViewById(R.id.sighting_source_label);
        lSource.setText("Notificado por:");
        TextView tSource = (TextView) findViewById(R.id.sighting_source);
        String source = sighting.getSource();
        tSource.setText(source);
        if (source.equals("web")) {
            // tSource.setTextAppearance(R.style.computerIcon);
        } else if (source.equals("app")) {
            // tSource.setTextAppearance(R.style.computerIcon);
        } else {
            //tSource.setTextAppearance(R.style.computerIcon);
        }

        TextView lLat = (TextView) findViewById(R.id.sighting_lat_label);
        lLat.setText("Latitud:");
        TextView tLat = (TextView) findViewById(R.id.sighting_lat);
        tLat.setText(String.valueOf(sighting.getLat()));

        TextView lLng = (TextView) findViewById(R.id.sighting_lng_label);
        lLng.setText("Longitud:");
        TextView tLng = (TextView) findViewById(R.id.sighting_lng);
        tLng.setText(String.valueOf(sighting.getLng()));

        TextView lType = (TextView) findViewById(R.id.sighting_type_label);
        lType.setText("Tipo:");
        TextView tType = (TextView) findViewById(R.id.sighting_type);
        int type = sighting.getType();
        if (type == 1) {
            tType.setText(String.valueOf("Avispa"));
//            tType.setTextAppearance(R.style.computerIcon);
        } else {
            tType.setText(String.valueOf("Nido"));
        }

        TextView lStatus = (TextView) findViewById(R.id.sighting_status_label);
        lStatus.setText("Estado:");
        TextView tStatus = (TextView) findViewById(R.id.sighting_status);
        int status = sighting.getStatus();
        if (status == 0) {
            tStatus.setText(String.valueOf("Pendiente"));
        } else if (status == 1) {
            tStatus.setText(String.valueOf("Procesando"));
        } else if (status == 2) {
            tStatus.setText(String.valueOf("Procesado"));
        }

        TextView lResult = (TextView) findViewById(R.id.sighting_result_label);
        lResult.setText("Resultado:");
        TextView tResult = (TextView) findViewById(R.id.sighting_result);
        Boolean result = sighting.is_valid();
        System.out.println("EY" + result);
        if (result == null) {
            tResult.setText(String.valueOf("Desconocido"));
        } else if (result == false) {
            tResult.setText(String.valueOf("Negativo"));
        } else if (result == true) {
            tResult.setText(String.valueOf("Positivo"));
        }

        TextView lData = (TextView) findViewById(R.id.sighting_data_label);
        lData.setText("Fecha:");
        TextView tData = (TextView) findViewById(R.id.sighting_data);
        tData.setText("hue");

        TextView lDescription = (TextView) findViewById(R.id.sighting_description_label);
        lDescription.setText("Descripción:");
        TextView tDescription = (TextView) findViewById(R.id.sighting_description);
        tDescription.setText(sighting.getFree_text());

    }

    private void getPictures() {
        final VespappApi api = Vespapp.get(this).getApi();

        final Callback<List<Picture>> callback = new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                List<Picture> pictureList = response.body();
                sighting.setPictures(pictureList);
                printPictures(pictureList);
            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {
                System.out.println("onFailure " + t);
            }
        };
        Task.doInBackground(new TaskCallback<List<Picture>>() {
            @Override
            public List<Picture> executeInBackground() {
                Call<List<Picture>> call = api.getPhotos(String.valueOf(sighting.getId()));
                call.enqueue(callback);
                return null;
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(List<Picture> locations) {
                callback.onResponse(null, Response.success((List<Picture>) null));

            }
        });
    }


    private void printPictures(List<Picture> picturesList) {
        for (Picture picture : picturesList) {
            addItemList(picture);
        }
    }

    private void addItemList(final Picture picture) {
        try {
            LinearLayout ll = (LinearLayout) findViewById(R.id.layout_pictures_sighting_tab);

            ImageView imageInfo = new ImageView(this);
            LinearLayout.LayoutParams vp =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
            imageInfo.setLayoutParams(vp);

            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(picture.getFile()).getContent());
            imageInfo.setImageBitmap(bitmap);

            ll.addView(imageInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMap() {
        final GoogleMap Gmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        Gmap.setMyLocationEnabled(true);
        map = new Map(Gmap);

        double lat = sighting.getLat();
        double lng = sighting.getLng();

        LatLng myLocation = new LatLng(lat, lng);
        marker = Gmap.addMarker(new MarkerOptions().position(myLocation));
        map.moveCamera(lat, lng);
    }
}
