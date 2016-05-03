package com.habitissimo.vespapp.async;

public interface TaskCallback<T> {
    T executeInBackground();

    void onError(Throwable t);

    void onCompleted(T t);
}