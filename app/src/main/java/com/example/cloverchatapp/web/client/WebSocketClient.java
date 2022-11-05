package com.example.cloverchatapp.web.client;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.component.recyclerview.chatmessage.ChatMessageList;
import com.example.cloverchatapp.util.Constants;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.chat.RequestChatMessage;
import com.example.cloverchatapp.web.domain.chat.ResponseChatMessage;
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
    private final ResponseChatRoom chatRoom;

    public StompClient stompClient;

    private final String wsRequestUrl;
    private final String subPath;
    private final String sendPath;
    private final String jSessionValue;

    public WebSocketClient(MainActivity activity, ChatMessageList chatMessageList, ResponseChatRoom chatRoom) {
        this.activity = activity;
        this.chatMessageList = chatMessageList;
        this.chatRoom = chatRoom;

        this.wsRequestUrl = Constants.SERVER_URL + "/stomp/websocket";
        this.subPath = "/sub/" + chatRoom.id;
        this.sendPath = "/pub/" + chatRoom.id;
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
        RequestChatMessage requestChatMessage = new RequestChatMessage(
                chatRoom.id,
                activity.authStorage.me.id,
                content
        );

        String json = new Gson().toJson(requestChatMessage);
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
                    System.out.println("Stomp connection opened");
                    break;
                case ERROR:
                    Exception ex = lifecycleEvent.getException();
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    break;
                case CLOSED:
                    System.out.println("closed");
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
            ResponseChatMessage chatMsg = new Gson().fromJson(topicMessage.getPayload(), ResponseChatMessage.class);

            chatMessageList.addItem(chatMsg);
        };
    }
}
