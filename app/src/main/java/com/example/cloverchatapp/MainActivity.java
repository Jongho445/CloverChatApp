package com.example.cloverchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cloverchatapp.page.FragmentEnum;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.util.Navigator;
import com.example.cloverchatapp.util.TestHelper;

public class MainActivity extends AppCompatActivity {

    public GlobalContext global = new GlobalContext();
    public Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.navigator = new Navigator(this);

//        if (BuildConfig.BUILD_TYPE == "debug") {
//            TestHelper testHelper = new TestHelper(this);
//            testHelper.login(this::init);
//        } else {
//            init();
//        }
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.chatUsersBtn).setVisible(false);
        global.menu = menu;

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
        switch (global.mainFragment) {
            case CHAT_ROOM_LIST:
            case SIGN_IN:
                super.onBackPressed(); break;
            case SIGN_UP:
                navigator.navigate(FragmentEnum.SIGN_IN); break;
            case CHAT_USER_LIST:
                navigator.navigate(FragmentEnum.CHAT_ROOM_DETAIL); break;
            case CHAT_ROOM_CREATE:
            case CHAT_ROOM_DETAIL:
                navigator.navigate(FragmentEnum.CHAT_ROOM_LIST); break;
        }
    }

    private void init() {
        if (global.auth.myInfo == null) {
            setTitle("?????????");
            navigator.goToLogin();
        } else {
            setTitle("????????? ?????????");
            navigator.goToIndex();
        }
    }

    public void setTitle(FragmentEnum fragment) {
        switch (fragment) {
            case CHAT_ROOM_LIST:
                setTitle("????????? ?????????"); break;
            case CHAT_ROOM_CREATE:
                setTitle("????????? ?????????"); break;
            case CHAT_ROOM_DETAIL:
                setTitle("?????????: " + global.chat.curChatRoom.title); break;
            case CHAT_USER_LIST:
                setTitle("?????? ??????: " + global.chat.curChatRoom.title); break;
            case SIGN_IN:
                setTitle("?????????"); break;
            case SIGN_UP:
                setTitle("????????????"); break;
        }
    }
}