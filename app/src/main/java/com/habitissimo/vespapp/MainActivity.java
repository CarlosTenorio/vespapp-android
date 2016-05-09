package com.habitissimo.vespapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.async.TaskCallback;

import retrofit2.Call;
import retrofit2.Callback;
import com.habitissimo.vespapp.database.Database;
import com.habitissimo.vespapp.fotos.ConfirmCaptureActivity;
import com.habitissimo.vespapp.fotos.ListaFotos;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.info.Info;
import com.habitissimo.vespapp.info.InfoDescription;

//import com.google.android.gms.maps.MapView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final int TAKE_CAPTURE_REQUEST = 0;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ListaFotos lista;
    private File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            lista = Database.get(this).load(Constants.FOTOS_LIST, ListaFotos.class);
        } catch (Exception e) {
            lista = new ListaFotos();
        }

        initTabs();
        initCamBtn();
        initSelFotosBtn();
        getCurrentPosition();
    }

    private void getCurrentPosition() {
        GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);

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
                Toast.makeText(getApplicationContext(), "Gps enabled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(), "Gps disabled.", Toast.LENGTH_SHORT).show();
            }
        };
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        locManager.requestLocationUpdates(locationProvider, 0, 0, locListener);

        // Display my position in the map
        Location currentLocation = locManager.getLastKnownLocation(locationProvider);
        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        CameraPosition camPos = new CameraPosition.Builder().target(myLocation).zoom(14).build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);

        map.animateCamera(camUpd3);
        map.addMarker(new MarkerOptions().position(myLocation));
    }

    private void initCamBtn() {
        Button btn = (Button) findViewById(R.id.btn_cam);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    takePhoto();
                } catch (IOException e) {
                    Log.e(TAG, "Could not take photo: " + e);
                }
            }
        });
    }

    private void takePhoto() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = createImageFile();
        Database.get(this).save(Constants.KEY_CAPTURE, photoFile.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent, TAKE_CAPTURE_REQUEST);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    private void initTabs() {
        final TabHost tabs = (TabHost) findViewById(R.id.tabs_main);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("GuiaTab");
        spec.setContent(R.id.layout_info_tab);
        spec.setIndicator("Guía");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("MainTab");
        spec.setContent(R.id.layout_main_tab);
        spec.setIndicator("Haz una foto");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("MapTab");
        spec.setContent(R.id.map);
        spec.setIndicator("Mapa");
        tabs.addTab(spec);

        tabs.setCurrentTab(1);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                int i = tabs.getCurrentTab();
                if (i == 0) {
                    getInfo();
                } else if (i == 1) {
                    LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                    ll.removeAllViews();
                } else if (i == 2) {
                    LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                    ll.removeAllViews();

                }
            }
        });
    }

    private void initSelFotosBtn() {
        Button btn = (Button) findViewById(R.id.btn_selFotos);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE_REQUEST);
            }
        });
    }

    private void getInfo() {
        final VespappApi api = Vespapp.get(this).getApi();

        final Callback<List<Info>> callback = new Callback<List<Info>>() {
            @Override
            public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                LinearLayout ll = (LinearLayout) findViewById(R.id.layout_info_tab);
                final List<Info> infoList = response.body();
                for (final Info info : infoList) {
                    Button btn = new Button(getApplicationContext());
                    btn.setTextAppearance(getApplicationContext(), R.style.ButtonOrangeRipple);
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
                System.out.println("onError " + t);
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(List<Info> infos) {
                System.out.println("onCompleted " + infos);
                callback.onResponse(null, Response.success((List<Info>) null));

            }
        });
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String picturePath = null;

            switch (requestCode) {
                case TAKE_CAPTURE_REQUEST:
                    picturePath = Database.get(this).load(Constants.KEY_CAPTURE);
                    photoFile = new File(picturePath);
                    resize(photoFile, 640, 480);
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

            Intent i = new Intent(this, ConfirmCaptureActivity.class);
            startActivity(i);
        }
    }

    private void resize(File photo, int width, int height) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photo.getAbsolutePath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / width, photoH / height);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photo.getAbsolutePath(), bmOptions);

        saveBitmapToFile(bitmap, photo);
    }

    private void saveBitmapToFile(Bitmap bitmap, File photo) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(photo.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            throw new RuntimeException("Could not save bitmap into file(" + photo.getAbsolutePath() + "): " + e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Could not save bitmap into file(" + photo.getAbsolutePath() + "): " + e);
            }
        }
    }

    private void savePictureToDatabase(String picturePath) {
        if (lista == null) {
            lista = new ListaFotos();
        } else {
            lista = Database.get(this).load(Constants.FOTOS_LIST, ListaFotos.class);
        }
        lista.getLista().add(picturePath);
        Database.get(this).save(Constants.FOTOS_LIST, lista);
    }
}
