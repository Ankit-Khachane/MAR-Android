package com.mar.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.mar.R;
import com.mar.appmonitor.AppMonitorEngine;

public class AppLockService extends Service {
    private static final String TAG = "AppLockService";
    private volatile HandlerThread mBackgroundThread;
    private ServiceHandler mServiceHandler;
    private AppMonitorEngine appMonitorEngine;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params = null;
    private View lock_page_view;
    private String restricted_app_name = "com.google.android.gm";
    private boolean isAppLocked = false;
    private boolean isRestrictedAppFound = false;
    private Intent homeintent;


    public AppLockService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lock_page_view = LayoutInflater.from(this).inflate(R.layout.lock_view, null);
        mBackgroundThread = new HandlerThread("AppLockService.HandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
        mBackgroundThread.start();
        mServiceHandler = new ServiceHandler(mBackgroundThread.getLooper());
        appMonitorEngine = new AppMonitorEngine(this, mServiceHandler);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    PixelFormat.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    PixelFormat.TRANSPARENT);
        }

        if (params != null)
            params.gravity = Gravity.CENTER;
        lock_page_view.setFocusableInTouchMode(true);
        lock_page_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    lockApp(false);
                    goToHome();
                } else {
                    return false;
                }
                return false;
            }
        });

        Log.i(TAG, "onCreate: mBackgroundThread Thread Id : - " + mBackgroundThread.getThreadId());
    }

    private void goToHome() {
        //intent call to launcher home of the system
        homeintent = new Intent(Intent.ACTION_MAIN);
        homeintent.addCategory(Intent.CATEGORY_HOME);
        homeintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeintent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mServiceHandler.sendEmptyMessage(0);
        Log.i(TAG, "onStartCommand: AppLockService Start Command is Triggered");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        appMonitorEngine.stop();
        mBackgroundThread.quitSafely();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(new Intent(this, AppLockService.class));
        } else {
            this.startService(new Intent(this, AppLockService.class));
        }
        Log.i(TAG, "onDestroy: AppLock Service Destroyed");

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(new Intent(this, AppLockService.class));
        } else {
            this.startService(new Intent(this, AppLockService.class));
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void lockApp(boolean lockflag) {
        if (lockflag) {
            windowManager.addView(lock_page_view, params);
            isAppLocked = true;
        } else {
            windowManager.removeViewImmediate(lock_page_view);
            isAppLocked = false;
        }
    }

    private class ServiceHandler extends Handler {

        private ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            //any app
            appMonitorEngine
                    .when(getPackageName(), new AppMonitorEngine.Listener() {
                        @Override
                        public void onForeground(String process) {
                            Log.i(TAG, "onForeground: Our App Monitored");
                        }
                    })
                    .whenOther(new AppMonitorEngine.Listener() {
                        @Override
                        public void onForeground(String process) {
                            if (process.equals(restricted_app_name)) {
                                /*if (!isAppLocked) {
                                    //if app detected first time lock over it @isAppLocked = false
                                    lockApp(true);
                                    Log.i(TAG, "App Locked First Time");
                                } else {
                                    //if  app is locked before do following
                                    if (isAppLocked) {
                                        Log.i(TAG, "App Already Locked isAppLocked is True = " + isAppLocked);
                                    } else {
                                        Log.i(TAG, "isAppLocked Flag is False = " + isAppLocked);
                                    }
                                }*/
                                isRestrictedAppFound = true;
                            } else {
                                isRestrictedAppFound = false;
                            }
                            if (isRestrictedAppFound && !isAppLocked) {
                                lockApp(true);
                            }
                            if (!isRestrictedAppFound && isAppLocked)
                                lockApp(false);
                            Log.i(TAG, "onForeground: On Any App Launches = " + process);
                        }
                    })
                    .timeout(1000)
                    .start(getApplicationContext());
        }
    }
}
