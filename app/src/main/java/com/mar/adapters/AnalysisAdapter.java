package com.mar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mar.R;
import com.mar.model.AnalysisItem;

import java.util.ArrayList;

public class AnalysisAdapter extends ArrayAdapter<AnalysisItem> {
    private static final String TAG = "AnalysisAdapter";
    private Context mContext;
    private String listType;
    private ArrayList<AnalysisItem> safeUsage;
    private ArrayList<AnalysisItem> unsafeUsage;
    private LayoutInflater inflator;

    public AnalysisAdapter(Context context, ArrayList<AnalysisItem> list, @NonNull String type) {
        super(context, R.layout.item_analysis_listview, list);
        this.mContext = context;
        inflator = LayoutInflater.from(mContext);
        if (type.equals(AnalysisListType.SAFE)) {
            this.safeUsage = list;
            listType = AnalysisListType.SAFE;
        } else if (type.equals(AnalysisListType.UNSAFE)) {
            this.unsafeUsage = list;
            listType = AnalysisListType.UNSAFE;
        }
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnalysisItem item = getItem(position);
        AnalysisViewHolder holder;
        final View analysisusageview;
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.item_analysis_listview, parent, false);
            holder = new AnalysisViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AnalysisViewHolder) convertView.getTag();
        }
        assert item != null;
        holder.appIconIv.setImageDrawable(item.getAppIcon());
        holder.appNameTv.setText(item.getAppName());
        holder.addictionLevelTv.setText(item.getAddictionLevel());
        holder.usageTimeTv.setText(item.getUsageTime());
        return convertView;
    }

    @Override
    public AnalysisItem getItem(int position) {
        if (listType.equals(AnalysisListType.SAFE)) {
            return safeUsage.get(position);
        }
        if (listType.equals(AnalysisListType.UNSAFE)) {
            return unsafeUsage.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (listType.equals(AnalysisListType.SAFE)) {
            return safeUsage.size();
        }
        if (listType.equals(AnalysisListType.UNSAFE)) {
            return unsafeUsage.size();
        }
        return 0;
    }

    private static class AnalysisViewHolder {
        ImageView appIconIv;
        TextView appNameTv;
        TextView addictionLevelTv;
        TextView usageTimeTv;

        AnalysisViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            appIconIv = rootView.findViewById(R.id.app_icon_iv);
            appNameTv = rootView.findViewById(R.id.app_name_tv);
            addictionLevelTv = rootView.findViewById(R.id.addiction_level_tv);
            usageTimeTv = rootView.findViewById(R.id.usage_time_tv);
        }
    }

    public static class AnalysisListType {
        public static final String SAFE = "safe";
        public static final String UNSAFE = "unsafe";
    }
}
