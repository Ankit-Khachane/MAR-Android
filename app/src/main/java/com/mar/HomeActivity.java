package com.mar;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mar.adapter.AppListAdapter;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.ItemDecoration itemDecoration;
    private AppListAdapter mAppListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mRecyclerView = findViewById(R.id.app_list_recyclerview);
        PackageManager packageManager = getApplicationContext().getPackageManager();
        List<ApplicationInfo> mApplicationInfoList = packageManager.getInstalledApplications(0);
        if (mApplicationInfoList.isEmpty()) {
            Toast.makeText(this, "App Fetching Failed", Toast.LENGTH_SHORT).show();
        } else {
            mAppListAdapter = new AppListAdapter(this, mApplicationInfoList);
            Toast.makeText(this, "Apps Details Fetched", Toast.LENGTH_SHORT).show();
        }
        itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

    }

    public void fetchInstalledApps(View v) {
        mRecyclerView.setAdapter(mAppListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(itemDecoration);
    }
}
