package com.example.cloverchatapp.client;

import com.example.cloverchatapp.util.AuthStorage;
import com.example.cloverchatapp.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private final AuthStorage authStorage;

    public RetrofitClient(AuthStorage authStorage) {
        this.authStorage = authStorage;
    }

    public RetrofitService getApiService(){
        return getInstance().create(RetrofitService.class);
    }

    private Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();

        return new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new AppInterceptor(authStorage))
                .build();
    }
}
