package com.example.cloverchatapp.web;

public class ResponseUser {

    public final Long id;
    public final String email;
    public final String nickname;

    public ResponseUser(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}
