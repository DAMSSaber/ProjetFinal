package com.ecolemultimedia.projetfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.models.User;
import com.ecolemultimedia.projetfinal.views.ViewMenu;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfilActivity extends AppCompatActivity {

    private ImageView mUserProfilSelfie;
    RelativeLayout ui_rl_menu = null;
    private Firebase mFirebaseRef;

    private TextView ui_username = null;
    private TextView ui_email = null;
    private TextView ui_age = null;
    private TextView ui_sex = null;
    private Button ui_btn_valider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        ui_rl_menu = (RelativeLayout) findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu = new ViewMenu(this, ProfilActivity.this);
        viewMenu.init(2);
        ui_rl_menu.addView(viewMenu);


        ui_username = (TextView) findViewById(R.id.username);
        ui_email = (TextView) findViewById(R.id.email);
        ui_age = (TextView) findViewById(R.id.age);
        ui_sex = (TextView) findViewById(R.id.sex);
        ui_btn_valider = (Button) findViewById(R.id.ui_btn_valider);


        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/");

        mUserProfilSelfie = (ImageView) findViewById(R.id.user_profil_selfie);

    }

    public void disconnect(View view) {
        mFirebaseRef.unauth();
        getApplicationContext().getSharedPreferences("currentUser", MODE_PRIVATE).edit().clear().commit();
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    public void editProfil(View view) {
        Intent intent = new Intent(this, EditProfilActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();


        mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // do some stuff once
                Log.d("•••", "" + snapshot.getValue());

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                    User currentUser = new User();
                    currentUser.initUser(jsonObject);


                    if (currentUser.getUsername() != null) {
                        ui_username.setText(currentUser.getUsername());

                    }
                    if (currentUser.getEmail() != null) {
                        ui_email.setText(currentUser.getEmail());
                    }
                    if (currentUser.getBirthdate() != null) {
                        ui_age.setText(currentUser.getBirthdate());
                    }
                    if (currentUser.getSex() != null) {
                        ui_sex.setText(currentUser.getSex());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("•••", "azazaz");
            }
        });


        ui_btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this, EditProfilActivity.class);
                startActivity(intent);
            }
        });

    }
}
