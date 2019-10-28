package com.yy.electric.maintenance;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

public class TaskHandler extends Handler {

    TaskHandler(Looper looper) {
        super(looper);
    }

    public <T> void postData(final com.yy.electric.maintenance.api.Callback<T> callback, final T t) {
        post(new Runnable() {
            @Override
            public void run() {
                callback.result(t);
            }
        });
    }

    public <T> void postList(final com.yy.electric.maintenance.api.Callback<List<T>> callback, final List<T> t) {
        post(new Runnable() {
            @Override
            public void run() {
                callback.result(t);
            }
        });
    }
}
