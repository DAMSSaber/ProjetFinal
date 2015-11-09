package com.ecolemultimedia.projetfinal.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ecolemultimedia.projetfinal.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class SplashScreen extends Activity {

    public static final String TAG = SplashScreen.class.getSimpleName();
    protected boolean _active = true;
    protected int _splashTime = 1000;
    public Context mContext;
    public Firebase mFirebaseRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        mContext = this.getApplicationContext();
        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/");

        Thread splashTread = new Thread() {
            @Override
            public void run() {


                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(500);
                        if (_active) {
                            waited += 100;

                        }
                    }
                } catch (Exception e) {

                } finally {

                    checkIfLogged();
                    overridePendingTransition(0, 0);

                    finish();
                }
            }

            ;
        };
        splashTread.start();

    }

    public void checkIfLogged() {
        if (mFirebaseRef.getAuth() != null) {
            mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    // do some stuff once

                    if (String.valueOf(snapshot.child("sex").getValue()) == "null" || String.valueOf(snapshot.child("birthdate").getValue()) == "null" || String.valueOf(snapshot.child("username").getValue()) == "null") {
                        Intent intent = new Intent(SplashScreen.this, InitialUserInformationsActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashScreen.this, MapActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d("•••", "error");
                }
            });
        } else {
            Intent intent = new Intent(SplashScreen.this, LogInActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }
}