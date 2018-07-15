package com.nexogen.routefinder.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nexogen.routefinder.R;

/**
 * Created by bimal on 19/4/18.
 */

public class SearchElocFragment extends Fragment implements OnMapReadyCallback {
    public GoogleMap mMap;

    Double lat=SearchEloc.lati;
    Double lng=SearchEloc.longi;



    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.eloc_search_map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap= googleMap;
        LatLng eloclatlng= new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(eloclatlng).title("Here").icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eloclatlng));
    }
}
