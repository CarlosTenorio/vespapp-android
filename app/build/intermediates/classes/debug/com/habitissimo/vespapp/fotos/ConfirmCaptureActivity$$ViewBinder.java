// Generated code from Butter Knife. Do not modify!
package com.habitissimo.vespapp.fotos;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ConfirmCaptureActivity$$ViewBinder<T extends com.habitissimo.vespapp.fotos.ConfirmCaptureActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492973, "method 'onNidoPressed'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onNidoPressed();
        }
      });
    view = finder.findRequiredView(source, 2131492974, "method 'onAvispaPressed'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onAvispaPressed();
        }
      });
  }

  @Override public void unbind(T target) {
  }
}
