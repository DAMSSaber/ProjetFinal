package com.ecolemultimedia.projetfinal.cells;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.models.Message;


/**
 * Created by saberdams on 10/07/15.
 */
public class CellDemoChild extends RelativeLayout {

    private TextView author = null;
    private TextView message = null;
    private LayoutInflater layoutInflater = null;
    private Context m_context;

    public CellDemoChild(Context context) {
        super(context);
        m_context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public View init() {

        View convertView = layoutInflater
                .inflate(R.layout.chat_message, this);
        author = (TextView) convertView.findViewById(R.id.author);
        message = (TextView) convertView.findViewById(R.id.message);
        return this;
    }

    public void reuse(Message mess) {

        cleanView();
        if (mess.getAuthor() != null) {
            author.setText(mess.getAuthor());
        }
        if (mess.getMessage() != null) {
            message.setText(mess.getMessage());
        }

    }

    public void cleanView() {

        author.setText("");

    }
}
