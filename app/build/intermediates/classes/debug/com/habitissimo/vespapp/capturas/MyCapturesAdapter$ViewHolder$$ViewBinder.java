// Generated code from Butter Knife. Do not modify!
package com.habitissimo.vespapp.capturas;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyCapturesAdapter$ViewHolder$$ViewBinder<T extends com.habitissimo.vespapp.capturas.MyCapturesAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492931, "field 'titleTextView'");
    target.titleTextView = finder.castView(view, 2131492931, "field 'titleTextView'");
    view = finder.findRequiredView(source, 2131492927, "field 'photoImageView'");
    target.photoImageView = finder.castView(view, 2131492927, "field 'photoImageView'");
    view = finder.findRequiredView(source, 2131492978, "field 'statusImageView'");
    target.statusImageView = finder.castView(view, 2131492978, "field 'statusImageView'");
  }

  @Override public void unbind(T target) {
    target.titleTextView = null;
    target.photoImageView = null;
    target.statusImageView = null;
  }
}
