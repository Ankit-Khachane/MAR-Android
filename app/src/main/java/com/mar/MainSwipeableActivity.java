package com.mar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mar.appmonitor.AppMonitorUtil;
import com.mar.fragments.AnalysisFragment;
import com.mar.fragments.MonitorFragment;
import com.mar.fragments.RestrictionFragment;
import com.mar.services.AppLockService;

public class MainSwipeableActivity extends AppCompatActivity {
    public static final int usage_access_request = 8898;
    public static final int draw_over_other_app_request = 9989;
    private static final String TAG = "MainSwipeableActivity";
    private Intent serviceintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_swipable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        serviceintent = new Intent(this, AppLockService.class);


        if (AppMonitorUtil.hasUsageStatsPermission(getApplicationContext()) && AppMonitorUtil.hasDrawOverOtherAppPermission(getApplicationContext())) {
            Log.i(TAG, "run: Usage Permission Granted Draw Over Other App is Allowed");
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


        TabLayout tabLayout = findViewById(R.id.tab_groups);
        ViewPager mViewPager = findViewById(R.id.viewpager);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_swipable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case usage_access_request:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(MainSwipeableActivity.this, MainSwipeableActivity.class));
                    finish();
                    break;
                } else {
                    Log.i(TAG, "onActivityResult: Usage Access Permission Not Allowed");
                    break;
                }
            case draw_over_other_app_request:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(MainSwipeableActivity.this, MainSwipeableActivity.class));
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

    public void lockapps(View view) {
        // TODO: 25-03-2019 Adding Passcode Mechanism On Lock For Parental Control
        startService(serviceintent);
        Log.i(TAG, "lockapps: service started");
    }

    public void unlockapps(View view) {
        // TODO: 25-03-2019 CrossChecking Passcode Mechanism For Unlock Parental Control
        stopService(serviceintent);
        Log.i(TAG, "unlockapps: service started");
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MonitorFragment();
                case 1:
                    return new AnalysisFragment();
                case 2:
                    return new RestrictionFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}