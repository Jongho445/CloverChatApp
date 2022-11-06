package com.example.cloverchatapp.web.client;

import com.example.cloverchatapp.global.AuthContext;
import com.example.cloverchatapp.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private final AuthContext authContext;

    public RetrofitClient(AuthContext authContext) {
        this.authContext = authContext;
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
                .addInterceptor(new HttpInterceptor(authContext))
                .build();
    }
}
