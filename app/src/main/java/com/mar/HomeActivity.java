package com.mar;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mar.adapter.AppListAdapter;
import com.mar.appmonitor.AppMonitorUtil;
import com.mar.services.AppLockService;
import com.mar.utils.AppLockUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    public static final int usage_access_request = 8898;
    public static final int draw_over_other_app_request = 9989;

    private RecyclerView mRecyclerView;
    private AppListAdapter mAppListAdapter;
    private AppLockUtils apputil;
    private List<ApplicationInfo> mappinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mRecyclerView = findViewById(R.id.app_list_recyclerview);
        apputil = new AppLockUtils(this);
        mappinfo = new ArrayList<>();
        if (AppMonitorUtil.hasUsageStatsPermission(getApplicationContext()) && AppMonitorUtil.hasDrawOverOtherAppPermission(getApplicationContext())) {
            Log.i(TAG, "run: Usage Permission Granted");
        } else {
            if (!AppMonitorUtil.hasUsageStatsPermission(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivityForResult(intent, usage_access_request);
            } else if (!AppMonitorUtil.hasDrawOverOtherAppPermission(getApplicationContext())) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivityForResult(i, draw_over_other_app_request);
                }
//                startActivityForResult(i, draw_overlay_request);
            }
        }
       /* if (mappinfo.isEmpty()) {
            mappinfo = apputil.getLaunchableInstalledApplications();
        } else {
            Log.i(TAG, "onCreate: applist is initialised already");
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case usage_access_request:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                    finish();
                    break;
                } else {
                    Log.i(TAG, "onActivityResult: Usage Access Permission Not Allowed");
                    break;
                }
            case draw_over_other_app_request:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                    finish();
                    break;
                } else {
                    Log.i(TAG, "onActivityResult: Usage Access Permission Not Allowed");
                    break;
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void fetchInstalledApps(View v) {
        mAppListAdapter = new AppListAdapter(this, mappinfo);
        mRecyclerView.setAdapter(mAppListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        startService(new Intent(this, AppLockService.class));
    }
}
