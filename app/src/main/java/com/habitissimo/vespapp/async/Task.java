package com.habitissimo.vespapp.async;

import android.os.AsyncTask;

public class Task {
    public static <T> void doInBackground(final TaskCallback<T> taskCallback) {
        new AsyncTask<Void, Void, T>() {
            @Override protected T doInBackground(Void... params) {
                try {
                    return taskCallback.executeInBackground();
                } catch (Throwable t) {
                    taskCallback.onError(t);
                    return null;
                }
            }

            @Override protected void onPostExecute(T t) {
                if (t != null) {
                    taskCallback.onCompleted(t);
                }
            }
        }.execute((Void) null);
    }
}