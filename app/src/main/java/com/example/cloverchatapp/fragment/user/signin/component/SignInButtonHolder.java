package com.example.cloverchatapp.fragment.user.signin.component;

import android.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cloverchatapp.MainActivity;
import com.example.cloverchatapp.R;
import com.example.cloverchatapp.fragment.FragmentEnum;
import com.example.cloverchatapp.web.domain.user.RequestLoginForm;

public class SignInButtonHolder {

    private final MainActivity activity;
    private final EditText editId;
    private final EditText editPassword;

    public final Button targetButton;

    public SignInButtonHolder(MainActivity activity, ViewGroup rootView, EditText editId, EditText editPassword) {
        this.activity = activity;
        this.editId = editId;
        this.editPassword = editPassword;

        this.targetButton = rootView.findViewById(R.id.signInBtn);

        targetButton.setOnClickListener(view -> login());
    }

    private void login() {
        RequestLoginForm requestLoginForm = new RequestLoginForm(editId.getText().toString(), editPassword.getText().toString());
        activity.httpClient.login(requestLoginForm, res -> {
            if (!res.isSuccessful()) {
                showAlertDialog("회원 정보가 잘못되었습니다.");
                return;
            }

            activity.authStorage.storeData(res);
            activity.navigator.navigate(FragmentEnum.INDEX);
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
