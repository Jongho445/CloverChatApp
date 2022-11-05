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

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.web.client.WebSocketClient;
import com.example.cloverchatapp.component.recyclerview.chatmessage.ChatMessageList;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

public class ChatRoomDetailFragment extends Fragment {

    MainActivity activity;
    ViewGroup rootView;

    ChatMessageList chatMessageList;

    ResponseChatRoom chatRoom;

    HttpClient httpClient;
    WebSocketClient webSocketClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);

        httpClient = new HttpClient(activity.authStorage);

        httpClient.getChatMessagesByChatRoomId(chatRoom.id, res -> {
            chatMessageList = new ChatMessageList(activity, rootView, res.body());
            setSendBtnListener();

            webSocketClient = new WebSocketClient(activity, chatMessageList, chatRoom);
            webSocketClient.connect();
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        chatMessageList.clearList();
        webSocketClient.disconnect();
    }

    private void setSendBtnListener() {
        EditText editText = rootView.findViewById(R.id.et_chatting);
        Button sendBtn = rootView.findViewById(R.id.btn_send);

        sendBtn.setOnClickListener(view -> {
            webSocketClient.send(editText.getText().toString());
        });
    }

    public void setChatRoom(ResponseChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
