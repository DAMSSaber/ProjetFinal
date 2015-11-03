package com.ecolemultimedia.projetfinal.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.activities.ChatActivity;
import com.ecolemultimedia.projetfinal.activities.MapActivity;
import com.ecolemultimedia.projetfinal.activities.ProfilActivity;


/**
 * Created by saberdams on 10/07/15.
 */
public class ViewMenu extends RelativeLayout {

    private RelativeLayout ui_rl_menu_map = null;
    private RelativeLayout ui_rl_menu_chat = null;
    private RelativeLayout ui_rl_menu_profil = null;


    private ImageView ui_img_menu_map = null;
    private ImageView ui_img_menu_chat = null;
    private ImageView ui_img_menu_profil = null;


    private LayoutInflater layoutInflater = null;
    private Context m_context;
    private Activity activity;


    public ViewMenu(Context context, Activity activity) {
        super(context);
        m_context = context;
        this.activity=activity;
        layoutInflater = LayoutInflater.from(context);
    }

    public View init(final int positionScreen) {

        View convertView = layoutInflater
                .inflate(R.layout.view_menu, this);

        ui_rl_menu_map = (RelativeLayout) convertView.findViewById(R.id.ui_rl_menu_map);
        ui_rl_menu_chat = (RelativeLayout) convertView.findViewById(R.id.ui_rl_menu_chat);
        ui_rl_menu_profil = (RelativeLayout) convertView.findViewById(R.id.ui_rl_menu_profil);


        ui_img_menu_map = (ImageView) convertView.findViewById(R.id.ui_img_menu_map);
        ui_img_menu_chat = (ImageView) convertView.findViewById(R.id.ui_img_menu_chat);
        ui_img_menu_profil = (ImageView) convertView.findViewById(R.id.ui_img_menu_profil);





        //TODO: if position on 0, desactivate call on 0 item (or it reload activity)
        if (positionScreen == 0) {
            ui_img_menu_map.setImageResource(R.drawable.scond_map);
        } else if (positionScreen == 1) {
            ui_img_menu_chat.setImageResource(R.drawable.second_chat);
        }else if(positionScreen == 2){
            ui_img_menu_profil.setImageResource(R.drawable.second_profil);
        }


        ui_rl_menu_map.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(m_context, MapActivity.class);
                m_context.startActivity(intent);
                activity.overridePendingTransition(0,0);
             //   activity.finish();
            }
        });

        ui_rl_menu_chat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(m_context, ChatActivity.class);
                m_context.startActivity(intent);
                activity.overridePendingTransition(0, 0);
               // activity.finish();
            }
        });
        ui_rl_menu_profil.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(m_context, ProfilActivity.class);
                m_context.startActivity(intent);
                activity.overridePendingTransition(0, 0);
                activity.finish();

            }
        });



        return this;

    }


}
