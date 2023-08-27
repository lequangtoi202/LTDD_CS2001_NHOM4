package com.example.androidapp.api;

import com.example.androidapp.constants.Constants;
import com.example.androidapp.model.SignUp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiSignUp {
    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy")
            .create();

    ApiSignUp apiService = new Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiSignUp.class);

    @POST("api/v1/auth/register")
    Call<String>  SignUp(@Body SignUp signUp);
}
