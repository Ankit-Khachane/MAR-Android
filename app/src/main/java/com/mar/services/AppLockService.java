package com.mar.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppLockService extends Service {
    public AppLockService() {
        // TODO: 23-02-2019 Develope this Service To monitor apps with restricted flag and lock it.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
