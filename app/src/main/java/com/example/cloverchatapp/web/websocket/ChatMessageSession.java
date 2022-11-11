package com.example.cloverchatapp.web.websocket;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.google.gson.Gson;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatMessageSession extends AbstractStompSession {

    public final Long chatRoomId;

    public ChatMessageSession(MainActivity activity, Long chatRoomId) {
        super(activity);

        this.chatRoomId = chatRoomId;
    }

    public void subscribeMessage(Consumer<StompMessage> handle) {
        super.subscribe(handle);
        super.isSubscribe = true;
    }

    public void sendChatMessage(ResponseStompChatMessage responseStompChatMessage) {
        String json = new Gson().toJson(responseStompChatMessage);

        super.send(json);
    }

    @Override
    protected Consumer<LifecycleEvent> lifecycleHandle() {
        return (LifecycleEvent lifecycleEvent) -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    System.out.println("opened ChatMessageSession");
                    break;
                case ERROR:
                    Exception ex = lifecycleEvent.getException();
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    break;
                case CLOSED:
                    System.out.println("closed ChatMessageSession");
                    break;
            }
        };
    }

    @Override
    protected String getSubPath() {
        return "/user/sub/message/" + global.chat.curChatRoom.id;
    }

    @Override
    protected String getPubPath() {
        return "/pub/message/" + global.chat.curChatRoom.id;
    }
}
