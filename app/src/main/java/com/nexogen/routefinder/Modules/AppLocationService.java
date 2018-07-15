package com.nexogen.routefinder.Modules;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;


public class AppLocationService extends Service implements LocationListener {

    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    protected LocationManager locationManager;
    Location location;
    Context mcontext;

    public AppLocationService(Context context) {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        this.mcontext = context;
    }

    public Location getLocation(String provider) {
        if (ActivityCompat.checkSelfPermission((Activity) mcontext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mcontext, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 10);
        }


        locationManager.requestLocationUpdates(provider, 60000*15, MIN_DISTANCE_FOR_UPDATE, this);
        location = locationManager.getLastKnownLocation(provider);
        return location;
    }


    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}