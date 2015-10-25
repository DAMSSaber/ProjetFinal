package com.mobvalue.mobfactory.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class Loader {
    private ProgressDialog dialog = null;
    private Context m_context = null;

    public Loader(Context context) {

        this.m_context = context;

    }

    public void showDialog() {

        dialog = new ProgressDialog(m_context);
        dialog.setMessage("Chargement..");

        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismissDialog() {

        if (dialog != null && dialog.isShowing())

            dialog.dismiss();
    }

}
