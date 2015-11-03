package com.ecolemultimedia.projetfinal.activities;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RelativeLayout;

import com.ecolemultimedia.projetfinal.R;
import com.ecolemultimedia.projetfinal.views.ViewMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    RelativeLayout ui_rl_menu=null;

    private LocationManager locationManager;
    private GoogleMap gMap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ui_rl_menu=(RelativeLayout)findViewById(R.id.ui_rl_menu);
        ViewMenu viewMenu= new ViewMenu(this,MapActivity.this);
        viewMenu.init(0);
        ui_rl_menu.addView(viewMenu);


        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();

        //Obtention de la référence du service
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        //Si le GPS est disponible, on s'y abonne
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        desabonnementGPS();
    }


    public void abonnementGPS() {
        //On s'abonne
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }


    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(final Location location) {

        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (latLng != null) {
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            gMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Hello Saber"));
        }
    }


    @Override
    public void onProviderDisabled(final String provider) {
        //Si le GPS est désactivé on se désabonne
        if ("gps".equals(provider)) {
            desabonnementGPS();
        }
    }


    @Override
    public void onProviderEnabled(final String provider) {
        //Si le GPS est activé on s'abonne
        if ("gps".equals(provider)) {
            abonnementGPS();
        }
    }


    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.setTrafficEnabled(true);
    }
}