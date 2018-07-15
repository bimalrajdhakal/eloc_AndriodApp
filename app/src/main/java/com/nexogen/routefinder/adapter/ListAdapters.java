package com.nexogen.routefinder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.nexogen.routefinder.R;

import java.util.List;


public class ListAdapters extends BaseAdapter {

    Activity context;
    LayoutInflater inflator;
    int currentSelection = -1;
    private List<String> list;
    private RelativeLayout radiobtn;

    public ListAdapters(Activity context, List<String> tv_title) {
        // TODO Auto-generated constructor stub
        this.list = tv_title;
        this.context = context;
        inflator = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View v = (View) inflator.inflate(R.layout.item_list_activity, null);
        TextView view = (TextView) v.findViewById(R.id.tv_title);
        radiobtn = (RelativeLayout) v.findViewById(R.id.radiobtn);
        view.setText(list.get(position));
        SharedPreferences sharedPreferences = context.getSharedPreferences("common", Context.MODE_PRIVATE);

        if (sharedPreferences.getInt("position",0) == position) {
            radiobtn.setBackgroundResource(R.drawable.radiobtn_click);

        }


        return v;
    }

    public void setCurrentSelection(int index) {
        currentSelection = index;
    }


}
