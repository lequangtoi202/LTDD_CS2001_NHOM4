package com.example.androidapp.api;

import com.example.androidapp.constants.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiResetPassWord {
    ApiResetPassWord apiService = new Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiResetPassWord.class);

    @FormUrlEncoded
    @POST("api/v1/accounts/reset-password")
    Call<String> resetPass(
            @Field("token") String token,
            @Field("password") String newPass
    );

    @PUT("api/v1/accounts/change-password/{id}")
    Call<String> changePass(@Query("id") long id, @Field("password") String newPass);
}
