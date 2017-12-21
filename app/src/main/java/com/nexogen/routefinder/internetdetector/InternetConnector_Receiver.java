package com.nexogen.routefinder.internetdetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import static com.nexogen.routefinder.intefaces.TagName.connected;
import static com.nexogen.routefinder.intefaces.TagName.connectedColor;
import static com.nexogen.routefinder.intefaces.TagName.notConnected;
import static com.nexogen.routefinder.intefaces.TagName.notConnectedColor;

public class InternetConnector_Receiver extends BroadcastReceiver {
    public InternetConnector_Receiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            boolean isVisible = MyApplication.isActivityVisible();// Check if
            // activity
            // is
            // visible
            // or not
            Log.i("Activity is Visible ", "Is activity visible : " + isVisible);

            // If it is visible then trigger the task else do nothing
            if (isVisible == true) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();

                // Check internet connection and accrding to state change the
                // text of activity by calling method
                if (networkInfo != null && networkInfo.isConnected()) {

                    new com.nexogen.routefinder.MainActivity().changeTextStatusSnackBar(connected, connectedColor);
                    ;
                } else {
                    new com.nexogen.routefinder.MainActivity().changeTextStatusSnackBar(notConnected, notConnectedColor);
                    ;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
