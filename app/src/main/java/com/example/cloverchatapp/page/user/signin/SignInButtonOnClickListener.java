package com.example.cloverchatapp.page.user.signin;

import android.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.page.FragmentEnum;
import com.example.cloverchatapp.global.GlobalContext;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;
import com.example.cloverchatapp.web.http.user.UserHttpClient;

public class SignInButtonOnClickListener implements View.OnClickListener {

    private final MainActivity activity;
    private final GlobalContext global;

    private final EditText editId;
    private final EditText editPassword;

    private final UserHttpClient userHttpClient;

    public SignInButtonOnClickListener(MainActivity activity, EditText editId, EditText editPassword) {
        this.activity = activity;
        this.global = activity.global;
        this.editId = editId;
        this.editPassword = editPassword;

        this.userHttpClient = new UserHttpClient(global.auth);
    }

    @Override
    public void onClick(View view) {
        RequestLoginForm requestLoginForm = new RequestLoginForm(editId.getText().toString(), editPassword.getText().toString());
        userHttpClient.login(requestLoginForm, res -> {
            if (!res.isSuccessful()) {
                showAlertDialog("회원 정보가 잘못되었습니다.");
                return;
            }

            global.auth.storeData(res);
            activity.navigator.navigate(FragmentEnum.CHAT_ROOM_LIST);
        });
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage(message)
                .setPositiveButton("확인", null)
                .show();
    }
}
