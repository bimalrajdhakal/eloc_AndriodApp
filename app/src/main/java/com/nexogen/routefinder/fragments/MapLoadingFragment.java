package com.nexogen.routefinder.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nexogen.routefinder.Modules.DirectionFinder;
import com.nexogen.routefinder.Modules.LocationAddress;
import com.nexogen.routefinder.R;
import com.nexogen.routefinder.activity.MainActivity;
import com.nexogen.routefinder.intefaces.DirectionFinderListener;
import com.nexogen.routefinder.model.Route;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static com.nexogen.routefinder.activity.MainActivity.toolbar_title;
import static com.nexogen.routefinder.intefaces.TagName.DESTINATION;


public class MapLoadingFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, DirectionFinderListener, LocationListener {

    public static int MAP_TYPE_NORMAL = 1;
    protected LocationManager locationManager;
    private View view;
    private GoogleMap mMap;
    private Bundle bundle;
    private LatLng CurrentLocat;
    private String locationAddress;
    private TextView Current_location;
    private ImageButton btn_search;
    private EditText edt_search;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private MarkerOptions markerOptions;
    private double latitude;
    private double longitude;
    private Location location;
    private SharedPreferences sharedPreferences;
    private int time;


    public MapLoadingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.current_location_layout, container, false);
        init();
        edt_search.setCursorVisible(false);

        toolbar_title.setText(getResources().getString(R.string.currentLocation));
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
        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_search.setCursorVisible(true);
            }
        });

    }

    private void init() {
        sharedPreferences = getActivity().getSharedPreferences("common", Context.MODE_PRIVATE);


        Current_location = view.findViewById(R.id.tv_locations);

        btn_search = (ImageButton) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

        edt_search = (EditText) view.findViewById(R.id.edt_search);
        edt_search.setText("");

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (isGPSOn()) {

            if (sharedPreferences.getBoolean("service_on", true) == true) {
                getLocation(LocationManager.GPS_PROVIDER);

//                Toast.makeText(getActivity(), serviceOn, Toast.LENGTH_SHORT).show();

            } else {

//                Toast.makeText(getActivity(), serviceOff, Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }


    private boolean isGPSOn() {
        if ((locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
            return true;
        }
        return false;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            CurrentLocat = new LatLng(MainActivity.latLongModel.get(0).getLatitude(), MainActivity.latLongModel.get(0).getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Location getLocation(String provider) {
        try {
            if (ActivityCompat.checkSelfPermission((Activity) getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 10);
            }


            time = sharedPreferences.getInt("time", 60000 * 10);

            locationManager.requestLocationUpdates(provider, 60000 * time, 0, this);
            location = locationManager.getLastKnownLocation(provider);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return location;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_search:

                sendRequest();
                edt_search.setCursorVisible(false);

                break;
        }

    }


    private void sendRequest() {
        String origin = LocationAddress.address.getAddressLine(0);
        String destination = edt_search.getText().toString();
        if (destination.isEmpty()) {
            Toast.makeText(getActivity(), DESTINATION, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder((DirectionFinderListener) this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(getActivity(), "Please wait.",
                "Finding direction..!", true, false);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        mMap.clear();
        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 12));
            ((TextView) view.findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) view.findViewById(R.id.tvDistance)).setText(route.distance.text);

//            originMarkers.add(mMap.addMarker(new MarkerOptions()
//
//                    // origin direction polyline path
//                    .icon(BitmapDescriptorFactory.fromResource(Color.TRANSPARENT))
////                    .title(route.startAddress)
//                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLACK).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap.clear();
        stopUsingGPS();

    }

    private void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(getActivity());
        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 3);

        } catch (IOException e) {
            e.printStackTrace();

        }
        try {
            Current_location.setText("Current Location : " + addressList.get(0).getAddressLine(0) + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        LatLng latLng = new LatLng(latitude, longitude);

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

        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);

        markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue));
        markerOptions.alpha(0.99f);
        mMap.addMarker(markerOptions.position(latLng).snippet("Current location").title(" Your are here" /*+ bundle.getString("city") + "," + bundle.getString("country")*/)/*.icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location))*/);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


        // set map type
        mMap.setMapType(MAP_TYPE_NORMAL);

        // user control zoom in and zoom out
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

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

            mMap.setIndoorEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);

            mMap.setPadding(0, 120, 0, 0);
            markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue));
            markerOptions.alpha(0.99f);
            mMap.addMarker(markerOptions.position(CurrentLocat).snippet("Current location").title(" Your are here" /*+ bundle.getString("city") + "," + bundle.getString("country")*/)/*.icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location))*/);

            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    CurrentLocat, 16);
            mMap.animateCamera(location);


            // set map type
            mMap.setMapType(MAP_TYPE_NORMAL);

            // user control zoom in and zoom out
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);

        }
    }


}
