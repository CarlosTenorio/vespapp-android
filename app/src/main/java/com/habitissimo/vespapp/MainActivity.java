package com.habitissimo.vespapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;


import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.async.TaskCallback;

import retrofit2.Call;
import retrofit2.Callback;
import com.habitissimo.vespapp.database.Database;
import com.habitissimo.vespapp.fotos.ConfirmCaptureActivity;
import com.habitissimo.vespapp.fotos.ListaFotos;
import com.habitissimo.vespapp.async.Task;
import com.habitissimo.vespapp.info.Info;

//import com.google.android.gms.maps.MapView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        initButtons();
    }


    /*LinearLayout ll = (LinearLayout) findViewById(R.id.layout_guia_tab);

    for (int i = 1; i <= 5; i++) {
        Button btn = new Button(this);
        btn.setText("button " + i);
        ll.addView(btn);
    }*/

    private void initButtons() {
        final VespappApi api = Vespapp.get(this).getApi();

        final Callback<List<Info>> callback = new Callback<List<Info>>() {
            @Override
            public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                List<Info> infoList = response.body();
                for (Info info : infoList) {
                    System.out.println("Title: " + info.getTitle());
                    System.out.println("Body: " + info.getBody());
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

    private void initCamBtn() {

        ImageButton btn = (ImageButton) findViewById(R.id.btn_cam);
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

    /**
     * Inicializar pestañas
     */
    private void initTabs() {

        TabHost tabs = (TabHost) findViewById(R.id.tabs_main);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("GuiaTab");
        spec.setContent(R.id.layout_guia_tab);
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

        tabs.setCurrentTab(0);
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
