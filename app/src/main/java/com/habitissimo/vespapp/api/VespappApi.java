package com.habitissimo.vespapp.api;

import com.habitissimo.vespapp.sighting.Location;
import com.habitissimo.vespapp.sighting.Picture;
import com.habitissimo.vespapp.sighting.Sighting;
import com.habitissimo.vespapp.info.Info;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface VespappApi {


//    @GET("/sightings/{sightingId}/questions")
//    void getQuestions(@Path("sightingId") String sightingId, Callback<List<Answer>> callback);
//
//    @GET("/sightings/{sightingId}/questions/{questionId}")
//    void getQuestionById(@Path("sightingId") String sightingId, @Path("questionId") String questionId, Callback<Answer> callback);
//
//    @PATCH("/sightings/{sightingId}/questions/{questionId}")
//    void updateQuestion(@Path("sightingId") String sightingId, @Path("questionId") String questionId, @Body QuestionRequest answersid,
//                        Callback</* TODO */Void> callback);












    @GET("sightings/{sightingId}/photos/")
    Call <List<Picture>> getPhotos(@Path("sightingId") String sightingId);

    @GET("locations/")
    Call<List<Location>> getLocations();

    @GET("sightings/")
    Call<List<Sighting>> getSightings();

    @GET("info/")
    Call<List<Info>> getInfo();


    @POST("sightings/")
    Call<Sighting> createSighting(@Body Sighting sighting);

    /**
     * @see VespappApiHelper#buildPhotoApiParameter(File)
     */
    @Multipart
    @POST("sightings/{sightingId}/photos/")
    Call<Void> addPhoto(@Path("sightingId") String sightingId, @Part("file\"; filename=\"photo.png\" ") RequestBody photo);


}
