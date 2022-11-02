package com.example.cloverchatapp.web.board;

import com.example.cloverchatapp.web.user.ResponseUser;

public class ResponseChatRoom {

    public final Long id;
    public final ResponseUser createUser;
    public final String title;
    public final String createDate;

    public ResponseChatRoom(Long id, ResponseUser createUser, String title, String createDate) {
        this.id = id;
        this.createUser = createUser;
        this.title = title;
        this.createDate = createDate;
    }
}
