package com.example.cloverchatapp.fragment.chat;

import android.content.Context;
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
import com.example.cloverchatapp.web.ResponseChatRoom;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomDetailFragment extends Fragment {

    MainActivity activity;

    RecyclerView rvList;
    ChatMessageAdapter adapter;
    List<ChatMessage> chatMessageList;

    ResponseChatRoom chatRoom;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_chat_room_detail, container, false);
        init(rootView);

        setSendBtnListener(rootView);

        return rootView;
    }

    private void setSendBtnListener(View rootView) {
        EditText editText = rootView.findViewById(R.id.et_chatting);
        Button sendBtn = rootView.findViewById(R.id.btn_send);
        sendBtn.setOnClickListener(view -> {
            chatMessageList.add(new ChatMessage("user2", editText.getText().toString(), "123"));
            adapter.notifyDataSetChanged();
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
