package com.example.androidapp.activity;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.androidapp.R;
import com.example.androidapp.adapter.OrderAdapter;
import com.example.androidapp.api.ApiCartService;
import com.example.androidapp.api.ApiOrderService;
import com.example.androidapp.model.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOrdersList extends Fragment {
    String token;
    ListView mlvOrder;
    ArrayList<Order> list = new ArrayList<Order>();
    OrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);



        CallApiGetOrder();
        adapter = new OrderAdapter(getActivity(), R.layout.orders_item, list, this);
        mlvOrder.setAdapter(adapter);

        return view;
    }

    // Phương thức xử lý gọi API
    private void CallApiGetOrder() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        ApiOrderService.apiOrderService.getAllMyOrders("Bearer " + token)
                .enqueue(new Callback<ArrayList<Order>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {

                        Toast.makeText(requireContext(), "Call API 1 ERROR", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                        Toast.makeText(requireContext(), "Call API 2 ERROR", Toast.LENGTH_LONG).show();
                    }
                });
    }
}