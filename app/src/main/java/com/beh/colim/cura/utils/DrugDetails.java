package com.beh.colim.cura.utils;

/**
 * Created by hadri on 4/29/2016.
 */
public class DrugDetails {
    private String price = "";
    private boolean availability;
    private String date = "";
    private LocationDetails locationDetail;

    public DrugDetails(String price, boolean availability, String date, LocationDetails locationDetail) {
        this.price = price;
        this.availability = availability;
        this.date = date;
        this.locationDetail = locationDetail;
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

    public LocationDetails getLocationDetail() {
        return locationDetail;
    }
}
