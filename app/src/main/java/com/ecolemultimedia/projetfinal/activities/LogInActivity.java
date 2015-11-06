package com.ecolemultimedia.projetfinal.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LogInActivity extends AppCompatActivity {

    private EditText mEmailET;
    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mLogInButton;
    private Button mSignInButton;
    private Button mFacebookConnectButton;
    private Context mContext;
    private Firebase mFirebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mContext = getApplicationContext();
        mEmailET = (EditText)findViewById(R.id.email_input);
        mPasswordET = (EditText)findViewById(R.id.password_input);
        mLogInButton = (Button)findViewById(R.id.log_in_button);
        mSignInButton = (Button)findViewById(R.id.sign_in_button);
        mFacebookConnectButton = (Button)findViewById(R.id.facebook_connect_button);
        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/");

        //checkIfLogged();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void logIn(View view) {
        if(mEmailET.getText() != null) {
            if(mPasswordET.getText() != null) {
                //Handler for firebase password authentication
                Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // authentication successful

                        setCurrentUserSharedPreferences(authData.getUid());

                        //TODO: check if user informations are filled
                        //if not :
                        //Intent intent = new Intent(this, InitialUserInformationsActivity.class);
                        //startActivity(intent);
                        //else :
                        Intent intent = new Intent(LogInActivity.this, MapActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        //TODO: utiliser string
                        Toast noPassword = Toast.makeText(mContext, firebaseError.getMessage(), Toast.LENGTH_LONG);
                        noPassword.show();
                    }
                };
                    mFirebaseRef.authWithPassword(String.valueOf(mEmailET.getText()), String.valueOf(mPasswordET.getText()), authResultHandler);
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

    public void setCurrentUserSharedPreferences(String uid) {
        SharedPreferences pref = getSharedPreferences("currentUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        //TODO: set infos with DB
        //editor.putString("uid", "");
        //editor.putString("email", String.valueOf(mEmailET.getText()));
        //editor.putBoolean("isVisible", true);
        editor.commit();
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
