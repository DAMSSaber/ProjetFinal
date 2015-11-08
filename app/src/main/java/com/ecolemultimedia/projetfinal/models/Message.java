package com.ecolemultimedia.projetfinal.models;


import org.json.JSONException;
import org.json.JSONObject;

public class Message {

    private String message = null;
    private String author = null;
    private String type = null;
    private String date = null;


    public void initMessage(JSONObject jsonObject) throws JSONException {

        if (jsonObject.has("message")) {
            setMessage(jsonObject.getString("message"));
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
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
