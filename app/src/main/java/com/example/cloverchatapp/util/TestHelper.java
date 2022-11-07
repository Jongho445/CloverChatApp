package com.example.cloverchatapp.util;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;
import com.example.cloverchatapp.web.http.user.UserHttpClient;

public class TestHelper {

    private final GlobalContext global;
    private final UserHttpClient userHttpClient;

    public TestHelper(MainActivity activity) {
        this.global = activity.global;
        this.userHttpClient = new UserHttpClient(global.auth);
    }

    public void login(Runnable callback) {
        RequestLoginForm requestLoginForm = new RequestLoginForm("user1@gmail.com", "1234");
        userHttpClient.login(requestLoginForm, res -> {
            if (!res.isSuccessful()) {
                callback.run();
                return;
            }

            global.auth.storeData(res);
            callback.run();
        });
    }
}
