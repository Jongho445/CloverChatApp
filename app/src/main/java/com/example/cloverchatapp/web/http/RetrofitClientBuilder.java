package com.example.cloverchatapp.web.http;

import com.example.cloverchatapp.global.AuthContext;
import com.example.cloverchatapp.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientBuilder {

    public static <T extends RetrofitClient> T getRetrofitClient(AuthContext authContext, Class<T> clientClass){
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVER_URL)
                .client(provideOkHttpClient(authContext))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(clientClass);
    }

    private static OkHttpClient provideOkHttpClient(AuthContext authContext) {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpInterceptor(authContext))
                .build();
    }
}
