package com.example.cloverchatapp.web.websocket;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.web.domain.chat.RequestStompChatMessage;
import com.example.cloverchatapp.web.http.chat.ChatHttpClient;
import com.google.gson.Gson;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatMessageSession extends AbstractStompSession {

    private final ChatHttpClient chatHttpClient;

    private final Consumer<StompMessage> subscribeHandle;

    public ChatMessageSession(MainActivity activity, Consumer<StompMessage> subscribeHandle) {
        super(activity);

        this.subscribeHandle = subscribeHandle;
        this.chatHttpClient = new ChatHttpClient(activity.global.auth);
    }

    public void sendChatMessage(String msgContent) {
        RequestStompChatMessage requestStompChatMessage = new RequestStompChatMessage(
                global.chat.curChatRoom.id,
                global.auth.myInfo.id,
                msgContent
        );

        String json = new Gson().toJson(requestStompChatMessage);
        super.send(json);
    }

    @Override
    protected Consumer<StompMessage> subscribeHandle() {
        return subscribeHandle;
    }

    @Override
    protected Consumer<LifecycleEvent> lifecycleHandle() {
        return (LifecycleEvent lifecycleEvent) -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    System.out.println("opened");
                    chatHttpClient.createChatUser(global.chat.curChatRoom.id, res -> {});
                    break;
                case ERROR:
                    Exception ex = lifecycleEvent.getException();
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    chatHttpClient.deleteChatUser(res -> {});
                    break;
                case CLOSED:
                    System.out.println("closed");
                    chatHttpClient.deleteChatUser(res -> {});
                    break;
            }
        };
    }

    @Override
    protected String getEndpoint() {
        return "/stomp/websocket";
    }

    @Override
    protected String getSubPath() {
        return "/sub/" + global.chat.curChatRoom.id;
    }

    @Override
    protected String getPubPath() {
        return "/pub/" + global.chat.curChatRoom.id;
    }
}
