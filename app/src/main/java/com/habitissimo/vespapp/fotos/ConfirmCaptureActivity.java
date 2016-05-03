package com.habitissimo.vespapp.fotos;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.habitissimo.vespapp.Constants;
import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.database.Database;
import com.habitissimo.vespapp.dialog.LoadingDialog;
import com.habitissimo.vespapp.dialog.LoadingDialog.Listener;
import com.habitissimo.vespapp.fotos.interactor.AddPhotosToSighting;
import com.habitissimo.vespapp.questions.Sighting;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmCaptureActivity extends AppCompatActivity implements LocationListener {

    public static final String TAG = "ConfirmCaptureActivity";
    private AlertDialog dialog;

    private LocationManager locationManager;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_cap);
        ButterKnife.bind(this);

        initToolbar();
        initAlbum();
        initAddBtn();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_confirm_cap);
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

    private void initAddBtn() {
        Button btn = (Button) findViewById(R.id.btn_añadir_foto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_nido) void onNidoPressed() {
        onTypeOfSightPressed(Sighting.TYPE_NEST);
    }

    @OnClick(R.id.btn_avispa) void onAvispaPressed() {
        onTypeOfSightPressed(Sighting.TYPE_WASP);
    }

    private void onTypeOfSightPressed(int type) {
        showLoading();
        Sighting sighting = new Sighting();
        sighting.type = type;
        sighting.lat = location == null ? 1 : (float) location.getLatitude();
        sighting.lng = location == null ? 2 : (float) location.getLongitude();
        Call<Sighting> call = Vespapp.get(this).getApi().createSighting(sighting);
        call.enqueue(new Callback<Sighting>() {
            @Override public void onResponse(Call<Sighting> call, Response<Sighting> response) {
                if (response.body() == null) {
                    throw new RuntimeException("Sighting creation call returned null body");
                }

                onSightingCreated(response.body());
            }

            @Override public void onFailure(Call<Sighting> call, Throwable t) {
                onSightingCreationError(t);
            }
        });
    }

    private void showLoading() {
        dialog = LoadingDialog.show(this, new Listener() {
            @Override public void onDialogDismissed() {
                //Put something
            }
        });
    }

    private void onSightingCreated(final Sighting sighting) {
        ListaFotos listaFotos = getListaFotos();
        new AddPhotosToSighting(this).execute(new AddPhotosToSighting.Arguments(sighting, listaFotos), new Callback<Void>() {
            @Override public void onResponse(Call<Void> call, Response<Void> response) {
                onPhotosUploaded(sighting);
            }

            @Override public void onFailure(Call<Void> call, Throwable t) {
                onPhotosUploadingError(t);
            }
        });
    }

    private void onPhotosUploaded(Sighting sighting) {
        Log.i(TAG, "Photos of sighting " + sighting.id + " uploaded");
        Toast.makeText(this, "Fotos subidas", Toast.LENGTH_LONG).show();
        hideDialog();
        //TODO:
//        CuestionarioActivity.launchActivity();
    }

    private void onPhotosUploadingError(Throwable t) {
        hideDialog();
        Log.e(TAG, "Error uploading photos: " + t);
        Toast.makeText(this, "Error subiendo fotos", Toast.LENGTH_LONG).show();
    }

    private void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void onSightingCreationError(Throwable t) {
        Log.e(TAG, "Error creating sighting: " + t);
    }

    private void initAlbum() {
        // obtener lista del database
        ListaFotos listaFotos = getListaFotos();

        // inicializar recicleview
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycle_fotos);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(this, listaFotos.getLista());
        rView.setAdapter(rcAdapter);
    }

    private ListaFotos getListaFotos() {
        return Database.get(this).load(Constants.FOTOS_LIST, ListaFotos.class);
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Toast.makeText(ConfirmCaptureActivity.this, "Latitude:" + location.getLatitude()
                + ", Longitude:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
