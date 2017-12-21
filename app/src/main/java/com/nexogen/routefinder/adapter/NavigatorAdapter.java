package com.nexogen.routefinder.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nexogen.routefinder.MultipleSelection.NavigatorModel;
import com.nexogen.routefinder.R;

import java.util.List;


public class NavigatorAdapter extends ArrayAdapter<NavigatorModel> {

    public static List<NavigatorModel> navList;
    private final Activity context;


    public NavigatorAdapter(Activity context, List<NavigatorModel> list) {
        super(context, R.layout.navigator_item, list);
        this.context = context;
        this.navList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderNav viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.navigator_item, null);
            viewHolder = new ViewHolderNav();
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            viewHolder.tv_destination = (TextView) convertView.findViewById(R.id.tv_destination);

            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.check);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    navList.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.tv_date, viewHolder.tv_date);
            convertView.setTag(R.id.tv_source, viewHolder.tv_source);
            convertView.setTag(R.id.tv_destination, viewHolder.tv_destination);
            convertView.setTag(R.id.check, viewHolder.checkbox);
        } else {
            viewHolder = (ViewHolderNav) convertView.getTag();
        }
        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.tv_date.setText(navList.get(position).getDateTime());
        viewHolder.tv_source.setText(navList.get(position).getSource());
        viewHolder.tv_destination.setText(navList.get(position).getDestination());

        viewHolder.checkbox.setChecked(navList.get(position).isSelected());

        return convertView;
    }

    static class ViewHolderNav {
        protected TextView tv_date, tv_source, tv_destination;
        protected CheckBox checkbox;
    }
}
