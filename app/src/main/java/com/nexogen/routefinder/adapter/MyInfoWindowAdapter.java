package com.nexogen.routefinder.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nexogen.routefinder.R;

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final View myContentsView;

    String placeName;
    private Context mContext;

    @SuppressLint({"InflateParams"})
    public MyInfoWindowAdapter(Context mContexts, String placeName) {
        this.mContext = mContexts;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myContentsView = inflater.inflate(R.layout.title_place_detail_infoin_map, null);

        this.placeName = placeName;
    }

    public View getInfoContents(Marker marker) {
        String title = marker.getTitle();
        ((TextView) this.myContentsView.findViewById(R.id.title)).setText(title.substring(0, title.indexOf(":")));
        TextView tvSnippet = (TextView) this.myContentsView.findViewById(R.id.snippet);

        String distance = title.substring(title.indexOf(":") + 1);
        tvSnippet.setText(distance);


        return this.myContentsView;
    }

    public View getInfoWindow(Marker marker) {
        return null;
    }
}