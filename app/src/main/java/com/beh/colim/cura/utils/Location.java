package com.beh.colim.cura.utils;

/**
 * Created by hadri on 4/29/2016.
 */
public class Location {

    private String lat = "";
    private String lon = "";
    private String name = "";

    public Location(String lat, String lon, String name) {

        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }
}
