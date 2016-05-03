package com.habitissimo.vespapp.fotos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.habitissimo.vespapp.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final Callback callback;
    public ImageView foto;
    private Context context;

    public RecyclerViewHolders(View itemView, Context context, Callback callback) {
        super(itemView);
        this.context = context;
        this.callback = callback;
        itemView.setOnClickListener(this);
        foto = (ImageView) itemView.findViewById(R.id.confirm_cap_img);
    }

    @Override
    public void onClick(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirm_cap_DeleteDialog_msg)
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onImageDeleted(getAdapterPosition());
                    }
                });
        builder.create().show();
    }

    public interface Callback {
        void onImageDeleted(int index);
    }
}
