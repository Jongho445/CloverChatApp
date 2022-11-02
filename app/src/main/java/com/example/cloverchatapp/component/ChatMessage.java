package com.example.cloverchatapp.component;

public class ChatMessage {

    private String nickname;
    private String contents;
    private String time;

    public ChatMessage(String nickname, String contents, String time) {
        this.nickname = nickname;
        this.contents = contents;
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContents() {
        return contents;
    }

    public String getTime() {
        return time;
    }
}
