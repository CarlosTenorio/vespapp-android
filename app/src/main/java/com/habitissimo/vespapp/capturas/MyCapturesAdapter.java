package com.habitissimo.vespapp.capturas;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.base.EasyRecyclerAdapter;
import com.habitissimo.vespapp.model.SightingUi;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class MyCapturesAdapter extends EasyRecyclerAdapter<SightingUi, MyCapturesAdapter.ViewHolder> {

    public static final String TAG = "MyCapturesAdapter";

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.capturas_item, parent, false));
    }

    @Override protected void onBind(ViewHolder holder, SightingUi sighting) {
        Context context = holder.itemView.getContext();

        holder.setTitle(sighting.getTitleFromStatusCode(context));
        holder.setStatusColor(sighting.getStatusColor(context));
        holder.setImageUrl(sighting.imageUrl);
    }

    public static class ViewHolder extends EasyRecyclerAdapter.ViewHolder {
        @Bind(R.id.title) TextView titleTextView;
        @Bind(R.id.image) ImageView photoImageView;
        @Bind(R.id.image_status) ImageView statusImageView;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setTitle(String title) {
            titleTextView.setText(title);
        }

        public void setImageUrl(final String imageUrl) {
            if (TextUtils.isEmpty(imageUrl))
                return;

            Picasso.with(itemView.getContext()).load(imageUrl).into(photoImageView);
        }

        public void setStatusColor(int color) {
            statusImageView.setImageDrawable(new ColorDrawable(color));
        }
    }
}


