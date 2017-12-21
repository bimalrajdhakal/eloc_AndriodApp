package com.nexogen.routefinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nexogen.routefinder.MultipleSelection.NavigatorModel;
import com.nexogen.routefinder.R;
import com.nexogen.routefinder.adapter.NavigatorAdapter;
import com.nexogen.routefinder.databases.AppDatabase;
import com.nexogen.routefinder.databases.NavigatorTable;
import com.nexogen.routefinder.utils.UtilClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nexogen on 8/12/17.
 */

public class LocationHistoryListFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    UtilClass utilClass;
    private View view;
    private AppDatabase appDatabase;
    private List<NavigatorTable> navigatorTables;
    private ListView listView;
    private ImageButton btnCancel;
    private ImageButton btnDelete;
    private List<NavigatorModel> navigatorModels;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_history_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navigatorModels = new ArrayList<>();
        init();
        utilClass = new UtilClass();


        appDatabase = AppDatabase.getDatabase(getActivity());

        navigatorTables = appDatabase.navigatorDao().getAllUser();


        for (int count = 0; count < navigatorTables.size(); count++) {
            navigatorModels.add(new NavigatorModel(count, navigatorTables.get(count).getSource(), navigatorTables.get(count).getDestination(), navigatorTables.get(count).getDateTime()));
        }


        listView.setAdapter(new NavigatorAdapter(getActivity(), navigatorModels));

        Log.w("check", navigatorTables.toString());

    }

    private void init() {
        listView = (ListView) view.findViewById(R.id.list_view);

        listView.setOnItemClickListener(this);

        btnCancel = (ImageButton) view.findViewById(R.id.btn_cancel);
        btnDelete = (ImageButton) view.findViewById(R.id.delete_btn);

        btnCancel.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tv_source = (TextView) view.getTag(R.id.tv_source);
        TextView tv_destination = (TextView) view.getTag(R.id.tv_destination);
        CheckBox checkbox = (CheckBox) view.getTag(R.id.check);

        String source = tv_source.getText().toString();
        String destination = tv_destination.getText().toString();


        Log.w("check", source + "-" + destination);

//        utilClass.openGoogleMap(getActivity(), MainActivity.latLongModel.get(0).latitude, MainActivity.latLongModel.get(0).longitude
//                , data[0], data[1]);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.delete_btn:
                break;

            case R.id.btn_cancel:
                break;
        }


    }
}
