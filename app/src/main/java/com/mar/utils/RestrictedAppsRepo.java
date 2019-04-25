package com.mar.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.mar.R;

import java.util.ArrayList;

public class RestrictedAppsRepo {
    private static final String TAG = "RestrictedAppsRepo";
    private static ArrayList<String> restrictedAppList;
    private static ArrayList<Integer> colorList;
    private static Context mContext;

    public RestrictedAppsRepo() {
    }

    public static ArrayList<String> getRestrictedAppList() {
        restrictedAppList = new ArrayList<>();
        if (restrictedAppList.isEmpty()) {
            restrictedAppList.add(0, "com.snapchat.android");
            restrictedAppList.add(1, "com.whatsapp");
            restrictedAppList.add(2, "com.netflix.mediaclient");
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
            Log.i(TAG, "getRestrictedAppList: Restricted Apps List  Is created and Filled");
            return restrictedAppList;
        } else {
            Log.i(TAG, "getRestrictedAppList: Restricted Apps List  Already Created");
        }
        return restrictedAppList;
    }

    public static ArrayList<Integer> getAssociatedColorsForRestrictedApps() {
        colorList = new ArrayList<>();
        colorList.add(ContextCompat.getColor(mContext, R.color.whatsapp_color));
        colorList.add(ContextCompat.getColor(mContext, R.color.facebook_color));
        colorList.add(ContextCompat.getColor(mContext, R.color.instagram_color));
        colorList.add(ContextCompat.getColor(mContext, R.color.twitter_color));
        colorList.add(ContextCompat.getColor(mContext, R.color.snapchat_color));
        colorList.add(ContextCompat.getColor(mContext, R.color.netflix_color));
        colorList.add(ContextCompat.getColor(mContext, R.color.youtube_color));
        colorList.add(ContextCompat.getColor(mContext, R.color.hike_color));
        return colorList;
    }
}
