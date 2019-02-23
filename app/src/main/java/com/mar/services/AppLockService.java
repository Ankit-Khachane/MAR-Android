package com.mar.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

public class AppLockService extends Service {
    private static final String TAG = "AppLockService";
    private volatile HandlerThread mBackgroundThread;
    private ServiceHandler mServiceHandler;

    public AppLockService() {
        // TODO: 23-02-2019 Develop this Service To monitor apps with restricted flag and lock it.
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBackgroundThread = new HandlerThread("AppLockService.HandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
        mBackgroundThread.start();
        mServiceHandler = new ServiceHandler(mBackgroundThread.getLooper());
        Log.i(TAG, "onCreate: mBackgroundThread Thread Id : - " + mBackgroundThread.getThreadId());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: AppLockService Start Command is Triggered");
        mServiceHandler.sendEmptyMessage(0);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBackgroundThread.quitSafely();
        Log.i(TAG, "onDestroy: AppLock Serrvice Destroyed");

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ServiceHandler extends Handler {
        boolean val = true;
        int i = 0;

        private ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            /*
            TODO: 24-02-2019 Implement Restricted Apps Blocking Logic Code Here.As Thread is in Continues While Loop, Which Helps Us with Checking Recently launched Activity second by second.
            */
            try {
                while (val) {
                    i++;
                    Log.i(TAG, "Thread Loop i = " + i);
                    if (i >= 1000) {
                        val = false;
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
