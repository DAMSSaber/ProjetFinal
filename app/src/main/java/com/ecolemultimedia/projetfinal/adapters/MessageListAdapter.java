package com.ecolemultimedia.projetfinal.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ecolemultimedia.projetfinal.cells.CellDemoChild;
import com.ecolemultimedia.projetfinal.models.Message;

import java.util.ArrayList;


public class MessageListAdapter extends BaseAdapter {

    private ArrayList<Message> listMessage = null;
    private Context context = null;
    private Activity activity = null;

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    private String currentUser=null;

    public MessageListAdapter(Context context, ArrayList<Message> listMessage, Activity activity,String currentUser) {

        this.listMessage = listMessage;
        this.context = context;
        this.activity = activity;
        this.currentUser=currentUser;
    }


    @Override
    public int getCount() {
        return listMessage.size();
    }

    @Override
    public Message getItem(int position) {
        return listMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message item = getItem(position);

        CellDemoChild cellMenu = null;

        if (convertView == null) {
            cellMenu = new CellDemoChild(context);
            cellMenu = (CellDemoChild) cellMenu.init();
        } else
            cellMenu = (CellDemoChild) convertView;

        cellMenu.reuse(item,currentUser);
        return cellMenu;
    }

}

