package com.example.sqlliteexample.app;

import android.app.ListActivity;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.sqlliteexample.app.database.LocationDataSource;
import com.example.sqlliteexample.app.model.Location;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private static final String CLASS_NAME = MainActivity.class.getSimpleName();
    private LocationDataSource locationDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        locationDataSource = new LocationDataSource(this);
        locationDataSource.open();
        List<Location> locationList = locationDataSource.getAll();
        ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationList);
        setListAdapter(adapter);
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
        ArrayAdapter<Location> adapter = (ArrayAdapter<Location>) getListAdapter();
        Location location;
        switch (view.getId()) {
            case R.id.add:
                location = new Location(new BigInteger(16, new Random()).toString(10), "San Antonio", "Bexar County",
                    0L, 0);
                try {
                    locationDataSource.createLocation(location);
                    adapter.add(location);
                } catch (SQLiteException e) {
                    Log.e(CLASS_NAME, e.getMessage());
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    location = (Location) getListAdapter().getItem(0);
                    if (locationDataSource.delete(location.getId()) > 0) {
                        adapter.remove(location);
                    }
                }                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        locationDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        locationDataSource.close();
        super.onPause();
    }
}
