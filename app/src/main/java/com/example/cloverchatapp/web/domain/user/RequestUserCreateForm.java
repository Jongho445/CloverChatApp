package com.example.cloverchatapp.web.domain.user;

public class RequestUserCreateForm {

    public final String email;
    public final String password;
    public final String nickname;

    public RequestUserCreateForm(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
