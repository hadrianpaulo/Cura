package com.beh.colim.cura.activities;

import android.app.Application;

/**
 * Created by jerelynco on 4/25/16.
 */
public class CuraApplication extends Application {
    private String username = "";
    private String lat = "";
    private String lon = "";

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
}
