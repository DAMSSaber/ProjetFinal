package com.ecolemultimedia.projetfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.components.AppController;
import com.ecolemultimedia.projetfinal.views.ViewMenu;
import com.firebase.client.Firebase;
import com.parse.ParseUser;

public class ProfilActivity extends AppCompatActivity {

    private ImageView mUserProfilSelfie;
    RelativeLayout ui_rl_menu=null;
    private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        ui_rl_menu=(RelativeLayout)findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu= new ViewMenu(this,ProfilActivity.this);
        viewMenu.init(2);
        ui_rl_menu.addView(viewMenu);
        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/");

        mUserProfilSelfie = (ImageView)findViewById(R.id.user_profil_selfie);

        /*
        String userProfilSelfieUrl = "http://1.bp.blogspot.com/-zuajEFAjpmw/VVwsMcfHyPI/AAAAAAAABHk/T5r-HYMy4No/s1600/Screenshot_2015-05-20-10-55-23.png";
        AppController.getInstance().getRequestQueue().getCache().remove(userProfilSelfieUrl);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(userProfilSelfieUrl, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {

                    mUserProfilSelfie.setImageBitmap(response.getBitmap());
                }
            }
        });
        */
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

}
