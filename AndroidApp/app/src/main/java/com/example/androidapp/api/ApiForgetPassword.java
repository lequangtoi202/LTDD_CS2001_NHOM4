package com.example.androidapp.api;

import com.example.androidapp.constants.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiForgetPassword {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .client(new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Thời gian chờ kết nối
                    .readTimeout(30, TimeUnit.SECONDS)    // Thời gian chờ đọc dữ liệu
                    .writeTimeout(30, TimeUnit.SECONDS)   // Thời gian chờ ghi dữ liệu
                    .build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    ApiForgetPassword apiService = retrofit.create(ApiForgetPassword.class);


    @POST("api/v1/accounts/forgot-password")
    Call<String> forgotPass(@Query("email") String email);

    @GET("api/v1/accounts/reset-password")
    Call<String> resetPass(@Query("token") String token);


}
