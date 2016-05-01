package com.beh.colim.cura.activities;

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

    private CuraApplication _app;
    private Toolbar _toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        _app = (CuraApplication) getApplication();

        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _toolbar.setTitle("Login");
        setSupportActionBar(_toolbar);
    }

    public void add(View view){


        Firebase ref = new Firebase("https://cura.firebaseio.com/drugs");
        String drug_name = ((EditText) findViewById(R.id.et_addReport_drugname))
                .getText().toString();
        String price = ((EditText) findViewById(R.id.et_addReport_price))
                .getText().toString();
        String drugstore_name = ((EditText) findViewById(R.id.et_addReport_drugstore))
                .getText().toString();
        Boolean availability = ((Switch) findViewById(R.id.sw_addReport_availability)).isChecked();

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.US);
        String date = df.format(Calendar.getInstance().getTime());

        LocationDetails someplace = new LocationDetails(_app.getLat(), _app.getLon(), drugstore_name);
        DrugDetails sample = new DrugDetails(price, availability, date);
        sample.addLocation(someplace);
        ref.child(drug_name).push().setValue(sample);

        Toast.makeText(this, "Report is added.", Toast.LENGTH_SHORT).show();
    }

    public void clear(View view){

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
