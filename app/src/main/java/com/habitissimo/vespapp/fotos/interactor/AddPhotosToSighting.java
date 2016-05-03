package com.habitissimo.vespapp.fotos.interactor;

import android.content.Context;

import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.api.VespappApiHelper;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;
import com.habitissimo.vespapp.fotos.ListaFotos;
import com.habitissimo.vespapp.questions.Sighting;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPhotosToSighting {
    private final VespappApi api;

    public AddPhotosToSighting(Context context) {
        Vespapp vespapp = Vespapp.get(context);
        api = vespapp.getApi();
    }

    public void execute(final Arguments arguments, final Callback<Void> callback) {
        final String sightingId = arguments.sighting.id;

        Task.doInBackground(new TaskCallback<Void>() {
            @Override public Void executeInBackground() {
                try {
                    uploadPhotos(arguments, sightingId);
                } catch (IOException e1) {
                    throw new RuntimeException("Error uploading photos", e1);
                }
                return null;
            }

            @Override public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override public void onCompleted(Void aVoid) {
                callback.onResponse(null, Response.success((Void) null));
            }
        });
    }

    private void uploadPhotos(Arguments arguments, String sightingId) throws java.io.IOException {
        for (String photoPath : arguments.listaFotos.getLista()) {
            Call<Void> call = api.addPhoto(sightingId, VespappApiHelper.buildPhotoApiParameter(new File(photoPath)));
            call.execute();
        }
    }

    public static class Arguments {
        public final Sighting sighting;
        public final ListaFotos listaFotos;

        public Arguments(Sighting sighting, ListaFotos listaFotos) {
            this.sighting = sighting;
            this.listaFotos = listaFotos;
        }
    }
}
