package com.example.cloverchatapp.web;

public class RequestLoginForm {

    public final String email;
    public final String password;

    public RequestLoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
