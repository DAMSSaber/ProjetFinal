package com.ecolemultimedia.projetfinal.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.models.CustomLocation;
import com.ecolemultimedia.projetfinal.models.User;
import com.ecolemultimedia.projetfinal.views.ViewMenu;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    RelativeLayout ui_rl_menu = null;

    private GoogleMap gMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    Firebase mFirebaseRef;
    GeoFire mFirebaseGeoRef;
    Boolean parametersLayoutIsOpen;
    RelativeLayout mParametersLayout;
    RelativeLayout mParametersButton;
    LinearLayout seekbarlayout;
    RangeSeekBar<Integer> rangeSeekBar;
    CheckBox manCB;
    CheckBox womanCB;
    Double mDistance;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mFirebaseRef = new Firebase("https://projetfinal.firebaseio.com");
        mFirebaseGeoRef = new GeoFire(mFirebaseRef.child("locations"));

        ui_rl_menu = (RelativeLayout) findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu = new ViewMenu(this, MapActivity.this);
        viewMenu.init(0);
        ui_rl_menu.addView(viewMenu);
        parametersLayoutIsOpen = false;
        mParametersLayout = (RelativeLayout)findViewById(R.id.parameters_layout);
        mParametersButton = (RelativeLayout)findViewById(R.id.parameters_button);
        manCB = (CheckBox)findViewById(R.id.man_checkbox);
        manCB.setChecked(true);
        womanCB = (CheckBox)findViewById(R.id.woman_checkbox);
        womanCB.setChecked(true);
        //set maximum visible users distance
        mDistance= 5.0;

        //set 2 thumbs seekbar
        rangeSeekBar = new RangeSeekBar<Integer>(this);
        rangeSeekBar.setRangeValues(18, 100);
        rangeSeekBar.setSelectedMinValue(18);
        rangeSeekBar.setSelectedMaxValue(100);
        seekbarlayout = (LinearLayout) findViewById(R.id.seekbar_custom);
        seekbarlayout.addView(rangeSeekBar);



        buildGoogleApiClient();
        mGoogleApiClient.connect();
        setUpMapIfNeeded();
        gMap.getUiSettings().setMyLocationButtonEnabled(false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);



        try {
            String string = "test test";
            String encodedString = URLEncoder.encode(string, "UTF-8");
            String decodedString = URLDecoder.decode(encodedString, "UTF-8");


            Log.d("•••", "string : " + string);
            Log.d("•••", "encoded string : " + encodedString);
            Log.d("•••", "decoded string : " + decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        checkForLocationPermission();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(MapActivity.this)
                .addConnectionCallbacks(MapActivity.this)
                .addOnConnectionFailedListener(MapActivity.this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            checkForLocationPermission();
            gMap.setMapType(gMap.MAP_TYPE_NORMAL);
            gMap.setTrafficEnabled(false);
            // Check if we were successful in obtaining the map
            if (gMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        checkForLocationPermission();
        gMap.setMapType(gMap.MAP_TYPE_NORMAL);
        gMap.setTrafficEnabled(false);
    }

    private void checkForLocationPermission() {
        //check permission for location (android 6 & +)
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //TODO: make a refresh of the map (if localisation permission is different on resume)
            gMap.setMyLocationEnabled(true);
        } else {
            //TODO: utiliser string

            // set alert dialog for android 6 & + user feedback
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MapActivity.this);
            alertDialogBuilder.setTitle("Autoriser la localisation");
            alertDialogBuilder
                    .setMessage("Allez dans les préférences de votre téléphone > menu applications > TouchMe > autorisations, puis activez la position.")
                    .setPositiveButton("J'ai compris", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //TODO: dialog.dismiss() not closing alertdialog
                            dialog.dismiss();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }

    public void updateUserLastLocationWithLocation(final Location location) {
        mFirebaseGeoRef.setLocation(mFirebaseRef.getAuth().getUid(), new GeoLocation(location.getLatitude(), location.getLongitude()));
        mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                CustomLocation loc = new CustomLocation();
                loc.initAndroidLocation(location);
                /*//Custom class not supported with function updateChildren()
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("location", loc);
                mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid()).updateChildren(map);
                */
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                    Log.d("•••", "" + jsonObject);
                    User currentUser = new User();
                    currentUser.initUser(jsonObject);
                    currentUser.setLocation(loc);
                    Firebase user = mFirebaseRef.child("users/" + mFirebaseRef.getAuth().getUid());
                    user.setValue(currentUser);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("•••", "error");
            }
        });
    }

    public void updateMapWithMarkers(Location location, Double distance) {
        gMap.clear();
        GeoQuery geoQuery = mFirebaseGeoRef.queryAtLocation(new GeoLocation(location.getLatitude(), location.getLongitude()), distance);
        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                System.out.println(String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));
                if (!key.equals(mFirebaseRef.getAuth().getUid())) {

                    mFirebaseRef.child("users/" + key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            try {
                                JSONObject jsonObject = new JSONObject(String.valueOf(snapshot.getValue()));
                                User currentUser = new User();
                                currentUser.initUser(jsonObject);

                                if (getUserAge(currentUser.getBirthdate()) <= rangeSeekBar.getSelectedMaxValue() && getUserAge(currentUser.getBirthdate()) >= rangeSeekBar.getSelectedMinValue()) {
                                    if ((manCB.isChecked() && String.valueOf(currentUser.getSex()).equals("man")) || (womanCB.isChecked() && String.valueOf(currentUser.getSex()).equals("woman"))) {
                                        if(currentUser.getLocation() != null) {
                                            gMap.addMarker(new MarkerOptions().position(new LatLng(currentUser.getLocation().latitude, currentUser.getLocation().longitude)).title(currentUser.getUsername()));
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Log.d("•••", "azazaz");
                        }
                    });
                }
            }

            @Override
            public void onKeyExited(String key) {
                System.out.println(String.format("Key %s is no longer in the search area", key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("All initial data has been loaded and events have been fired!");
            }

            @Override
            public void onGeoQueryError(FirebaseError error) {
                System.err.println("There was an error with this query: " + error);
            }
        });
    }

    public void updateCameraWithLocation(Location location) {
        LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate centerCamera = CameraUpdateFactory.newLatLng(locationLatLng);
        CameraUpdate zoomCamera = CameraUpdateFactory.zoomTo(15);
        gMap.moveCamera(centerCamera);
        gMap.animateCamera(zoomCamera);
    }

    public void showParametersLayout(View view) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mParametersLayout.getLayoutParams();
        if(parametersLayoutIsOpen) {
            params.height = 0;
            mParametersLayout.setLayoutParams(params);
            parametersLayoutIsOpen = false;
        } else {
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            mParametersLayout.setLayoutParams(params);
            parametersLayoutIsOpen = true;
        }
        Log.d("•••", "" + manCB.isChecked());
        Log.d("•••", "" + womanCB.isChecked());
        updateMapWithMarkers(mLastLocation, mDistance);

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            String toastString = "Location successful";
            Toast toast = Toast.makeText(MapActivity.this, toastString, Toast.LENGTH_SHORT);
            toast.show();

            updateCameraWithLocation(mLastLocation);
            updateUserLastLocationWithLocation(mLastLocation);
            updateMapWithMarkers(mLastLocation, mDistance);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        String toastString = "Connexion failed";
        Toast toast = Toast.makeText(MapActivity.this, toastString, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        String toastString = "Connexion suspended";
        Toast toast = Toast.makeText(MapActivity.this, toastString, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        updateUserLastLocationWithLocation(location);
    }

    public int getUserAge(String birthdate) {
        String[] separated = birthdate.split("-");
        int year = Integer.parseInt(separated[2]);
        int month = Integer.parseInt(separated[1]);
        int day = Integer.parseInt(separated[0]);
        Date userBirthdate = getDate(year, month, day);
        Date today = new Date();
        long diff = today.getTime() - userBirthdate.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long years = days / 365;
        return (int) years;
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}