package com.mar.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    // String Value Variable for Assigning SharedPreference File Name
    private static final String PreferenceFileName = "com.mar.MARSharedPreference";
    //Static Key Values For Preferences <Key,Value>
    private static String AccessUsagePermissionStatus = "AccessUsagePermissionStatus";
    private static String IsOpenedFirstTime = "IsOpenedFirstTime";
    private static String IsAppLockPinSet = "IsAppLockPinSet";
    private static String AppLockPin = "AppLockPin";
    //SharedPreference Api for Persistent data consumption
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    public Preference(Context context) {
        mSharedPreferences = context.getSharedPreferences(PreferenceFileName, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public int getAppLockPin() {
        return mSharedPreferences.getInt(AppLockPin, 1234);
    }

    public void setAppLockPin(int appLockPin) {
        mEditor.putInt(AppLockPin, appLockPin);
    }

    public boolean getIsAppLockPinSet() {
        return mSharedPreferences.getBoolean(IsAppLockPinSet, false);
    }

    public void setIsAppLockPinSet(boolean isAppLockPinSet) {
        mEditor.putBoolean(IsAppLockPinSet, isAppLockPinSet).commit();
    }

    public boolean getAccessUsagePermissionStatus() {
        return mSharedPreferences.getBoolean(AccessUsagePermissionStatus, false);
    }

    public void setAccessUsagePermissionStatus(Boolean accessUsagePermissionStatus) {
        mEditor.putBoolean(AccessUsagePermissionStatus, accessUsagePermissionStatus).commit();
    }

    public boolean getIsOpenedFirstTime() {
        return mSharedPreferences.getBoolean(IsOpenedFirstTime, false);
    }

    public void setIsOpenedFirstTime(Boolean isOpenedFirstTime) {
        mEditor.putBoolean(IsOpenedFirstTime, isOpenedFirstTime).commit();
    }
}