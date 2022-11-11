package com.example.cloverchatapp.page.board.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.cloverchatapp.util.DialogRenderer;
import com.example.cloverchatapp.web.domain.board.StompUpdateChatRoom;
import com.example.cloverchatapp.web.http.board.BoardHttpClient;
import com.example.cloverchatapp.web.websocket.ChatRoomSession;
import com.google.gson.Gson;

import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatRoomListFragment extends Fragment {

    private MainActivity activity;
    private ViewGroup rootView;
    private GlobalContext global;

    private ChatRoomRecyclerViewHolder rvHolder;

    private BoardHttpClient boardHttpClient;
    private WebSocketSessionContext ws;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initFields(inflater, container);

        setChatUserBtnToInvisible();
        setIndexToWriteBtn();

        getChatRoomList();

        return rootView;
    }

    private void initFields(LayoutInflater inflater, ViewGroup container) {
        this.rootView = (ViewGroup) inflater.inflate(R.layout.fragment_index, container, false);
        this.activity = (MainActivity) getActivity();

        this.global = activity.global;
        this.ws = global.ws;

        this.boardHttpClient = new BoardHttpClient(global.auth);
    }

    private void setChatUserBtnToInvisible() {
        MenuItem menuItem = this.global.menu.findItem(R.id.chatUsersBtn);
        menuItem.setVisible(false);
    }

    private void getChatRoomList() {
        boardHttpClient.getChatRoomList(res -> {
            if (!res.isSuccessful()) {
                DialogRenderer.showAlertDialog(activity, "에러입니다");
                return;
            }

            rvHolder = new ChatRoomRecyclerViewHolder(activity, rootView, res.body());

            disconnectSessions();
            initWebSocketSession(activity);
        });
    }

    private void disconnectSessions() {
        if (ws.messageSession != null) {
            ws.messageSession.disconnect();
            ws.messageSession = null;
        }

        if (ws.chatUserSession != null) {
            ws.chatUserSession.disconnect();
            ws.chatUserSession = null;
        }
    }

    private void initWebSocketSession(MainActivity activity) {
        if (ws.chatRoomSession != null) {
            return;
        }

        ws.chatRoomSession = new ChatRoomSession(activity);
        ws.chatRoomSession.connect();

        ws.chatRoomSession.subscribeChatRoom((StompMessage topicMessage) -> {
            StompUpdateChatRoom stompForm = new Gson().fromJson(topicMessage.getPayload(), StompUpdateChatRoom.class);

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

    private void setIndexToWriteBtn() {
        Button indexToCreateBtn = rootView.findViewById(R.id.indexToCreateBtn);
        indexToCreateBtn.setOnClickListener(view -> {
            rvHolder.clearList();
            activity.navigator.navigate(FragmentEnum.CHAT_ROOM_CREATE);
        });
    }
}