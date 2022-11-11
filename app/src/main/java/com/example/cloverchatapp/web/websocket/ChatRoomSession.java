package com.example.cloverchatapp.web.websocket;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.web.domain.board.StompUpdateChatRoom;
import com.google.gson.Gson;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatRoomSession extends AbstractStompSession {

    public ChatRoomSession(MainActivity activity) {
        super(activity);
    }

    public void subscribeChatRoom(Consumer<StompMessage> handle) {
        super.subscribe(handle);
        super.isSubscribe = true;
    }

    public void sendChatRoom(StompUpdateChatRoom stompUpdateChatRoom) {
        String json = new Gson().toJson(stompUpdateChatRoom);

        super.send(json);
    }

    @Override
    protected Consumer<LifecycleEvent> lifecycleHandle() {
        return (LifecycleEvent lifecycleEvent) -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    System.out.println("opened chatRoomSession");
                    break;
                case ERROR:
                    Exception ex = lifecycleEvent.getException();
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    break;
                case CLOSED:
                    System.out.println("closed chatRoomSession");
                    break;
            }
        };
    }

    @Override
    protected String getSubPath() {
        return "/sub/room";
    }

    @Override
    protected String getPubPath() {
        return "/pub/room";
    }
}
