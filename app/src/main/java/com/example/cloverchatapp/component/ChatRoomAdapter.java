package com.example.cloverchatapp.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.web.board.ResponseChatRoom;
import com.example.cloverchatapp.R;

import java.util.List;

public class ChatRoomAdapter extends BaseAdapter {

    MainActivity activity;
    Context context;
    List<ResponseChatRoom> list;

    LayoutInflater inflater;

    public ChatRoomAdapter(MainActivity activity, Context context, List<ResponseChatRoom> list) {
        this.activity = activity;
        this.context = context;
        this.list = list;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(ResponseChatRoom post) {
        list.add(post);
    }
    public void remove(int position) {
        list.remove(position);
    }
    public void removeAll() {
        list.clear();
    }

    @Override public int getCount() {
        return list.size();
    }
    @Override public Object getItem(int position) {
        return list.get(position);
    }
    @Override public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.chat_room_list_item, parent, false);

        ChatRoomListItem chatRoomListItem = new ChatRoomListItem(list.get(position), position, this, list);

        chatRoomListItem.initFields(convertView);
        chatRoomListItem.setView();
        chatRoomListItem.setRemoveListener();
        chatRoomListItem.setOnClickListener(activity, convertView);

        return convertView;
    }
}
