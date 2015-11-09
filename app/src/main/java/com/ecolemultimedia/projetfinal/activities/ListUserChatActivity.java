package com.ecolemultimedia.projetfinal.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.adapters.UserListAdapter;
import com.ecolemultimedia.projetfinal.models.User;
import com.ecolemultimedia.projetfinal.views.ViewMenu;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class ListUserChatActivity extends Activity {

    private RelativeLayout ui_rl_menu = null;
    private ListView ui_list_user = null;
    private UserListAdapter adapter = null;

    private ArrayList<User> listUser = null;

    private Firebase mFirebaseRef;
    private int mClickedUserId;
    private String mCurrentRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_chat);

        ui_rl_menu = (RelativeLayout) findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu = new ViewMenu(this, ListUserChatActivity.this);
        viewMenu.init(1);
        ui_rl_menu.addView(viewMenu);

        listUser = new ArrayList<>();
        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com/users");


        ui_list_user = (ListView) findViewById(R.id.ui_list_user);
        adapter = new UserListAdapter(this, listUser);
        ui_list_user.setAdapter(adapter);

        mFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                try {


                    listUser.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                     //   User post = postSnapshot.getValue(User.class);
                        User user = new User();
                        JSONObject jsonObject = new JSONObject(String.valueOf(postSnapshot.getValue()));
                        user.initUser(jsonObject);
                        if (String.valueOf(user.getUid()) != String.valueOf(mFirebaseRef.getAuth().getUid())) {
                            listUser.add(user);
                        }
                    }
                    Log.d("•••", "size : " + listUser.size());
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        ui_list_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mClickedUserId = position;
                Log.d("•••", "id user clicked : " + listUser.get(position).getUid());

                mFirebaseRef.child(mFirebaseRef.getAuth().getUid() + "/links").orderByValue().equalTo(listUser.get(position).getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                                Iterator key = jsonObject.keys();

                                Intent intent = new Intent(ListUserChatActivity.this, ChatActivity.class);
                                intent.putExtra("mCurrentRoom", String.valueOf(key.next()));
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                            Firebase chatRoom = mFirebaseRef.getParent().child("chat").push();
                            ArrayList<String> users = new ArrayList<String>();
                            users.add(mFirebaseRef.getAuth().getUid());
                            users.add(listUser.get(mClickedUserId).getUid());
                            chatRoom.child("users").setValue(users);

                            mCurrentRoomId = chatRoom.getKey();

                            //set chatroom for current user
                            mFirebaseRef.child(mFirebaseRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                                        User currentUser = new User();
                                        currentUser.initUser(jsonObject);
                                        currentUser.addLink(mCurrentRoomId, listUser.get(mClickedUserId).getUid());
                                        Firebase user = mFirebaseRef.child(mFirebaseRef.getAuth().getUid());
                                        user.setValue(currentUser);

                                        Intent intent = new Intent(ListUserChatActivity.this, ChatActivity.class);
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
                            mFirebaseRef.child(listUser.get(mClickedUserId).getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                                        User currentUser = new User();
                                        currentUser.initUser(jsonObject);
                                        currentUser.addLink(mCurrentRoomId, mFirebaseRef.getAuth().getUid());
                                        Firebase user = mFirebaseRef.child(listUser.get(mClickedUserId).getUid());
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
                //listUser.get(position).getUid()
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
