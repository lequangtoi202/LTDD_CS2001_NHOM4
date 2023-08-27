package com.example.androidapp.api;

import com.example.androidapp.constants.Constants;
import com.example.androidapp.model.Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiAccountService {
    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy")
            .create();
    ApiAccountService apiAccountService = new Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiAccountService.class);

    @GET("api/v1/accounts/me")
    Call<Account> getMyAccount(@Header("Authorization") String authorization);
}
