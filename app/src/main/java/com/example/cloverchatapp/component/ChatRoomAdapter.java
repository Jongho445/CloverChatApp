package com.example.cloverchatapp.component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.client.AppClient;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.board.ResponseChatRoom;

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomViewHolder>  {

    private final List<ResponseChatRoom> itemList;
    private final MainActivity activity;
    private final AppClient httpClient;

    private static String PASSWORD = "1234";

    public ChatRoomAdapter(List<ResponseChatRoom> itemList, MainActivity activity) {
        this.itemList = itemList;
        this.activity = activity;
        this.httpClient = new AppClient();
    }

    @NonNull
    @Override
    public ChatRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.chat_room_list_item, parent, false);

        return new ChatRoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRoomViewHolder holder, int position) {
        ResponseChatRoom chatRoom = itemList.get(position);

        setOnClickListener(holder, chatRoom);
        setRemoveListener(holder, chatRoom, position);

        setViewText(holder, chatRoom);
    }
    private void setOnClickListener(ChatRoomViewHolder holder, ResponseChatRoom chatRoom) {
        holder.itemView.setOnClickListener(view -> {
            itemList.clear();
            activity.navigate(FragmentEnum.CHAT_ROOM_DETAIL, chatRoom);
        });
    }

    private void setRemoveListener(ChatRoomViewHolder holder, ResponseChatRoom chatRoom, int position) {
        holder.removeBtn.setOnClickListener(view -> {
            httpClient.deleteChatRoom(
                    chatRoom.id, PASSWORD,
                    res -> {
                        itemList.remove(position);
                        this.notifyDataSetChanged();
                    }, t -> {
                        System.out.println(t.getMessage());
                        t.printStackTrace();
                    }
            );
        });
    }

    private void setViewText(ChatRoomViewHolder holder, ResponseChatRoom chatRoom) {
        holder.chatRoomTitle.setText(chatRoom.title);
        holder.chatRoomCreateBy.setText(chatRoom.createUser.nickname);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
