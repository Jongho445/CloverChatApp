package com.example.cloverchatapp.page.chat.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.cloverchatapp.util.DialogRenderer;
import com.example.cloverchatapp.web.domain.chat.RequestStompChatMessage;
import com.example.cloverchatapp.web.http.chat.ChatHttpClient;
import com.example.cloverchatapp.web.websocket.ChatMessageSession;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.example.cloverchatapp.web.websocket.ChatUserSession;
import com.google.gson.Gson;

import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatRoomDetailFragment extends Fragment {

    private MainActivity activity;
    private ViewGroup rootView;

    private EditText editText;
    private Button sendBtn;

    private ChatMessageRecyclerViewHolder rvHolder;
    private GlobalContext global;

    private ChatHttpClient chatHttpClient;
    private WebSocketSessionContext ws;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initFields(inflater, container);

        setChatUserBtnToVisible();
        setSendBtnListener();

        initChatMessageSession();
        initChatUserSession();

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        clearInput();
    }

    private void initFields(LayoutInflater inflater, ViewGroup container) {
        this.activity = (MainActivity) getActivity();
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);

        this.editText = rootView.findViewById(R.id.et_chatting);
        this.sendBtn = rootView.findViewById(R.id.btn_send);

        this.global = activity.global;
        this.ws = activity.global.ws;

        this.chatHttpClient = new ChatHttpClient(global.auth);
        this.rvHolder = new ChatMessageRecyclerViewHolder(activity, rootView, global.chat.curChatMessages);
    }

    private void setChatUserBtnToVisible() {
        MenuItem menuItem = this.global.menu.findItem(R.id.chatUsersBtn);
        menuItem.setVisible(true);
    }

    private void clearInput() {
        editText.setText(null);
    }

    private void setSendBtnListener() {
        sendBtn.setOnClickListener(view -> {
            RequestStompChatMessage form = new RequestStompChatMessage(
                    global.chat.curChatRoom.id,
                    global.auth.myInfo.id,
                    editText.getText().toString()
            );

            chatHttpClient.createChatMessage(global.chat.curChatRoom.id, form, res -> {
                if (!res.isSuccessful()) {
                    DialogRenderer.showAlertDialog(activity, "에러입니다");
                    return;
                }

                ws.messageSession.sendChatMessage(res.body());
                editText.setText(null);
            });
        });
    }

    private void initChatMessageSession() {
        // 세션을 재연결할 필요가 없는 상황
        if (ws.messageSession != null && ws.messageSession.chatRoomId == global.chat.curChatRoom.id) {
            return;
        }

        // 다른 방의 세션이 이미 연결되어있는 상황
        if (ws.messageSession != null) {
            ws.messageSession.disconnect();
        }

        // 새로운 세션 생성 -> 연결
        ws.messageSession = new ChatMessageSession(activity, global.chat.curChatRoom.id);
        ws.messageSession.connect();

        // subscribe 리스너 등록
        ws.messageSession.subscribeMessage((StompMessage topicMessage) -> {
            ResponseStompChatMessage chatMsg = new Gson().fromJson(topicMessage.getPayload(), ResponseStompChatMessage.class);

            rvHolder.addItem(chatMsg);
        });
    }

    private void initChatUserSession() {
        // 세션을 재연결할 필요가 없는 상황
        if (ws.chatUserSession != null && ws.chatUserSession.chatRoomId == global.chat.curChatRoom.id) {
            return;
        }

        // 다른 방의 세션이 이미 연결되어있는 상황
        if (ws.chatUserSession != null) {
            ws.chatUserSession.disconnect();
        }

        // 새로운 세션 생성 -> 연결
        ws.chatUserSession = new ChatUserSession(activity, global.chat.curChatRoom.id);
        ws.chatUserSession.connect();
    }
}
