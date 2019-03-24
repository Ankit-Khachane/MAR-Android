package com.mar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.mar.R;

import java.util.ArrayList;

public class AnalysisFragment extends Fragment {
    protected Spinner timeSpinner;
    protected BarChart barChart;
    protected ListView safeUsageListView;
    protected ListView unsafeUsageListView;
    private ArrayList SafeApps, UnsafeApps;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_analysis, container, false);
        //add view element initialization code in this block
        initView(fragmentView);
        return fragmentView;
    }

    private void initView(View rootView) {
        timeSpinner = rootView.findViewById(R.id.time_span_selector_spinner);
        barChart = rootView.findViewById(R.id.app_usage_bar_chart);
        safeUsageListView = rootView.findViewById(R.id.safe_usage_listview);
        unsafeUsageListView = rootView.findViewById(R.id.unsafe_usage_listview);
    }

    public void prepareTimeRangeSpinner() {

    }

    public void prepareBarChartData() {

    }

    public void prepareSafeUsageAppsListData() {
    }

    public void prepareUnsafeUsageAppsListData() {
    }

}
