package com.nexogen.routefinder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.nexogen.routefinder.R;
import com.nexogen.routefinder.fragments.MapLoadingFragment;
import com.nexogen.routefinder.model.ObjectDrawerItem;

import static com.nexogen.routefinder.activity.MainActivity.mNavigationDrawerHelper;


public class DrawerItemCustomAdapter extends BaseAdapter {

    private final MapLoadingFragment mapLoadingFragment;
    Context mContext;
    int mLayoutResourceId;
    ObjectDrawerItem mData[] = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public DrawerItemCustomAdapter(Context context, int layoutResourceId, ObjectDrawerItem[] data) {
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;

        sharedPreferences = mContext.getSharedPreferences("common", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

         mapLoadingFragment= new MapLoadingFragment();

    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        ObjectDrawerItem objectDrawerItem = mData[position];


        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(mLayoutResourceId, parent, false);

        ImageView iconImageView = (ImageView) listItem.findViewById(R.id.drawer_item_icon);
        TextView nameTextView = (TextView) listItem.findViewById(R.id.drawer_item_name);
        final SwitchButton switchButton = listItem.findViewById(R.id.sb_default);

        if (position > 3) {
            listItem.findViewById(R.id.dvider_reltive).setVisibility(View.VISIBLE);
            iconImageView.setImageDrawable(listItem.getResources().getDrawable(objectDrawerItem.getIcon()));
            nameTextView.setText(objectDrawerItem.getName());

        }
        if (position == 10) {
            listItem.findViewById(R.id.dvider_reltive).setVisibility(View.GONE);

        }

        if (position == 3) {
            switchButton.setVisibility(View.VISIBLE);
            listItem.findViewById(R.id.dvider_reltive).setVisibility(View.VISIBLE);
            nameTextView.setText(objectDrawerItem.getName());

        } else {
            switchButton.setVisibility(View.GONE);
//            nameTextView.setText(objectDrawerItem.getName());
        }
        switchButton.setTextSize(18);
        switchButton.setWidth(40);
        switchButton.setChecked(sharedPreferences.getBoolean("service_on", true));
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (switchButton.isChecked() == b) {
                    editor.putBoolean("service_on", b);
                    editor.commit();
                    mapLoadingFragment.getLocation(LocationManager.GPS_PROVIDER);

                } else {
                    editor.putBoolean("service_on", b);
                    editor.commit();
                    mapLoadingFragment.getLocation("");

                }

                mNavigationDrawerHelper.handleSelect(0);


            }
        });

        return listItem;
    }
}
