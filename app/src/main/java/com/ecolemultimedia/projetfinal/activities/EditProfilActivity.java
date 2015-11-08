package com.ecolemultimedia.projetfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditProfilActivity extends AppCompatActivity {

    RelativeLayout ui_rl_menu = null;

    private EditText ui_username = null;
    private EditText ui_email = null;
    private EditText ui_age = null;
    private EditText ui_sex = null;
    private Button ui_btn_valider = null;

    private Firebase mFirebaseRef;

    private ImageView user_profil_selfie = null;
    private TextView username_label = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);


        ui_username = (EditText) findViewById(R.id.username_label);
        ui_email = (EditText) findViewById(R.id.email);
        ui_age = (EditText) findViewById(R.id.age_label);
        ui_sex = (EditText) findViewById(R.id.sex_label);



        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/");

        ui_btn_valider = (Button) findViewById(R.id.ui_btn_valider);

        user_profil_selfie = (ImageView) findViewById(R.id.user_profil_selfie);


        ui_rl_menu = (RelativeLayout) findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu = new ViewMenu(this, EditProfilActivity.this);
        viewMenu.init(2);
        ui_rl_menu.addView(viewMenu);


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
               onBackPressed();
            }
        });

    }
}
