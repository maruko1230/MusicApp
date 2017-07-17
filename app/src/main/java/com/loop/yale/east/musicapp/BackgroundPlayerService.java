package com.loop.yale.east.musicapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class BackgroundPlayerService extends Service {

    private static final String TAG = "BackgroundPlayerService";

    public BackgroundPlayerService() {
        Log.e(TAG, "Constructor");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
