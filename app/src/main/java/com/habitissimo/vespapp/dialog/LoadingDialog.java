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

public class LoadingDialog extends Dialog {
    @Bind(R.id.container) View container;
    private Listener listener;

    protected LoadingDialog(Context context) {
        super(context);
        init();
    }

    protected LoadingDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public static AlertDialog show(Context context, final Listener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_loading);
        builder.setOnDismissListener(new OnDismissListener() {
            @Override public void onDismiss(DialogInterface dialog) {
                listener.onDialogDismissed();;
            }
        });
        return builder.show();
    }

    private LoadingDialog setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
        getWindow().setBackgroundDrawable(transparentDrawable);

        setContentView(R.layout.dialog_loading);
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
