package com.nexogen.routefinder.Modules;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {
    private static final String TAG = "LocationAddress";
    public static Address address;

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = null;
        try {
            thread = new Thread() {
                @Override
                public void run() {
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    String result = null;
                    try {
                        List<Address> addressList = geocoder.getFromLocation(
                                latitude, longitude, 1);
                        if (addressList != null && addressList.size() > 0) {
                            address = addressList.get(0);
                            StringBuilder sb1 = new StringBuilder();
                            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                                sb1.append(address.getAddressLine(i)).append(",");
                            }

                            result = sb1.toString();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Unable connect to Geocoder", e);
                    } finally {
                        Message message = Message.obtain();
                        message.setTarget(handler);
                        if (result != null) {
                            message.what = 1;
                            Bundle bundle = new Bundle();

                            bundle.putString("address1", String.valueOf(address));

                            bundle.putString("sublocality", address.getSubLocality());
                            bundle.putString("city", address.getLocality());
                            bundle.putString("postalCode", address.getPostalCode());
                            bundle.putString("country", address.getCountryName());

                            message.setData(bundle);
                        } else {
                            message.what = 1;
                            Bundle bundle = new Bundle();
                            result = "Latitude: " + latitude + " Longitude: " + longitude +
                                    "\n Unable to get address for this lat-long.";
                            bundle.putString("address", result);
                            message.setData(bundle);
                        }
                        message.sendToTarget();
                    }
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        thread.start();
    }
}