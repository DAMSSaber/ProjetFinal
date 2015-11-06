package com.ecolemultimedia.projetfinal.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ecolemultimedia.projetfinal.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmailET;    private EditText mPasswordET;
    private Context mContext;
    private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mContext = getApplicationContext();
        mEmailET = (EditText)findViewById(R.id.email_input);
        mPasswordET = (EditText)findViewById(R.id.password_input);
        mPasswordET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    signIn(null);
                }
                return false;
            }
        });
        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/");
    }

    public void signIn(View view) {
        if(mEmailET.getText() != null) {
                if(mPasswordET.getText() != null) {
                    mFirebaseRef.createUser(String.valueOf(mEmailET.getText()), String.valueOf(mPasswordET.getText()), new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            System.out.println("Successfully created user account with uid: " + result.get("uid"));
                            //TODO: use this instead for prod :
                            //Intent intent = new Intent(this, InitialUserInformationsActivity.class);
                            //startActivity(intent);
                            //dev :
                            Intent intent = new Intent(SignInActivity.this, MapActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onError(FirebaseError firebaseError) {
                            // there was an error
                        }
                    });
                } else {
                    //TODO: utiliser string
                    Toast noEmail = Toast.makeText(mContext, "Veuillez renseigner votre mot de passe", Toast.LENGTH_LONG);
                    noEmail.show();
                }
        } else {
            //TODO: utiliser string
            Toast noPassword = Toast.makeText(mContext, "Veuillez renseigner votre email", Toast.LENGTH_LONG);
            noPassword.show();
        }
    }
}
