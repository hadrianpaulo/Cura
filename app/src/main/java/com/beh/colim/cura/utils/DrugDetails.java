package com.beh.colim.cura.utils;

import java.util.ArrayList;

/**
 * Created by hadri on 4/29/2016.
 */
public class DrugDetails {
    private String price = "";
    private boolean availability;
    private String date = "";
    private ArrayList<LocationDetails> locations = new ArrayList<>();

    public DrugDetails(String price, boolean availability, String date) {
        this.price = price;
        this.availability = availability;
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<LocationDetails> getLocations() {
        return locations;
    }

    public void addLocation(LocationDetails location) {
        this.locations.add(location);
    }
}
