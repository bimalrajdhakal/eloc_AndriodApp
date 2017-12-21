package com.nexogen.routefinder.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexogen.routefinder.R;
import com.nexogen.routefinder.utils.Global;


public class RecyclerAdapter extends BaseAdapter {

    String[] placeTypeNames, placesNames;
    private Context mContext;

    public RecyclerAdapter(FragmentActivity activity, String[] placeTypeName, String[] placesName) {
        this.mContext = activity;
        this.placeTypeNames = placeTypeName;
        this.placesNames = placesName;


    }


    @Override
    public int getCount() {
        return placesNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int posistion, View view, ViewGroup viewGroup) {

        ViewHolde viewHolde = null;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.list_row_tems, null);
            viewHolde = new ViewHolde();

            viewHolde.tvPlaceName = (TextView) view.findViewById(R.
                    id.tv_bankName);

            viewHolde.imageView = (ImageView) view.findViewById(R.id.image_bank);

            view.setTag(viewHolde);
        } else {
            viewHolde = (ViewHolde) view.getTag();

        }

        /////GLide library for image downloading

        viewHolde.tvPlaceName.setText(placeTypeNames[posistion]);

        viewHolde.imageView.setBackgroundResource(Global.placesImages[posistion]);

        return view;
    }


    public class ViewHolde {

        public TextView tvPlaceName;
        public ImageView imageView;

    }

}