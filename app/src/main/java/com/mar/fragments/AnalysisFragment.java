package com.mar.fragments;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mar.R;
import com.mar.adapters.AnalysisAdapter;
import com.mar.model.AnalysisItem;
import com.mar.utils.AppUsageDataUtils;
import com.mar.utils.MultipleListUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AnalysisFragment extends Fragment {
    private static final String TAG = "AnalysisFragment";
    private AppUsageDataUtils appUsageDataUtils;
    protected Spinner timeSpinner;
    protected BarChart barChart;
    protected ListView safeUsageListView;
    protected ListView unsafeUsageListView;
    private ArrayList<AnalysisItem> safeApps;
    private ArrayList<AnalysisItem> unsafeApps;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_analysis, container, false);
        mContext = getContext();
        assert mContext != null;
        appUsageDataUtils = new AppUsageDataUtils(mContext);
        //add view element initialization code in this block
        initView(fragmentView);
        prepareTimeRangeSpinner();
        prepareBarChartData();
        prepareSafeUsageAppsListData();
        prepareUnsafeUsageAppsListData();

        return fragmentView;
    }

    private void initView(View rootView) {
        timeSpinner = rootView.findViewById(R.id.time_span_selector_spinner);
        barChart = rootView.findViewById(R.id.app_usage_bar_chart);
        safeUsageListView = rootView.findViewById(R.id.safe_usage_listview);
        unsafeUsageListView = rootView.findViewById(R.id.unsafe_usage_listview);
    }

    public void prepareTimeRangeSpinner() {
        String[] timeSpanOptions = getResources().getStringArray(R.array.time_span_range);
        ArrayAdapter<String> timeSpanAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, timeSpanOptions);
        timeSpanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeSpanAdapter);
        Log.i(TAG, "prepareTimeRangeSpinner: Spinner setup is good to use !");
    }

    public void prepareBarChartData() {
        List<BarEntry> NoOfEmp = new ArrayList<BarEntry>();

        NoOfEmp.add(new BarEntry(70f, 10, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));
        NoOfEmp.add(new BarEntry(80f, 14, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));
        NoOfEmp.add(new BarEntry(90f, 12, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));
        NoOfEmp.add(new BarEntry(100f, 13, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));
        NoOfEmp.add(new BarEntry(110f, 14, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));
        NoOfEmp.add(new BarEntry(120f, 15, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));
        NoOfEmp.add(new BarEntry(130f, 16, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));
        NoOfEmp.add(new BarEntry(140f, 17, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));
        NoOfEmp.add(new BarEntry(150f, 18, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));
        NoOfEmp.add(new BarEntry(160f, 19, getResources().getDrawable(R.drawable.ic_lock_locked_black_36dp)));

        BarDataSet barDataSet = new BarDataSet(NoOfEmp, "App Usage");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextSize(16);
        barDataSet.setHighlightEnabled(false);
        barDataSet.setDrawIcons(false);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(5);
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat decimalFormat = new DecimalFormat();
                return decimalFormat.format(value) + " %";
            }
        });
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);

        XAxis x = barChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        Log.i(TAG, "prepareBarChartData: BarChart DataSet Good To Go !!!");
    }

    public void prepareSafeUsageAppsListData() {
        safeApps = new ArrayList<AnalysisItem>();
        List<ApplicationInfo> appsInfoFromSystem = AppUsageDataUtils.getRestrictedAppsInfoFromSystem();
        int i = 4;
        while (i < appsInfoFromSystem.size()) {
            ApplicationInfo a = appsInfoFromSystem.get(i);
            AnalysisItem aItem = new AnalysisItem(
                    appUsageDataUtils.getPackageManager().getApplicationLabel(a).toString(),
                    "Normal",
                    "30 min",
                    appUsageDataUtils.getPackageManager().getApplicationIcon(a)
            );
            safeApps.add(aItem);
            i++;
        }
        AnalysisAdapter safeAdapter = new AnalysisAdapter(mContext, safeApps, AnalysisAdapter.AnalysisListType.SAFE);
        safeUsageListView.setAdapter(safeAdapter);
        MultipleListUtil.setListViewHeightBasedOnChildren(safeUsageListView);
        Log.i(TAG, "prepareSafeUsageAppsListData: Safe Apps Data set is Generated");
    }

    public void prepareUnsafeUsageAppsListData() {
        unsafeApps = new ArrayList<AnalysisItem>();
        List<ApplicationInfo> appsInfoFromSystem = AppUsageDataUtils.getRestrictedAppsInfoFromSystem();
        int i = 1;
        if (appsInfoFromSystem.size() >= 3) {
            while (i <= 3) {
                ApplicationInfo a = appsInfoFromSystem.get(i);
                AnalysisItem aItem = new AnalysisItem(
                        appUsageDataUtils.getPackageManager().getApplicationLabel(a).toString(),
                        "High",
                        "3 Hr",
                        appUsageDataUtils.getPackageManager().getApplicationIcon(a)
                );
                unsafeApps.add(aItem);
                i++;
            }
        }
        AnalysisAdapter unsafeAdapter = new AnalysisAdapter(mContext, unsafeApps, AnalysisAdapter.AnalysisListType.UNSAFE);
        unsafeUsageListView.setAdapter(unsafeAdapter);
        MultipleListUtil.setListViewHeightBasedOnChildren(unsafeUsageListView);
        Log.i(TAG, "prepareSafeUsageAppsListData: Unsafe Apps Data set is Generated");

    }

}
