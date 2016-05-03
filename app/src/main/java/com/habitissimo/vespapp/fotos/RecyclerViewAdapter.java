package com.habitissimo.vespapp.fotos;

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

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_cap_img, parent, false);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, context, new RecyclerViewHolders.Callback() {
            @Override
            public void onImageDeleted(int index) {
                // Actualizar database
                ListaFotos lista = Database.get(context).load(Constants.FOTOS_LIST, ListaFotos.class);
                String removeUrl = lista.getLista().remove(index);
                Database.get(context).save(Constants.FOTOS_LIST, lista);

                // Actualizar adapter
                itemList.remove(removeUrl);
                notifyDataSetChanged();
            }
        });
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        Bitmap img = BitmapFactory.decodeFile(itemList.get(position));
        img = getResizedBitmap(img, 640);
        holder.foto.setImageBitmap(img);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
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
