package com.beh.colim.cura.activities;

import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beh.colim.cura.R;

public class SearchActivity extends AppCompatActivity {

    private Toolbar _toolbar;
    EditText _drugstoreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _toolbar.setTitle("Search");
        setSupportActionBar(_toolbar);

        _drugstoreName = (EditText) findViewById(R.id.et_search_drug_name);

    }

    public void search(View view) {
        String searchedDrugstore = _drugstoreName.getText().toString();

        Intent intent = new Intent(SearchActivity.this, LocatorActivity.class);
        intent.putExtra("DRUGSTORE", searchedDrugstore);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.logout_menu_item) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
