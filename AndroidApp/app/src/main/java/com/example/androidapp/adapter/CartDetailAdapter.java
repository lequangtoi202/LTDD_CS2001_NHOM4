package com.example.androidapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.androidapp.R;
import com.example.androidapp.model.CartDetail;

import java.util.ArrayList;

public class CartDetailAdapter extends ArrayAdapter<CartDetail> {
    Activity context = null;
    ArrayList<CartDetail> myArray = null;
    int layoutId;

    /**
     *
     * @param context
     * @param layoutId
     * @param arr
     */
    public CartDetailAdapter(Activity context, int layoutId, ArrayList<CartDetail> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.myArray = arr;
        this.layoutId = layoutId;
    }


    /**
     *
     * @param position The position of the item within the adapter's data set of the item whose view
     *        we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *        is non-null and of an appropriate type before using. If it is not possible to convert
     *        this view to display the correct data, this method can create a new view.
     *        Heterogeneous lists can specify their number of view types, so that this View is
     *        always of the right type (see {@link #getViewTypeCount()} and
     *        {@link #getItemViewType(int)}).
     * @param parent The parent that this view will eventually be attached to
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        final TextView txtdisplay = convertView.findViewById(R.id.lblItemInfo);
        final CartDetail p = myArray.get(position);
        txtdisplay.setText(p.toString());

        return convertView;
    }
}
