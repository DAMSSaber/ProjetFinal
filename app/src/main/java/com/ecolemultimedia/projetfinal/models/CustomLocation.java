package com.ecolemultimedia.projetfinal.models;

import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saber on 06/11/15.
 */
public class CustomLocation {

    public double latitude = 0;
    public double longitude = 0;


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void initLocation(JSONObject jsonObject) {


        try {
            if (jsonObject.has("latitude")) {

                setLatitude(jsonObject.getDouble("latitude"));

            }
            if (jsonObject.has("longitude")) {
                setLongitude(jsonObject.getDouble("longitude"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initAndroidLocation(Location loc) {

        if (loc != null) {
            setLatitude(loc.getLatitude());
            setLongitude(loc.getLongitude());
        }

    }
}
