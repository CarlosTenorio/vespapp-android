package com.habitissimo.vespapp.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;

import com.habitissimo.vespapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InfoDialog extends AlertDialog {
    @Bind(R.id.container) View container;
    private Listener listener;

    protected InfoDialog(Context context) {
        super(context);
        init();
    }

    protected InfoDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    protected InfoDialog (Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public static AlertDialog show (Context context, final Listener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("La imagen no puede pesar más de 1'5 MB")
                .setTitle("Ups!!")
                .setPositiveButton("Vale", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.show();
    }

    private InfoDialog setListener (Listener listener) {
        this.listener = listener;
        return this;
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
        getWindow().setBackgroundDrawable(transparentDrawable);
        setContentView(R.layout.dialog_next_step_questions);
        ButterKnife.bind(this);

        container.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                dismiss();
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override public void onDismiss(DialogInterface dialog) {
                listener.onDialogDismissed();
            }
        });
    }

    public interface Listener {
        void onDialogDismissed();
    }
}