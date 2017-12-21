package com.nexogen.routefinder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nexogen.routefinder.R;

import static com.nexogen.routefinder.fragments.MapLoadingFragment.MAP_TYPE_NORMAL;

/**
 * Created by nexogen on 11/12/17.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {
    public static boolean radios6, radios7, radios8, radios9;
    private View view;
    private boolean locFlag = false, remFlag = false, typeFlag = false;
    private RadioButton radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9;
    private ImageButton locationTrackTime, typeOfMap, removeLocHistory;
    private RelativeLayout relytLaytlocationTrackTime, relytLayttypeOfMap, relytLaytremoveLoc;
    private ImageButton btnCancel, btnDelete;

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


        radioButton(radios6, radios7, radios8, radios9);

        radioGroup();
    }

    private void radioGroup() {

        final RadioGroup radioGroup = view.findViewById(R.id.radio_container);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.radio1:
                        Toast.makeText(getActivity(), "radio1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio2:
                        break;
                    case R.id.radio3:
                        break;
                    case R.id.radio4:
                        break;
                    case R.id.radio5:
                        break;

                }
            }
        });
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

        radio1 = view.findViewById(R.id.radio1);
        radio2 = view.findViewById(R.id.radio2);
        radio3 = view.findViewById(R.id.radio3);
        radio4 = view.findViewById(R.id.radio4);
        radio5 = view.findViewById(R.id.radio5);
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
                Toast.makeText(getActivity(), "btnDelete", Toast.LENGTH_SHORT).show();
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
