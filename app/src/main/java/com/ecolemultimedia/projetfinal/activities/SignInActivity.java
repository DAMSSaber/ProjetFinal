package com.ecolemultimedia.projetfinal.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.models.User;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmailET;
    private EditText mPasswordET;
    private Context mContext;
    private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mContext = getApplicationContext();
        mEmailET = (EditText) findViewById(R.id.email_input);
        mPasswordET = (EditText) findViewById(R.id.password_input);
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
        if (mEmailET.getText() != null) {
            if (mPasswordET.getText() != null) {
                mFirebaseRef.createUser(String.valueOf(mEmailET.getText()), String.valueOf(mPasswordET.getText()), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(final Map<String, Object> result) {

                        Log.v("Debeug", "===result====" + result);
                        Log.v("Debeug", "=======" + result.get("uid"));

                        User currentUser = new User();
                        currentUser.setEmail(String.valueOf(mEmailET.getText()));
                        currentUser.setIsVisible(true);
                        currentUser.setUid(String.valueOf(result.get("uid")));

                        Log.v("Debeug", "====user===" + currentUser.getEmail());
                        Firebase user = mFirebaseRef.child("users/" + String.valueOf(result.get("uid")));

                        Log.v("Debeug", "====user===" + user);


                        user.setValue(currentUser);



                        //TODO: utilser string
                        Toast success = Toast.makeText(SignInActivity.this, "compte créé, vous pouvez vous connecter", Toast.LENGTH_LONG);
                        success.show();
                        Intent intent = new Intent(SignInActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // there was an error
                        Toast error = Toast.makeText(SignInActivity.this, "" + firebaseError.getMessage(), Toast.LENGTH_LONG);
                        error.show();
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
