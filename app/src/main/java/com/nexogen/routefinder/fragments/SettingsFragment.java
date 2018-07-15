package com.nexogen.routefinder.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nexogen.routefinder.R;
import com.nexogen.routefinder.adapter.ListAdapters;
import com.nexogen.routefinder.databases.AppDatabase;
import com.nexogen.routefinder.databases.NavigatorTable;
import com.nexogen.routefinder.utils.CustomToast_nearby;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nexogen.routefinder.activity.MainActivity.toolbar_title;
import static com.nexogen.routefinder.fragments.MapLoadingFragment.MAP_TYPE_NORMAL;
import static com.nexogen.routefinder.intefaces.TagName.deleteLocationHistory;
import static com.nexogen.routefinder.intefaces.TagName.locationHistorynotAvailable;



public class SettingsFragment extends Fragment implements View.OnClickListener {
    public static boolean radios6, radios7, radios8, radios9;
    private View view;
    private boolean locFlag = false, remFlag = false, typeFlag = false;
    private RadioButton radio6, radio7, radio8, radio9;
    private ImageButton locationTrackTime, typeOfMap, removeLocHistory;
    private RelativeLayout relytLaytlocationTrackTime, relytLayttypeOfMap, relytLaytremoveLoc;
    private ImageButton btnCancel, btnDelete;
    private ListView listView;
    private ListAdapters listAdapters;
    private List<String> listData;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.setting_layout, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();


        toolbar_title.setText(getResources().getString(R.string.Settings));

        radioButton(radios6, radios7, radios8, radios9);

        radioGroup();

    }

    private void radioGroup() {


        RadioGroup radioGroups = view.findViewById(R.id.radio_container2);
        radioGroups.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.radio6:
                        // Normal map type  1
                        normalMap();
                        break;
                    case R.id.radio7:
                        // Hybrid map type 4
                        hybridMap();
                        break;
                    case R.id.radio8:
                        // satellite map type 2
                        settelitMap();
                        break;
                    case R.id.radio9:
                        // Terrain map type 3
                        terrinMap();

                        break;
                }
            }
        });


    }

    private void terrinMap() {
        radioButton(false, false, false, true);
        MAP_TYPE_NORMAL = 3;

        radios6 = false;
        radios7 = false;
        radios8 = false;
        radios9 = true;
    }

    private void settelitMap() {
        radios6 = false;
        radios7 = false;
        radios8 = true;
        radios9 = false;
        MAP_TYPE_NORMAL = 2;
        radioButton(false, false, true, false);
    }

    private void hybridMap() {
        MAP_TYPE_NORMAL = 4;
        radios6 = false;
        radios7 = true;
        radios8 = false;
        radios9 = false;
        radioButton(false, true, false, false);

    }

    private void normalMap() {
        MAP_TYPE_NORMAL = 1;
        radios6 = true;
        radios7 = false;
        radios8 = false;
        radios9 = false;
        radioButton(true, false, false, false);
    }

    private void radioButton(boolean radios6, boolean radios7, boolean radios8, boolean radios9) {
        radio6.setChecked(radios6);
        radio7.setChecked(radios7);
        radio8.setChecked(radios8);
        radio9.setChecked(radios9);

    }

    private void init() {
        sharedPreferences = getActivity().getSharedPreferences("common", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();


        listData=new ArrayList<>();

        String [] data=getActivity().getResources().getStringArray(R.array.radioTime);

        listData= Arrays.asList(data);

        listView =view. findViewById(R.id.list_view);

        listAdapters = new ListAdapters(getActivity(), listData);

        listView.setAdapter(listAdapters);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                editor.putInt("position",position);

                switch (position){
                    case 0:
                        editor.putInt("time",10);
                        break;
                    case 1:
                        editor.putInt("time",15);
                        break;
                    case 2:
                        editor.putInt("time",20);
                        break;
                    case 3:
                        editor.putInt("time",30);
                        break;
                    case 4:
                        editor.putInt("time",60);
                        break;

                }
                editor.commit();
                listAdapters.setCurrentSelection(position);
                listAdapters.notifyDataSetChanged();

            }
        });




        radio6 = view.findViewById(R.id.radio6);
        radio7 = view.findViewById(R.id.radio7);
        radio8 = view.findViewById(R.id.radio8);
        radio9 = view.findViewById(R.id.radio9);

        btnCancel = view.findViewById(R.id.btn_cancel);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnCancel.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


        locationTrackTime = view.findViewById(R.id.rootLayt1);
        locationTrackTime.setOnClickListener(this);

        typeOfMap = view.findViewById(R.id.rootLayt2);
        typeOfMap.setOnClickListener(this);

        removeLocHistory = view.findViewById(R.id.rootLayt3);
        removeLocHistory.setOnClickListener(this);

        relytLaytlocationTrackTime = view.findViewById(R.id.relayLaytLocTrackTime);
        relytLayttypeOfMap = view.findViewById(R.id.reltLayt_typeOfMap);
        relytLaytremoveLoc = view.findViewById(R.id.relyLayt_removeLoc);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rootLayt1:
                locTrack();
                break;
            case R.id.rootLayt2:
                typeOFMap();
                break;
            case R.id.rootLayt3:
                removeLoc();
                break;
            case R.id.btn_cancel:
                remFlag = false;
                removeLocHistory.setBackgroundResource(R.drawable.removelocation);
                relytLaytremoveLoc.setVisibility(View.GONE);

                break;
            case R.id.btn_delete:
                AppDatabase appDatabase = AppDatabase.getDatabase(getActivity());
                List<NavigatorTable> updatedData = appDatabase.navigatorDao().getAllUser();


                if (updatedData.size() > 0) {
                    CustomToast_nearby.Show(getActivity(), deleteLocationHistory, true);
                    appDatabase.navigatorDao().removeAllUsers();

                } else {
                    CustomToast_nearby.Show(getActivity(), locationHistorynotAvailable, false);

                }

                break;
        }
    }

    private void removeLoc() {
        if (remFlag == false) {
            remFlag = true;
            removeLocHistory.setBackgroundResource(R.drawable.removelocation_click);
            relytLaytremoveLoc.setVisibility(View.VISIBLE);

        } else {
            remFlag = false;
            removeLocHistory.setBackgroundResource(R.drawable.removelocation);
            relytLaytremoveLoc.setVisibility(View.GONE);

        }
        if (locFlag == true) {
            locationTrackTime.setBackgroundResource(R.drawable.locationt);
            relytLaytlocationTrackTime.setVisibility(View.GONE);
            locFlag = false;
        }

        if (typeFlag == true) {
            typeOfMap.setBackgroundResource(R.drawable.typeofmap);
            relytLayttypeOfMap.setVisibility(View.GONE);
            typeFlag = false;
        }
    }

    private void typeOFMap() {
        if (typeFlag == false) {
            typeFlag = true;
            typeOfMap.setBackgroundResource(R.drawable.typeofmap_click);
            relytLayttypeOfMap.setVisibility(View.VISIBLE);

        } else {
            typeFlag = false;
            typeOfMap.setBackgroundResource(R.drawable.typeofmap);
            relytLayttypeOfMap.setVisibility(View.GONE);

        }
        if (locFlag == true) {
            locationTrackTime.setBackgroundResource(R.drawable.locationt);
            relytLaytlocationTrackTime.setVisibility(View.GONE);
            locFlag = false;
        }

        if (remFlag == true) {
            removeLocHistory.setBackgroundResource(R.drawable.removelocation);
            relytLaytremoveLoc.setVisibility(View.GONE);
            remFlag = false;
        }
    }

    private void locTrack() {
        if (locFlag == false) {
            locFlag = true;
            locationTrackTime.setBackgroundResource(R.drawable.locationt_click);
            relytLaytlocationTrackTime.setVisibility(View.VISIBLE);

        } else {
            locFlag = false;
            locationTrackTime.setBackgroundResource(R.drawable.locationt);
            relytLaytlocationTrackTime.setVisibility(View.GONE);

        }
        if (remFlag == true) {
            removeLocHistory.setBackgroundResource(R.drawable.removelocation);
            relytLaytremoveLoc.setVisibility(View.GONE);
            remFlag = false;
        }

        if (typeFlag == true) {
            typeOfMap.setBackgroundResource(R.drawable.typeofmap);
            relytLayttypeOfMap.setVisibility(View.GONE);
            typeFlag = false;
        }
    }


}
