package com.mar.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AppLockUtils {
    // TODO: 23-02-2019 Design Applock Util Functions For App.
    private static final String TAG = "AppLockUtils";
    private static List<ApplicationInfo> mApplicationInfo = null;
    private static PackageManager packageManager;
    private Context mContext;

    public AppLockUtils(Context context) {
        mApplicationInfo = new ArrayList<>();
        this.mContext = context;
        packageManager = context.getPackageManager();
    }

    public List<ApplicationInfo> getLaunchableInstalledApplications() {
        List<ApplicationInfo> list = packageManager.getInstalledApplications(0);

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (mContext.getPackageManager().getLaunchIntentForPackage(list.get(i).packageName) != null) {
                    //Launchable app filter using ApplicationInfo fetched in list
                    mApplicationInfo.add(list.get(i));
                } else {
                    Log.i(TAG, "Non Launchable App : - " + list.get(i).packageName);
                }
            }
        } else {
            Log.i(TAG, "getLaunchableInstalledApplications: Apps Info Fetching Failed");
        }
        return mApplicationInfo;
    }
}
