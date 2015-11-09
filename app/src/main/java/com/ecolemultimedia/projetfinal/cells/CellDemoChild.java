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


/**
 * Created by saberdams on 10/07/15.
 */
public class CellDemoChild extends RelativeLayout {

    private TextView author = null;
    private TextView message = null;
    private ImageView selfie = null;


    private TextView author_two = null;
    private TextView message_two = null;
    private ImageView selfie_two = null;
    private LayoutInflater layoutInflater = null;

    private LayoutInflater layoutInflater1 = null;
    private LayoutInflater layoutInflater2 = null;
    private Context m_context;

    View firstView = null;
    View secondView = null;


    private RelativeLayout ui_rl_content = null;

    public CellDemoChild(Context context) {
        super(context);
        m_context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public View init() {
        layoutInflater1 = (LayoutInflater) m_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater2 = (LayoutInflater) m_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View convertView = layoutInflater
                .inflate(R.layout.chat, this);

        firstView = layoutInflater1
                .inflate(R.layout.chat_message, null);

        secondView = layoutInflater2
                .inflate(R.layout.chat_message_two, null);


        author = (TextView) secondView.findViewById(R.id.author__);
        message = (TextView) secondView.findViewById(R.id.message__);
        selfie = (ImageView) secondView.findViewById(R.id.selfie__);


        author_two = (TextView) firstView.findViewById(R.id.author_two);
        message_two = (TextView) firstView.findViewById(R.id.message_two);
        selfie_two = (ImageView) firstView.findViewById(R.id.selfie_two);

        ui_rl_content = (RelativeLayout) convertView.findViewById(R.id.ui_rl_content);

        return this;
    }

    public void reuse(Message mess, String currentUser) {

        cleanView();


        Log.v("Debeug","Test"+currentUser);
        if (mess.getAuthor().equals(currentUser)) {

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

            ui_rl_content.removeAllViews();
            ui_rl_content.addView(secondView);
        } else {

            if (mess.getAuthor() != null) {
                author_two.setText(mess.getAuthor());
            }
            if (mess.getType() != null) {
                if (mess.getType().equals("text")) {
                    message_two.setText(mess.getMessage());
                } else {
                    Log.v("Debeug", "Image :" + mess.getMessage());


                    selfie_two.setImageBitmap(decodeBase64(mess.getMessage()));
                }

            }
            ui_rl_content.removeAllViews();
            ui_rl_content.addView(firstView);
        }
    }

    public void cleanView() {
        author.setText("");
        author.setText("");
        selfie.setImageBitmap(null);

        author_two.setText("");
        author_two.setText("");
        selfie_two.setImageBitmap(null);

    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
