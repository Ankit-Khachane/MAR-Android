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
            restrictedAppList.add("com.whatsapp");
            restrictedAppList.add("com.bsb.hike");
            restrictedAppList.add("com.facebook.katana");
            restrictedAppList.add("com.facebook.lite");
            restrictedAppList.add("com.facebook.orca");
            restrictedAppList.add("com.facebook.mlite");
            restrictedAppList.add("com.twitter.android");
            restrictedAppList.add("com.twitter.android.lite");
            restrictedAppList.add("com.instagram.android");
            restrictedAppList.add("com.zhiliaoapp.musically.go");
            restrictedAppList.add("com.zhiliaoapp.musically");
            restrictedAppList.add("com.google.android.gm");
            restrictedAppList.add("com.google.android.youtube");
            restrictedAppList.add("com.netflix.mediaclient");
            restrictedAppList.add("com.snapchat.android");
            Log.i(TAG, "getRestrictedAppList: Restricted Apps List  Is created and Filled");
            return restrictedAppList;
        } else {
            Log.i(TAG, "getRestrictedAppList: Restricted Apps List  Already Created");
        }
        return restrictedAppList;
    }
}
