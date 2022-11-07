package com.example.cloverchatapp.web.http;

import androidx.core.util.Consumer;
import androidx.core.util.Supplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract public class AbstractHttpClient {

    protected <T> void templateCallback(Supplier<Call<T>> requestFx, Consumer<Response<T>> onResponse) {
        Call<T> call = requestFx.get();

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                onResponse.accept(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                System.out.println(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
