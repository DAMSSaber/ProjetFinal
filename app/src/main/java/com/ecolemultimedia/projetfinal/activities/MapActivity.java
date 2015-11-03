package com.ecolemultimedia.projetfinal.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.views.ViewMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    RelativeLayout ui_rl_menu=null;

    private GoogleMap gMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ui_rl_menu=(RelativeLayout)findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu= new ViewMenu(this, MapActivity.this);
        viewMenu.init(0);
        ui_rl_menu.addView(viewMenu);


        setUpMapIfNeeded();
        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
        buildGoogleApiClient();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        //LatLng sydney = new LatLng(-34, 151);
        //map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            String toastString = "•••" + String.valueOf(mLastLocation.getLatitude()) + String.valueOf(mLastLocation.getLongitude());
            Toast toast = Toast.makeText(MapActivity.this, toastString, Toast.LENGTH_SHORT);
            toast.show();
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
}