package com.ecolemultimedia.projetfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.components.AppController;
import com.ecolemultimedia.projetfinal.models.CustomLocation;
import com.ecolemultimedia.projetfinal.models.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;

public class UserSelfieActivity extends AppCompatActivity {

    String mUID;
    Firebase mFirebaseRef;
    ImageView mUserSelfie;
    String mUserSelfieUrl;
    String mCurrentRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selfie);

        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com");
        mUID = getIntent().getExtras().getString("uid");
        mUserSelfie = (ImageView)findViewById(R.id.user_photo);

        mFirebaseRef.child("users/" + mUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                    User currentUser = new User();
                    currentUser.initUser(jsonObject);
                    mUserSelfieUrl = currentUser.getSelfieUrl();
                    if(mUserSelfieUrl != null) {
                        String decodedUrl = String.valueOf(mUserSelfieUrl);
                        try {
                            decodedUrl = URLDecoder.decode(decodedUrl, "UTF-8");
                            //decodedUrl = URLDecoder.decode(decodedUrl, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            Log.d("•••", "error decoding utf8 : " + e.getMessage());
                        }
                        AppController.getInstance().getRequestQueue().getCache()
                                .remove(mUserSelfieUrl);
                        ImageLoader imageLoader = AppController.getInstance()
                                .getImageLoader();
                        imageLoader.get(decodedUrl, new ImageLoader.ImageListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }

                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                                if (response.getBitmap() != null) {
                                    mUserSelfie.setImageBitmap(response.getBitmap());
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
                Log.d("•••", "error");
            }
        });

    }


    public void createChatRoom(View view) {
        mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid() + "/links").orderByValue().equalTo(mUID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Log.d("•••", "salle déjà créée");
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                        Iterator key = jsonObject.keys();

                        Intent intent = new Intent(UserSelfieActivity.this, ChatActivity.class);
                        intent.putExtra("mCurrentRoom", String.valueOf(key.next()));
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("•••", "nouvelle salle créée");

                    Firebase chatRoom = mFirebaseRef.child("chat").push();
                    ArrayList<String> users = new ArrayList<String>();
                    users.add(mFirebaseRef.getAuth().getUid());
                    users.add(mUID);
                    chatRoom.child("users").setValue(users);

                    mCurrentRoomId = chatRoom.getKey();

                    //set chatroom for current user
                    mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            try {
                                JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                                User currentUser = new User();
                                currentUser.initUser(jsonObject);
                                currentUser.addLink(mCurrentRoomId, mUID);
                                Firebase user = mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid());
                                user.setValue(currentUser);

                                Intent intent = new Intent(UserSelfieActivity.this, ChatActivity.class);
                                intent.putExtra("mCurrentRoom", mCurrentRoomId);
                                startActivity(intent);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Log.d("•••", "azazaz");
                        }
                    });

                    //set chatroom for other user
                    mFirebaseRef.child("users/" + mUID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            try {
                                JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                                User currentUser = new User();
                                currentUser.initUser(jsonObject);
                                currentUser.addLink(mCurrentRoomId, mFirebaseRef.getAuth().getUid());
                                Firebase user = mFirebaseRef.child("users/" + mUID);
                                user.setValue(currentUser);

                                Log.d("•••", "créer intent en pasant la clé " + mCurrentRoomId);


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

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

}
