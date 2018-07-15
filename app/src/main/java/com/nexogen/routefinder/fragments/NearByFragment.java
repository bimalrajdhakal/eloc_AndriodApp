package com.nexogen.routefinder.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nexogen.routefinder.R;
import com.nexogen.routefinder.adapter.RecyclerAdapter;
import com.nexogen.routefinder.model.placesNamesModel;
import com.nexogen.routefinder.nearplaces.MapsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nexogen.routefinder.activity.MainActivity.toolbar_title;


public class NearByFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private View view;
    private RelativeLayout rly_search, dropBax;
    private GridView listView;
    private String[] placesName;
    private SharedPreferences.Editor editor;
    private EditText editText;
    private ImageButton btnCancel, search_btn;
    private List<String> placesNames;
    private String value;
    private TextView tv_range;
    private List<placesNamesModel> placesNamesList, newPlaceNames;
    private Spinner spinner;
    private boolean flag = false;


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
        toolbar_title.setText(getResources().getString(R.string.nearBy));

        spinnerAdapter();

        searchFromEditText();


    }

    private void spinnerAdapter() {
        rly_search = view.findViewById(R.id.rly_search);
        dropBax = view.findViewById(R.id.dropBax);


        tv_range = view.findViewById(R.id.tv_range);

        search_btn = view.findViewById(R.id.search_btn);

        search_btn.setOnClickListener(this);

        spinner = (Spinner) view.findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);

        String[] range = getResources().getStringArray(R.array.proximity_radius);

        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, range);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    private void searchFromEditText() {


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnCancel = (ImageButton) view.findViewById(R.id.btn_cancel);
                newPlaceNames.clear();
                value = editText.getText().toString().toLowerCase();

                for (int j = 0; j < placesNamesList.size(); j++) {

                    if (placesNamesList.get(j).getPlaceName().startsWith(value)) {

                        newPlaceNames.add(placesNamesList.get(j));

                    }
                }

                if (newPlaceNames.isEmpty()) {
                    listView.setAdapter(new RecyclerAdapter(getActivity(), placesNamesList));

                } else {

                    listView.setAdapter(new RecyclerAdapter(getActivity(), newPlaceNames));


                }
                if (value.isEmpty()) {
                    listView.setAdapter(new RecyclerAdapter(getActivity(), placesNamesList));

                    btnCancel.setVisibility(View.GONE);
                } else if (value != null) {
                    btnCancel.setVisibility(View.VISIBLE);
                }


                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText.setText("");
                        btnCancel.setVisibility(View.GONE);
                        listView.setAdapter(new RecyclerAdapter(getActivity(), placesNamesList));
                    }
                });


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void init() {
        placesNames = new ArrayList<>();

        newPlaceNames = new ArrayList<>();
        placesNamesList = new ArrayList<>();

        editText = view.findViewById(R.id.edtText);


        placesName = getResources().getStringArray(R.array.place_type);

        placesNames = Arrays.asList(placesName);

        for (int count = 0; count < placesNames.size(); count++) {
            placesNamesList.add(new placesNamesModel(placesNames.get(count), count));
        }
        listView = (GridView) view.findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);


        listView.setAdapter(new RecyclerAdapter(getActivity(), placesNamesList));


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(getContext(), MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (newPlaceNames.size() == 0) {
            intent.putExtra("places", placesNamesList.get(position).getPlaceName());
            intent.putExtra("position", placesNamesList.get(position).getId());

        } else {
            intent.putExtra("places", newPlaceNames.get(position).getPlaceName());
            intent.putExtra("position", newPlaceNames.get(position).getId());
        }

        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("common", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        switch (position) {
            case 0:
                editPut(10000);
                break;
            case 1:
                editPut(15000);
                break;
            case 2:
                editPut(25000);
                break;
            case 3:
                editPut(35000);
                break;
            case 4:
                editPut(40000);
                break;
            case 5:
                editPut(50000);
                break;

        }
        editor.commit();


    }

    private void editPut( int range) {
        editor.putInt("PROXIMITY_RADIUS", range);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_btn:

                if (flag == false) {
                    rly_search.setVisibility(View.VISIBLE);
                    tv_range.setVisibility(View.GONE);
                    spinner.setVisibility(View.GONE);
                    dropBax.setVisibility(View.GONE);
                    flag = true;
                } else {
                    rly_search.setVisibility(View.GONE);
                    tv_range.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                    dropBax.setVisibility(View.VISIBLE);
                    flag = false;
                }


                break;
        }
    }
}
