package com.example.cloverchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.cloverchatapp.fragment.IndexFragment;
import com.example.cloverchatapp.fragment.ChatRoomDetailFragment;
import com.example.cloverchatapp.fragment.MainFragment;
import com.example.cloverchatapp.fragment.TestFragment;
import com.example.cloverchatapp.fragment.ChatRoomCreateFragment;

public class MainActivity extends AppCompatActivity {

    IndexFragment indexFragment;
    ChatRoomCreateFragment chatRoomCreateFragment;
    ChatRoomDetailFragment chatRoomDetailFragment;
    TestFragment testFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
    }

    public void navigate(MainFragment fragment) {
        switch (fragment) {
            case INDEX:
                navigate(indexFragment); break;
            case CHAT_ROOM_CREATE:
                navigate(chatRoomCreateFragment); break;
            case CHAT_ROOM_DETAIL:
                navigate(chatRoomDetailFragment); break;
            case TEST:
                navigate(testFragment); break;
        }
    }

    private void navigate(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.action_container, fragment)
                .commit();
    }

    private void initFragments() {
        FragmentManager fm = getSupportFragmentManager();
        indexFragment = (IndexFragment) fm.findFragmentById(R.id.mainFragment);

        chatRoomCreateFragment = new ChatRoomCreateFragment();
        chatRoomDetailFragment = new ChatRoomDetailFragment();
        testFragment = new TestFragment();
    }
}