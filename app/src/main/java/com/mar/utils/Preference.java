package com.mar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    // String Value Variable for Assigning SharedPreference File Name
    private static final String PreferenceFileName = "com.mar.MARSharedPreference";

    //Static Key Values For Preferences <Key,Value>
    private static String AccessUsagePermissionStatus = "AccessUsagePermissionStatus";
    private static String IsOpenedFirstTime = "IsOpenedFirstTime";

    //SharedPreference Api for Persistent data consumption
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;

    public Preference(Context context) {
        mSharedPreferences = context.getSharedPreferences(PreferenceFileName, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static Boolean getAccessUsagePermissionStatus() {
        return mSharedPreferences.getBoolean(AccessUsagePermissionStatus, false);
    }

    public static void setAccessUsagePermissionStatus(Boolean accessUsagePermissionStatus) {
        mEditor.putBoolean(AccessUsagePermissionStatus, accessUsagePermissionStatus).commit();
    }

    public static Boolean getIsOpenedFirstTime() {
        return mSharedPreferences.getBoolean(IsOpenedFirstTime, false);
    }

    public static void setIsOpenedFirstTime(Boolean isOpenedFirstTime) {
        mEditor.putBoolean(IsOpenedFirstTime, isOpenedFirstTime).commit();
    }
}
