package com.habitissimo.vespapp.sighting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.habitissimo.vespapp.Constants;
import com.habitissimo.vespapp.MainActivity;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.api.VespappApiHelper;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.async.TaskCallback;
import com.habitissimo.vespapp.database.Database;
import com.habitissimo.vespapp.dialog.LoadingDialog;
import com.habitissimo.vespapp.questions.Question;
import com.habitissimo.vespapp.questions.QuestionsActivity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewSighting {

    private final VespappApi api;
    private AlertDialog dialog;

    private Context context;


    public AddNewSighting(Context context) {
        api = Vespapp.get(context).getApi();
        this.context = context;
    }

    public void sendSighting(final Sighting sighting) {
        showLoading();

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



    private void onSightingCreated(Sighting sighting) {
        uploadPhotosToDatabase(sighting);
        getQuestionFromDatabase(sighting);
    }


    private void uploadPhotosToDatabase(final Sighting sighting) {
        final Callback<Void> callback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                onPhotosUploaded();
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
                    uploadPhotos(sightingId, callback);
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

    private void uploadPhotos(String sightingId, Callback callback) throws java.io.IOException {
        PicturesActions picturesActions = getPicturesList();

        for (String picturePath : picturesActions.getList()) {
            Call<Void> call = api.addPhoto(sightingId, VespappApiHelper.buildPhotoApiParameter(new File(picturePath)));
            call.enqueue(callback);
        }
    }

    private PicturesActions getPicturesList() {
        return Database.get(context).load(Constants.PICTURES_LIST, PicturesActions.class);
    }

    private void onSightingCreationError(Throwable t) {
        Toast.makeText(context, "Error al subir avistamiento", Toast.LENGTH_SHORT).show();
    }

    private void onPhotosUploaded() {
        Toast.makeText(context, "Fotos subidas", Toast.LENGTH_SHORT).show();
        hideDialog();
    }

    private void onPhotosUploadingError(Throwable t) {
        Toast.makeText(context, "Error subiendo fotos", Toast.LENGTH_SHORT).show();
        hideDialog();
    }




    private void getQuestionFromDatabase(final Sighting sighting) {
        final Callback<List<Question>> callback = new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                List<Question> questionsList = new ArrayList<>();

                List<Question> list = response.body();
                for (Question question : list) {
                    if (sighting.getType() == question.getSighting_type() && question.is_active()) {
                        questionsList.add(question);
                    }
                }
                hideDialog();
                if (questionsList.isEmpty()) {
                    goToMainActivity();
                } else {
                    Intent i = new Intent(context, QuestionsActivity.class);
                    i.putExtra("sightingObject", sighting);
                    i.putExtra("questionsList", (Serializable) questionsList);
                    context.startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                System.out.println("onFailure " + t);
            }
        };
        Task.doInBackground(new TaskCallback<List<Question>>() {
            @Override
            public List<Question> executeInBackground() {
                Call<List<Question>> call = api.getQuestions(String.valueOf(sighting.getId()));
                call.enqueue(callback);
                return null;
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(null, t);
            }

            @Override
            public void onCompleted(List<Question> questions) {
                callback.onResponse(null, Response.success((List<Question>) null));

            }
        });
    }


    private void goToMainActivity() {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
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
