package com.ecolemultimedia.projetfinal.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.views.ViewMenu;


public class ChatActivity extends Activity {

    private RelativeLayout ui_rl_menu=null;
    private EditText ui_edit_text=null;
    private Button ui_btn_send=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ui_rl_menu=(RelativeLayout)findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu= new ViewMenu(this,ChatActivity.this);
        viewMenu.init(1);
        ui_rl_menu.addView(viewMenu);

        ui_edit_text=(EditText)findViewById(R.id.ui_edit_text);
        ui_btn_send=(Button)findViewById(R.id.ui_btn_send);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
