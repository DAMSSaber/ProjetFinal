package com.ecolemultimedia.projetfinal.models;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by damien on 05/11/2015.
 */
public class User {

    String uid;
    String email;
    String username;
    Location location;
    Boolean isVisible;
    ArrayList<String> selfies;
    Integer usedSelfieIndex;
    String sex;
    Date birthdate;

    public User(String uid, String email, String username, Location location, Boolean isVisible, ArrayList<String> selfies, Integer usedSelfieIndex, String sex, Date birthdate) {
        this.uid = uid;
        this.email = email;
        this.username = username;
        this.location = location;
        this.isVisible = isVisible;
        this.selfies = selfies;
        this.usedSelfieIndex = usedSelfieIndex;
        this.sex = sex;
        this.birthdate = birthdate;
    }

    public User(String uid, String email, Boolean isVisible) {
        this.uid = uid;
        this.email = email;
        this.isVisible = isVisible;
    }

    public String getUID() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Location getLocation() {
        return location;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public ArrayList<String> getSelfies() {
        return selfies;
    }

    public Integer getUsedSelfieIndex() {
        return usedSelfieIndex;
    }

    public String getSex() {
        return sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
