package com.habitissimo.vespapp.sighting;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
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
import com.habitissimo.vespapp.map.Map;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTitle));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.confirm_cap_map_sight);
    }

    private void initTabs() {
        final TabHost tabs = (TabHost) findViewById(R.id.tabs_sighting_view);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("PicsTab");
        spec.setContent(R.id.layout_pictures_sighting_tab);
        spec.setIndicator("Fotos");

        tabs.addTab(spec);

        spec = tabs.newTabSpec("MainTab");
        spec.setContent(R.id.layout_info_sighting_tab);
        spec.setIndicator("Info");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("MapTab");
        spec.setContent(R.id.layout_map_sighting_tab);
        spec.setIndicator("Mapa");
        tabs.addTab(spec);

        //Add color initial
        tabs.getTabWidget().getChildAt(1).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

        //Set color text
        int totalTabs = tabs.getTabWidget().getTabCount();

        for (int i=0; i<totalTabs; i++){
            TextView tv = (TextView) tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.colorTitle));
        }

        tabs.setCurrentTab(1);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                int i = tabs.getCurrentTab();
                if (i == 0) {
                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.orange));
                    tabs.getTabWidget().getChildAt(1).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(2).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

                } else if (i == 1) {
                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.orange));
                    tabs.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(2).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

                } else if (i == 2) {
                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.orange));
                    tabs.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(1).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

                }
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void getInfo() {
        TextView lSource = (TextView) findViewById(R.id.sighting_source_label);
        lSource.setText("Notificado por:");
        TextView tSource = (TextView) findViewById(R.id.sighting_source);
        String source = sighting.getSource();
        tSource.setText(source);

        if (source.equals("web")) {
            ImageView myImage = (ImageView) findViewById(R.id.sighting_source_image);
            myImage.setImageResource(R.mipmap.ic_computer);
        } else if (source.equals("app")) {
            ImageView myImage = (ImageView) findViewById(R.id.sighting_source_image);
            myImage.setImageResource(R.mipmap.ic_movile);
        } else {
            ImageView myImage = (ImageView) findViewById(R.id.sighting_source_image);
            myImage.setImageResource(R.mipmap.ic_computer);
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
        } else if(type == 2){
            tType.setText(String.valueOf("Nido"));
        }else {
            tType.setText("-");
        }


        TextView lStatus = (TextView) findViewById(R.id.sighting_status_label);
        lStatus.setText("Estado:");
        TextView tStatus = (TextView) findViewById(R.id.sighting_status);
        int status = sighting.getStatus();
        if (status == 0) {
            tStatus.setText(String.valueOf("Pendiente"));
            tStatus.setBackgroundColor(getResources().getColor(R.color.statusPending));
        } else if (status == 1) {
            tStatus.setText(String.valueOf("Procesando"));
            tStatus.setBackgroundColor(getResources().getColor(R.color.statusProcessing));
        } else if (status == 2) {
            tStatus.setText(String.valueOf("Procesado"));
            tStatus.setBackgroundColor(getResources().getColor(R.color.statusValidated));
        }

        TextView lResult = (TextView) findViewById(R.id.sighting_result_label);
        lResult.setText("Resultado:");
        TextView tResult = (TextView) findViewById(R.id.sighting_result);
        Boolean result = sighting.is_valid();
        if (result == null) {
            tResult.setText(String.valueOf("Desconocido"));
            tResult.setBackgroundColor(getResources().getColor(R.color.resultUnknown));
        } else if (result == false) {
            tResult.setText(String.valueOf("Negativo"));
            tResult.setBackgroundColor(getResources().getColor(R.color.resultNo));
        } else if (result == true) {
            tResult.setText(String.valueOf("Positivo"));
            tResult.setBackgroundColor(getResources().getColor(R.color.resultYes));
        }

        TextView lData = (TextView) findViewById(R.id.sighting_data_label);
        lData.setText("Fecha:");
        TextView tData = (TextView) findViewById(R.id.sighting_data);
        tData.setText(parseDateToddMMyyyy(sighting.getCreated_at()));


        TextView lDescription = (TextView) findViewById(R.id.sighting_description_label);
        lDescription.setText("Descripción:");
        TextView tDescription = (TextView) findViewById(R.id.sighting_description);
        if(!sighting.getFree_text().isEmpty()){
            tDescription.setText(sighting.getFree_text());
        }else{
            tDescription.setText("-");
        }


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

    public String parseDateToddMMyyyy(String time) {
        System.out.println(time);
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String outputPattern = "dd-MMM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    private void printPictures(List<Picture> picturesList) {
        for (Picture picture : picturesList) {
            addItemList(picture);
        }
    }

    private void addItemList(final Picture picture) {
        Bitmap bitmap = null;
        ImageView imageInfo = null;
        try {
            LinearLayout ll = (LinearLayout) findViewById(R.id.layout_pictures_sighting_tab);

            imageInfo = new ImageView(this);
            LinearLayout.LayoutParams vp =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
            imageInfo.setLayoutParams(vp);

            imageInfo.setAdjustViewBounds(true); //Adjust the height to size photo
            imageInfo.setCropToPadding(true);
            vp.setMargins(0, 35, 0, 0); //(left, top, right, bottom);

            bitmap = BitmapFactory.decodeStream((InputStream) new URL(picture.getFile()).getContent());
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int widthDisplay = size.x;
            int heightDisplay = size.y;
            double heightFinal = widthDisplay*0.5;
            int i =  (int) heightFinal;

            bitmap = resizeBitmap(bitmap, widthDisplay, i);
            imageInfo.setImageBitmap(bitmap);

            ll.addView(imageInfo);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    private void initMap() {
        final GoogleMap Gmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.layout_map_sighting_tab)).getMap();
        Gmap.setMyLocationEnabled(false);
        map = new Map(Gmap);

        double lat = sighting.getLat();
        double lng = sighting.getLng();
        int zoom = 15;

        LatLng myLocation = new LatLng(lat, lng);
        marker = Gmap.addMarker(new MarkerOptions().position(myLocation));
        map.moveCamera(lat, lng, zoom);
    }
}
