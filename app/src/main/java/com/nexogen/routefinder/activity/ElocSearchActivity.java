package com.nexogen.routefinder.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nexogen.routefinder.R;
import com.nexogen.routefinder.model.LatLongModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.nexogen.routefinder.activity.SearchEloc.eloc_info;

public class ElocSearchActivity extends FragmentActivity implements OnMapReadyCallback {

    TextView tv_eloc_info;


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eloc_search);

        tv_eloc_info=(TextView)findViewById(R.id.tv_eloc_info);

        tv_eloc_info.setText(SearchEloc.eloc_info);

                            //1L6WMS FPY22U

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.eloc_search_map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;
            LatLng currentloc = new LatLng(SearchEloc.lati,SearchEloc.longi);
            mMap.addMarker(new MarkerOptions().position(currentloc).title(SearchEloc.eloc_id));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentloc,17));

    }
}
