package com.example.cloverchatapp.web.websocket;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.util.Constants;

import java.util.HashMap;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

abstract public class AbstractStompSession {

    protected final GlobalContext global;

    private final StompClient stompClient;

    private final String wsRequestUrl;
    private final String subPath;
    private final String sendPath;
    private final String jSessionValue;

    public AbstractStompSession(MainActivity activity) {
        this.global = activity.global;

        this.wsRequestUrl = Constants.SERVER_URL + "/stomp/websocket";
        this.subPath = "/sub/" + global.chat.curChatRoom.id;
        this.sendPath = "/pub/" + global.chat.curChatRoom.id;
        this.jSessionValue = "JSESSIONID=" + global.auth.sessionId;

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", jSessionValue);

        this.stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsRequestUrl, headers);
        setLifecycleListener();
    }

    private void setLifecycleListener() {
        stompClient.lifecycle().subscribe(lifecycleHandle());
    }

    abstract protected Consumer<LifecycleEvent> lifecycleHandle();

    public void connect() {
        stompClient.connect();
    }

    public void disconnect() {
        stompClient.disconnect();
    }

    protected void send(String msg) {
        stompClient.send(sendPath, msg).subscribe();
    }

    protected void subscribeTopic(Consumer<StompMessage> handle) {
        stompClient.topic(subPath).subscribe(handle);
    }
}
