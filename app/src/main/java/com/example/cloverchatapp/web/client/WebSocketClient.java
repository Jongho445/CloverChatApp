package com.example.cloverchatapp.web.client;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.util.Constants;
import com.example.cloverchatapp.web.domain.chat.RequestStompChatMessage;
import com.google.gson.Gson;

import java.util.HashMap;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class WebSocketClient {

    private final GlobalContext global;

    public StompClient stompClient;

    private final String wsRequestUrl;
    public final String subPath;
    private final String sendPath;
    private final String jSessionValue;

    public WebSocketClient(MainActivity activity) {
        this.global = activity.global;

        this.wsRequestUrl = Constants.SERVER_URL + "/stomp/websocket";
        this.subPath = "/sub/" + global.chat.curChatRoom.id;
        this.sendPath = "/pub/" + global.chat.curChatRoom.id;
        this.jSessionValue = "JSESSIONID=" + global.auth.sessionId;
    }

    public void connect() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", jSessionValue);

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsRequestUrl, headers);

        setLifecycleListener();
        stompClient.connect();
    }

    public void disconnect() {
        stompClient.disconnect();
    }

    public void subscribeChatRoom(Consumer<StompMessage> handle) {
        stompClient.topic(subPath).subscribe(handle);
    }

    public void send(String content) {
        RequestStompChatMessage requestStompChatMessage = new RequestStompChatMessage(
                global.chat.curChatRoom.id,
                global.auth.me.id,
                content
        );

        String json = new Gson().toJson(requestStompChatMessage);
        stompClient.send(sendPath, json).subscribe();
    }

    private void setLifecycleListener() {
        stompClient.lifecycle().subscribe(lifecycleHandle());
    }

    private Consumer<LifecycleEvent> lifecycleHandle() {
        return (LifecycleEvent lifecycleEvent) -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    System.out.println("opened");
                    global.http.createChatUser(global.chat.curChatRoom.id, res -> {});
                    break;
                case ERROR:
                    Exception ex = lifecycleEvent.getException();
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    global.http.deleteChatUser(res -> {});
                    break;
                case CLOSED:
                    System.out.println("closed");
                    global.http.deleteChatUser(res -> {});
                    break;
            }
        };
    }
}
