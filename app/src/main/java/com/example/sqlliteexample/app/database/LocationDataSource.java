package com.example.sqlliteexample.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlliteexample.app.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkocian on 5/29/14.
 */
public class LocationDataSource {
    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;

    public LocationDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() {
        if (this.db == null) {
            this.db = dbHelper.getWritableDatabase();
        }
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public void createLocation(Location location) {
        ContentValues values = getLocationContentValues(location);
        long id = this.db.insertOrThrow(MySQLiteHelper.DB_TABLE, null, values);
        location.setId(id);
    }

    public void update(Location location) {
        ContentValues values = getLocationContentValues(location);
        this.db.update(MySQLiteHelper.DB_TABLE, values, Location.ID_COL + "=" + location.getId(), null);
    }

    public int delete(long id) {
        return this.db.delete(MySQLiteHelper.DB_TABLE, Location.ID_COL + "=" + id, null);
    }

    public int delete(String zip) {
        return this.db.delete(MySQLiteHelper.DB_TABLE, Location.ZIP_COL + "=" + zip, null);
    }

    public Location get(String zip) {
        Location location = null;
        Cursor cursor = this.db.query(true, MySQLiteHelper.DB_TABLE, Location.COLS,
                Location.ZIP_COL + " = '" + zip + "'", null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            location = cursorToLocation(cursor);
        }
        return location;
    }

    public List<Location> getAll() {
        ArrayList<Location> allLocationsList = new ArrayList<>();
        Cursor cursor = this.db.query(MySQLiteHelper.DB_TABLE, Location.COLS, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToLocation(cursor);
            if (!location.getZip().equals(MySQLiteHelper.DEVICE_ALERT_ENABLED_ZIP)) {
                allLocationsList.add(location);
            }
            cursor.moveToNext();
        }
        return allLocationsList;
    }

    private ContentValues getLocationContentValues(Location location) {
        ContentValues values = new ContentValues();
        values.put(Location.ZIP_COL, location.getZip());
        values.put(Location.CITY_COL, location.getCity());
        values.put(Location.REGION_COL, location.getRegion());
        values.put(Location.LAST_ALERT_COL, location.getLastAlert());
        values.put(Location.ALERT_ENABLED_COL, location.getAlertEnabled());
        return values;
    }

    private static Location cursorToLocation(Cursor c) {
        return new Location(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getLong(4), c.getInt(5));
    }
}
