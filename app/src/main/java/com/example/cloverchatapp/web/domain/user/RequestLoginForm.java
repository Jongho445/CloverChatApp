package com.example.cloverchatapp.web.domain.user;

public class RequestLoginForm {

    public final String email;
    public final String password;

    public RequestLoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
