package com.example.cloverchatapp.util;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;

public class TestHelper {

    private final MainActivity activity;
    private final HttpClient httpClient;

    public TestHelper(MainActivity activity) {
        this.activity = activity;
        this.httpClient = new HttpClient(activity.authStorage);
    }

    public void login(Runnable callback) {
        RequestLoginForm requestLoginForm = new RequestLoginForm("user2@gmail.com", "1234");
        httpClient.login(requestLoginForm, res -> {
            if (!res.isSuccessful()) {
                callback.run();
                return;
            }

            activity.authStorage.storeData(res);
            callback.run();
        });
    }
}
