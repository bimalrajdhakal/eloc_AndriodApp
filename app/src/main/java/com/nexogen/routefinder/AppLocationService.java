package com.nexogen.routefinder;

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


public class AppLocationService extends Service implements LocationListener {

    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;
    protected LocationManager locationManager;
    Location location;
    Context mcontext;

    public AppLocationService(Context context) {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        mcontext = context;
    }

    public Location getLocation(String provider) {
        if (ActivityCompat.checkSelfPermission((Activity) mcontext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mcontext, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 10);
        }


        locationManager.requestLocationUpdates(provider, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
        location = locationManager.getLastKnownLocation(provider);
        return location;
    }


    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();


//        SharedPreferences sharedPreferences=sharedPreferences(this);
//        SharedPreferences.Editor editor =sharedPreferences.edit();
//        editor.putFloat("latitude", (float) latitude);
//        editor.putFloat("longitude", (float) longitude);
//        editor.commit();


    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
//        Toast.makeText(this, "provider--"+provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}