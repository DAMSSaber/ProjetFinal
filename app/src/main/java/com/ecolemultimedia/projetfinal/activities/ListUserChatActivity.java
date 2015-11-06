package com.ecolemultimedia.projetfinal.activities;

import android.app.Activity;
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


public class ListUserChatActivity extends Activity {

    private RelativeLayout ui_rl_menu = null;
    private ListView ui_list_user = null;
    private UserListAdapter adapter = null;

    private ArrayList<User> listUser = null;

    private Firebase mFirebaseRef;

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


                    for (int i = 0; i < snapshot.getChildrenCount(); i++) {

                    }

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User post = postSnapshot.getValue(User.class);
                        User user = new User();
                        JSONObject jsonObject = new JSONObject(String.valueOf(postSnapshot.getValue()));
                        user.initUser(jsonObject);
                        listUser.add(user);
                    }
                    Log.v("Debeug", "size:" + listUser.size());
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
