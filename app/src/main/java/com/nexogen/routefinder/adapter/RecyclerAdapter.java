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
import com.nexogen.routefinder.model.placesNamesModel;
import com.nexogen.routefinder.utils.Global;

import java.util.AbstractCollection;
import java.util.List;


public class RecyclerAdapter extends BaseAdapter {

    private Context mContext;
    List<placesNamesModel> placesNames;
    public RecyclerAdapter(FragmentActivity activity , List<placesNamesModel> placesName) {
        this.mContext = activity;
        this.placesNames = placesName;


    }


    @Override
    public int getCount() {
        return placesNames.size();
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

        viewHolde.tvPlaceName.setText(placesNames.get(posistion).getPlaceName().toUpperCase().replace("_"," "));

        viewHolde.imageView.setBackgroundResource(Global.placesImages[placesNames.get(posistion).getId()]);

        return view;
    }


    public class ViewHolde {

        public TextView tvPlaceName;
        public ImageView imageView;

    }

}