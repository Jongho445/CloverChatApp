package com.example.cloverchatapp.util;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.web.user.RequestLoginForm;
import com.example.cloverchatapp.web.user.ResponseUser;

public class TestHelper {

    private final MainActivity activity;
    private final AppClient httpClient;

    public TestHelper(MainActivity activity) {
        this.activity = activity;
        this.httpClient = new AppClient(activity.authStorage);
    }

    public void login(AuthStorage authStorage, Runnable callback) {
        httpClient.login(
                new RequestLoginForm("user1@gmail.com", "1234"),
                res -> {
                    ResponseUser responseUser = res.body();
                    String jSessionId = authStorage.getJSessionId(res.headers());

                    authStorage.sessionId = jSessionId;
                    authStorage.me = responseUser;

                    callback.run();
                },
                e -> {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
        );
    }
}
