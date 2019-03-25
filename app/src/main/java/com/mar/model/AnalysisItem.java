package com.mar.model;

import android.graphics.drawable.Drawable;

public class AnalysisItem {
    private String appName;
    private String addictionLevel;
    private String usageTime;
    private Drawable appIcon;

    public AnalysisItem() {
    }

    public AnalysisItem(String appName, String addictionLevel, String usageTime, Drawable appIcon) {
        this.appName = appName;
        this.addictionLevel = addictionLevel;
        this.usageTime = usageTime;
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAddictionLevel() {
        return addictionLevel;
    }

    public void setAddictionLevel(String addictionLevel) {
        this.addictionLevel = addictionLevel;
    }

    public String getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(String usageTime) {
        this.usageTime = usageTime;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
