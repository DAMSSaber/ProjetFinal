package com.ecolemultimedia.projetfinal.models;

import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saber on 04/11/15.
 */
public class User {

    private String userName = null;
    private String userMail = null;
    private String userBirthday = null;
    private String userSexe = null;
    private ArrayList<String> listOfSelfie = null;
    private Location userLocation = null;
    private Boolean isVisible = null;


    public void initUser(JSONObject jsonObject) {


        try {

            if (jsonObject.has("name")) {
                setUserName(jsonObject.getString("name"));
            }

            if (jsonObject.has("mail")) {
                setUserMail(jsonObject.getString("mail"));
            }

            if (jsonObject.has("mail")) {
                setUserMail(jsonObject.getString("mail"));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> getListOfSelfie() {
        return listOfSelfie;
    }

    public void setListOfSelfie(ArrayList<String> listOfSelfie) {
        this.listOfSelfie = listOfSelfie;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Location getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserSexe() {
        return userSexe;
    }

    public void setUserSexe(String userSexe) {
        this.userSexe = userSexe;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
