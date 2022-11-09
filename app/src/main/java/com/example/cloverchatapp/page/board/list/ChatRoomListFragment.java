package com.example.cloverchatapp.page.board.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.global.WebSocketSessionContext;
import com.example.cloverchatapp.page.FragmentEnum;
import com.example.cloverchatapp.page.board.list.recyclerview.ChatRoomRecyclerViewHolder;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.util.MethodType;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.board.StompUpdateChatRoom;
import com.example.cloverchatapp.web.domain.chat.ResponseChatUser;
import com.example.cloverchatapp.web.domain.chat.StompUpdateChatUser;
import com.example.cloverchatapp.web.http.board.BoardHttpClient;
import com.example.cloverchatapp.web.http.chat.ChatHttpClient;
import com.example.cloverchatapp.web.websocket.ChatRoomSession;
import com.google.gson.Gson;

import java.util.List;

import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatRoomListFragment extends Fragment {

    private MainActivity activity;
    private GlobalContext global;

    private ChatRoomRecyclerViewHolder rvHolder;

    private BoardHttpClient boardHttpClient;
    private ChatHttpClient chatHttpClient;
    private WebSocketSessionContext ws;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //(사용할 자원, 자원을 담을 곳, T/F)
        //메인에 직접 들어가면 True, 프래그먼트에 있으면 False
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_index, container, false);
        this.activity = (MainActivity) getActivity();
        this.global = activity.global;
        this.ws = global.ws;
        this.boardHttpClient = new BoardHttpClient(global.auth);
        this.chatHttpClient = new ChatHttpClient(global.auth);

        setIndexToWriteBtn(rootView);

        boardHttpClient.getChatRoomList(res -> {
            List<ResponseChatRoom> chatRooms = res.body();

            rvHolder = new ChatRoomRecyclerViewHolder(activity, rootView, chatRooms);

            if (ws.messageSession != null) {
                ws.messageSession.disconnect();
                ws.messageSession = null;
            }

            deleteChatUser();
            initWebSocketSession(activity);
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        global.menu.findItem(R.id.chatUsersBtn).setVisible(false);
    }

    private void deleteChatUser() {
        chatHttpClient.deleteChatUser(res -> {
            List<ResponseChatUser> deletedChatUsers = res.body();

            for (ResponseChatUser deletedChatUser : deletedChatUsers) {
                StompUpdateChatUser stompForm = new StompUpdateChatUser(MethodType.DELETE, deletedChatUser);
                global.ws.chatUserSession.sendChatChatUser(stompForm);
            }

            if (ws.chatUserSession != null) {
                ws.chatUserSession.disconnect();
                ws.chatUserSession = null;
            }
        });
    }

    private void initWebSocketSession(MainActivity activity) {
        if (ws.chatRoomSession != null) {
            ws.chatRoomSession.disconnect();
        }

        ws.chatRoomSession = new ChatRoomSession(activity);

        ws.chatRoomSession.connect();
        ws.chatRoomSession.subscribeChatRoom((StompMessage topicMessage) -> {
            StompUpdateChatRoom stompForm = new Gson().fromJson(
                    topicMessage.getPayload(),
                    StompUpdateChatRoom.class
            );

            switch (stompForm.type) {
                case CREATE:
                    rvHolder.addItem(stompForm.chatRoom); break;
                case UPDATE:
                    break;
                case DELETE:
                    rvHolder.removeItem(stompForm.chatRoom); break;
            }
        });
    }

    private void setIndexToWriteBtn(ViewGroup rootView) {
        Button indexToCreateBtn = rootView.findViewById(R.id.indexToCreateBtn);
        indexToCreateBtn.setOnClickListener(view -> {
            rvHolder.clearList();
            activity.navigator.navigate(FragmentEnum.CHAT_ROOM_CREATE);
        });
    }
}