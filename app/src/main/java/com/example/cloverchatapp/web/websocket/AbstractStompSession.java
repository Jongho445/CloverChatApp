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

    private final StompClient stompClient;

    protected final GlobalContext global;

    private final String subPath;
    private final String sendPath;

    public AbstractStompSession(MainActivity activity) {
        this.global = activity.global;

        this.subPath = getSubPath();
        this.sendPath = getPubPath();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "JSESSIONID=" + global.auth.sessionId);

        String wsRequestUrl = Constants.SERVER_URL + getEndpoint();
        this.stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsRequestUrl, headers);

        setLifecycleListener();
    }

    public void connect() {
        stompClient.connect();
    }

    public void disconnect() {
        stompClient.disconnect();
    }

    private void setLifecycleListener() {
        stompClient.lifecycle().subscribe(lifecycleHandle());
    }

    abstract protected Consumer<LifecycleEvent> lifecycleHandle();

    protected void send(String message) {
        stompClient.send(sendPath, message).subscribe();
    }

    public void subscribe() {
        stompClient.topic(subPath).subscribe(subscribeHandle());
    }

    abstract protected Consumer<StompMessage> subscribeHandle();

    abstract protected String getEndpoint();
    abstract protected String getSubPath();
    abstract protected String getPubPath();
}
