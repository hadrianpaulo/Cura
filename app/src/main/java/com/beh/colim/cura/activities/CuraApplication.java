package com.beh.colim.cura.activities;

import android.app.Application;

import com.beh.colim.cura.utils.DrugDetails;
import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by jerelynco on 4/25/16.
 */
public class CuraApplication extends Application {
    private String username = "";
    private String lat = "";
    private String lon = "";
    private Firebase firebaseRef;
    private ArrayList<DrugDetails> multipleDrugDetails;
    private String drugName;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Firebase getFirebaseRef() {
        return firebaseRef;
    }

    public void setFirebaseRef(Firebase firebaseRef) {
        this.firebaseRef = firebaseRef;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public ArrayList<DrugDetails> getMultipleDrugDetails() {
        return multipleDrugDetails;
    }

    public void setMultipleDrugDetails(ArrayList<DrugDetails> multipleDrugDetails) {
        this.multipleDrugDetails = multipleDrugDetails;
    }
}
