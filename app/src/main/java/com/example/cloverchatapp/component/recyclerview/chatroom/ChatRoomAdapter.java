package com.example.cloverchatapp.component.recyclerview.chatroom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;

import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomViewHolder>  {

    private final List<ResponseChatRoom> itemList;
    private final MainActivity activity;
    private final HttpClient httpClient;

    public ChatRoomAdapter(List<ResponseChatRoom> itemList, MainActivity activity) {
        this.itemList = itemList;
        this.activity = activity;
        this.httpClient = new HttpClient(activity.authStorage);
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

        setOnClickItemListener(holder, chatRoom);
        setOnClickRemoveBtnListener(holder, chatRoom, position);

        setViewText(holder, chatRoom);
    }

    private void setOnClickItemListener(ChatRoomViewHolder holder, ResponseChatRoom chatRoom) {
        holder.itemView.setOnClickListener(view -> {
            itemList.clear();
            activity.navigate(FragmentEnum.CHAT_ROOM_DETAIL, chatRoom);
        });
    }

    private void setOnClickRemoveBtnListener(ChatRoomViewHolder holder, ResponseChatRoom chatRoom, int position) {
        holder.removeBtn.setOnClickListener(view -> {
            showConfirmDialog((DialogInterface dialogInterface, int i) -> {
                requestDelete(chatRoom, position);
            });
        });
    }

    private void requestDelete(ResponseChatRoom chatRoom, int position) {
        httpClient.deleteChatRoom(chatRoom.id, res -> {
            if (!res.isSuccessful()) {
                showAlertDialog("채팅방 생성자가 아닙니다");
                return;
            }

            showAlertDialog("삭제되었습니다.");

            itemList.remove(position);
            this.notifyDataSetChanged();
        });
    }

    private void showConfirmDialog(DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage("정말로 지우시겠습니까?")
                .setPositiveButton("확인", listener)
                .setNegativeButton("취소", null)
                .show();
    }

    private void showAlertDialog(String msg) {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage(msg)
                .setPositiveButton("확인", null)
                .show();
    }

    private void setViewText(ChatRoomViewHolder holder, ResponseChatRoom chatRoom) {
        holder.chatRoomTitle.setText(chatRoom.title);
        holder.chatRoomCreateBy.setText(chatRoom.createUser.nickname);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
