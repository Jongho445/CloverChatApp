package com.example.cloverchatapp.web;

public class ResponseChatRoom {

    private Long id;
    private String createBy;
    private String title;
    private String createDate;

    public ResponseChatRoom(Long id, String createBy, String title, String createDate) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }
    public String getCreateBy() {
        return createBy;
    }
    public String getTitle() {
        return title;
    }
    public String getCreateDate() {
        return createDate;
    }
}
