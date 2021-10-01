package com.hipradeep.recyclerpagination.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "http://www.omdbapi.com/";

    private static final Gson gson = new GsonBuilder().setLenient().create();

//    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//    private static Retrofit.Builder builderFile = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson));
//    private static final Retrofit retrofit = builderFile.build();


    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }



    private static <S> S createService(Class<S> serviceClass, String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(serviceClass);
    }



    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).build();
    }



}