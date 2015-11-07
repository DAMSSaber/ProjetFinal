package com.ecolemultimedia.projetfinal.models;

import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by damien on 05/11/2015.
 */
public class User {

    String uid=null;
    String email=null;
    String username=null;
    CustomLocation location=null;
    Boolean isVisible=null;
    ArrayList<String> selfies=null;
    Integer usedSelfieIndex=null;
    String sex=null;
    String birthdate=null;
    Map<String, String> links=null;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CustomLocation getLocation() {
        return location;
    }

    public void setLocation(CustomLocation location) {
        this.location = location;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public ArrayList<String> getSelfies() {
        return selfies;
    }

    public void setSelfies(ArrayList<String> selfies) {
        this.selfies = selfies;
    }

    public Integer getUsedSelfieIndex() {
        return usedSelfieIndex;
    }

    public void setUsedSelfieIndex(Integer usedSelfieIndex) {
        this.usedSelfieIndex = usedSelfieIndex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

    public void addLink(String key, String value) {
        this.links.put(key, value);
    }

    public void initUser(JSONObject jsonObject) {


        try {
            if (jsonObject.has("uid")) {
                setUid(jsonObject.getString("uid"));
            }
            if (jsonObject.has("username")) {
                setUsername(jsonObject.getString("username"));
            }
            if (jsonObject.has("email")) {
                setEmail(jsonObject.getString("email"));
            }
            if (jsonObject.has("birthdate")) {
                setBirthdate(jsonObject.getString("birthdate"));

            }

            if (jsonObject.has("birthdate")) {
                setBirthdate(jsonObject.getString("birthdate"));
            }
            if (jsonObject.has("isVisible")) {
                setIsVisible(jsonObject.getBoolean("isVisible"));
            }

            if (jsonObject.has("sex")) {
                setSex(jsonObject.getString("sex"));
            }

            if (jsonObject.has("links")) {
                Map<String, String> list = new HashMap<String, String>();
                //JSONObject json = new JSONObject();
                for (Iterator iterator = jsonObject.getJSONObject("links").keys(); iterator.hasNext();) {
                    String key = String.valueOf(iterator.next());
                    String value = String.valueOf(jsonObject.getJSONObject("links").get(key));
                    list.put(key, value);
                }
                setLinks(list);
            }

            if(jsonObject.has("location")){
                CustomLocation location =new CustomLocation();
                location.initLocation(jsonObject.getJSONObject("location"));
                setLocation(location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
