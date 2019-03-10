package com.mar;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mar.appmonitor.AppMonitorUtil;
import com.mar.services.AppLockService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int usage_access_request = 8898;
    public static final int draw_over_other_app_request = 9989;
    private static final String TAG = "MainActivity";
    private PieChart pieChart;
    private List<PieEntry> pieEntries;
    private PieDataSet pieDataSet;
    private PieData pieData;
    private List<Integer> colorlist;
    private Intent serviceintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        serviceintent = new Intent(this, AppLockService.class);
        pieChart = findViewById(R.id.pie_chart_view);

        pieEntries = new ArrayList<PieEntry>();
        pieEntries.add(0, new PieEntry(34.5f, "WhatsApp"));
        pieEntries.add(1, new PieEntry(25, "Facebook"));
        pieEntries.add(2, new PieEntry(25, "Instagram"));
        pieEntries.add(3, new PieEntry(15.5f, "TikTok"));

        pieDataSet = new PieDataSet(pieEntries, "AppUsagePieChart");

        colorlist = new ArrayList<>();

        colorlist.add(getResources().getColor(R.color.whatsapp_color));
        colorlist.add(getResources().getColor(R.color.facebook_color));
        colorlist.add(getResources().getColor(R.color.instagram_color));
        colorlist.add(getResources().getColor(R.color.tiktok_color));

        pieDataSet.setColors(colorlist);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat decimalFormat = new DecimalFormat();
                return decimalFormat.format(value) + " %";
            }
        });
        pieDataSet.setValueTextSize(18);

        pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawSlicesUnderHole(true);
        pieChart.invalidate();
        pieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceintent);
                Log.i(TAG, "lockapps: service stopped");
            }
        });


    }

    public void lockapps(View view) {
        startService(serviceintent);
        Log.i(TAG, "lockapps: service stareted");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case usage_access_request:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                    break;
                } else {
                    Log.i(TAG, "onActivityResult: Usage Access Permission Not Allowed");
                    break;
                }
            case draw_over_other_app_request:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
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
}
