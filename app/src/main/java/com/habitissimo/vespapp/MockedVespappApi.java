package com.habitissimo.vespapp;

import android.support.annotation.NonNull;

import com.habitissimo.vespapp.questions.ExpertComment;
import com.habitissimo.vespapp.questions.Location;
import com.habitissimo.vespapp.questions.Picture;
import com.habitissimo.vespapp.questions.Sighting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class MockedVespappApi implements com.habitissimo.vespapp.api.VespappApi {
    private Map<String, Sighting> map = new HashMap<>();

    public MockedVespappApi() {
        List<Sighting> list = new ArrayList<Sighting>() {{
            add(createRandomSighting());
            add(createRandomSighting());
            add(createRandomSighting());
            add(createRandomSighting());
            add(createRandomSighting());
        }};

        for (Sighting sighting : list) {
            map.put(sighting.id, sighting);
        }
    }

    @NonNull private Sighting createRandomSighting() {
        Sighting sighting = new Sighting();
        sighting.id = UUID.randomUUID().toString();
        sighting.status = getRandomStatus();
        sighting.pictures = createRandomPictures(sighting);
        return sighting;
    }

    @Override public Call<List<Sighting>> getSightings() {
        return null;
    }

    @Override public void getSightingById(@Path("sightingId") String sightingId, Callback<Sighting> callback) {

    }

//    @Override public void getSightings(Callback<List<Sighting>> callback) {
//        callback.onResponse(null, Response.<List<Sighting>>success(new ArrayList<>(map.values())));
//    }

    @Override
    public void updateSighting(@Path("sightingId") String sightingId, @Body Sighting sighting, Callback<Sighting> callback) {

    }

    @Override public Call<Sighting> createSighting(@Body Sighting sighting) {
        return null;
    }

//    @Override public void createSighting(@Body Sighting sighting, Callback<Sighting> callback) {
//
//    }

    @Override
    public Call<Void> addPhoto(@Path("sightingId") String sightingId, @Part("file\"; filename=\"photo.png\" ") RequestBody photo) {
        return null;
    }

    @Override public void getPhotos(@Path("sightingId") String sightingId, Callback<List<Picture>> callback) {

    }

    @Override public void getExpertComments(@Path("sightingId") String sightingId, Callback<ExpertComment> callback) {

    }

    @Override public void createExpertComment(@Path("sightingId") String sightingId, @Body Void comment, Callback<ExpertComment> callback) {

    }

    @Override
    public void getExpertCommentById(@Path("sightingId") String sightingId, @Path("commentId") String commentId, Callback<ExpertComment> callback) {

    }

    @Override public void getLocations(Callback<List<Location>> callback) {

    }

    private List<Picture> createRandomPictures(Sighting sighting) {
        return Arrays.asList(
                new Picture("http://lorempixel.com/" + random(375, 425) + "/" + random(375, 425), "1", sighting.id),
                new Picture("http://lorempixel.com/400/400/", "2", sighting.id)
        );
    }

    private int random(int min, int max) {
        return min + new Random().nextInt(max - min);
    }

    private int getRandomStatus() {
        return new Integer[]{Sighting.STATUS_PENDING, Sighting.STATUS_PROCESSED, Sighting.STATUS_PROCESSING, Sighting.STATUS_UNSENT}[new Random().nextInt(4)];
    }
}
