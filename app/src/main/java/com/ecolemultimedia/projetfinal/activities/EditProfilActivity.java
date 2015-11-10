package com.ecolemultimedia.projetfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.components.AppController;
import com.ecolemultimedia.projetfinal.models.User;
import com.ecolemultimedia.projetfinal.views.ViewMenu;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

public class EditProfilActivity extends AppCompatActivity {

    RelativeLayout ui_rl_menu = null;

    private EditText ui_username = null;
    private EditText ui_selfie_url = null;
    private EditText ui_email = null;
    private EditText ui_age = null;
    private DatePicker ui_birthdate_picker;
    private EditText ui_sex = null;
    private Button ui_btn_valider = null;

    private Firebase mFirebaseRef;

    private ImageView user_profil_selfie = null;
    private TextView username_label = null;
    private String mUserSelfieUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);


        //ui_username = (EditText) findViewById(R.id.username_label);
        ui_selfie_url = (EditText)findViewById(R.id.selfie_url);
        //ui_email = (EditText) findViewById(R.id.email);
        ui_birthdate_picker = (DatePicker)findViewById(R.id.ui_birthdate_picker);
        //ui_age = (EditText) findViewById(R.id.age_label);
        //ui_sex = (EditText) findViewById(R.id.sex_label);



        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/");

        ui_btn_valider = (Button) findViewById(R.id.ui_btn_valider);

        user_profil_selfie = (ImageView) findViewById(R.id.user_profil_selfie);


        ui_rl_menu = (RelativeLayout) findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu = new ViewMenu(this, EditProfilActivity.this);
        viewMenu.init(2);
        ui_rl_menu.addView(viewMenu);
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
                    if (!String.valueOf(currentUser.getBirthdate()).equals(null)) {
                        String[] separated = String.valueOf(currentUser.getBirthdate()).split("-");
                        int year = Integer.parseInt(separated[2]);
                        int month = Integer.parseInt(separated[1]);
                        int day = Integer.parseInt(separated[0]);
                        ui_birthdate_picker.updateDate(year, month, day);
                    }
                    if (currentUser.getSelfieUrl() != null) {
                        String decodedUrl = String.valueOf(currentUser.getSelfieUrl());
                        try {
                            decodedUrl = URLDecoder.decode(decodedUrl, "UTF-8");
                            //decodedUrl = URLDecoder.decode(decodedUrl, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Log.d("•••", "error decoding utf8 : " + e.getMessage());
                        }
                        mUserSelfieUrl = decodedUrl;
                        AppController.getInstance().getRequestQueue().getCache()
                                .remove(mUserSelfieUrl);
                        ImageLoader imageLoader = AppController.getInstance()
                                .getImageLoader();
                        imageLoader.get(mUserSelfieUrl, new ImageLoader.ImageListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }

                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                                if (response.getBitmap() != null) {
                                    user_profil_selfie.setImageBitmap(response.getBitmap());
                                }
                            }
                        });
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

    }

    @Override
    protected void onResume() {
        super.onResume();


        /*mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // do some stuff once
                Log.d("•••", "" + snapshot.getValue());

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                    User currentUser = new User();
                    currentUser.initUser(jsonObject);
                    if (!String.valueOf(currentUser.getBirthdate()).equals(null)) {
                        String[] separated = String.valueOf(currentUser.getBirthdate()).split("-");
                        int year = Integer.parseInt(separated[2]);
                        int month = Integer.parseInt(separated[1]);
                        int day = Integer.parseInt(separated[0]);
                        ui_birthdate_picker.updateDate(year, month, day);
                    }
                    if (!String.valueOf(currentUser.getSelfieUrl()).equals("null")) {
                        String decodedUrl = String.valueOf(currentUser.getSelfieUrl());
                        try {
                            decodedUrl = URLDecoder.decode(decodedUrl, "UTF-8");
                            //decodedUrl = URLDecoder.decode(decodedUrl, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Log.d("•••", "error decoding utf8 : " + e.getMessage());
                        }
                        ui_selfie_url.setText(decodedUrl);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("•••", "azazaz");
            }
        });*/


        /*ui_btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/

    }


    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public void editProfil(View view) {
        if(ui_selfie_url.getText().equals(null)) {
            //TODO: utiliser string
            Toast noSelfie = Toast.makeText(getApplicationContext(), "Veuillez remplir l'url de votre photo", Toast.LENGTH_LONG);
            noSelfie.show();
        } else {
            mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                        User currentUser = new User();
                        currentUser.initUser(jsonObject);
                        String birthdateString = "" + ui_birthdate_picker.getDayOfMonth() + "-" + ui_birthdate_picker.getMonth() + "-" + ui_birthdate_picker.getYear();
                        currentUser.setBirthdate(birthdateString);
                        String encodedString = null;
                        try {
                            encodedString = URLEncoder.encode(String.valueOf(ui_selfie_url.getText()), "UTF-8");
                            Log.d("•••", "selfie url encoded : " + encodedString);
                            currentUser.setSelfieUrl(encodedString);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Log.d("•••", e.getMessage());
                        }
                        Firebase user = mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid());
                        user.setValue(currentUser);
                        Intent intent = new Intent(EditProfilActivity.this, ProfilActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("•••", e.getMessage());
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d("•••", "error");
                }
            });
        }
    }
}
