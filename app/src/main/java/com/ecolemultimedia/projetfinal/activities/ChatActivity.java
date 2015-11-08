package com.ecolemultimedia.projetfinal.activities;

import android.app.Activity;
import android.content.Intent;
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

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.adapters.MessageListAdapter;
import com.ecolemultimedia.projetfinal.models.Message;
import com.ecolemultimedia.projetfinal.models.User;
import com.ecolemultimedia.projetfinal.views.ViewMenu;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChatActivity extends Activity {

    private RelativeLayout ui_rl_menu = null;
    private EditText ui_edit_text = null;
    private Button ui_btn_send = null;
    private ListView ui_lis_message = null;
    private String mUsername = null;
    private String mRoomId = null;
    private Firebase mFirebaseRef;

    private Firebase mFirebaseRef2;
    private String FIREBASE_URL = null;
    private ValueEventListener mConnectedListener;
    private MessageListAdapter mChatListAdapter;

    private ArrayList<Message> listMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent value = getIntent();
        mRoomId = value.getStringExtra("mCurrentRoom");

        FIREBASE_URL = "https://projetfinal.firebaseio.com/chat/" + mRoomId + "/message";
        listMessage = new ArrayList<>();
        setContentView(R.layout.activity_chat);

        ui_rl_menu = (RelativeLayout) findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu = new ViewMenu(this, ChatActivity.this);
        viewMenu.init(1);
        ui_rl_menu.addView(viewMenu);

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL);

        mFirebaseRef2 = new Firebase("https://projetfinal.firebaseio.com/users");
        // Make sure we have a mUsername
        mFirebaseRef2.child(mFirebaseRef2.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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
                        mUsername = currentUser.getUsername();
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


        ui_edit_text = (EditText) findViewById(R.id.ui_edit_text);
        ui_btn_send = (Button) findViewById(R.id.ui_btn_send);
        ui_lis_message = (ListView) findViewById(R.id.ui_lis_message);


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


        mFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                Log.d("•••", "size : " + snapshot.getValue());

                JSONObject jsonObject = null;
                listMessage.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Message post = postSnapshot.getValue(Message.class);
                    try {
                        jsonObject = new JSONObject(String.valueOf(postSnapshot.getValue()));
                        Message currentMessage = new Message();
                        currentMessage.initMessage(jsonObject);

                        listMessage.add(currentMessage);
                        mChatListAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });


        mChatListAdapter = new MessageListAdapter(this, listMessage, ChatActivity.this);
        ui_lis_message.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                ui_lis_message.setSelection(mChatListAdapter.getCount() - 1);
            }
        });


    }


    private void sendMessage() {
        String input = ui_edit_text.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Message chat = new Message();
            chat.setAuthor(mUsername);
            chat.setMessage(input);
            chat.setType("text");
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            ui_edit_text.setText("");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


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
