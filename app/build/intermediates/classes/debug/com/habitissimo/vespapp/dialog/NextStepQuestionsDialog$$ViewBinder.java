// Generated code from Butter Knife. Do not modify!
package com.habitissimo.vespapp.dialog;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NextStepQuestionsDialog$$ViewBinder<T extends com.habitissimo.vespapp.dialog.NextStepQuestionsDialog> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492992, "field 'container'");
    target.container = view;
  }

  @Override public void unbind(T target) {
    target.container = null;
  }
}
