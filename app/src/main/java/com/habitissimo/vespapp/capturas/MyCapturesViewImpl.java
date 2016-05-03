package com.habitissimo.vespapp.capturas;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.Vespapp;
import com.habitissimo.vespapp.api.VespappApi;
import com.habitissimo.vespapp.model.SightingUi;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mariano on 11/03/16.
 */
public class MyCapturesViewImpl extends FrameLayout implements MyCapturesView {
    @Bind(R.id.list)
    RecyclerView list;

    private MyCapturesController controller;
    private MyCapturesAdapter adapter;
    private VespappApi vespappApi;
    private MyCapturesController.SightingsMapper sightingsMapper;

    public MyCapturesViewImpl(Context context) {
        super(context);
        init();
    }

    public MyCapturesViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCapturesViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyCapturesViewImpl(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.activity_capturas, this);
        ButterKnife.bind(this);

        vespappApi = Vespapp.get(getContext()).getApi();
        sightingsMapper = new MyCapturesController.SightingsMapper();

        list.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new MyCapturesAdapter();
        list.setAdapter(adapter);

        controller = new MyCapturesController(vespappApi, sightingsMapper);
        controller.takeView(this);
        controller.onLoad(null);
    }

    @Override
    public void setItems(List<SightingUi> items) {
        adapter.setItems(items);
    }
}
