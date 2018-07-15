package com.nexogen.routefinder.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nexogen.routefinder.R;

public class ElocByScan extends FragmentActivity implements OnMapReadyCallback {

    TextView tv_eloc_info;
    private GoogleMap mMap;
    String eloc_info;

    public String eloc_id,hno,street,landmark,area,village_town,pincode,district,state,resident_type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eloc_by_scan);
        tv_eloc_info=(TextView)findViewById(R.id.tv_eloc_info_map);

        eloc_id=showQRCocde.map_eloc_id;
        hno=showQRCocde.map_hno;
        street=showQRCocde.map_street;
        landmark=showQRCocde.map_landmark;
        area=showQRCocde.map_area;
        village_town=showQRCocde.map_village_town;
        pincode=showQRCocde.map_pincode;
        district=showQRCocde.map_district;
        state=showQRCocde.map_state;
        resident_type=showQRCocde.map_resident_type;


        eloc_info =("eloc_id:"+eloc_id+","+"Resident Type:"+resident_type+","+"House/Floor/Door No:"+hno+","+"Street/Road/Lane:"+street+","+
                "Landmark:"+landmark+","+"Area/Locality:"+area+","+"Village/Town/City:"+village_town+","+"PIN_Code:"+pincode+","+"District:"+district+","+
                "State:"+state);


        tv_eloc_info.setText(eloc_info);
        //Toast.makeText(getApplicationContext(),eloc_info,Toast.LENGTH_LONG).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.eloc_scan_by_map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in searched place
        LatLng currentloc = new LatLng(showQRCocde.map_lat,showQRCocde.map_lng);
        mMap.addMarker(new MarkerOptions().position(currentloc).title(eloc_id));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentloc,17));

    }
}
