package com.example.cloverchatapp.util;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;

public class TestHelper {

    private final MainActivity activity;

    public TestHelper(MainActivity activity) {
        this.activity = activity;
    }

    public void login(Runnable callback) {
        RequestLoginForm requestLoginForm = new RequestLoginForm("user1@gmail.com", "1234");
        activity.httpClient.login(requestLoginForm, res -> {
            if (!res.isSuccessful()) {
                callback.run();
                return;
            }

            activity.authStorage.storeData(res);
            callback.run();
        });
    }
}
