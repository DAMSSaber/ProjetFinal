package com.ecolemultimedia.projetfinal.models;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Message {

    private String message = null;
    private String author = null;
    private String type = null;
    private String date = null;


    public void initMessage(JSONObject jsonObject) throws JSONException {

        if (jsonObject.has("message")) {
            String encodedString = null;
            try {
                encodedString = URLEncoder.encode(jsonObject.getString("message"), "UTF-8");
                this.message = encodedString;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //setMessage(jsonObject.getString("message"));
        }
        if (jsonObject.has("author")) {
            setAuthor(jsonObject.getString("author"));
        }

        if (jsonObject.has("type")) {
            setType(jsonObject.getString("type"));
        }
        if (jsonObject.has("date")) {

            setDate(jsonObject.getString("date"));
        }


    }

    public String getMessage() {
        String decodedString = null;
        try {
            decodedString = URLDecoder.decode(message, "UTF-8");
            return decodedString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        //return this.message;
    }

    public void setMessage(String message) {

        String encodedString = null;
        try {
            encodedString = URLEncoder.encode(message, "UTF-8");
            this.message = encodedString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
