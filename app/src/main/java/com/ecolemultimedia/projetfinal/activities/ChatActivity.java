package com.ecolemultimedia.projetfinal.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.adapters.ChatListAdapter;
import com.ecolemultimedia.projetfinal.models.Chat;
import com.ecolemultimedia.projetfinal.views.ViewMenu;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Random;


public class ChatActivity extends Activity {

    private RelativeLayout ui_rl_menu = null;
    private EditText ui_edit_text = null;
    private Button ui_btn_send = null;
    private ListView ui_lis_message = null;
    private String mUsername = null;


    private Firebase mFirebaseRef;
    private static final String FIREBASE_URL = "https://projetfinal.firebaseio-demo.com";
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ui_rl_menu = (RelativeLayout) findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu = new ViewMenu(this, ChatActivity.this);
        viewMenu.init(1);
        ui_rl_menu.addView(viewMenu);

        // Make sure we have a mUsername
        setupUsername();

        ui_edit_text = (EditText) findViewById(R.id.ui_edit_text);
        ui_btn_send = (Button) findViewById(R.id.ui_btn_send);
        ui_lis_message = (ListView) findViewById(R.id.ui_lis_message);


        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("chat");

       // Log.v("Debeug", "userrrrrr: " + mFirebaseRef.getAuth().getProvider());

        ui_edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                    ui_lis_message.setSelection(mChatListAdapter.getCount() - 1);

                }
                return true;
            }
        });

        ui_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                ui_lis_message.setSelection(mChatListAdapter.getCount() - 1);
            }
        });


        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_message, mUsername);
        ui_lis_message.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                ui_lis_message.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(ChatActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
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


    private void setupUsername() {
        SharedPreferences prefs = getApplication().getSharedPreferences("ChatPrefs", 0);
        mUsername = prefs.getString("username", null);
        if (mUsername == null) {
            Random r = new Random();


            // Assign a random user name if we don't have one saved.
            mUsername = "Saber" + r.nextInt(100000);
            prefs.edit().putString("username", mUsername).commit();
        }
    }


    private void sendMessage() {
        String input = ui_edit_text.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            ui_edit_text.setText("");
        }
    }

}
