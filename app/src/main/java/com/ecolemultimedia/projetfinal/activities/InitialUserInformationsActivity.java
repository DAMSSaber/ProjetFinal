package com.ecolemultimedia.projetfinal.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.models.CustomLocation;
import com.ecolemultimedia.projetfinal.models.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class InitialUserInformationsActivity extends AppCompatActivity {

    private Firebase mFirebaseRef;
    private EditText mUsernameET;
    private RadioButton mSexManRadio;
    private RadioButton mSexWomanRadio;
    private RadioGroup mSexRadioGroup;
    private DatePicker mBirthdateDatePicker;


    SharedPreferences pref = null;
    private SharedPreferences editor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_user_informations);


        pref = getApplicationContext().getSharedPreferences("MyPref", 0);



        mUsernameET = (EditText)findViewById(R.id.username_input);
        mSexManRadio = (RadioButton)findViewById(R.id.man_radio_button);
        mSexWomanRadio = (RadioButton)findViewById(R.id.woman_radio_button);
        mSexRadioGroup = (RadioGroup)findViewById(R.id.sex_radio_group);
        mBirthdateDatePicker = (DatePicker)findViewById(R.id.birthdate_picker);

        //long yourDateMillis = System.currentTimeMillis() - (18 * 365 * 24 * 60 * 60 * 1000);
        //mBirthdateDatePicker.setMaxDate(yourDateMillis);

        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com");

        //TODO: allow to pick birthdate for people beetween 18 & 100
    }

    public void saveUserInformations(View view) {
        //TODO: add test on selfie
        if(mUsernameET.getText().equals(null)) {
            //TODO: utiliser string
            Toast noUserame = Toast.makeText(getApplicationContext(), "Veuillez remplir votre nom d'utilisateur", Toast.LENGTH_LONG);
        } else {
            mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                        User currentUser = new User();
                        currentUser.initUser(jsonObject);
                        currentUser.setUsername(String.valueOf(mUsernameET.getText()));
                        String birthdateString = "" + mBirthdateDatePicker.getDayOfMonth() + "-" + mBirthdateDatePicker.getMonth() + "-" + mBirthdateDatePicker.getYear();
                        currentUser.setBirthdate(birthdateString);
                        String sexString = null;
                        if (mSexRadioGroup.getCheckedRadioButtonId() == mSexManRadio.getId()) {
                            sexString = "man";
                        } else if (mSexRadioGroup.getCheckedRadioButtonId() == mSexWomanRadio.getId()) {
                            sexString = "woman";
                        }
                        currentUser.setSex(sexString);
                        Firebase user = mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid());
                        user.setValue(currentUser);
                        Intent intent = new Intent(InitialUserInformationsActivity.this, MapActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d("•••", "error");
                }
            });
        }
    }

    public void addUserPhoto (View view) {

        Intent intent = new Intent(InitialUserInformationsActivity.this, CameraActivity.class);
        startActivity(intent);
    }

}
