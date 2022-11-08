package com.example.cloverchatapp.global;

import android.view.Menu;

import com.example.cloverchatapp.page.FragmentEnum;

public class GlobalContext {

    public final AuthContext auth = new AuthContext();
    public final ChatContext chat = new ChatContext();

    public final WebSocketSessionContext ws = new WebSocketSessionContext();

    public FragmentEnum mainFragment;
    public Menu menu;
}
