package com.nexogen.routefinder.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.nexogen.routefinder.R;

import static com.nexogen.routefinder.MainActivity.mNavigationDrawerHelper;


/**
 * Created by Andy on 10-Dec-14.
 */
public class DrawerItemCustomAdapter extends BaseAdapter {

    Context mContext;
    int mLayoutResourceId;
    ObjectDrawerItem mData[] = null;

    public DrawerItemCustomAdapter(Context context, int layoutResourceId, ObjectDrawerItem[] data) {
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;
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
        if (position == 9) {
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
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (switchButton.isChecked() == b)
                    Toast.makeText(mContext, "on--" + b, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(mContext, "off--" + b, Toast.LENGTH_SHORT).show();

                mNavigationDrawerHelper.handleSelect(0);


            }
        });

        return listItem;
    }
}
