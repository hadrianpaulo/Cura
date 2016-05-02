package com.beh.colim.cura.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.beh.colim.cura.R;
import com.beh.colim.cura.utils.DrugDetails;
import com.beh.colim.cura.utils.LocationDetails;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText _drugName;
    CuraApplication app;
    private Toolbar _toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _toolbar.setTitle("Search");
        setSupportActionBar(_toolbar);

        _drugName = (EditText) findViewById(R.id.et_search_drug_name);
        Firebase.setAndroidContext(this);
        app = ((CuraApplication) getApplication());

    }

    public void search(View view) {
        String searchedDrug = _drugName.getText().toString();
        app.setDrugName(searchedDrug);

        final ArrayList<DrugDetails> multipleDrugDetails = new ArrayList<>();
        final Firebase firebaseRef = app.getFirebaseRef().child("drugs").child(searchedDrug);
        Query queryRef = firebaseRef.orderByChild("locations/lat");


        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren()) {

                    DataSnapshot location_ss = i.child("locationDetail");
                    LocationDetails locationDetails = new LocationDetails(
                            location_ss.child("lat").getValue(String.class),
                            location_ss.child("lon").getValue(String.class),
                            location_ss.child("name").getValue(String.class));

                    DrugDetails _drugDetails =
                            new DrugDetails(i.child("price").getValue(String.class),
                                    i.child("availability").getValue(Boolean.class),
                                    i.child("date").getValue(String.class),
                                    locationDetails);


                    multipleDrugDetails.add(_drugDetails);
                    app.setMultipleDrugDetails(multipleDrugDetails);
                }

                // TODO: Handle search not found

                Intent intent = new Intent(SearchActivity.this, LocatorActivity.class);
                startActivity(intent);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

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
