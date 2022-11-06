package com.example.cloverchatapp.util;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;

public class TestHelper {

    private final GlobalContext global;

    public TestHelper(MainActivity activity) {
        this.global = activity.global;
    }

    public void login(Runnable callback) {
        RequestLoginForm requestLoginForm = new RequestLoginForm("user1@gmail.com", "1234");
        global.http.login(requestLoginForm, res -> {
            if (!res.isSuccessful()) {
                callback.run();
                return;
            }

            global.auth.storeData(res);
            callback.run();
        });
    }
}
