package com.ecolemultimedia.projetfinal.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.models.User;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<String> {

    private Activity context;


    private ArrayList<User> listUser;
    CellViewHolder viewHolder = null;
    ;
    private View rowView = null;


    public UserListAdapter(Activity context, ArrayList<User> listUser) {
        super(context, android.R.layout.simple_list_item_1);
        this.listUser = listUser;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.listUser.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 10 == 0 && position != 0 ? 1 : 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        rowView = convertView;


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (rowView == null) {
            viewHolder = new CellViewHolder();

            rowView = inflater.inflate(R.layout.cell_list_user_chat, null);


            viewHolder.ui_name_user = (TextView) rowView
                    .findViewById(R.id.ui_name_user);


            rowView.setTag(viewHolder);

        }

        viewHolder = (CellViewHolder) rowView.getTag();
        if (listUser.get(position).getUsername() != null) {
            viewHolder.ui_name_user.setText(listUser.get(position)
                    .getUsername());
        }




    return rowView;
}

static class CellViewHolder {

    // use for my view
    public TextView ui_name_user = null;


}

}
