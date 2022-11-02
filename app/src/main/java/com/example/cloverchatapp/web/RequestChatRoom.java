package com.example.cloverchatapp.web;

public class RequestChatRoom {

    private String createBy;
    private String password;
    private String title;

    public RequestChatRoom(String createBy, String password, String title) {
        this.createBy = createBy;
        this.password = password;
        this.title = title;
    }
}
