package com.habitissimo.vespapp.sighting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.habitissimo.vespapp.Constants;
import com.habitissimo.vespapp.R;
import com.habitissimo.vespapp.database.Database;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<String> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<String> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, context, new RecyclerViewHolders.Callback() {
            @Override
            public void onImageDeleted(int index) {
                // Update database
                PicturesActions list = Database.get(context).load(Constants.PICTURES_LIST, PicturesActions.class);
                String removeUrl = list.getList().remove(index);
                Database.get(context).save(Constants.PICTURES_LIST, list);

                // Update adapter
                itemList.remove(removeUrl);
                notifyDataSetChanged();
            }
        });
        return rcv;
    }


    public void addPhoto(String photo) {
        itemList.add(photo);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        Bitmap img = BitmapFactory.decodeFile(itemList.get(position));
        img = getResizedBitmap(img, 640);
        holder.photo.setImageBitmap(img);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
