package com.nexogen.routefinder.utils;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;

import com.nexogen.routefinder.AppLocationService;

import java.io.IOException;
import java.util.List;

/**
 * Created by nexogen on 19/12/17.
 */

public class UtilClass {

    public double[] getAddressName(String locationAdd, Context mContext) {
        List<Address> addressList = null;
        double[] position = {0, 0};

        AppLocationService appLocationService = new AppLocationService(mContext);
        Location locations = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
        if (locations == null) {
            locations = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);
        }

        if (locations != null || !locations.equals("")) {
            Geocoder geocoder = new Geocoder(mContext);
            try {
                addressList = geocoder.getFromLocationName(locationAdd, 3);

            } catch (IOException e) {
                e.printStackTrace();

            }

            try {
                Address address = addressList.get(0);
                position[0] = address.getLatitude();
                position[1] = address.getLongitude();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        }
        return position;
    }

    public void launchGoogleNavigation(Context context, double mLat, double mLong) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + mLat + "," + mLong));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void openGoogleMap(Context mContext, double latitude, double longitude, double latitude1, double longitude1) {
        Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                .parse("http://maps.google.com/maps?saddr="
                        + latitude + ","
                        + longitude + "&daddr="
                        + latitude1 + "," + longitude1));
        mContext.startActivity(navigation);
    }

}
