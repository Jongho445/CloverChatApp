package com.example.cloverchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.util.AuthStorage;
import com.example.cloverchatapp.util.Navigator;
import com.example.cloverchatapp.util.TestHelper;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.web.client.WebSocketClient;
import com.example.cloverchatapp.web.domain.board.ResponseChatRoom;
import com.example.cloverchatapp.web.domain.chat.ResponseStompChatMessage;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public HttpClient httpClient;
    public WebSocketClient webSocketClient = null;

    public AuthStorage authStorage;
    public FragmentEnum mainFragment;
    public Navigator navigator;
    public Menu menu;

    public ResponseChatRoom curChatRoom;
    public List<ResponseStompChatMessage> curChatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigator = new Navigator(this);
        authStorage = new AuthStorage();
        httpClient = new HttpClient(authStorage);

        if (BuildConfig.BUILD_TYPE == "debug") {
            TestHelper testHelper = new TestHelper(this);
            testHelper.login(this::init);
        } else {
            init();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.chatUsersBtn).setVisible(false);
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myStatusBtn:
                System.out.println("Status");
                break;
            case R.id.chatUsersBtn:
                navigator.navigate(FragmentEnum.CHAT_USER_LIST);
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        switch (mainFragment) {
            case INDEX:
            case SIGN_IN:
                super.onBackPressed(); break;
            case SIGN_UP:
                navigator.navigate(FragmentEnum.SIGN_IN); break;
            case CHAT_USER_LIST:
                navigator.navigate(FragmentEnum.CHAT_ROOM_DETAIL); break;
            case CHAT_ROOM_CREATE:
            case CHAT_ROOM_DETAIL:
                navigator.navigate(FragmentEnum.INDEX); break;
        }
    }

    private void init() {
        if (authStorage.me == null) {
            setTitle("로그인");
            navigator.goToLogin();
        } else {
            setTitle("채팅방 리스트");
            navigator.goToIndex();
        }
    }

    public void setTitle(FragmentEnum fragment) {
        switch (fragment) {
            case INDEX:
                setTitle("채팅방 리스트"); break;
            case CHAT_ROOM_CREATE:
                setTitle("채팅방 만들기"); break;
            case CHAT_ROOM_DETAIL:
                setTitle("채팅방: " + curChatRoom.title); break;
            case CHAT_USER_LIST:
                setTitle("유저 목록: " + curChatRoom.title); break;
            case SIGN_IN:
                setTitle("로그인"); break;
            case SIGN_UP:
                setTitle("회원가입"); break;
        }
    }
}