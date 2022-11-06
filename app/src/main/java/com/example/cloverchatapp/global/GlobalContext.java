package com.example.cloverchatapp.global;

import android.view.Menu;

import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.web.client.WebSocketClient;

public class GlobalContext {

    public FragmentEnum mainFragment;

    public final AuthContext auth;
    public final ChatContext chat;

    public Menu menu;

    public final HttpClient http;
    public WebSocketClient ws;

    public GlobalContext() {
        this.auth = new AuthContext();
        this.chat = new ChatContext();
        this.http = new HttpClient(auth);
    }
}
