package com.habitissimo.vespapp.sighting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.habitissimo.vespapp.Constants;
import com.habitissimo.vespapp.database.Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PicturesActions {

    private List<String> list = new ArrayList<>();


    public PicturesActions() {
    }


    public List<String> getList() {
        return list;
    }

    public File createImageFile() throws IOException {
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



    public void resize(File photo, int width, int height) {
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
}

