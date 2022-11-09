package com.example.cloverchatapp.web.websocket;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.web.domain.chat.StompUpdateChatUser;
import com.google.gson.Gson;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatUserSession extends AbstractStompSession {

    public ChatUserSession(MainActivity activity) {
        super(activity);
    }

    public void subscribeChatUser(Consumer<StompMessage> handle) {
        super.subscribe(handle);
    }

    public void sendChatChatUser(StompUpdateChatUser stompUpdateChatUser) {
        String json = new Gson().toJson(stompUpdateChatUser);

        super.send(json);
    }

    @Override
    protected Consumer<LifecycleEvent> lifecycleHandle() {
        return (LifecycleEvent lifecycleEvent) -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    System.out.println("opened ChatUserSession");
                    break;
                case ERROR:
                    Exception ex = lifecycleEvent.getException();
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    break;
                case CLOSED:
                    System.out.println("closed ChatUserSession");
                    break;
            }
        };
    }

    @Override
    protected String getSubPath() {
        return "/user/sub/user/" + global.chat.curChatRoom.id;
    }

    @Override
    protected String getPubPath() {
        return "/pub/user/" + global.chat.curChatRoom.id;
    }
}
