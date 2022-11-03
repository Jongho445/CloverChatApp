package com.example.cloverchatapp.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.component.ChatMessage;
import com.example.cloverchatapp.component.ChatMessageAdapter;
import com.example.cloverchatapp.web.board.ResponseChatRoom;
import com.example.cloverchatapp.web.chat.RequestChatMessage;
import com.example.cloverchatapp.web.chat.ResponseChatMessage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatRoomDetailFragment extends Fragment {

    MainActivity activity;

    RecyclerView rvList;
    ChatMessageAdapter adapter;
    List<ChatMessage> chatMessageList;

    ResponseChatRoom chatRoom;

    StompClient stompClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);
        init(rootView);

        setSendBtnListener(rootView);

        initStomp();

        System.out.println(chatRoom.id);
        System.out.println(chatRoom.title);

        return rootView;
    }

    private void initStomp() {
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "http://10.0.2.2:11730/sub/websocket");

        stompClient.lifecycle().subscribe(lifecycleHandle());
        stompClient.connect();

        stompClient.topic("/topic/" + chatRoom.id).subscribe(subscribeHandle());
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

    private Consumer<StompMessage> subscribeHandle() {
        return (StompMessage topicMessage) -> {
            Gson gson = new Gson();
            ResponseChatMessage msg = gson.fromJson(topicMessage.getPayload(), ResponseChatMessage.class);

            chatMessageList.add(new ChatMessage("user2", "haha", "123"));
            activity.runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
            });
        };
    }

    private void setSendBtnListener(View rootView) {
        EditText editText = rootView.findViewById(R.id.et_chatting);
        Button sendBtn = rootView.findViewById(R.id.btn_send);

        sendBtn.setOnClickListener(view -> {
            Gson gson = new Gson();
            RequestChatMessage requestChatMessage = new RequestChatMessage(1L, 1L, "aaaa");
            String json = gson.toJson(requestChatMessage);
            stompClient.send("/pub/" + chatRoom.id, json).subscribe();
        });
    }

    private void init(View rootView) {
        rvList = rootView.findViewById(R.id.rv_list);
        chatMessageList = new ArrayList<>();
        adapter = new ChatMessageAdapter(chatRoom.title, chatMessageList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(adapter);
    }

    public void setChatRoom(ResponseChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
