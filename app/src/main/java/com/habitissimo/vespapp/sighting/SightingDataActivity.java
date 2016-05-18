package com.habitissimo.vespapp.sighting;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.habitissimo.vespapp.Constants;
import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.database.Database;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SightingDataActivity extends AppCompatActivity {

    public static final String TAG = "SightingDataActivity";

    private static final int TAKE_CAPTURE_REQUEST = 0;
    private static final int PICK_IMAGE_REQUEST = 1;
    private File photoFile;
    private PicturesActions picturesActions;

    RecyclerViewAdapter rcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighting_data);
        ButterKnife.bind(this);

        initToolbar();
        initAlbum();
        initAddBtn();
        initSelectBtn();
    }

    private void initToolbar() {
        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sigthing_data);
        setSupportActionBar(toolbar);
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

    private void initAlbum() {
        picturesActions = getPicturesList();

        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycle_pictures);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(gridLayout);

        rcAdapter = new RecyclerViewAdapter(this, picturesActions.getList());
        rView.setAdapter(rcAdapter);
    }

    private void initAddBtn() {
        Button btn = (Button) findViewById(R.id.btn_add_picture);
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

    private void initSelectBtn() {
        Button btn = (Button) findViewById(R.id.btn_select_picture);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
    }

    public void takePhoto() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = picturesActions.createImageFile();
        savePicturePathToDatabase(photoFile.getAbsolutePath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(intent, TAKE_CAPTURE_REQUEST);
    }

    public void selectPicture() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @OnClick(R.id.btn_nido)
    void onNidoPressed() {
        onTypeOfSightPressed(Sighting.TYPE_NEST);
    }

    @OnClick(R.id.btn_avispa)
    void onAvispaPressed() {
        onTypeOfSightPressed(Sighting.TYPE_WASP);
    }



    public void onTypeOfSightPressed(int type) {
        Sighting sighting = new Sighting();
        sighting.setType(type);
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String userEmail = account.name;
                sighting.setContact(userEmail);
            }
        }
        Intent i = new Intent(this, LocationsListActivity.class);
        i.putExtra("sightingObject", sighting);
        startActivity(i);
    }



    private PicturesActions getPicturesList() {
        return Database.get(this).load(Constants.FOTOS_LIST, PicturesActions.class);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

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

                    Intent i = getIntent();
                    finish();
                    startActivity(i);
                    break;
            }
            savePictureToDatabase(picturePath);
        }
    }

    public void savePicturePathToDatabase(String picturePath) {
        Database.get(this).save(Constants.KEY_CAPTURE, picturePath);
    }

    public String getPicturePathFromDatabase() {
        return Database.get(this).load(Constants.KEY_CAPTURE);
    }

    public void savePictureToDatabase(String picturePath) {
        if (picturesActions == null) {
            picturesActions = new PicturesActions();
        } else {
            picturesActions = Database.get(this).load(Constants.FOTOS_LIST, PicturesActions.class);
        }
        picturesActions.getList().add(picturePath);
        Database.get(this).save(Constants.FOTOS_LIST, picturesActions);
    }
}
