package com.ecolemultimedia.projetfinal.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecolemultimedia.projetfinal.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmailET;
    private EditText mUsernameET;
    private EditText mPasswordET;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mContext = getApplicationContext();
        mEmailET = (EditText)findViewById(R.id.email_input);
        mUsernameET = (EditText)findViewById(R.id.username_input);
        mPasswordET = (EditText)findViewById(R.id.password_input);
    }

    public void checkIfLogged() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //TODO: utiliser string
            Toast success = Toast.makeText(mContext, "Bienvenue, " + currentUser.getUsername(), Toast.LENGTH_LONG);
            success.show();
            //check if user informations are filled
            if (currentUser.getDate("userBirthday") == null || currentUser.getString("sex") == null || currentUser.getJSONArray("selfies") == null) {
                //if profil not complete show profil
                // TODO: utiliser string
                Toast notFilledProfil = Toast.makeText(mContext, "Votre profil n'est pas complet", Toast.LENGTH_LONG);
                success.show();
                Intent intent = new Intent(this, ProfilActivity.class);
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

    public void signIn(View view) {
        if(mEmailET.getText() != null) {
            if(mUsernameET.getText() != null) {
                if(mPasswordET.getText() != null) {
                    ParseUser user = new ParseUser();
                    user.setUsername(String.valueOf(mUsernameET.getText()));
                    user.setPassword(String.valueOf(mPasswordET.getText()));
                    user.setEmail(String.valueOf(mEmailET.getText()));
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                //TODO: utiliser string
                                Toast success = Toast.makeText(mContext, "compte créé avec succès", Toast.LENGTH_LONG);
                                success.show();
                                checkIfLogged();
                            } else {
                                Toast error = Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG);
                                error.show();
                            }
                        }
                    });
                } else {
                    //TODO: utiliser string
                    Toast noEmail = Toast.makeText(mContext, "Veuillez renseigner votre email", Toast.LENGTH_LONG);
                    noEmail.show();
                }
            } else {
                //TODO: utiliser string
                Toast noUsername = Toast.makeText(mContext, "Veuillez renseigner votre nom d'utilisateur", Toast.LENGTH_LONG);
                noUsername.show();
            }
        } else {
            //TODO: utiliser string
            Toast noPassword = Toast.makeText(mContext, "Veuillez renseigner votre mot de passe", Toast.LENGTH_LONG);
            noPassword.show();
        }
    }
}