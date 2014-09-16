package com.example.sqlliteexample.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sqlliteexample.app.model.Location;

/**
 * Created by dkocian on 5/29/14.
 */
public class LocationDbHelper extends SQLiteOpenHelper {
    public static final String DEVICE_ALERT_ENABLED_ZIP = "DAEZ99";
    public static final String DB_NAME = "w_alert";
    public static final String DB_TABLE = "w_alert_loc";
    public static final int DB_VERSION = 1;

    private static final String CLASS_NAME = LocationDbHelper.class.getSimpleName();
    public static final String DB_CREATE = "CREATE TABLE "
            + LocationDbHelper.DB_TABLE
            + " (" + Location.ID_COL + " INTEGER PRIMARY KEY, " + Location.ZIP_COL + " TEXT UNIQUE NOT NULL, "
            + Location.CITY_COL + " TEXT, " + Location.REGION_COL + " TEXT, " + Location.LAST_ALERT_COL + " INTEGER, "
            + Location.ALERT_ENABLED_COL + " INTEGER)";

    public LocationDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(CLASS_NAME,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + LocationDbHelper.DB_TABLE);
        onCreate(db);
    }
}
