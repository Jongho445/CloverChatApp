package com.example.cloverchatapp.fragment.chat.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.websocket.ChatMessageSession;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;
import com.google.gson.Gson;

import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatRoomDetailFragment extends Fragment {

    private GlobalContext global;
    private ViewGroup rootView;

    private RecyclerView recyclerView;
    private ChatMessageRecyclerViewModel chatMessageRecyclerViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);
        this.global = activity.global;
        this.recyclerView = rootView.findViewById(R.id.rv_list);
        this.chatMessageRecyclerViewModel = new ChatMessageRecyclerViewModel(activity, recyclerView, global.chat.curChatMessages);

        setSendBtnListener();
        global.menu.findItem(R.id.chatUsersBtn).setVisible(true);

        if (global.ws.messageSession != null) {
            global.ws.messageSession.disconnect();
        }

        global.ws.messageSession = new ChatMessageSession(activity);
        global.ws.messageSession.connect();
        global.ws.messageSession.subscribeChatRoom((StompMessage topicMessage) -> {
            ResponseStompChatMessage chatMsg = new Gson().fromJson(topicMessage.getPayload(), ResponseStompChatMessage.class);

            chatMessageRecyclerViewModel.addItem(chatMsg);
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        chatMessageRecyclerViewModel.clearList();
    }

    private void setSendBtnListener() {
        EditText editText = rootView.findViewById(R.id.et_chatting);
        Button sendBtn = rootView.findViewById(R.id.btn_send);

        sendBtn.setOnClickListener(view -> {
            global.ws.messageSession.sendChatMessage(editText.getText().toString());
            editText.setText(null);
        });
    }
}
