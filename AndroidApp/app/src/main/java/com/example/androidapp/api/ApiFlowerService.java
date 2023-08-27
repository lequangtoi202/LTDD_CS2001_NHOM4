package com.example.androidapp.api;


import com.example.androidapp.constants.Constants;
import com.example.androidapp.model.Cart;
import com.example.androidapp.model.Category;
import com.example.androidapp.model.Flower;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiFlowerService {

    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy")
            .create();

    ApiFlowerService apiFlowerService = new Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiFlowerService.class);

    @GET("api/v1/flowers")
    Call<List<Flower>> getListFlowers();

    @GET("api/v1/flowers/{id}")
    Call<Flower> getFlowerById(@Path("id") long id);

    @GET("api/v1/categories")
    Call<List<Category>> getListCategories();

    @GET("api/v1/flowers/categories/{id}")
    Call<List<Flower>> getListFlowersByCategory(@Path("id") int idCate);

    @GET("api/v1/flowers/bestSeller")
    Call<List<Flower>> getListBestSellerFLowers();

    @GET("api/v1/flowers/{flowerId}/sold")
    Call<Integer> getAmountSoldByFLowerId(@Path("flowerId") int flowerId);

    @POST("api/v1/cart")
    Call<Cart> addItemToCart(@Header("Authorization") String authorization, @Query("id") long id, @Query("quantity") int quantity );

    @GET("api/v1/flowers/{id}/previews/total-previews")
    Call<Flower> getPreview(@Path("id") long id);

    @POST("api/v1/flowers/{id}/previews")
    Call<Flower> postPreview(@Header("Authorization") String authorization, @Path("id") long id,  @Query("score") double score);

}
