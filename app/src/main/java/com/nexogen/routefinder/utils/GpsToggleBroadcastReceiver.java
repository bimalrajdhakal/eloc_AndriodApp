package com.nexogen.routefinder.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;



public class GpsToggleBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("com.nexogen.routefinder.location.enabled"));


                Toast.makeText(context, "location-" + locationManager, Toast.LENGTH_SHORT).show();

            }
        } else if (intent.getAction().matches("android.net.conn.CONNECTIVITY_CHANGE") || intent.getAction().matches("android.net.wifi.WIFI_STATE_CHANGED")) {
            int status = ConnectivityUtils.getConnectivityStatusString(context);
            if (status != ConnectivityUtils.NETWORK_STATUS_NOT_CONNECTED) {
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("com.nexogen.routefinder.internet.enabled"));
            }
        }
    }

}
