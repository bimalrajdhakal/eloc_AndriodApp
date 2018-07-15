package com.nexogen.routefinder.Modules;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String> {
    Context mContext;
    MarkerOptions markerOptions;
    TextView current_locations;
    GoogleMap mMap;
    public ReverseGeocodingTask(MarkerOptions markerOptions, GoogleMap mMap, Context context, TextView current_location){
        super();
        mContext = context;
        this.mMap=mMap;
        this.markerOptions=markerOptions;
        this.current_locations=current_location;
    }

    // Finding address using reverse geocoding
    @Override
    protected String doInBackground(LatLng... params) {
        Geocoder geocoder = new Geocoder(mContext);
        double latitude = params[0].latitude;
        double longitude = params[0].longitude;

        List<Address> addresses = null;
        String addressText="";

        try {
            addresses = geocoder.getFromLocation(latitude, longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses != null && addresses.size() > 0 ){
            addressText = addresses.get(0).getAddressLine(0);
        }

        return addressText;
    }

    @Override
    protected void onPostExecute(String addressText) {
        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(addressText);


        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);
        current_locations.setText(addressText);



    }
}