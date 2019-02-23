package com.mar;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mar.adapter.AppListAdapter;
import com.mar.services.AppLockService;
import com.mar.utils.AppLockUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

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
        if (mappinfo.isEmpty()) {
            mappinfo = apputil.getLaunchableInstalledApplications();
        } else {
            Log.i(TAG, "onCreate: applist is initialised already");
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
