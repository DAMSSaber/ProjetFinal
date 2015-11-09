package com.ecolemultimedia.projetfinal.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ecolemultimedia.projetfinal.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SelfieActivity extends Activity {


    private static final String TAG = "SelfieActivity";

    //use for view
    private ImageView view;
    private Button scan;
    private ImageView ui_cancel = null;
    private ImageView ui_save = null;


    private String pathPicture;
    private RelativeLayout ui_layout_edit = null;

    long offset = -1; //text position will be corrected when touching.
    int numeroScan;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie);

        Intent intent = getIntent();
        pathPicture = intent.getStringExtra("path");

        //	String pathForAppFiles = getFilesDir().getAbsolutePath();
        //	pathForAppFiles = picture;
        //		Log.d("Still image source filename:", pathForAppFiles);

        ui_cancel = (ImageView) findViewById(R.id.ui_cancel);
        ui_save = (ImageView) findViewById(R.id.ui_save);


        Bitmap bma = BitmapFactory.decodeFile(pathPicture);
        ImageView picCamera = (ImageView) this.findViewById(R.id.pic);


        Matrix matrix = new Matrix();
        matrix.postRotate(-90);
        Bitmap bm = bma.createBitmap(bma, 0, 0, bma.getWidth(), bma.getHeight(), matrix, true);
        picCamera.setImageBitmap(bm);

        ui_layout_edit = (RelativeLayout) findViewById(R.id.ui_layout_edit);
        final EditText dwEdit = (EditText) findViewById(R.id.edit_text);
        dwEdit.getBackground().setAlpha(128);

        ui_layout_edit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    // Offsets are for centering the TextView on the touch location
                    //  v.setX(event.getRawX() - v.getWidth() / 2.0f);
                    v.setY(event.getRawY() - v.getHeight() / 2.0f);
                }

                return true;
            }

        });


        ui_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroScan++;
                saveBitmap(takeScreenshot(), numeroScan);

            }
        });


        ui_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public Bitmap takeScreenshot() {
        findViewById(R.id.ui_save).setVisibility(View.INVISIBLE);
        findViewById(R.id.ui_cancel).setVisibility(View.INVISIBLE);
        View rootView = findViewById(R.id.keyneo_activity_saveProduct).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();

    }

    /**
     * Save the Bitmap when you scan screen
     */
    public String saveBitmap(Bitmap bitmap, int num) {

        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot" + num + ".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }

        return imagePath.getPath();
    }


}