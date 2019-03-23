package com.mar.model;

import android.graphics.drawable.Drawable;

public class MonitorItem {
    private String dailyAverageUser;
    private String userLevel;
    private String appName;
    private Drawable appIcon;
    private float progressValue;

    public MonitorItem() {
    }

    public MonitorItem(String dailyAverageUser, String userLevel, String appName, Drawable appIcon, float progressValue) {
        this.dailyAverageUser = dailyAverageUser;
        this.userLevel = userLevel;
        this.appName = appName;
        this.appIcon = appIcon;
        this.progressValue = progressValue;
    }

    public String getDailyAverageUser() {
        return dailyAverageUser;
    }

    public void setDailyAverageUser(String dailyAverageUser) {
        this.dailyAverageUser = dailyAverageUser;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getLogForItem() {
        String title = null;
        title = dailyAverageUser + " - " + userLevel + " - " + appName + " - " + progressValue;
        return title;
    }

    public float getProgressValue() {
        return progressValue;
    }

    public void setProgressValue(float progressValue) {
        this.progressValue = progressValue;
    }

}
