package com.mar.utils;

import android.util.Log;

import java.util.ArrayList;

public class RestrictedAppsRepo {
    private static final String TAG = "RestrictedAppsRepo";
    private static ArrayList<String> restrictedAppList;

    public RestrictedAppsRepo() {
    }

    public static ArrayList<String> getRestrictedAppList() {
        restrictedAppList = new ArrayList<>();
        if (restrictedAppList.isEmpty()) {
            restrictedAppList.add(0, "com.snapchat.android");
            restrictedAppList.add(1, "com.whatsapp");
            restrictedAppList.add(2, "com.bsb.hike");
            restrictedAppList.add(3, "com.facebook.katana");
            restrictedAppList.add(4, "com.facebook.lite");
            restrictedAppList.add(5, "com.facebook.orca");
            restrictedAppList.add(6, "com.facebook.mlite");
            restrictedAppList.add(7, "com.twitter.android");
            restrictedAppList.add(8, "com.twitter.android.lite");
            restrictedAppList.add(9, "com.instagram.android");
            restrictedAppList.add(10, "com.zhiliaoapp.musically.go");
            restrictedAppList.add(11, "com.zhiliaoapp.musically");
            restrictedAppList.add(12, "com.google.android.gm");
            restrictedAppList.add(13, "com.google.android.youtube");
            restrictedAppList.add(14, "com.netflix.mediaclient");
            Log.i(TAG, "getRestrictedAppList: Restricted Apps List  Is created and Filled");
            return restrictedAppList;
        } else {
            Log.i(TAG, "getRestrictedAppList: Restricted Apps List  Already Created");
        }
        return restrictedAppList;
    }
}
