package com.habitissimo.vespapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;
import com.habitissimo.vespapp.database.Database;
import com.habitissimo.vespapp.info.Info;
import com.habitissimo.vespapp.info.InfoDescriptionActivity;
import com.habitissimo.vespapp.map.Map;
import com.habitissimo.vespapp.menu.ContributorsActivity;
import com.habitissimo.vespapp.menu.AboutUsActivity;
import com.habitissimo.vespapp.menu.ContactActivity;
import com.habitissimo.vespapp.sighting.PicturesActions;
import com.habitissimo.vespapp.sighting.Sighting;
import com.habitissimo.vespapp.sighting.NewSightingDataActivity;
import com.habitissimo.vespapp.sighting.SightingViewActivity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final int TAKE_CAPTURE_REQUEST = 0;
    private static final int PICK_IMAGE_REQUEST = 1;
    private File photoFile;
    private Map map;
    private Marker marker;
    private HashMap<String, Sighting> relation = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();
        initCamBtn();
        initSelectPicturesBtn();
    }

    private void initTabs() {
        final TabHost tabs = (TabHost) findViewById(R.id.tabs_main);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("GuiaTab");
        spec.setContent(R.id.layout_info_tab);
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_dialog_info, null));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("MainTab");
        spec.setContent(R.id.layout_main_tab);
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_camera, null));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("MapTab");
        spec.setContent(R.id.map);
        spec.setIndicator("Mapa");
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_dialog_map, null));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("MenuTab");
        spec.setContent(R.id.layout_menu_tab);
        spec.setIndicator("", ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_dialog_dialer, null));
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
                    tabs.getTabWidget().getChildAt(3).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

                    getInfo();
                } else if (i == 1) {
                    if (map != null) {
                        map.removeMap();
                    }
                    LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                    ll.removeAllViews();

                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.orange));
                    tabs.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(2).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(3).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

                } else if (i == 2) {
                    LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                    ll.removeAllViews();

                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.orange));
                    tabs.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(1).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(3).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

                    initMap();
                } else if (i == 3) {
                    if (map != null) {
                        map.removeMap();
                    }
                    LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                    ll.removeAllViews();

                    tabs.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.orange));
                    tabs.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(1).setBackgroundColor(getResources().getColor(R.color.brandPrimary));
                    tabs.getTabWidget().getChildAt(2).setBackgroundColor(getResources().getColor(R.color.brandPrimary));

                    initMenuOptions();
                }
            }
        });
    }

    private void initCamBtn() {
        ImageButton btn = (ImageButton) findViewById(R.id.btn_cam);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    takePhoto();
                } catch (IOException e) {
                }
            }
        });
    }

    private void initMenuOptions() {
        ((RelativeLayout) findViewById(R.id.layout_menu_tab_contact)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(i);
            }
        });
        ((RelativeLayout) findViewById(R.id.layout_menu_tab_about_us)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AboutUsActivity.class);
                startActivity(i);
            }
        });
        ((RelativeLayout) findViewById(R.id.layout_menu_tab_contributors)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContributorsActivity.class);
                startActivity(i);
            }
        });
    }

    private void initSelectPicturesBtn() {
        Button btn = (Button) findViewById(R.id.btn_selFotos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
    }


    public void takePhoto() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = PicturesActions.createImageFile();
        savePicturePathToDatabase(photoFile.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent, TAKE_CAPTURE_REQUEST);
    }

    public void selectPicture() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    private void getInfo() {
        final VespappApi api = Vespapp.get(this).getApi();

        final Callback<List<Info>> callback = new Callback<List<Info>>() {
            @Override
            public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                final List<Info> infoList = response.body();
                for (final Info info : infoList) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                    params.setMargins(15, 50, 20, 20);
                    Button btn = new Button(getApplicationContext());
                    btn.setText(info.getTitle());
                    btn.setBackgroundResource(R.drawable.button_selector);
                    btn.setTextSize((int) getResources().getDimension(R.dimen.text_info_button));
                    btn.setLayoutParams(params);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(), InfoDescriptionActivity.class);
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

    private void initMap() {
        final VespappApi api = Vespapp.get(this).getApi();

        final GoogleMap Gmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        Gmap.setMyLocationEnabled(false);
        map = new Map(Gmap);


        Gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Sighting s = getSightingByMarker(marker);
                changeActivityToSightingView(s);
                return false;
            }
        });


        final Callback<List<Sighting>> callback = new Callback<List<Sighting>>() {
            @Override
            public void onResponse(Call<List<Sighting>> call, Response<List<Sighting>> response) {
                List<Sighting> sightingList = response.body();
                for (Sighting sighting : sightingList) {
                    if (sighting.is_public()) {
                        LatLng myLocation = new LatLng(sighting.getLat(), sighting.getLng());
                        marker = Gmap.addMarker(new MarkerOptions().position(myLocation));
                        relation.put(marker.getId(), sighting);
                    }
                }
                double lat = 39.56;
                double lng = 2.62;
                int zoom = 8;
                map.moveCamera(lat, lng, zoom);
            }

            @Override
            public void onFailure(Call<List<Sighting>> call, Throwable t) {
                System.out.println("onFailure " + t);
            }
        };
        Task.doInBackground(new TaskCallback<List<Sighting>>() {
            @Override
            public List<Sighting> executeInBackground() {
                Call<List<Sighting>> call = api.getSightings();
                call.enqueue(callback);
                return null;
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(List<Sighting> sightings) {
                callback.onResponse(null, Response.success((List<Sighting>) null));

            }
        });
    }

    private void changeActivityToSightingView(Sighting sighting) {
        Intent i = new Intent(this, SightingViewActivity.class);
        i.putExtra("sightingObject", sighting);
        startActivity(i);
    }

    private Sighting getSightingByMarker(Marker m) {
        Sighting sighting = relation.get(m.getId());
        return sighting;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String picturePath = null;

            switch (requestCode) {
                case TAKE_CAPTURE_REQUEST:
                    picturePath = getPicturePathFromDatabase();
                    //photoFile = new File(picturePath);
                    //picturesActions.resize(photoFile, 640, 480);
                    break;
                case PICK_IMAGE_REQUEST:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    break;
            }

            savePictureToDatabase(picturePath);
            Intent i = new Intent(this, NewSightingDataActivity.class);
            startActivity(i);
        }
    }

    private void savePicturePathToDatabase(String picturePath) {
        Database.get(this).save(Constants.KEY_CAPTURE, picturePath);
    }

    private String getPicturePathFromDatabase() {
        return Database.get(this).load(Constants.KEY_CAPTURE);
    }

    private void savePictureToDatabase(String picturePath) {
        PicturesActions picturesActions = new PicturesActions();
        picturesActions.getList().add(picturePath);
        Database.get(this).save(Constants.PICTURES_LIST, picturesActions);
    }
}
