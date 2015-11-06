package com.ecolemultimedia.projetfinal.utils;

import com.firebase.client.Firebase;

/**
 * Created by saber on 04/11/15.
 */
public class ServiceFirebase {
    public static final String TAG = ServiceFirebase.class
            .getSimpleName();


    private  Firebase myFirebaseRef = null;

    private String mUserName=null;
    private static ServiceFirebase mInstance = null;


    public static ServiceFirebase getInstance() {
        if (mInstance == null) {
            mInstance = new ServiceFirebase();
        }

        return mInstance;
    }


    public void initFirebise() {
        myFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/");
        setMyFirebaseRef(myFirebaseRef);
    }


    public  Firebase getMyFirebaseRef() {
        return this.myFirebaseRef;
    }

    public  void setMyFirebaseRef(Firebase myFirebaseRef) {
       this.myFirebaseRef = myFirebaseRef;
    }

}
