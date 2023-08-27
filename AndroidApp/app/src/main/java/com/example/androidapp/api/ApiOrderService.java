package com.example.androidapp.api;

import com.example.androidapp.constants.Constants;
import com.example.androidapp.model.Flower;
import com.example.androidapp.model.Order;
import com.example.androidapp.model.OrderDetail;
import com.example.androidapp.model.OrderObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiOrderService {

    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy")
            .create();

    ApiOrderService apiOrderService = new Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiOrderService.class);

    @GET("api/v1/orders/me")
    Call<ArrayList<Order>> getAllMyOrders(@Header("Authorization") String authorization);

    @POST("api/v1/orders")
    Call<Order> saveOrder(@Header("Authorization") String authorization);

    @GET("api/v1/orders/{id}/order-details")
    Call<ArrayList<OrderDetail>> getOrderDetail(@Header("Authorization") String authorization, @Query("id") long id);
}
