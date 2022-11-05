package com.example.cloverchatapp.web.client;

import androidx.annotation.NonNull;

import com.example.cloverchatapp.util.AuthStorage;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {

    private final AuthStorage authStorage;

    public HttpInterceptor(AuthStorage authStorage) {
        this.authStorage = authStorage;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Cookie", "JSESSIONID=" + authStorage.sessionId)
                .build();

        return chain.proceed(request);
    }
}