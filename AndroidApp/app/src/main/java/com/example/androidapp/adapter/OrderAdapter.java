package com.example.androidapp.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.example.androidapp.R;
import com.example.androidapp.model.Order;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {
    Activity context = null;
    ArrayList<Order> data = null;
    int layoutId;
    Fragment fragment;
    LayoutInflater layoutInflater;

    /**
     *
     * @param context
     * @param layoutId
     * @param arr
     */
    public OrderAdapter(Activity context, int layoutId, ArrayList<Order> arr, Fragment fragment) {
        super(context, layoutId, arr);
        this.context = context;
        this.data = arr;
        this.layoutId = layoutId;
        this.layoutInflater = context.getLayoutInflater();
        this.fragment = fragment;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutId, parent, false);
        }

        if (data.size() > 0 && position >= 0 && position < data.size()) {
            final TextView txtdisplay = (TextView) convertView.findViewById(R.id.lblInfoOrder);
            final Order ord = data.get(position);
            final Button btnShowDetailItem = convertView.findViewById(R.id.btnGoToOrderDetail);
            final Button btnCancelOrder = convertView.findViewById(R.id.btnCancelOrder);

            txtdisplay.setText(ord.toString());
            btnCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
                    builder.setTitle("Thông báo!");

                    String msg = "Bạn có chắc muốn hủy đơn đặt hàng này?";
                    builder.setMessage(msg);

                    builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton("Hủy đơn đăt hàng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            data.remove(position);
                            notifyDataSetChanged();
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                }
            });
//            btnShowDetailItem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Tạo Intent để chuyển sang Activity 2
//                    Intent intent = new Intent(getContext(), CartDetailActivity.class);
//
//                    // Tạo Bundle để chứa dữ liệu
////                    Bundle bundle = new Bundle();
//
//                    // Đưa dữ liệu của Order vào Bundle
////                    bundle.putParcelable("selectedOrder", ord);
////                    bundle.putParcelableArrayList("productList", ord.getProductList());
//
//                    // Đặt Bundle vào Intent
////                    intent.putExtras(bundle);
//
//                    // Chuyển sang Activity 2
//                    fragment.startActivity(intent);
//                }
//            });

        }
        return convertView;
    }
}
