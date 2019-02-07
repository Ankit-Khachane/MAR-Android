package com.mar;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class SplashScreen extends AppCompatActivity {
    //log tag of class
    private static final String TAG = "SplashScreen";
    //List Collection for Type<PackageInfo>
    private List<PackageInfo> mPackageInfoList;
    //PackageManager Class Variable
    private PackageManager packageManager;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //instantiated PackageManager variable with Context Of This Activity
        packageManager = this.getPackageManager();
        //fetched installed package query to pakagemanager
        mPackageInfoList = packageManager.getInstalledPackages(0);
        //used foreach loop to iterate the packages retrieved by above query and logged the result one by one
        for (PackageInfo p : mPackageInfoList) {
            Log.i(TAG, "Package : - " + p.packageName + " - " + p.versionName);
            count++;
        }
        Log.i(TAG, "Total installed - " + count + " Package manager list size - " + mPackageInfoList.size());
    }
}
