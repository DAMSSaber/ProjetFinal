package com.ecolemultimedia.projetfinal.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecolemultimedia.projetfinal.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    private EditText mEmailET;
    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mLogInButton;
    private Button mSignInButton;
    private Button mFacebookConnectButton;
    private Context mContext;
    private Firebase myFirebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mContext = getApplicationContext();
        mUsernameET = (EditText)findViewById(R.id.age_input);
        mPasswordET = (EditText)findViewById(R.id.password_input);
        mLogInButton = (Button)findViewById(R.id.log_in_button);
        mSignInButton = (Button)findViewById(R.id.sign_in_button);
        mFacebookConnectButton = (Button)findViewById(R.id.facebook_connect_button);
        Firebase myFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/");

        checkIfLogged();
        mPasswordET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    logIn(null);
                }
                return false;
            }
        });
    }

    public void checkIfLogged() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //TODO: utiliser string
            Toast success = Toast.makeText(mContext, "Bienvenue, " + currentUser.getUsername(), Toast.LENGTH_LONG);
            success.show();
            //check if user informations are filled
            if(currentUser.getDate("userBirthday") == null || currentUser.getString("sex") == null || currentUser.getJSONArray("selfies") == null) {
                //if profil not complete show profil
                // TODO: utiliser string
                Toast notFilledProfil = Toast.makeText(mContext, "Votre profil n'est pas complet", Toast.LENGTH_LONG);
                success.show();
                Intent intent = new Intent(this, InitialUserInformationsActivity.class);
                startActivity(intent);
            } else {
                //else show map
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
            }
        } else {
            //not logged
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void logIn(View view) {
        if(mUsernameET.getText() != null) {
            if(mPasswordET.getText() != null) {
// Create a handler to handle the result of the authentication
                Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authenticated successfully with payload authData

                        Intent intent = new Intent(LogInActivity.this, MapActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Authenticated failed with error firebaseError
                    }
                };
// Authenticate users with a custom Firebase token
                myFirebaseRef.authWithCustomToken("<token>", authResultHandler);
// Alternatively, authenticate users anonymously
                myFirebaseRef.authAnonymously(authResultHandler);
// Or with an email/password combination
                myFirebaseRef.authWithPassword("jenny@example.com", "correcthorsebatterystaple", authResultHandler);
// Or via popular OAuth providers ("facebook", "github", "google", or "twitter")
                myFirebaseRef.authWithOAuthToken("<provider>", "<oauth-token>", authResultHandler);



//                ParseUser.logInInBackground(String.valueOf(mUsernameET.getText()), String.valueOf(mPasswordET.getText()), new LogInCallback() {
//                    public void done(ParseUser user, ParseException e) {
//                        if (user != null) {
//                            checkIfLogged();
//                        } else {
//                            //TODO: utiliser string
//                            Toast wrongInfos = Toast.makeText(mContext, "Vos informations sont eronnées", Toast.LENGTH_LONG);
//                            wrongInfos.show();
//                        }
//                    }
//                });
            } else {
                //TODO: utiliser string
                Toast noPassword = Toast.makeText(mContext, "Vous devez renseigner votre mot de passe", Toast.LENGTH_LONG);
                noPassword.show();
            }
        } else {
            //TODO: utiliser string
            Toast noUsername = Toast.makeText(mContext, "Vous devez renseigner votre nom d'utilisateur", Toast.LENGTH_LONG);
            noUsername.show();
        }
    }

    public void goToSignInActivity(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void clickOnFacebookConnectButton(View view) {
//        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
//            @Override
//            public void done(ParseUser user, ParseException err) {
//                if (user == null) {
//                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
//                } else if (user.isNew()) {
//                    Log.d("MyApp", "User signed up and logged in through Facebook!");
//                } else {
//                    Log.d("MyApp", "User logged in through Facebook!");
//                }
//            }
//        });
    }
}
