package com.mar.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompletedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_BOOT_COMPLETED)) {
            // TODO: 23-02-2019 Implement logic to start ApplockService after device reboots everry time.
            Log.i(TAG, "onReceive: Boot Completed Bradcast Received after  Boot Completed");
        } else if (Objects.equals(intent.getAction(), Intent.ACTION_LOCKED_BOOT_COMPLETED)) {
            // TODO: 23-02-2019 implement logic to start applock service after user unlockes device for first time.
            Log.i(TAG, "onReceive: Boot Completed Bradcast Received after Phone Unlocked");


        }
    }
}
