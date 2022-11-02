package com.example.cloverchatapp.util;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketHandler extends WebSocketListener {

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println(bytes);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("connected");
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        System.out.println("closing");
        webSocket.close(1000, "close");
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        System.out.println("closed");
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println(t.getMessage());
        t.printStackTrace();
    }
}
