package com.ecolemultimedia.projetfinal.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.components.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends Activity {

    private static final String TAG = "CameraActivity";

    private ImageView captureButton = null;
    private ImageView switchButton = null;
    public String mPathPicture;

    protected static final int MEDIA_TYPE_IMAGE = 0;

    private Camera mCamera = null;
    private CameraPreview mPreview = null;
    private Camera.PictureCallback mPicture = null;
    FrameLayout preview = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        // Create an instance of Camera
        if (mCamera == null) {
            mCamera = mCamera.open(1);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        captureButton = (ImageView) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        takePhoto();
                    }
                }
        );

        switchButton = (ImageView) findViewById(R.id.button_switch);
        switchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   mPreview.switchCamera();
                    }
                }
        );

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }


    public void takePhoto() {
        Camera.PictureCallback pictureCB = new Camera.PictureCallback() {
            public void onPictureTaken(byte[] data, Camera cam) {
                File picFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                if (picFile == null) {
                    Log.e(TAG, "Couldn't create media file; check storage permissions?");
                    return;
                }

                try {
                    FileOutputStream fos = new FileOutputStream(picFile);
                    fos.write(data);
                    fos.close();

                    Intent t = new Intent(CameraActivity.this, SelfieActivity.class);
                    t.putExtra("path", mPathPicture);
                    startActivity(t);

                } catch (FileNotFoundException e) {
                    Log.e(TAG, "File not found: " + e.getMessage());
                    e.getStackTrace();
                } catch (IOException e) {
                    Log.e(TAG, "I/O error writing file: " + e.getMessage());
                    e.getStackTrace();
                }
            }
        };
        mCamera.takePicture(null, null, pictureCB);
    }

    private File getOutputMediaFile(int type) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName());
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(TAG, "Failed to create storage directory.");
                return null;
            }
        }
        String timeStamp =
                new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
        if (type == MEDIA_TYPE_IMAGE) {
            File pic = new File(dir.getPath() + File.separator + "IMG_"
                    + timeStamp + ".png");
            mPathPicture = pic.getPath();
            return pic;
        } else {
            return null;
        }
    }



}