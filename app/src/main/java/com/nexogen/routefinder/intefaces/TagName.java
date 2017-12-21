package com.nexogen.routefinder.intefaces;


import android.graphics.Color;

public interface TagName {

    String GPS_SETTINGS = "GPS SETTINGS";

    String GPSENABLED = "GPS is not enabled! Want to go to settings menu?";

    String Cancel = "Cancel";
    String Settings = "Settings";

    String HomeFragment = "HomeFragment";
    String connected = "Good! Connected to Internet";
    String notConnected = "Sorry! Not connected to internet";


    int connectedColor = Color.YELLOW;
    int notConnectedColor = Color.RED;


    String fromAndTo = "please enter  source to destination";
}
