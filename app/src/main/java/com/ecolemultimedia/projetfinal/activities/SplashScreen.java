package com.ecolemultimedia.projetfinal.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ecolemultimedia.projetfinal.R;
import com.parse.Parse;


public class SplashScreen extends Activity {

    public static final String TAG = SplashScreen.class.getSimpleName();
    protected boolean _active = true;
    protected int _splashTime = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "nF9TvVOMOcRnOekpGnHc5Eb490XF5VvqnT3FbIEd", "ENLn1YKpPcX6la7NBDtAyv8lRy49K5fBVPEAfr0v");

        setContentView(R.layout.activity_splach);


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

                    Intent intent = new Intent(SplashScreen.this,
                            LogInActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                    finish();
                }
            }

            ;
        };
        splashTread.start();

    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }
}