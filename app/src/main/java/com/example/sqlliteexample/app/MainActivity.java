package com.example.sqlliteexample.app;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.sqlliteexample.app.contentprovider.MyLocationContentProvider;
import com.example.sqlliteexample.app.model.Location;

import java.math.BigInteger;
import java.util.Random;

public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String CLASS_NAME = MainActivity.class.getSimpleName();
    private static final int LOCATION_LOADER = 0;
    private SimpleCursorAdapter cursorAdapter;
    String[] LOCATION_COLUMNS = new String[]{ Location.ZIP_COL, Location.ID_COL};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null,
                LOCATION_COLUMNS, new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(cursorAdapter);
        getLoaderManager().initLoader(LOCATION_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                Location location =
                        new Location(new BigInteger(16, new Random()).toString(10), "San Antonio", "Bexar County", 0L, 0);
                getContentResolver().insert(MyLocationContentProvider.CONTENT_URI, location.getContentValues());
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    Cursor cursor = (Cursor) getListAdapter().getItem(0);
                    if (cursor.moveToFirst()) {
                        Long id = cursor.getLong(cursor.getColumnIndex(Location.ID_COL));
                        getContentResolver().delete(MyLocationContentProvider.CONTENT_URI, Location.ID_COL + " = ?",
                                new String[]{String.valueOf(id)});
                    }
                }
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = Location.ZIP_COL + " ASC";
        return new CursorLoader(this, MyLocationContentProvider.CONTENT_URI, LOCATION_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
