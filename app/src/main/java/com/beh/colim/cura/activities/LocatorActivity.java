package com.beh.colim.cura.activities;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.beh.colim.cura.R;
import com.beh.colim.cura.utils.DrugDetails;

import java.util.ArrayList;

public class LocatorActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CuraApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);

        app = ((CuraApplication) getApplication());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available. This callback is triggered when the map is ready to be
     * used. This is where we can add markers or lines, add listeners or move the camera. In this
     * case, we just add a marker near Sydney, Australia. If Google Play services is not installed
     * on the device, the user will be prompted to install it inside the SupportMapFragment. This
     * method will only be triggered once the user has installed Google Play services and returned
     * to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        // TODO: Make info windows prettier.

        ArrayList<DrugDetails> mulitpleDrugDetails = app.getMultipleDrugDetails();


        mMap = googleMap;

        try {
            for (DrugDetails drugDetails : mulitpleDrugDetails) {
                LatLng loc = new LatLng(
                        Double.parseDouble(drugDetails.getLocationDetail().getLat()),
                        Double.parseDouble(drugDetails.getLocationDetail().getLon()));
                mMap.addMarker(new MarkerOptions().position(loc)
                        .title(drugDetails.getLocationDetail().getName())
                        .snippet("Price: " + drugDetails.getPrice()));
            }
        } catch (NullPointerException e) {
            Toast.makeText(LocatorActivity.this, "No search results found.", Toast.LENGTH_SHORT)
                    .show();
        }
        LatLng latLng = new LatLng(Double.parseDouble(app.getLat()),
                Double.parseDouble(app.getLon()));
        // move to current location
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }


}
