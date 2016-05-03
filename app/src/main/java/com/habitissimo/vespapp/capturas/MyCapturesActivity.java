package com.habitissimo.vespapp.capturas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.capturas.MyCapturesController.SightingsMapper;
import com.habitissimo.vespapp.model.SightingUi;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyCapturesActivity extends AppCompatActivity implements MyCapturesView {
    @Bind(R.id.list) RecyclerView list;

    private MyCapturesController controller;
    private MyCapturesAdapter adapter;
    private VespappApi vespappApi;
    private SightingsMapper sightingsMapper;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturas);
        ButterKnife.bind(this);

        vespappApi = Vespapp.get(this).getApi();
        sightingsMapper = new SightingsMapper();

        list.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MyCapturesAdapter();
        list.setAdapter(adapter);

        controller = new MyCapturesController(vespappApi, sightingsMapper);
        controller.takeView(this);
        controller.onLoad(null);
    }

    @Override public void setItems(List<SightingUi> items) {
        adapter.setItems(items);
    }
}
