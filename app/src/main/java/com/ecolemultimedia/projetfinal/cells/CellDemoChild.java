package com.ecolemultimedia.projetfinal.cells;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.models.Message;
import com.ecolemultimedia.projetfinal.utils.Base64ToBitmap;


/**
 * Created by saberdams on 10/07/15.
 */
public class CellDemoChild extends RelativeLayout {

    private TextView author = null;
    private TextView message = null;
    private ImageView selfie = null;
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
        selfie = (ImageView) convertView.findViewById(R.id.selfie);

        return this;
    }

    public void reuse(Message mess) {

        cleanView();


        if (mess.getAuthor() != null) {
            author.setText(mess.getAuthor());
        }
        if (mess.getType() != null) {
            if (mess.getType().equals("text")) {
                message.setText(mess.getMessage());
            } else {
                Log.v("Debeug", "Image :" + mess.getMessage());



                selfie.setImageBitmap(decodeBase64(mess.getMessage()));
            }

        }

    }

    public void cleanView() {
        author.setText("");
        author.setText("");
        selfie.setImageBitmap(null);

    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
