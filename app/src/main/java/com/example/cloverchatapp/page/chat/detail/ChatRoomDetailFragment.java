package com.example.cloverchatapp.page.chat.detail;

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
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.global.WebSocketSessionContext;
import com.example.cloverchatapp.page.chat.detail.recyclerview.ChatMessageRecyclerViewHolder;
import com.example.cloverchatapp.util.MethodType;
import com.example.cloverchatapp.web.domain.chat.RequestStompChatMessage;
import com.example.cloverchatapp.web.domain.chat.StompUpdateChatUser;
import com.example.cloverchatapp.web.http.chat.ChatHttpClient;
import com.example.cloverchatapp.web.websocket.ChatMessageSession;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.example.cloverchatapp.web.websocket.ChatUserSession;
import com.google.gson.Gson;

import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatRoomDetailFragment extends Fragment {

    private ViewGroup rootView;

    private ChatMessageRecyclerViewHolder rvHolder;
    private GlobalContext global;

    private ChatHttpClient chatHttpClient;
    private WebSocketSessionContext ws;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);
        this.global = activity.global;
        this.ws = activity.global.ws;
        this.chatHttpClient = new ChatHttpClient(global.auth);

        this.rvHolder = new ChatMessageRecyclerViewHolder(activity, rootView, global.chat.curChatMessages);
        this.global.menu.findItem(R.id.chatUsersBtn).setVisible(true);

        setSendBtnListener();
        initChatMessageSession(activity);
        initChatUserSession(activity);
        createChatUser();

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        rvHolder.clearList();
    }

    private void initChatMessageSession(MainActivity activity) {
        if (ws.messageSession != null) {
            ws.messageSession.disconnect();
        }

        ws.messageSession = new ChatMessageSession(activity);

        ws.messageSession.connect();
        ws.messageSession.subscribeMessage((StompMessage topicMessage) -> {
            ResponseStompChatMessage chatMsg = new Gson().fromJson(
                    topicMessage.getPayload(),
                    ResponseStompChatMessage.class
            );

            rvHolder.addItem(chatMsg);
        });
    }

    private void initChatUserSession(MainActivity activity) {
        if (ws.chatUserSession != null) {
            ws.chatUserSession.disconnect();
        }

        ws.chatUserSession = new ChatUserSession(activity);

        ws.chatUserSession.connect();
    }

    private void createChatUser() {
        chatHttpClient.createChatUser(global.chat.curChatRoom.id, res -> {
            if (!res.isSuccessful()) {
                return;
            }

            StompUpdateChatUser stompForm = new StompUpdateChatUser(MethodType.CREATE, res.body());
            global.ws.chatUserSession.sendChatUser(stompForm);
        });
    }

    private void setSendBtnListener() {
        EditText editText = rootView.findViewById(R.id.et_chatting);
        Button sendBtn = rootView.findViewById(R.id.btn_send);

        sendBtn.setOnClickListener(view -> {
            RequestStompChatMessage form = new RequestStompChatMessage(
                    global.chat.curChatRoom.id,
                    global.auth.myInfo.id,
                    editText.getText().toString()
            );

            chatHttpClient.createChatMessage(global.chat.curChatRoom.id, form, res -> {
                if (!res.isSuccessful()) {
                    return;
                }

                ws.messageSession.sendChatMessage(res.body());
                editText.setText(null);
            });
        });
    }
}
