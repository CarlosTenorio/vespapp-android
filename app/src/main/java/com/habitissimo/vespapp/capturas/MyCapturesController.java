package com.habitissimo.vespapp.capturas;

import android.util.Log;
import android.widget.Toast;

import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.base.Controller;
import com.habitissimo.vespapp.model.SightingUi;
import com.habitissimo.vespapp.questions.Picture;
import com.habitissimo.vespapp.questions.Sighting;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCapturesController extends Controller<MyCapturesView, Void> {
    public static final String TAG = "MyCapturesController";
    private VespappApi api;
    private SightingsMapper mapper;

    public MyCapturesController(VespappApi api, SightingsMapper mapper) {
        this.api = api;
        this.mapper = mapper;
    }

    @Override public void onLoad(Void aVoid) {
        Call<List<Sighting>> call = api.getSightings();
        call.enqueue(new Callback<List<Sighting>>() {
            @Override public void onResponse(Call<List<Sighting>> call, Response<List<Sighting>> response) {
                onGotSightings(response);
            }

            @Override public void onFailure(Call<List<Sighting>> call, Throwable t) {
                onGettingSightingsError(t);
            }
        });
    }

    private void onGotSightings(Response<List<Sighting>> response) {
        getView().setItems(mapper.map(response.body()));
    }

    private void onGettingSightingsError(Throwable t) {
        Toast.makeText(getContext(), "Error getting sightings", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Error getting sightings: " + t);
    }

    public static class SightingsMapper {
        public SightingsMapper() {
        }

        public SightingUi map(Sighting apiModel) {
            if (apiModel.pictures == null)
                apiModel.pictures = new ArrayList<>();

            Picture firstPicture = apiModel.pictures.size() > 0 ? apiModel.pictures.get(0) : new Picture("", "", "");
            return new SightingUi(firstPicture.file, apiModel.status);
        }

        public List<SightingUi> map(List<Sighting> sightings) {
            List<SightingUi> list = new ArrayList<>();

            if (sightings == null) {
                return list;
            }

            if (sightings == null){
                return list;
            }
            for (Sighting sighting : sightings) {
                list.add(map(sighting));
            }

            return list;
        }
    }
}
