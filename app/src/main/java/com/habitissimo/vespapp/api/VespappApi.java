package com.habitissimo.vespapp.api;

import com.habitissimo.vespapp.questions.ExpertComment;
import com.habitissimo.vespapp.questions.Location;
import com.habitissimo.vespapp.questions.Picture;
import com.habitissimo.vespapp.questions.Sighting;

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
    String SIGHTINGS = "sightings/";

    @GET(SIGHTINGS) Call<List<Sighting>> getSightings();

    @GET(SIGHTINGS + "{sightingId}/")
    void getSightingById(@Path("sightingId") String sightingId, Callback<Sighting> callback);

    @PATCH(SIGHTINGS + "{sightingId}/")
    void updateSighting(@Path("sightingId") String sightingId, @Body Sighting sighting, Callback<Sighting> callback);

    @POST(SIGHTINGS)
    Call<Sighting> createSighting(@Body Sighting sighting);

    /**
     * @see VespappApiHelper#buildPhotoApiParameter(File)
     */
    @Multipart
    @POST(SIGHTINGS + "{sightingId}/photos/")
    Call<Void> addPhoto(@Path("sightingId") String sightingId, @Part("file\"; filename=\"photo.png\" ") RequestBody photo);

    @GET("/sightings/{sightingId}/photos/")
    void getPhotos(@Path("sightingId") String sightingId, Callback<List<Picture>> callback);

//    @GET("/sightings/{sightingId}/questions")
//    void getQuestions(@Path("sightingId") String sightingId, Callback<List<Answer>> callback);
//
//    @GET("/sightings/{sightingId}/questions/{questionId}")
//    void getQuestionById(@Path("sightingId") String sightingId, @Path("questionId") String questionId, Callback<Answer> callback);
//
//    @PATCH("/sightings/{sightingId}/questions/{questionId}")
//    void updateQuestion(@Path("sightingId") String sightingId, @Path("questionId") String questionId, @Body QuestionRequest answersid,
//                        Callback</* TODO */Void> callback);

    @GET("/sightings/{sightingId}/expert_comments/")
    void getExpertComments(@Path("sightingId") String sightingId, Callback<ExpertComment> callback);

    @POST("/sightings/{sightingId}/expert_comments/")
    void createExpertComment(@Path("sightingId") String sightingId, @Body /* TODO */ Void comment, Callback<ExpertComment> callback);

    @GET("/sightings/{sightingId}/expert_comments/{commentId}/")
    void getExpertCommentById(@Path("sightingId") String sightingId, @Path("commentId") String commentId, Callback<ExpertComment> callback);

    @GET("/locations/")
    void getLocations(Callback<List<Location>> callback);
}
