package com.nexogen.routefinder.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nexogen.routefinder.LocationAddress;
import com.nexogen.routefinder.MainActivity;
import com.nexogen.routefinder.R;

/**
 * Created by nexogen on 7/12/17.
 */

public class MapLoadingFragment extends Fragment implements OnMapReadyCallback {

    public static int MAP_TYPE_NORMAL = 1;
    private View view;
    private GoogleMap mMap;
    private Bundle bundle;
    private LatLng CurrentLocat;
    private String locationAddress;
    private TextView Current_location;

    public MapLoadingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.current_location_layout, container, false);
        init();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationAddress locationAddress = new LocationAddress();

        try {
            locationAddress.getAddressFromLocation(MainActivity.latLongModel.get(0).getLatitude(), MainActivity.latLongModel.get(0).getLongitude(),
                    getActivity(), new GeocoderHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void init() {

        Current_location = view.findViewById(R.id.tv_locations);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        try {
            CurrentLocat = new LatLng(MainActivity.latLongModel.get(0).getLatitude(), MainActivity.latLongModel.get(0).getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.w("location", CurrentLocat + "");

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            try {
                Current_location.setText("Current Location : " + LocationAddress.address.getAddressLine(0) + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

//            Log.d("address-", LocationAddress.address.getLocality() + LocationAddress.address.getAddressLine(0));

//            LatLng latLngDelhi = new LatLng(28.6139, 77.2090);
//            PolygonOptions polyline = new PolygonOptions().add(CurrentLocat).add(latLngDelhi).strokeWidth(10).strokeColor(Color.BLUE).geodesic(true);
//            mMap.addPolygon(polyline);

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.setTrafficEnabled(true);
            mMap.setIndoorEnabled(true);
            mMap.setBuildingsEnabled(true);

            mMap.addMarker(new MarkerOptions().position(CurrentLocat).title(" Your Current Location-" + bundle.getString("city") + "," + bundle.getString("country")));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocat, 15));

            // set map type
            mMap.setMapType(MAP_TYPE_NORMAL);

            // user control zoom in and zoom out
            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.getUiSettings().setCompassEnabled(true);

        }
    }
}
