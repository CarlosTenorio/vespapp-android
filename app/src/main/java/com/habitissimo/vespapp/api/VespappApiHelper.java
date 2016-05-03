package com.habitissimo.vespapp.api;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VespappApiHelper {
    public static RequestBody buildPhotoApiParameter(File file) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), file);
    }
}
