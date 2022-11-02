package com.example.cloverchatapp.util;

import com.example.cloverchatapp.web.user.ResponseUser;

import okhttp3.Headers;

public class AuthStorage {

    public String sessionKey;
    public ResponseUser me;

    public AuthStorage() {}

    public String getJSessionId(Headers headers) {
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
