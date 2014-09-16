package com.example.sqlliteexample.app.model;

import android.content.ContentValues;

/**
 * Created by dkocian on 5/29/14.
 */
public class Location {
    public static final String ID_COL = "_id";
    public static final String ZIP_COL = "zip";
    public static final String CITY_COL = "city";
    public static final String REGION_COL = "region";
    public static final String LAST_ALERT_COL = "last_alert";
    public static final String ALERT_ENABLED_COL = "alert_enabled";
    public static final String[] COLS = new String[]
            {ID_COL, ZIP_COL, CITY_COL, REGION_COL, LAST_ALERT_COL, ALERT_ENABLED_COL};
    private long id;
    private long lastAlert;
    private int alertEnabled;
    private String zip;
    private String city;
    private String region;

    public Location() {
    }

    public Location(long id, String zip, String city, String region, long lastAlert, int alertEnabled) {
        this(zip, city, region, lastAlert, alertEnabled);
        this.id = id;
    }

    public Location(String zip, String city, String region, long lastAlert, int alertEnabled) {
        this.zip = zip;
        this.city = city;
        this.region = region;
        this.lastAlert = lastAlert;
        this.alertEnabled = alertEnabled;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ZIP_COL, zip);
        contentValues.put(CITY_COL, city);
        contentValues.put(REGION_COL, region);
        contentValues.put(LAST_ALERT_COL, lastAlert);
        contentValues.put(ALERT_ENABLED_COL, alertEnabled);
        return contentValues;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLastAlert() {
        return lastAlert;
    }

    public void setLastAlert(long lastAlert) {
        this.lastAlert = lastAlert;
    }

    public int getAlertEnabled() {
        return alertEnabled;
    }

    public void setAlertEnabled(int alertEnabled) {
        this.alertEnabled = alertEnabled;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return zip;
    }
}
