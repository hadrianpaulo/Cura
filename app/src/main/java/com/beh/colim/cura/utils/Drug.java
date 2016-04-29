package com.beh.colim.cura.utils;

/**
 * Created by hadri on 4/29/2016.
 */
public class Drug {
    private String price = "";
    private boolean availability;
    private String date = "";
    private Location location;

    public Drug(String price, boolean availability, String date, Location location) {
        this.price = price;
        this.availability = availability;
        this.date = date;
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public String getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }
}
