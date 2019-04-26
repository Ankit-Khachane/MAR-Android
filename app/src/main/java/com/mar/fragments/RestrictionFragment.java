package com.mar.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mar.R;
import com.mar.model.AppModel;
import com.mar.services.AppLockService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RestrictionFragment extends Fragment {
    private PieChart pieChart;
    private List<PieEntry> pieEntries;
    private PieDataSet pieDataSet;
    private PieData pieData;
    private List<Integer> colorlist;
    private Intent serviceintent;
    private Button lock_btn, unlock_btn;
    private List<AppModel> data;
    private ArrayList<AppModel> unsafeApps;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_restriction, container, false);
        //add view element initialization code in this block
        serviceintent = new Intent(getContext(), AppLockService.class);
        pieChart = fragmentView.findViewById(R.id.pie_chart_view);
        lock_btn = fragmentView.findViewById(R.id.btn_lock);
        unlock_btn = fragmentView.findViewById(R.id.btn_unlock);
        pieChart.animateXY(1000, 2000, Easing.EaseInOutSine);
        pieChart.getDescription().setEnabled(false);

        pieEntries = new ArrayList<PieEntry>();
        /*pieEntries.add(0, new PieEntry(34.5f, "WhatsApp"));
        pieEntries.add(1, new PieEntry(25, "Facebook"));
        pieEntries.add(2, new PieEntry(25, "Instagram"));
        pieEntries.add(3, new PieEntry(15.5f, "Tik Tok"));*/
        unsafeApps = AnalysisFragment.unsafeApps;
        for (int i = 0; i < unsafeApps.size(); i++) {
            AppModel am = unsafeApps.get(i);
            assert am != null;
            pieEntries.add(i, new PieEntry(am.getUsageInPercent(), am.getAppName()));
        }

        pieDataSet = new PieDataSet(pieEntries, "Unsafe !");

        /*colorlist = new ArrayList<>();
        colorlist.add(getResources().getColor(R.color.whatsapp_color));
        colorlist.add(getResources().getColor(R.color.facebook_color));
        colorlist.add(getResources().getColor(R.color.instagram_color));
        colorlist.add(getResources().getColor(R.color.tiktok_color));
*/
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(18);
        pieDataSet.setFormSize(16);
        pieDataSet.setSliceSpace(3);
        pieDataSet.setForm(Legend.LegendForm.CIRCLE);
        pieDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat decimalFormat = new DecimalFormat();
                return decimalFormat.format(value) + " %";
            }
        });
        pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.setHoleRadius(45);
        pieChart.setTransparentCircleRadius(35);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.setTouchEnabled(false);
        pieChart.invalidate();
        return fragmentView;
    }
}
