package com.mar.fragments;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mar.R;
import com.mar.adapters.MonitorAdapter;
import com.mar.model.MonitorItem;
import com.mar.utils.AppUsageDataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonitorFragment extends Fragment {
    private static final String TAG = "MonitorFragment";
    private ListView monitorListView;
    private MonitorAdapter monitorAdapter;
    private ArrayList<MonitorItem> monitorData;
    private List<ApplicationInfo> restrictedAppFromSystem;
    private AppUsageDataUtils appUsageDataUtils;

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: Monitor Fragment started");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onStart: Monitor Fragment Resumed");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View monitorFragmentView = inflater.inflate(R.layout.fragment_monitor, container, false);
        monitorListView = monitorFragmentView.findViewById(R.id.monitor_listview);
        monitorData = new ArrayList<MonitorItem>();
        restrictedAppFromSystem = new ArrayList<>();
        appUsageDataUtils = new AppUsageDataUtils(Objects.requireNonNull(getContext()));
        restrictedAppFromSystem = AppUsageDataUtils.getRestrictedAppsInfoFromSystem();
        for (ApplicationInfo a : restrictedAppFromSystem) {
            MonitorItem monitorItem = new MonitorItem(
                    "2 Hr",
                    " Normal",
                    appUsageDataUtils.getPackageManager().getApplicationLabel(a).toString(),
                    a.loadIcon(appUsageDataUtils.getPackageManager()),
                    50);
            monitorData.add(monitorItem);
            Log.i(TAG, "onCreateView: " + monitorItem.getLogForItem());
        }
        monitorAdapter = new MonitorAdapter(getContext(), monitorData);
        monitorListView.setAdapter(monitorAdapter);
        monitorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonitorItem item = monitorData.get(position);
                Toast.makeText(getContext(), "Item Clicked " + item.getAppName(), Toast.LENGTH_SHORT).show();
            }
        });

        return monitorFragmentView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onStart: Monitor Fragment Paused");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStart: Monitor Fragment Stopped");

    }
}
