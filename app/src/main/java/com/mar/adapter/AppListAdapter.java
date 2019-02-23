package com.mar.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mar.R;

import java.util.List;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListViewHolder> {
    private static final String TAG = "AppListAdapter";
    private Context mContext;
    private static List<ApplicationInfo> mAppInstalledList;

    public AppListAdapter(Context context, List<ApplicationInfo> mAppInstalledList) {
        AppListAdapter.mAppInstalledList = mAppInstalledList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public AppListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View appItemView = inflater.inflate(R.layout.item_app_list, viewGroup, false);
        return new AppListViewHolder(appItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppListViewHolder appListViewHolder, int i) {
        //bind data to view
        ApplicationInfo applicationInfo = mAppInstalledList.get(i);
        TextView appname = appListViewHolder.tv_app_name;
        appname.setText(applicationInfo.loadLabel(mContext.getPackageManager()));
        TextView appno = appListViewHolder.tv_app_no;
        appno.setText(String.valueOf(i + 1));
        Log.i(TAG, "onBindViewHolder: Data AppName  - " + applicationInfo.loadLabel(mContext.getPackageManager()) + " No - " + i);
    }

    @Override
    public int getItemCount() {
        return mAppInstalledList.size();
    }

    class AppListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_app_no, tv_app_name;
        ImageView iv_lock_toggle;

        AppListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_app_no = itemView.findViewById(R.id.tv_app_no);
            tv_app_name = itemView.findViewById(R.id.tv_app_item_name);
            iv_lock_toggle = itemView.findViewById(R.id.iv_app_lock_toggle);
        }
    }
}
