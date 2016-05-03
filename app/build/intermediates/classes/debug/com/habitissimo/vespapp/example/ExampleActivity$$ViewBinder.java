// Generated code from Butter Knife. Do not modify!
package com.habitissimo.vespapp.example;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ExampleActivity$$ViewBinder<T extends com.habitissimo.vespapp.example.ExampleActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492975, "field 'titleText'");
    target.titleText = finder.castView(view, 2131492975, "field 'titleText'");
    view = finder.findRequiredView(source, 2131492976, "method 'onTitleButtonPressed'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onTitleButtonPressed();
        }
      });
  }

  @Override public void unbind(T target) {
    target.titleText = null;
  }
}
