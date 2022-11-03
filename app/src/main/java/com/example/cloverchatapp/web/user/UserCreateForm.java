package com.example.cloverchatapp.web.user;

public class UserCreateForm {

    public final String email;
    public final String password;
    public final String nickname;

    public UserCreateForm(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
