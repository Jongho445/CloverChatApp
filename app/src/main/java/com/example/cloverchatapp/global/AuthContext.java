package com.example.cloverchatapp.global;

import com.example.cloverchatapp.web.domain.user.ResponseUser;

import okhttp3.Headers;
import retrofit2.Response;

public class AuthContext {

    public String sessionId;
    public ResponseUser me;

    public AuthContext() {}

    public void storeData(Response<ResponseUser> response) {
        ResponseUser responseUser = response.body();
        String jSessionId = getJSessionId(response.headers());

        sessionId = jSessionId;
        me = responseUser;
    }

    private String getJSessionId(Headers headers) {
        String cookieStr = headers.get("Set-Cookie");

        if (cookieStr == null) {
            throw new RuntimeException("cookie is null");
        }

        String[] cookieEntries = cookieStr.split(";");

        String jSessionId = "";
        for (String entry : cookieEntries) {
            String rawStr = entry.trim();

            if (rawStr.startsWith("JSESSIONID")) {
                jSessionId = rawStr.split("=")[1];
            }
        }

        return jSessionId;
    }
}
