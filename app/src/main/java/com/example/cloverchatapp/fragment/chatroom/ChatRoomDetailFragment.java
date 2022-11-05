package com.example.cloverchatapp.fragment.chatroom;

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
import com.example.cloverchatapp.web.domain.chat.RequestChatMessagesReadForm;

public class ChatRoomDetailFragment extends Fragment {

    private MainActivity activity;
    private ViewGroup rootView;

    private ChatMessageList chatMessageList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);

        activity.menu.findItem(R.id.chatUsersBtn).setVisible(true);

        RequestChatMessagesReadForm form = new RequestChatMessagesReadForm(activity.curChatRoom.id, activity.curChatRoom.type, "1234");

        activity.httpClient.getChatMessageList(form, res -> {
            chatMessageList = new ChatMessageList(activity, rootView, res.body());
            setSendBtnListener();

            if (activity.webSocketClient == null) {
                activity.webSocketClient = new WebSocketClient(activity, chatMessageList);
                activity.webSocketClient.connect();
            }
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
            activity.webSocketClient.send(editText.getText().toString());
        });
    }
}
