package com.example.cloverchatapp.fragment.chatroom.detail;

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
import com.example.cloverchatapp.web.client.WebSocketClient;
import com.example.cloverchatapp.fragment.chatroom.detail.component.ChatMessageList;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.google.gson.Gson;

import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatRoomDetailFragment extends Fragment {

    private GlobalContext global;
    private ViewGroup rootView;

    private ChatMessageList chatMessageList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);
        global = activity.global;

        global.menu.findItem(R.id.chatUsersBtn).setVisible(true);

        chatMessageList = new ChatMessageList(activity, rootView, global.chat.curChatMessages);
        setSendBtnListener();

        if (global.ws != null) {
            global.ws.disconnect();
        }

        global.ws = new WebSocketClient(activity);
        global.ws.connect();
        global.ws.subscribeChatRoom((StompMessage topicMessage) -> {
            ResponseStompChatMessage chatMsg = new Gson().fromJson(topicMessage.getPayload(), ResponseStompChatMessage.class);

            chatMessageList.addItem(chatMsg);
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        chatMessageList.clearList();
    }

    private void setSendBtnListener() {
        EditText editText = rootView.findViewById(R.id.et_chatting);
        Button sendBtn = rootView.findViewById(R.id.btn_send);

        sendBtn.setOnClickListener(view -> {
            global.ws.send(editText.getText().toString());
            editText.setText(null);
        });
    }
}
