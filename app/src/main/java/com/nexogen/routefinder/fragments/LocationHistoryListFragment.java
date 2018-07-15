package com.nexogen.routefinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nexogen.routefinder.ChekNetwork.CheckNetworkConnection;
import com.nexogen.routefinder.model.NavigatorModel;
import com.nexogen.routefinder.R;
import com.nexogen.routefinder.adapter.LocationHistoryAdapter;
import com.nexogen.routefinder.databases.AppDatabase;
import com.nexogen.routefinder.databases.NavigatorTable;
import com.nexogen.routefinder.intefaces.TagName;
import com.nexogen.routefinder.utils.UtilClass;

import java.util.ArrayList;
import java.util.List;

import static com.nexogen.routefinder.adapter.LocationHistoryAdapter.navList;
import static com.nexogen.routefinder.intefaces.TagName.delteSuccsessfully;



public class LocationHistoryListFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {


    private UtilClass utilClass;
    private View view;
    private AppDatabase appDatabase;
    private List<NavigatorTable> navigatorTables;
    private ListView listView;
    private ImageButton btnCancel;
    private ImageButton btnDelete;
    private List<NavigatorModel> navigatorModels, deleteNavigatorModels;
    private List<NavigatorTable> updatedData;
    private double[] dataSource = {0, 0};
    private double[] dataDestination = {0, 0};

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


        init();
        objectIntialize();
        adapters();


    }

    private void objectIntialize() {
        navigatorModels = new ArrayList<>();
        deleteNavigatorModels = new ArrayList<>();
        utilClass = new UtilClass();
        appDatabase = AppDatabase.getDatabase(getActivity());
        navigatorTables = appDatabase.navigatorDao().getAllUser();

    }

    private void adapters() {
        for (int count = 0; count < navigatorTables.size(); count++) {
            navigatorModels.add(new NavigatorModel(navigatorTables.get(count).getId(), navigatorTables.get(count).getSource(), navigatorTables.get(count).getDestination(), navigatorTables.get(count).getDateTime()));
        }
        LocationHistoryAdapter locationHistoryAdapter = new LocationHistoryAdapter(getActivity(), navigatorModels);
        listView.setAdapter(locationHistoryAdapter);
        locationHistoryAdapter.notifyDataSetChanged();


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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TextView tv_source = (TextView) view.getTag(R.id.tv_source);
        TextView tv_source1 = (TextView) view.getTag(R.id.tv_source1);
        TextView tv_destination = (TextView) view.getTag(R.id.tv_destination);
        CheckBox checkbox = (CheckBox) view.getTag(R.id.check);

        String tv_sources = tv_source1.getText().toString();
        String destination = tv_destination.getText().toString();


        if (CheckNetworkConnection.isConnectionAvailable(getActivity())) {
            dataSource = utilClass.getAddressName(tv_sources, getActivity());
            dataDestination = utilClass.getAddressName(destination, getActivity());

            utilClass.openGoogleMap(getActivity(), dataSource[0], dataSource[1]
                    , dataDestination[0], dataDestination[1]);
        } else {
            Toast.makeText(getActivity(), TagName.notConnected, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_btn:
                delteTable(false);
                break;
            case R.id.btn_cancel:
                delteTable(true);
                break;
        }


    }

    private void delteTable(boolean b) {

        deleteNavigatorModels.clear();
        for (int count = 0; count < navList.size(); count++) {
            if (navList.get(count).isSelected() == true) {
                deleteNavigatorModels.add(navList.get(count));
            }
        }


        if (b == false) {
            for (int counts = 0; counts < deleteNavigatorModels.size(); counts++) {
                appDatabase.navigatorDao().deleteById(deleteNavigatorModels.get(counts).getId());
            }
            Toast.makeText(getActivity(), delteSuccsessfully, Toast.LENGTH_SHORT).show();

        }

        updatedData = appDatabase.navigatorDao().getAllUser();
        navigatorModels.clear();
        for (int count = 0; count < updatedData.size(); count++) {
            navigatorModels.add(new NavigatorModel(updatedData.get(count).getId(), updatedData.get(count).getSource(), updatedData.get(count).getDestination(), updatedData.get(count).getDateTime()));
        }
        LocationHistoryAdapter locationHistoryAdapter = new LocationHistoryAdapter(getActivity(), navigatorModels);
        listView.setAdapter(locationHistoryAdapter);
        locationHistoryAdapter.notifyDataSetChanged();


    }
}
