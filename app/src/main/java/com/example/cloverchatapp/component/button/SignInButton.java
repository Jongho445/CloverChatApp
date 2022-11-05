package com.example.cloverchatapp.component.button;

import android.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.web.client.HttpClient;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;

public class SignInButton {

    private final MainActivity activity;
    private final EditText editId;
    private final EditText editPassword;

    private final HttpClient httpClient;

    public final Button targetButton;

    public SignInButton(MainActivity activity, ViewGroup rootView, EditText editId, EditText editPassword) {
        this.activity = activity;
        this.editId = editId;
        this.editPassword = editPassword;

        this.httpClient = new HttpClient(activity.authStorage);
        this.targetButton = rootView.findViewById(R.id.signInBtn);

        setOnClickListener();
    }

    private void setOnClickListener() {
        targetButton.setOnClickListener(view -> {
            login();
        });
    }

    private void login() {
        RequestLoginForm requestLoginForm = new RequestLoginForm(editId.getText().toString(), editPassword.getText().toString());
        httpClient.login(requestLoginForm, res -> {
            if (!res.isSuccessful()) {
                showAlertDialog("회원 정보가 잘못되었습니다.");
                return;
            }

            activity.authStorage.storeData(res);
            activity.navigate(FragmentEnum.INDEX);
        });
    }

    private void showAlertDialog(String msg) {
        new AlertDialog.Builder(activity)
                .setTitle("알림")
                .setMessage(msg)
                .setPositiveButton("확인", null)
                .show();
    }
}
