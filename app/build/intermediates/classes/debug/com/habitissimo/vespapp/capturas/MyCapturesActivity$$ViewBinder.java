// Generated code from Butter Knife. Do not modify!
package com.habitissimo.vespapp.capturas;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyCapturesActivity$$ViewBinder<T extends com.habitissimo.vespapp.capturas.MyCapturesActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492969, "field 'list'");
    target.list = finder.castView(view, 2131492969, "field 'list'");
  }

  @Override public void unbind(T target) {
    target.list = null;
  }
}
