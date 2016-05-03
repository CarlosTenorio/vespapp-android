package com.habitissimo.vespapp.base;

public interface Callback<T> {
    void onSuccess(T item);

    void onError(Throwable throwable);
}
