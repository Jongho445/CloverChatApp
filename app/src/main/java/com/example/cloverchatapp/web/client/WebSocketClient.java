package com.example.cloverchatapp.web.client;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.fragment.chatroom.detail.component.ChatMessageList;
import com.example.cloverchatapp.util.Constants;
import com.example.cloverchatapp.web.domain.chat.RequestStompChatMessage;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.google.gson.Gson;

import java.util.HashMap;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class WebSocketClient {

    private final MainActivity activity;
    private final ChatMessageList chatMessageList;

    public StompClient stompClient;

    private final String wsRequestUrl;
    public final String subPath;
    private final String sendPath;
    private final String jSessionValue;

    public WebSocketClient(MainActivity activity, ChatMessageList chatMessageList) {
        this.activity = activity;
        this.chatMessageList = chatMessageList;

        this.wsRequestUrl = Constants.SERVER_URL + "/stomp/websocket";
        this.subPath = "/sub/" + activity.curChatRoom.id;
        this.sendPath = "/pub/" + activity.curChatRoom.id;
        this.jSessionValue = "JSESSIONID=" + activity.authStorage.sessionId;
    }

    public void connect() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", jSessionValue);

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsRequestUrl, headers);

        setLifecycleListener();
        stompClient.connect();

        subscribeChatRoom();
    }

    public void disconnect() {
        stompClient.disconnect();
    }

    public void send(String content) {
        RequestStompChatMessage requestStompChatMessage = new RequestStompChatMessage(
                activity.curChatRoom.id,
                activity.authStorage.me.id,
                content
        );

        String json = new Gson().toJson(requestStompChatMessage);
        stompClient.send(sendPath, json).subscribe();
    }

    private void setLifecycleListener() {
        stompClient.lifecycle()
                .subscribe(lifecycleHandle());
    }

    private Consumer<LifecycleEvent> lifecycleHandle() {
        return (LifecycleEvent lifecycleEvent) -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    System.out.println("opened");
                    activity.httpClient.createChatUser(activity.curChatRoom.id, res -> {});
                    break;
                case ERROR:
                    Exception ex = lifecycleEvent.getException();
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    activity.httpClient.deleteChatUser(res -> {});
                    break;
                case CLOSED:
                    System.out.println("closed");
                    activity.httpClient.deleteChatUser(res -> {});
                    break;
            }
        };
    }

    private void subscribeChatRoom() {
        stompClient.topic(subPath)
                .subscribe(subscribeHandle());
    }

    private Consumer<StompMessage> subscribeHandle() {
        return (StompMessage topicMessage) -> {
            ResponseStompChatMessage chatMsg = new Gson().fromJson(topicMessage.getPayload(), ResponseStompChatMessage.class);

            chatMessageList.addItem(chatMsg);
        };
    }
}
