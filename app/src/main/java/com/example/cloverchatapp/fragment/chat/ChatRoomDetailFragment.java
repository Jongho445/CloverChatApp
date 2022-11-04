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
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.component.ChatMessageAdapter;
import com.example.cloverchatapp.util.Constants;
import com.example.cloverchatapp.web.board.ResponseChatRoom;
import com.example.cloverchatapp.web.chat.RequestChatMessage;
import com.example.cloverchatapp.web.chat.ResponseChatMessage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatRoomDetailFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;

    RecyclerView rvList;
    ChatMessageAdapter adapter;
    List<ResponseChatMessage> itemList;

    ResponseChatRoom chatRoom;

    AppClient httpClient;
    StompClient stompClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);
        httpClient = new AppClient(activity.authStorage);

        httpClient.getChatMessagesByChatRoomId(chatRoom.id, res -> {
            setRecyclerView(res.body());
            setSendBtnListener();

            connectStomp();
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        itemList.clear();
        stompClient.disconnect();
    }

    private void setRecyclerView(List<ResponseChatMessage> chatMessages) {
        rvList = rootView.findViewById(R.id.rv_list);

        itemList = new ArrayList<>();
        itemList.addAll(chatMessages);

        adapter = new ChatMessageAdapter(itemList, activity);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(adapter);

        rvList.scrollToPosition(adapter.getItemCount() - 1);
    }

    private void connectStomp() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "JSESSIONID=" + activity.authStorage.sessionId);

        stompClient = Stomp.over(
                Stomp.ConnectionProvider.OKHTTP,
                Constants.SERVER_URL + "/stomp/websocket",
                headers
        );

        stompClient.lifecycle().subscribe(lifecycleHandle());
        stompClient.connect();

        stompClient.topic("/sub/" + chatRoom.id).subscribe(subscribeHandle());
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
            ResponseChatMessage chatMsg = gson.fromJson(topicMessage.getPayload(), ResponseChatMessage.class);

            itemList.add(chatMsg);
            activity.runOnUiThread(() -> {
                rvList.scrollToPosition(adapter.getItemCount() - 1);
            });
        };
    }

    private void setSendBtnListener() {
        EditText editText = rootView.findViewById(R.id.et_chatting);
        Button sendBtn = rootView.findViewById(R.id.btn_send);

        sendBtn.setOnClickListener(view -> {
            Gson gson = new Gson();
            RequestChatMessage requestChatMessage = new RequestChatMessage(
                    chatRoom.id,
                    activity.authStorage.me.id,
                    editText.getText().toString()
            );

            String json = gson.toJson(requestChatMessage);
            stompClient.send("/pub/" + chatRoom.id, json).subscribe();
        });
    }

    public void setChatRoom(ResponseChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
