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

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatRoomDetailFragment extends Fragment {

    MainActivity activity;

    RecyclerView rvList;
    ChatMessageAdapter adapter;
    List<ChatMessage> chatMessageList;

    ResponseChatRoom chatRoom;
    WebSocket session;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);
        init(rootView);

        setSendBtnListener(rootView);

        initWebSocket();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println("destroy");
        session.cancel();
        session.close(1000, "session closed");
    }

    private void initWebSocket() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("ws://10.0.2.2:11730/ws/chat")
                .build();

        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                System.out.println(text);
                chatMessageList.add(new ChatMessage("user2", "haha", "123"));
                adapter.notifyDataSetChanged();
            }
        };

        session = okHttpClient.newWebSocket(request, listener);
        okHttpClient.dispatcher().executorService().shutdown();
    }

    private void setSendBtnListener(View rootView) {
        EditText editText = rootView.findViewById(R.id.et_chatting);
        Button sendBtn = rootView.findViewById(R.id.btn_send);
        sendBtn.setOnClickListener(view -> {
            chatMessageList.add(new ChatMessage("user2", editText.getText().toString(), "123"));
            adapter.notifyDataSetChanged();

            session.send("hahaha");
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
