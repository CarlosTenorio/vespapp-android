package com.habitissimo.vespapp.sighting;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.api.VespappApiHelper;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;
import com.habitissimo.vespapp.dialog.LoadingDialog;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSighting {
    public static final String TAG = "AddSighting";

    private final VespappApi api;
    private PicturesActions picturesActions;
    private AlertDialog dialog;

    private Context context;

    public AddSighting(Context context, PicturesActions picturesActions) {
        Vespapp vespapp = Vespapp.get(context);
        api = vespapp.getApi();
        this.context = context;
        this.picturesActions = picturesActions;
    }

    public void onTypeOfSightPressed(int type) {
        showLoading();

        final Sighting sighting = new Sighting();
        sighting.setType(type);
        sighting.setLat(3.69f);
        sighting.setLng(2.69f);

        final Callback<Sighting> callback = new Callback<Sighting>() {
            @Override
            public void onResponse(Call<Sighting> call, Response<Sighting> response) {
                if (response.body() == null) {
                    throw new RuntimeException("Sighting creation call returned null body");
                }
                onSightingCreated(response.body());
            }

            @Override
            public void onFailure(Call<Sighting> call, Throwable t) {
                onSightingCreationError(t);
            }
        };
        Task.doInBackground(new TaskCallback<Sighting>() {
            @Override
            public Sighting executeInBackground() {
                Call<Sighting> call = api.createSighting(sighting);
                call.enqueue(callback);
                return null;
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(Sighting sigthing) {
                callback.onResponse(null, Response.success((Sighting) null));

            }
        });
    }



    private void onSightingCreated(final Sighting sighting) {
        final Callback<Void> callback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onPhotosUploaded(sighting);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onPhotosUploadingError(t);
            }
        };
        Task.doInBackground(new TaskCallback<Void>() {
            @Override
            public Void executeInBackground() {
                try {
                    String sightingId = String.valueOf(sighting.getId());
                    uploadPhotos(sightingId);
                } catch (IOException e1) {
                    throw new RuntimeException("Error uploading photos", e1);
                }
                return null;
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(Void photo) {
                callback.onResponse(null, Response.success((Void) null));

            }
        });
    }

    private void uploadPhotos(String sightingId) throws java.io.IOException {
        for (String picturePath : picturesActions.getList()) {
            Call<Void> call = api.addPhoto(sightingId, VespappApiHelper.buildPhotoApiParameter(new File(picturePath)));
            call.execute();
        }
    }

    private void onSightingCreationError(Throwable t) {
        Log.e(TAG, "Error creating sighting: " + t);
    }

    private void onPhotosUploaded(Sighting sighting) {
        Toast.makeText(context, "Fotos subidas", Toast.LENGTH_LONG).show();
        hideDialog();
        //CuestionarioActivity.launchActivity();
    }

    private void onPhotosUploadingError(Throwable t) {
        Toast.makeText(context, "Error subiendo fotos", Toast.LENGTH_LONG).show();
        hideDialog();
    }



    private void showLoading() {
        dialog = LoadingDialog.show(context, new LoadingDialog.Listener() {
            @Override public void onDialogDismissed() {
                //Put something
            }
        });
    }

    private void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
