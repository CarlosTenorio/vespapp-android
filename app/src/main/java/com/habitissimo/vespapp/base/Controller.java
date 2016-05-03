package com.habitissimo.vespapp.base;

import android.content.Context;
import android.view.View;

/**
 * Acuérdate de llamar a takeView(View) y a onLoad(Arguments)
 * @param <V>
 * @param <A>
 */
public abstract class Controller<V, A> {
    private V view;

    public Controller() {
    }

    public Context getContext() {
        if (view instanceof Context)
            return (Context) view;

        if (view instanceof View) {
            return ((View) view).getContext();
        }

        throw new RuntimeException("La vista no representa un Context (no extiende de Activity ni de View), pregunta al de al lado.");
    }

    public void takeView(V view) {
        this.view = view;
    }

    public abstract void onLoad(A a);

    public V getView() {
        return view;
    }
}
