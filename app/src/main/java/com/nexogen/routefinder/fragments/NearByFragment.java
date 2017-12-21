package com.nexogen.routefinder.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nexogen.routefinder.R;
import com.nexogen.routefinder.adapter.RecyclerAdapter;
import com.nexogen.routefinder.model.HomeModel;
import com.nexogen.routefinder.nearplaces.MapsActivity;

import java.util.ArrayList;

/**
 * Created by nexogen on 15/12/17.
 */

public class NearByFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View view;
    private ArrayList<HomeModel> homeModels;
    private GridView listView;
    private String[] placeTypeName, placesName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nearby_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init();


    }

    private void init() {
        placeTypeName = getResources().getStringArray(R.array.place_type_name);
        placesName = getResources().getStringArray(R.array.place_type);


        homeModels = new ArrayList<>();
        listView = (GridView) view.findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

        listView.setAdapter(new RecyclerAdapter(getActivity(), placeTypeName, placesName));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Intent intent = new Intent(getContext(), MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("places", placesName[position]);
        intent.putExtra("placesName", placeTypeName[position]);
        startActivity(intent);

    }
}
