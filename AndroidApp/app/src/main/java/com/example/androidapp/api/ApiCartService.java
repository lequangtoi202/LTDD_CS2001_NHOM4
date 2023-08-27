package com.example.androidapp.api;


import com.example.androidapp.constants.Constants;
import com.example.androidapp.model.Cart;
import com.example.androidapp.model.CartDetail;
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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiCartService {
    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy")
            .create();
    ApiCartService apiCartService = new Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiCartService.class);

    @GET("api/v1/cart/me")
    Call<Cart> getCart(@Header("Authorization") String authorization);
    @GET("api/v1/cart/details")
    Call<ArrayList<CartDetail>> getListProducts(@Header("Authorization") String authorization);

    @PUT("api/v1/cart/update-item-cart")
    Call<Cart> updateItemToCart(@Header("Authorization") String authorization, @Query("id") long id, @Query("quantity") long quantity);

    @DELETE("api/v1/cart/delete-item-cart")
    Call<Cart> deleteItemToCart(@Header("Authorization") String authorization, @Query("id") long id);
}
