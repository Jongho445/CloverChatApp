package com.example.cloverchatapp.web.client;

import androidx.annotation.NonNull;

import com.example.cloverchatapp.global.AuthContext;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpInterceptor implements Interceptor {

    private final AuthContext authContext;

    public HttpInterceptor(AuthContext authContext) {
        this.authContext = authContext;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Cookie", "JSESSIONID=" + authContext.sessionId)
                .build();

        return chain.proceed(request);
    }
}
