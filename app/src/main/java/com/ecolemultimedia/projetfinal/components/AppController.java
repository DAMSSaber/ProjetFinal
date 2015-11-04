package com.ecolemultimedia.projetfinal.components;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.ecolemultimedia.projetfinal.utils.LruBitmapCache;
import com.firebase.client.Firebase;
import com.parse.Parse;

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;


    public enum TrackerName {
        APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Firebase.setAndroidContext(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "nF9TvVOMOcRnOekpGnHc5Eb490XF5VvqnT3FbIEd", "ENLn1YKpPcX6la7NBDtAyv8lRy49K5fBVPEAfr0v");
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
