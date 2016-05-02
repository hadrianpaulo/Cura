package com.beh.colim.cura.activities;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.beh.colim.cura.R;
import com.beh.colim.cura.utils.DrugDetails;
import com.beh.colim.cura.utils.LocationDetails;
import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddReportActivity extends AppCompatActivity {

    private static final int PLACE_PICKER_REQUEST = 1;
    private CuraApplication _app;
    private Toolbar _toolbar;
    private String drugstore_name = "";
    private String lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        _app = (CuraApplication) getApplication();

        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _toolbar.setTitle("Login");
        setSupportActionBar(_toolbar);

    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            drugstore_name = place.getName().toString();
            lat = String.valueOf(place.getLatLng().latitude);
            lon = String.valueOf(place.getLatLng().longitude);
            Toast.makeText(this, "Picked " + place.getName(), Toast.LENGTH_SHORT).show();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void add(View view){

        Firebase ref = new Firebase("https://cura.firebaseio.com/drugs");
        String drug_name = ((EditText) findViewById(R.id.et_addReport_drugname))
                .getText().toString();
        String price = ((EditText) findViewById(R.id.et_addReport_price))
                .getText().toString();
        Boolean availability = ((Switch) findViewById(R.id.sw_addReport_availability)).isChecked();

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.US);
        String date = df.format(Calendar.getInstance().getTime());

        LocationDetails someplace = new LocationDetails(lat, lon, drugstore_name);
        DrugDetails sample = new DrugDetails(price, availability, date, someplace);
        ref.child(drug_name).push().setValue(sample);

        Toast.makeText(this, "Report is added.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddReportActivity.this, MenuActivity.class);
        startActivity(intent);

    }

    public void pickLocation(View view) {
        try {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(AddReportActivity.this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException
                | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void clear(View view){
        ((EditText) findViewById(R.id.et_addReport_drugname)).setText("");
        ((EditText) findViewById(R.id.et_addReport_price)).setText("");
        ((Switch) findViewById(R.id.sw_addReport_availability)).setChecked(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_report, menu);
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
